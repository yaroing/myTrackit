package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.SuiviMission;
import com.yarolab.mytrackit.transfert.repository.SuiviMissionRepository;
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
 * Integration tests for the {@link SuiviMissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SuiviMissionResourceIT {

    private static final String DEFAULT_PROBLEME_CONSTATE = "AAAAAAAAAA";
    private static final String UPDATED_PROBLEME_CONSTATE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_RECOMMANDEE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_RECOMMANDEE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_ECHEANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ECHEANCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/suivi-missions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SuiviMissionRepository suiviMissionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuiviMissionMockMvc;

    private SuiviMission suiviMission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SuiviMission createEntity(EntityManager em) {
        SuiviMission suiviMission = new SuiviMission()
            .problemeConstate(DEFAULT_PROBLEME_CONSTATE)
            .actionRecommandee(DEFAULT_ACTION_RECOMMANDEE)
            .dateEcheance(DEFAULT_DATE_ECHEANCE);
        return suiviMission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SuiviMission createUpdatedEntity(EntityManager em) {
        SuiviMission suiviMission = new SuiviMission()
            .problemeConstate(UPDATED_PROBLEME_CONSTATE)
            .actionRecommandee(UPDATED_ACTION_RECOMMANDEE)
            .dateEcheance(UPDATED_DATE_ECHEANCE);
        return suiviMission;
    }

    @BeforeEach
    public void initTest() {
        suiviMission = createEntity(em);
    }

    @Test
    @Transactional
    void createSuiviMission() throws Exception {
        int databaseSizeBeforeCreate = suiviMissionRepository.findAll().size();
        // Create the SuiviMission
        restSuiviMissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suiviMission)))
            .andExpect(status().isCreated());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeCreate + 1);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(DEFAULT_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(DEFAULT_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(DEFAULT_DATE_ECHEANCE);
    }

    @Test
    @Transactional
    void createSuiviMissionWithExistingId() throws Exception {
        // Create the SuiviMission with an existing ID
        suiviMission.setId(1L);

        int databaseSizeBeforeCreate = suiviMissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuiviMissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suiviMission)))
            .andExpect(status().isBadRequest());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSuiviMissions() throws Exception {
        // Initialize the database
        suiviMissionRepository.saveAndFlush(suiviMission);

        // Get all the suiviMissionList
        restSuiviMissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suiviMission.getId().intValue())))
            .andExpect(jsonPath("$.[*].problemeConstate").value(hasItem(DEFAULT_PROBLEME_CONSTATE.toString())))
            .andExpect(jsonPath("$.[*].actionRecommandee").value(hasItem(DEFAULT_ACTION_RECOMMANDEE.toString())))
            .andExpect(jsonPath("$.[*].dateEcheance").value(hasItem(DEFAULT_DATE_ECHEANCE)));
    }

    @Test
    @Transactional
    void getSuiviMission() throws Exception {
        // Initialize the database
        suiviMissionRepository.saveAndFlush(suiviMission);

        // Get the suiviMission
        restSuiviMissionMockMvc
            .perform(get(ENTITY_API_URL_ID, suiviMission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suiviMission.getId().intValue()))
            .andExpect(jsonPath("$.problemeConstate").value(DEFAULT_PROBLEME_CONSTATE.toString()))
            .andExpect(jsonPath("$.actionRecommandee").value(DEFAULT_ACTION_RECOMMANDEE.toString()))
            .andExpect(jsonPath("$.dateEcheance").value(DEFAULT_DATE_ECHEANCE));
    }

    @Test
    @Transactional
    void getNonExistingSuiviMission() throws Exception {
        // Get the suiviMission
        restSuiviMissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSuiviMission() throws Exception {
        // Initialize the database
        suiviMissionRepository.saveAndFlush(suiviMission);

        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();

        // Update the suiviMission
        SuiviMission updatedSuiviMission = suiviMissionRepository.findById(suiviMission.getId()).get();
        // Disconnect from session so that the updates on updatedSuiviMission are not directly saved in db
        em.detach(updatedSuiviMission);
        updatedSuiviMission
            .problemeConstate(UPDATED_PROBLEME_CONSTATE)
            .actionRecommandee(UPDATED_ACTION_RECOMMANDEE)
            .dateEcheance(UPDATED_DATE_ECHEANCE);

        restSuiviMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSuiviMission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSuiviMission))
            )
            .andExpect(status().isOk());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(UPDATED_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(UPDATED_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    @Transactional
    void putNonExistingSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();
        suiviMission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuiviMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, suiviMission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suiviMission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuiviMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suiviMission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuiviMissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suiviMission)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSuiviMissionWithPatch() throws Exception {
        // Initialize the database
        suiviMissionRepository.saveAndFlush(suiviMission);

        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();

        // Update the suiviMission using partial update
        SuiviMission partialUpdatedSuiviMission = new SuiviMission();
        partialUpdatedSuiviMission.setId(suiviMission.getId());

        partialUpdatedSuiviMission.actionRecommandee(UPDATED_ACTION_RECOMMANDEE).dateEcheance(UPDATED_DATE_ECHEANCE);

        restSuiviMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSuiviMission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSuiviMission))
            )
            .andExpect(status().isOk());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(DEFAULT_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(UPDATED_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    @Transactional
    void fullUpdateSuiviMissionWithPatch() throws Exception {
        // Initialize the database
        suiviMissionRepository.saveAndFlush(suiviMission);

        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();

        // Update the suiviMission using partial update
        SuiviMission partialUpdatedSuiviMission = new SuiviMission();
        partialUpdatedSuiviMission.setId(suiviMission.getId());

        partialUpdatedSuiviMission
            .problemeConstate(UPDATED_PROBLEME_CONSTATE)
            .actionRecommandee(UPDATED_ACTION_RECOMMANDEE)
            .dateEcheance(UPDATED_DATE_ECHEANCE);

        restSuiviMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSuiviMission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSuiviMission))
            )
            .andExpect(status().isOk());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(UPDATED_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(UPDATED_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    @Transactional
    void patchNonExistingSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();
        suiviMission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuiviMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, suiviMission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suiviMission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuiviMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suiviMission))
            )
            .andExpect(status().isBadRequest());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuiviMissionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(suiviMission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSuiviMission() throws Exception {
        // Initialize the database
        suiviMissionRepository.saveAndFlush(suiviMission);

        int databaseSizeBeforeDelete = suiviMissionRepository.findAll().size();

        // Delete the suiviMission
        restSuiviMissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, suiviMission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
