package com.yarolab.mytrackit.partenaire.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.partenaire.IntegrationTest;
import com.yarolab.mytrackit.partenaire.domain.PointFocalPartenaire;
import com.yarolab.mytrackit.partenaire.repository.PointFocalPartenaireRepository;
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

/**
 * Integration tests for the {@link PointFocalPartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointFocalPartenaireResourceIT {

    private static final String DEFAULT_NOM_PF = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_PF = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_PF = "BBBBBBBBBB";

    private static final String DEFAULT_GSM_PF = "AAAAAAAAAA";
    private static final String UPDATED_GSM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PF = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-focal-partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointFocalPartenaireRepository pointFocalPartenaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointFocalPartenaireMockMvc;

    private PointFocalPartenaire pointFocalPartenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPartenaire createEntity(EntityManager em) {
        PointFocalPartenaire pointFocalPartenaire = new PointFocalPartenaire()
            .nomPf(DEFAULT_NOM_PF)
            .fonctionPf(DEFAULT_FONCTION_PF)
            .gsmPf(DEFAULT_GSM_PF)
            .emailPf(DEFAULT_EMAIL_PF);
        return pointFocalPartenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPartenaire createUpdatedEntity(EntityManager em) {
        PointFocalPartenaire pointFocalPartenaire = new PointFocalPartenaire()
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);
        return pointFocalPartenaire;
    }

    @BeforeEach
    public void initTest() {
        pointFocalPartenaire = createEntity(em);
    }

    @Test
    @Transactional
    void createPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeCreate = pointFocalPartenaireRepository.findAll().size();
        // Create the PointFocalPartenaire
        restPointFocalPartenaireMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isCreated());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeCreate + 1);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(DEFAULT_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    @Transactional
    void createPointFocalPartenaireWithExistingId() throws Exception {
        // Create the PointFocalPartenaire with an existing ID
        pointFocalPartenaire.setId(1L);

        int databaseSizeBeforeCreate = pointFocalPartenaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointFocalPartenaireMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPointFocalPartenaires() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.saveAndFlush(pointFocalPartenaire);

        // Get all the pointFocalPartenaireList
        restPointFocalPartenaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointFocalPartenaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPf").value(hasItem(DEFAULT_NOM_PF)))
            .andExpect(jsonPath("$.[*].fonctionPf").value(hasItem(DEFAULT_FONCTION_PF)))
            .andExpect(jsonPath("$.[*].gsmPf").value(hasItem(DEFAULT_GSM_PF)))
            .andExpect(jsonPath("$.[*].emailPf").value(hasItem(DEFAULT_EMAIL_PF)));
    }

    @Test
    @Transactional
    void getPointFocalPartenaire() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.saveAndFlush(pointFocalPartenaire);

        // Get the pointFocalPartenaire
        restPointFocalPartenaireMockMvc
            .perform(get(ENTITY_API_URL_ID, pointFocalPartenaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointFocalPartenaire.getId().intValue()))
            .andExpect(jsonPath("$.nomPf").value(DEFAULT_NOM_PF))
            .andExpect(jsonPath("$.fonctionPf").value(DEFAULT_FONCTION_PF))
            .andExpect(jsonPath("$.gsmPf").value(DEFAULT_GSM_PF))
            .andExpect(jsonPath("$.emailPf").value(DEFAULT_EMAIL_PF));
    }

    @Test
    @Transactional
    void getNonExistingPointFocalPartenaire() throws Exception {
        // Get the pointFocalPartenaire
        restPointFocalPartenaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointFocalPartenaire() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.saveAndFlush(pointFocalPartenaire);

        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();

        // Update the pointFocalPartenaire
        PointFocalPartenaire updatedPointFocalPartenaire = pointFocalPartenaireRepository.findById(pointFocalPartenaire.getId()).get();
        // Disconnect from session so that the updates on updatedPointFocalPartenaire are not directly saved in db
        em.detach(updatedPointFocalPartenaire);
        updatedPointFocalPartenaire.nomPf(UPDATED_NOM_PF).fonctionPf(UPDATED_FONCTION_PF).gsmPf(UPDATED_GSM_PF).emailPf(UPDATED_EMAIL_PF);

        restPointFocalPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPointFocalPartenaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPointFocalPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    @Transactional
    void putNonExistingPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointFocalPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointFocalPartenaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointFocalPartenaireWithPatch() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.saveAndFlush(pointFocalPartenaire);

        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();

        // Update the pointFocalPartenaire using partial update
        PointFocalPartenaire partialUpdatedPointFocalPartenaire = new PointFocalPartenaire();
        partialUpdatedPointFocalPartenaire.setId(pointFocalPartenaire.getId());

        partialUpdatedPointFocalPartenaire.gsmPf(UPDATED_GSM_PF);

        restPointFocalPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointFocalPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    @Transactional
    void fullUpdatePointFocalPartenaireWithPatch() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.saveAndFlush(pointFocalPartenaire);

        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();

        // Update the pointFocalPartenaire using partial update
        PointFocalPartenaire partialUpdatedPointFocalPartenaire = new PointFocalPartenaire();
        partialUpdatedPointFocalPartenaire.setId(pointFocalPartenaire.getId());

        partialUpdatedPointFocalPartenaire
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);

        restPointFocalPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointFocalPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    @Transactional
    void patchNonExistingPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointFocalPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointFocalPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointFocalPartenaire() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.saveAndFlush(pointFocalPartenaire);

        int databaseSizeBeforeDelete = pointFocalPartenaireRepository.findAll().size();

        // Delete the pointFocalPartenaire
        restPointFocalPartenaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointFocalPartenaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
