package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.PointFocalPointService;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.PointFocalPointServiceRepository;
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

/**
 * Integration tests for the {@link PointFocalPointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PointFocalPointServiceResourceIT {

    private static final String DEFAULT_NOM_PF = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_PF = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_PF = "BBBBBBBBBB";

    private static final String DEFAULT_GSM_PF = "AAAAAAAAAA";
    private static final String UPDATED_GSM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PF = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-focal-point-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointFocalPointServiceRepository pointFocalPointServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private PointFocalPointService pointFocalPointService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPointService createEntity(EntityManager em) {
        PointFocalPointService pointFocalPointService = new PointFocalPointService()
            .nomPf(DEFAULT_NOM_PF)
            .fonctionPf(DEFAULT_FONCTION_PF)
            .gsmPf(DEFAULT_GSM_PF)
            .emailPf(DEFAULT_EMAIL_PF);
        return pointFocalPointService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPointService createUpdatedEntity(EntityManager em) {
        PointFocalPointService pointFocalPointService = new PointFocalPointService()
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);
        return pointFocalPointService;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(PointFocalPointService.class).block();
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
        pointFocalPointService = createEntity(em);
    }

    @Test
    void createPointFocalPointService() throws Exception {
        int databaseSizeBeforeCreate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        // Create the PointFocalPointService
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(DEFAULT_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    void createPointFocalPointServiceWithExistingId() throws Exception {
        // Create the PointFocalPointService with an existing ID
        pointFocalPointService.setId(1L);

        int databaseSizeBeforeCreate = pointFocalPointServiceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPointFocalPointServicesAsStream() {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        List<PointFocalPointService> pointFocalPointServiceList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(PointFocalPointService.class)
            .getResponseBody()
            .filter(pointFocalPointService::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(pointFocalPointServiceList).isNotNull();
        assertThat(pointFocalPointServiceList).hasSize(1);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(0);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(DEFAULT_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    void getAllPointFocalPointServices() {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        // Get all the pointFocalPointServiceList
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
            .value(hasItem(pointFocalPointService.getId().intValue()))
            .jsonPath("$.[*].nomPf")
            .value(hasItem(DEFAULT_NOM_PF))
            .jsonPath("$.[*].fonctionPf")
            .value(hasItem(DEFAULT_FONCTION_PF))
            .jsonPath("$.[*].gsmPf")
            .value(hasItem(DEFAULT_GSM_PF))
            .jsonPath("$.[*].emailPf")
            .value(hasItem(DEFAULT_EMAIL_PF));
    }

    @Test
    void getPointFocalPointService() {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        // Get the pointFocalPointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, pointFocalPointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(pointFocalPointService.getId().intValue()))
            .jsonPath("$.nomPf")
            .value(is(DEFAULT_NOM_PF))
            .jsonPath("$.fonctionPf")
            .value(is(DEFAULT_FONCTION_PF))
            .jsonPath("$.gsmPf")
            .value(is(DEFAULT_GSM_PF))
            .jsonPath("$.emailPf")
            .value(is(DEFAULT_EMAIL_PF));
    }

    @Test
    void getNonExistingPointFocalPointService() {
        // Get the pointFocalPointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPointFocalPointService() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();

        // Update the pointFocalPointService
        PointFocalPointService updatedPointFocalPointService = pointFocalPointServiceRepository
            .findById(pointFocalPointService.getId())
            .block();
        updatedPointFocalPointService.nomPf(UPDATED_NOM_PF).fonctionPf(UPDATED_FONCTION_PF).gsmPf(UPDATED_GSM_PF).emailPf(UPDATED_EMAIL_PF);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPointFocalPointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPointFocalPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    void putNonExistingPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, pointFocalPointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePointFocalPointServiceWithPatch() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();

        // Update the pointFocalPointService using partial update
        PointFocalPointService partialUpdatedPointFocalPointService = new PointFocalPointService();
        partialUpdatedPointFocalPointService.setId(pointFocalPointService.getId());

        partialUpdatedPointFocalPointService.gsmPf(UPDATED_GSM_PF).emailPf(UPDATED_EMAIL_PF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPointFocalPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    void fullUpdatePointFocalPointServiceWithPatch() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();

        // Update the pointFocalPointService using partial update
        PointFocalPointService partialUpdatedPointFocalPointService = new PointFocalPointService();
        partialUpdatedPointFocalPointService.setId(pointFocalPointService.getId());

        partialUpdatedPointFocalPointService
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPointFocalPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    void patchNonExistingPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, pointFocalPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().collectList().block().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePointFocalPointService() {
        // Initialize the database
        pointFocalPointServiceRepository.save(pointFocalPointService).block();

        int databaseSizeBeforeDelete = pointFocalPointServiceRepository.findAll().collectList().block().size();

        // Delete the pointFocalPointService
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, pointFocalPointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll().collectList().block();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
