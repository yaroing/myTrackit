package com.yarolab.mytrackit.partenaire.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.partenaire.IntegrationTest;
import com.yarolab.mytrackit.partenaire.domain.DetailsRequete;
import com.yarolab.mytrackit.partenaire.repository.DetailsRequeteRepository;
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
 * Integration tests for the {@link DetailsRequeteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailsRequeteResourceIT {

    private static final Double DEFAULT_QUANTITE_DEMANDEE = 1D;
    private static final Double UPDATED_QUANTITE_DEMANDEE = 2D;

    private static final Double DEFAULT_QUANTITE_APPROUVEE = 1D;
    private static final Double UPDATED_QUANTITE_APPROUVEE = 2D;

    private static final Double DEFAULT_QUANTITE_RECUE = 1D;
    private static final Double UPDATED_QUANTITE_RECUE = 2D;

    private static final String DEFAULT_ITEM_OBS = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/details-requetes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailsRequeteRepository detailsRequeteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailsRequeteMockMvc;

    private DetailsRequete detailsRequete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsRequete createEntity(EntityManager em) {
        DetailsRequete detailsRequete = new DetailsRequete()
            .quantiteDemandee(DEFAULT_QUANTITE_DEMANDEE)
            .quantiteApprouvee(DEFAULT_QUANTITE_APPROUVEE)
            .quantiteRecue(DEFAULT_QUANTITE_RECUE)
            .itemObs(DEFAULT_ITEM_OBS);
        return detailsRequete;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsRequete createUpdatedEntity(EntityManager em) {
        DetailsRequete detailsRequete = new DetailsRequete()
            .quantiteDemandee(UPDATED_QUANTITE_DEMANDEE)
            .quantiteApprouvee(UPDATED_QUANTITE_APPROUVEE)
            .quantiteRecue(UPDATED_QUANTITE_RECUE)
            .itemObs(UPDATED_ITEM_OBS);
        return detailsRequete;
    }

    @BeforeEach
    public void initTest() {
        detailsRequete = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailsRequete() throws Exception {
        int databaseSizeBeforeCreate = detailsRequeteRepository.findAll().size();
        // Create the DetailsRequete
        restDetailsRequeteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isCreated());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeCreate + 1);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(DEFAULT_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(DEFAULT_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(DEFAULT_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(DEFAULT_ITEM_OBS);
    }

    @Test
    @Transactional
    void createDetailsRequeteWithExistingId() throws Exception {
        // Create the DetailsRequete with an existing ID
        detailsRequete.setId(1L);

        int databaseSizeBeforeCreate = detailsRequeteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsRequeteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetailsRequetes() throws Exception {
        // Initialize the database
        detailsRequeteRepository.saveAndFlush(detailsRequete);

        // Get all the detailsRequeteList
        restDetailsRequeteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsRequete.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantiteDemandee").value(hasItem(DEFAULT_QUANTITE_DEMANDEE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteApprouvee").value(hasItem(DEFAULT_QUANTITE_APPROUVEE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteRecue").value(hasItem(DEFAULT_QUANTITE_RECUE.doubleValue())))
            .andExpect(jsonPath("$.[*].itemObs").value(hasItem(DEFAULT_ITEM_OBS.toString())));
    }

    @Test
    @Transactional
    void getDetailsRequete() throws Exception {
        // Initialize the database
        detailsRequeteRepository.saveAndFlush(detailsRequete);

        // Get the detailsRequete
        restDetailsRequeteMockMvc
            .perform(get(ENTITY_API_URL_ID, detailsRequete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailsRequete.getId().intValue()))
            .andExpect(jsonPath("$.quantiteDemandee").value(DEFAULT_QUANTITE_DEMANDEE.doubleValue()))
            .andExpect(jsonPath("$.quantiteApprouvee").value(DEFAULT_QUANTITE_APPROUVEE.doubleValue()))
            .andExpect(jsonPath("$.quantiteRecue").value(DEFAULT_QUANTITE_RECUE.doubleValue()))
            .andExpect(jsonPath("$.itemObs").value(DEFAULT_ITEM_OBS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDetailsRequete() throws Exception {
        // Get the detailsRequete
        restDetailsRequeteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetailsRequete() throws Exception {
        // Initialize the database
        detailsRequeteRepository.saveAndFlush(detailsRequete);

        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();

        // Update the detailsRequete
        DetailsRequete updatedDetailsRequete = detailsRequeteRepository.findById(detailsRequete.getId()).get();
        // Disconnect from session so that the updates on updatedDetailsRequete are not directly saved in db
        em.detach(updatedDetailsRequete);
        updatedDetailsRequete
            .quantiteDemandee(UPDATED_QUANTITE_DEMANDEE)
            .quantiteApprouvee(UPDATED_QUANTITE_APPROUVEE)
            .quantiteRecue(UPDATED_QUANTITE_RECUE)
            .itemObs(UPDATED_ITEM_OBS);

        restDetailsRequeteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailsRequete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailsRequete))
            )
            .andExpect(status().isOk());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(UPDATED_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(UPDATED_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(UPDATED_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(UPDATED_ITEM_OBS);
    }

    @Test
    @Transactional
    void putNonExistingDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();
        detailsRequete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsRequeteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailsRequete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsRequeteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsRequeteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsRequete)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailsRequeteWithPatch() throws Exception {
        // Initialize the database
        detailsRequeteRepository.saveAndFlush(detailsRequete);

        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();

        // Update the detailsRequete using partial update
        DetailsRequete partialUpdatedDetailsRequete = new DetailsRequete();
        partialUpdatedDetailsRequete.setId(detailsRequete.getId());

        partialUpdatedDetailsRequete.itemObs(UPDATED_ITEM_OBS);

        restDetailsRequeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailsRequete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsRequete))
            )
            .andExpect(status().isOk());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(DEFAULT_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(DEFAULT_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(DEFAULT_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(UPDATED_ITEM_OBS);
    }

    @Test
    @Transactional
    void fullUpdateDetailsRequeteWithPatch() throws Exception {
        // Initialize the database
        detailsRequeteRepository.saveAndFlush(detailsRequete);

        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();

        // Update the detailsRequete using partial update
        DetailsRequete partialUpdatedDetailsRequete = new DetailsRequete();
        partialUpdatedDetailsRequete.setId(detailsRequete.getId());

        partialUpdatedDetailsRequete
            .quantiteDemandee(UPDATED_QUANTITE_DEMANDEE)
            .quantiteApprouvee(UPDATED_QUANTITE_APPROUVEE)
            .quantiteRecue(UPDATED_QUANTITE_RECUE)
            .itemObs(UPDATED_ITEM_OBS);

        restDetailsRequeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailsRequete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsRequete))
            )
            .andExpect(status().isOk());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(UPDATED_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(UPDATED_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(UPDATED_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(UPDATED_ITEM_OBS);
    }

    @Test
    @Transactional
    void patchNonExistingDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();
        detailsRequete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsRequeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailsRequete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsRequeteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsRequeteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailsRequete))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailsRequete() throws Exception {
        // Initialize the database
        detailsRequeteRepository.saveAndFlush(detailsRequete);

        int databaseSizeBeforeDelete = detailsRequeteRepository.findAll().size();

        // Delete the detailsRequete
        restDetailsRequeteMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailsRequete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
