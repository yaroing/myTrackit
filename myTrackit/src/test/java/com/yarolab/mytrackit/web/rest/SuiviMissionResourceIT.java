package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.SuiviMission;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.SuiviMissionRepository;
import java.time.Duration;
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
 * Integration tests for the {@link SuiviMissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SuiviMission.class).block();
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
        suiviMission = createEntity(em);
    }

    @Test
    void createSuiviMission() throws Exception {
        int databaseSizeBeforeCreate = suiviMissionRepository.findAll().collectList().block().size();
        // Create the SuiviMission
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeCreate + 1);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(DEFAULT_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(DEFAULT_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(DEFAULT_DATE_ECHEANCE);
    }

    @Test
    void createSuiviMissionWithExistingId() throws Exception {
        // Create the SuiviMission with an existing ID
        suiviMission.setId(1L);

        int databaseSizeBeforeCreate = suiviMissionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSuiviMissionsAsStream() {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        List<SuiviMission> suiviMissionList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(SuiviMission.class)
            .getResponseBody()
            .filter(suiviMission::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(suiviMissionList).isNotNull();
        assertThat(suiviMissionList).hasSize(1);
        SuiviMission testSuiviMission = suiviMissionList.get(0);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(DEFAULT_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(DEFAULT_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(DEFAULT_DATE_ECHEANCE);
    }

    @Test
    void getAllSuiviMissions() {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        // Get all the suiviMissionList
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
            .value(hasItem(suiviMission.getId().intValue()))
            .jsonPath("$.[*].problemeConstate")
            .value(hasItem(DEFAULT_PROBLEME_CONSTATE.toString()))
            .jsonPath("$.[*].actionRecommandee")
            .value(hasItem(DEFAULT_ACTION_RECOMMANDEE.toString()))
            .jsonPath("$.[*].dateEcheance")
            .value(hasItem(DEFAULT_DATE_ECHEANCE));
    }

    @Test
    void getSuiviMission() {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        // Get the suiviMission
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, suiviMission.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(suiviMission.getId().intValue()))
            .jsonPath("$.problemeConstate")
            .value(is(DEFAULT_PROBLEME_CONSTATE.toString()))
            .jsonPath("$.actionRecommandee")
            .value(is(DEFAULT_ACTION_RECOMMANDEE.toString()))
            .jsonPath("$.dateEcheance")
            .value(is(DEFAULT_DATE_ECHEANCE));
    }

    @Test
    void getNonExistingSuiviMission() {
        // Get the suiviMission
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSuiviMission() throws Exception {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();

        // Update the suiviMission
        SuiviMission updatedSuiviMission = suiviMissionRepository.findById(suiviMission.getId()).block();
        updatedSuiviMission
            .problemeConstate(UPDATED_PROBLEME_CONSTATE)
            .actionRecommandee(UPDATED_ACTION_RECOMMANDEE)
            .dateEcheance(UPDATED_DATE_ECHEANCE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedSuiviMission.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedSuiviMission))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(UPDATED_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(UPDATED_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    void putNonExistingSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();
        suiviMission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, suiviMission.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSuiviMissionWithPatch() throws Exception {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();

        // Update the suiviMission using partial update
        SuiviMission partialUpdatedSuiviMission = new SuiviMission();
        partialUpdatedSuiviMission.setId(suiviMission.getId());

        partialUpdatedSuiviMission.actionRecommandee(UPDATED_ACTION_RECOMMANDEE).dateEcheance(UPDATED_DATE_ECHEANCE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSuiviMission.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSuiviMission))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(DEFAULT_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(UPDATED_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    void fullUpdateSuiviMissionWithPatch() throws Exception {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();

        // Update the suiviMission using partial update
        SuiviMission partialUpdatedSuiviMission = new SuiviMission();
        partialUpdatedSuiviMission.setId(suiviMission.getId());

        partialUpdatedSuiviMission
            .problemeConstate(UPDATED_PROBLEME_CONSTATE)
            .actionRecommandee(UPDATED_ACTION_RECOMMANDEE)
            .dateEcheance(UPDATED_DATE_ECHEANCE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSuiviMission.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSuiviMission))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
        SuiviMission testSuiviMission = suiviMissionList.get(suiviMissionList.size() - 1);
        assertThat(testSuiviMission.getProblemeConstate()).isEqualTo(UPDATED_PROBLEME_CONSTATE);
        assertThat(testSuiviMission.getActionRecommandee()).isEqualTo(UPDATED_ACTION_RECOMMANDEE);
        assertThat(testSuiviMission.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    void patchNonExistingSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();
        suiviMission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, suiviMission.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSuiviMission() throws Exception {
        int databaseSizeBeforeUpdate = suiviMissionRepository.findAll().collectList().block().size();
        suiviMission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(suiviMission))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SuiviMission in the database
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSuiviMission() {
        // Initialize the database
        suiviMissionRepository.save(suiviMission).block();

        int databaseSizeBeforeDelete = suiviMissionRepository.findAll().collectList().block().size();

        // Delete the suiviMission
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, suiviMission.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SuiviMission> suiviMissionList = suiviMissionRepository.findAll().collectList().block();
        assertThat(suiviMissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
