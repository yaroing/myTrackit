package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.Mission;
import com.yarolab.mytrackit.transfert.repository.MissionRepository;
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
 * Integration tests for the {@link MissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MissionResourceIT {

    private static final Instant DEFAULT_DATE_MISSION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MISSION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_RAPPORT_MISSION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RAPPORT_MISSION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RAPPORT_MISSION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RAPPORT_MISSION_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DEBUT_MISSION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBUT_MISSION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_MISSION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_MISSION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FIELD_10 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_10 = "BBBBBBBBBB";

    private static final String DEFAULT_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/missions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMissionMockMvc;

    private Mission mission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mission createEntity(EntityManager em) {
        Mission mission = new Mission()
            .dateMission(DEFAULT_DATE_MISSION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .rapportMission(DEFAULT_RAPPORT_MISSION)
            .rapportMissionContentType(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE)
            .debutMission(DEFAULT_DEBUT_MISSION)
            .finMission(DEFAULT_FIN_MISSION)
            .field10(DEFAULT_FIELD_10)
            .fin(DEFAULT_FIN);
        return mission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mission createUpdatedEntity(EntityManager em) {
        Mission mission = new Mission()
            .dateMission(UPDATED_DATE_MISSION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .rapportMission(UPDATED_RAPPORT_MISSION)
            .rapportMissionContentType(UPDATED_RAPPORT_MISSION_CONTENT_TYPE)
            .debutMission(UPDATED_DEBUT_MISSION)
            .finMission(UPDATED_FIN_MISSION)
            .field10(UPDATED_FIELD_10)
            .fin(UPDATED_FIN);
        return mission;
    }

    @BeforeEach
    public void initTest() {
        mission = createEntity(em);
    }

    @Test
    @Transactional
    void createMission() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();
        // Create the Mission
        restMissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isCreated());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate + 1);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getDateMission()).isEqualTo(DEFAULT_DATE_MISSION);
        assertThat(testMission.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testMission.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testMission.getRapportMission()).isEqualTo(DEFAULT_RAPPORT_MISSION);
        assertThat(testMission.getRapportMissionContentType()).isEqualTo(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE);
        assertThat(testMission.getDebutMission()).isEqualTo(DEFAULT_DEBUT_MISSION);
        assertThat(testMission.getFinMission()).isEqualTo(DEFAULT_FIN_MISSION);
        assertThat(testMission.getField10()).isEqualTo(DEFAULT_FIELD_10);
        assertThat(testMission.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    void createMissionWithExistingId() throws Exception {
        // Create the Mission with an existing ID
        mission.setId(1L);

        int databaseSizeBeforeCreate = missionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMissions() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList
        restMissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mission.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateMission").value(hasItem(DEFAULT_DATE_MISSION.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].rapportMissionContentType").value(hasItem(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rapportMission").value(hasItem(Base64Utils.encodeToString(DEFAULT_RAPPORT_MISSION))))
            .andExpect(jsonPath("$.[*].debutMission").value(hasItem(DEFAULT_DEBUT_MISSION.toString())))
            .andExpect(jsonPath("$.[*].finMission").value(hasItem(DEFAULT_FIN_MISSION.toString())))
            .andExpect(jsonPath("$.[*].field10").value(hasItem(DEFAULT_FIELD_10)))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN)));
    }

    @Test
    @Transactional
    void getMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get the mission
        restMissionMockMvc
            .perform(get(ENTITY_API_URL_ID, mission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mission.getId().intValue()))
            .andExpect(jsonPath("$.dateMission").value(DEFAULT_DATE_MISSION.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.rapportMissionContentType").value(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE))
            .andExpect(jsonPath("$.rapportMission").value(Base64Utils.encodeToString(DEFAULT_RAPPORT_MISSION)))
            .andExpect(jsonPath("$.debutMission").value(DEFAULT_DEBUT_MISSION.toString()))
            .andExpect(jsonPath("$.finMission").value(DEFAULT_FIN_MISSION.toString()))
            .andExpect(jsonPath("$.field10").value(DEFAULT_FIELD_10))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN));
    }

    @Test
    @Transactional
    void getNonExistingMission() throws Exception {
        // Get the mission
        restMissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission
        Mission updatedMission = missionRepository.findById(mission.getId()).get();
        // Disconnect from session so that the updates on updatedMission are not directly saved in db
        em.detach(updatedMission);
        updatedMission
            .dateMission(UPDATED_DATE_MISSION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .rapportMission(UPDATED_RAPPORT_MISSION)
            .rapportMissionContentType(UPDATED_RAPPORT_MISSION_CONTENT_TYPE)
            .debutMission(UPDATED_DEBUT_MISSION)
            .finMission(UPDATED_FIN_MISSION)
            .field10(UPDATED_FIELD_10)
            .fin(UPDATED_FIN);

        restMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMission))
            )
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getDateMission()).isEqualTo(UPDATED_DATE_MISSION);
        assertThat(testMission.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMission.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testMission.getRapportMission()).isEqualTo(UPDATED_RAPPORT_MISSION);
        assertThat(testMission.getRapportMissionContentType()).isEqualTo(UPDATED_RAPPORT_MISSION_CONTENT_TYPE);
        assertThat(testMission.getDebutMission()).isEqualTo(UPDATED_DEBUT_MISSION);
        assertThat(testMission.getFinMission()).isEqualTo(UPDATED_FIN_MISSION);
        assertThat(testMission.getField10()).isEqualTo(UPDATED_FIELD_10);
        assertThat(testMission.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void putNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();
        mission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMissionWithPatch() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission using partial update
        Mission partialUpdatedMission = new Mission();
        partialUpdatedMission.setId(mission.getId());

        partialUpdatedMission.dateDebut(UPDATED_DATE_DEBUT).field10(UPDATED_FIELD_10).fin(UPDATED_FIN);

        restMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMission))
            )
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getDateMission()).isEqualTo(DEFAULT_DATE_MISSION);
        assertThat(testMission.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMission.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testMission.getRapportMission()).isEqualTo(DEFAULT_RAPPORT_MISSION);
        assertThat(testMission.getRapportMissionContentType()).isEqualTo(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE);
        assertThat(testMission.getDebutMission()).isEqualTo(DEFAULT_DEBUT_MISSION);
        assertThat(testMission.getFinMission()).isEqualTo(DEFAULT_FIN_MISSION);
        assertThat(testMission.getField10()).isEqualTo(UPDATED_FIELD_10);
        assertThat(testMission.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void fullUpdateMissionWithPatch() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission using partial update
        Mission partialUpdatedMission = new Mission();
        partialUpdatedMission.setId(mission.getId());

        partialUpdatedMission
            .dateMission(UPDATED_DATE_MISSION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .rapportMission(UPDATED_RAPPORT_MISSION)
            .rapportMissionContentType(UPDATED_RAPPORT_MISSION_CONTENT_TYPE)
            .debutMission(UPDATED_DEBUT_MISSION)
            .finMission(UPDATED_FIN_MISSION)
            .field10(UPDATED_FIELD_10)
            .fin(UPDATED_FIN);

        restMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMission))
            )
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getDateMission()).isEqualTo(UPDATED_DATE_MISSION);
        assertThat(testMission.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMission.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testMission.getRapportMission()).isEqualTo(UPDATED_RAPPORT_MISSION);
        assertThat(testMission.getRapportMissionContentType()).isEqualTo(UPDATED_RAPPORT_MISSION_CONTENT_TYPE);
        assertThat(testMission.getDebutMission()).isEqualTo(UPDATED_DEBUT_MISSION);
        assertThat(testMission.getFinMission()).isEqualTo(UPDATED_FIN_MISSION);
        assertThat(testMission.getField10()).isEqualTo(UPDATED_FIELD_10);
        assertThat(testMission.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();
        mission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMissionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mission)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeDelete = missionRepository.findAll().size();

        // Delete the mission
        restMissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, mission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
