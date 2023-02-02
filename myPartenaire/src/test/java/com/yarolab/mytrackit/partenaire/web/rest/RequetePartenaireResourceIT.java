package com.yarolab.mytrackit.partenaire.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.partenaire.IntegrationTest;
import com.yarolab.mytrackit.partenaire.domain.RequetePartenaire;
import com.yarolab.mytrackit.partenaire.repository.RequetePartenaireRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RequetePartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequetePartenaireResourceIT {

    private static final Instant DEFAULT_REQUETE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUETE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_FICHIER_ATACHE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_ATACHE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_ATACHE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_ATACHE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REQUETE_OBS = "AAAAAAAAAA";
    private static final String UPDATED_REQUETE_OBS = "BBBBBBBBBB";

    private static final Integer DEFAULT_REQ_TRAITEE = 1;
    private static final Integer UPDATED_REQ_TRAITEE = 2;

    private static final String ENTITY_API_URL = "/api/requete-partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequetePartenaireRepository requetePartenaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequetePartenaireMockMvc;

    private RequetePartenaire requetePartenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequetePartenaire createEntity(EntityManager em) {
        RequetePartenaire requetePartenaire = new RequetePartenaire()
            .requeteDate(DEFAULT_REQUETE_DATE)
            .fichierAtache(DEFAULT_FICHIER_ATACHE)
            .fichierAtacheContentType(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(DEFAULT_REQUETE_OBS)
            .reqTraitee(DEFAULT_REQ_TRAITEE);
        return requetePartenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequetePartenaire createUpdatedEntity(EntityManager em) {
        RequetePartenaire requetePartenaire = new RequetePartenaire()
            .requeteDate(UPDATED_REQUETE_DATE)
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);
        return requetePartenaire;
    }

    @BeforeEach
    public void initTest() {
        requetePartenaire = createEntity(em);
    }

    @Test
    @Transactional
    void createRequetePartenaire() throws Exception {
        int databaseSizeBeforeCreate = requetePartenaireRepository.findAll().size();
        // Create the RequetePartenaire
        restRequetePartenaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isCreated());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeCreate + 1);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(DEFAULT_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(DEFAULT_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(DEFAULT_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(DEFAULT_REQ_TRAITEE);
    }

    @Test
    @Transactional
    void createRequetePartenaireWithExistingId() throws Exception {
        // Create the RequetePartenaire with an existing ID
        requetePartenaire.setId(1L);

        int databaseSizeBeforeCreate = requetePartenaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequetePartenaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequetePartenaires() throws Exception {
        // Initialize the database
        requetePartenaireRepository.saveAndFlush(requetePartenaire);

        // Get all the requetePartenaireList
        restRequetePartenaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requetePartenaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].requeteDate").value(hasItem(DEFAULT_REQUETE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fichierAtacheContentType").value(hasItem(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichierAtache").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_ATACHE))))
            .andExpect(jsonPath("$.[*].requeteObs").value(hasItem(DEFAULT_REQUETE_OBS.toString())))
            .andExpect(jsonPath("$.[*].reqTraitee").value(hasItem(DEFAULT_REQ_TRAITEE)));
    }

    @Test
    @Transactional
    void getRequetePartenaire() throws Exception {
        // Initialize the database
        requetePartenaireRepository.saveAndFlush(requetePartenaire);

        // Get the requetePartenaire
        restRequetePartenaireMockMvc
            .perform(get(ENTITY_API_URL_ID, requetePartenaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requetePartenaire.getId().intValue()))
            .andExpect(jsonPath("$.requeteDate").value(DEFAULT_REQUETE_DATE.toString()))
            .andExpect(jsonPath("$.fichierAtacheContentType").value(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichierAtache").value(Base64Utils.encodeToString(DEFAULT_FICHIER_ATACHE)))
            .andExpect(jsonPath("$.requeteObs").value(DEFAULT_REQUETE_OBS.toString()))
            .andExpect(jsonPath("$.reqTraitee").value(DEFAULT_REQ_TRAITEE));
    }

    @Test
    @Transactional
    void getNonExistingRequetePartenaire() throws Exception {
        // Get the requetePartenaire
        restRequetePartenaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequetePartenaire() throws Exception {
        // Initialize the database
        requetePartenaireRepository.saveAndFlush(requetePartenaire);

        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();

        // Update the requetePartenaire
        RequetePartenaire updatedRequetePartenaire = requetePartenaireRepository.findById(requetePartenaire.getId()).get();
        // Disconnect from session so that the updates on updatedRequetePartenaire are not directly saved in db
        em.detach(updatedRequetePartenaire);
        updatedRequetePartenaire
            .requeteDate(UPDATED_REQUETE_DATE)
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);

        restRequetePartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequetePartenaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRequetePartenaire))
            )
            .andExpect(status().isOk());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(UPDATED_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(UPDATED_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(UPDATED_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(UPDATED_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
    }

    @Test
    @Transactional
    void putNonExistingRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequetePartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requetePartenaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePartenaireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequetePartenaireWithPatch() throws Exception {
        // Initialize the database
        requetePartenaireRepository.saveAndFlush(requetePartenaire);

        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();

        // Update the requetePartenaire using partial update
        RequetePartenaire partialUpdatedRequetePartenaire = new RequetePartenaire();
        partialUpdatedRequetePartenaire.setId(requetePartenaire.getId());

        partialUpdatedRequetePartenaire
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);

        restRequetePartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequetePartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePartenaire))
            )
            .andExpect(status().isOk());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(DEFAULT_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(UPDATED_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(UPDATED_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(UPDATED_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
    }

    @Test
    @Transactional
    void fullUpdateRequetePartenaireWithPatch() throws Exception {
        // Initialize the database
        requetePartenaireRepository.saveAndFlush(requetePartenaire);

        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();

        // Update the requetePartenaire using partial update
        RequetePartenaire partialUpdatedRequetePartenaire = new RequetePartenaire();
        partialUpdatedRequetePartenaire.setId(requetePartenaire.getId());

        partialUpdatedRequetePartenaire
            .requeteDate(UPDATED_REQUETE_DATE)
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);

        restRequetePartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequetePartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePartenaire))
            )
            .andExpect(status().isOk());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(UPDATED_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(UPDATED_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(UPDATED_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(UPDATED_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
    }

    @Test
    @Transactional
    void patchNonExistingRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequetePartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requetePartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequetePartenaire() throws Exception {
        // Initialize the database
        requetePartenaireRepository.saveAndFlush(requetePartenaire);

        int databaseSizeBeforeDelete = requetePartenaireRepository.findAll().size();

        // Delete the requetePartenaire
        restRequetePartenaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, requetePartenaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
