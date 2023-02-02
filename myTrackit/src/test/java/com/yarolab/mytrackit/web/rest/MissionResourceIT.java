package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Mission;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.MissionRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Mission.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        mission = createEntity(em);
    }

    @Test
    void createMission() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().collectList().block().size();
        // Create the Mission
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
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
    void createMissionWithExistingId() throws Exception {
        // Create the Mission with an existing ID
        mission.setId(1L);

        int databaseSizeBeforeCreate = missionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMissionsAsStream() {
        // Initialize the database
        missionRepository.save(mission).block();

        List<Mission> missionList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Mission.class)
            .getResponseBody()
            .filter(mission::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(missionList).isNotNull();
        assertThat(missionList).hasSize(1);
        Mission testMission = missionList.get(0);
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
    void getAllMissions() {
        // Initialize the database
        missionRepository.save(mission).block();

        // Get all the missionList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(mission.getId().intValue()))
            .jsonPath("$.[*].dateMission")
            .value(hasItem(DEFAULT_DATE_MISSION.toString()))
            .jsonPath("$.[*].dateDebut")
            .value(hasItem(DEFAULT_DATE_DEBUT.toString()))
            .jsonPath("$.[*].dateFin")
            .value(hasItem(DEFAULT_DATE_FIN.toString()))
            .jsonPath("$.[*].rapportMissionContentType")
            .value(hasItem(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE))
            .jsonPath("$.[*].rapportMission")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_RAPPORT_MISSION)))
            .jsonPath("$.[*].debutMission")
            .value(hasItem(DEFAULT_DEBUT_MISSION.toString()))
            .jsonPath("$.[*].finMission")
            .value(hasItem(DEFAULT_FIN_MISSION.toString()))
            .jsonPath("$.[*].field10")
            .value(hasItem(DEFAULT_FIELD_10))
            .jsonPath("$.[*].fin")
            .value(hasItem(DEFAULT_FIN));
    }

    @Test
    void getMission() {
        // Initialize the database
        missionRepository.save(mission).block();

        // Get the mission
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, mission.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(mission.getId().intValue()))
            .jsonPath("$.dateMission")
            .value(is(DEFAULT_DATE_MISSION.toString()))
            .jsonPath("$.dateDebut")
            .value(is(DEFAULT_DATE_DEBUT.toString()))
            .jsonPath("$.dateFin")
            .value(is(DEFAULT_DATE_FIN.toString()))
            .jsonPath("$.rapportMissionContentType")
            .value(is(DEFAULT_RAPPORT_MISSION_CONTENT_TYPE))
            .jsonPath("$.rapportMission")
            .value(is(Base64Utils.encodeToString(DEFAULT_RAPPORT_MISSION)))
            .jsonPath("$.debutMission")
            .value(is(DEFAULT_DEBUT_MISSION.toString()))
            .jsonPath("$.finMission")
            .value(is(DEFAULT_FIN_MISSION.toString()))
            .jsonPath("$.field10")
            .value(is(DEFAULT_FIELD_10))
            .jsonPath("$.fin")
            .value(is(DEFAULT_FIN));
    }

    @Test
    void getNonExistingMission() {
        // Get the mission
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMission() throws Exception {
        // Initialize the database
        missionRepository.save(mission).block();

        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();

        // Update the mission
        Mission updatedMission = missionRepository.findById(mission.getId()).block();
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedMission.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedMission))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
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
    void putNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();
        mission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, mission.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMissionWithPatch() throws Exception {
        // Initialize the database
        missionRepository.save(mission).block();

        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();

        // Update the mission using partial update
        Mission partialUpdatedMission = new Mission();
        partialUpdatedMission.setId(mission.getId());

        partialUpdatedMission.dateDebut(UPDATED_DATE_DEBUT).field10(UPDATED_FIELD_10).fin(UPDATED_FIN);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMission.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMission))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
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
    void fullUpdateMissionWithPatch() throws Exception {
        // Initialize the database
        missionRepository.save(mission).block();

        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMission.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMission))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
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
    void patchNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();
        mission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, mission.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().collectList().block().size();
        mission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(mission))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMission() {
        // Initialize the database
        missionRepository.save(mission).block();

        int databaseSizeBeforeDelete = missionRepository.findAll().collectList().block().size();

        // Delete the mission
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, mission.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Mission> missionList = missionRepository.findAll().collectList().block();
        assertThat(missionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
