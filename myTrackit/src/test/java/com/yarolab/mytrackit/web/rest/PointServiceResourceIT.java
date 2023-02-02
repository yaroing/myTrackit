package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.PointService;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.PointServiceRepository;
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
 * Integration tests for the {@link PointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PointServiceResourceIT {

    private static final String DEFAULT_NOM_POS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_POS = "BBBBBBBBBB";

    private static final Double DEFAULT_POS_LON = 1D;
    private static final Double UPDATED_POS_LON = 2D;

    private static final Double DEFAULT_POS_LAT = 1D;
    private static final Double UPDATED_POS_LAT = 2D;

    private static final String DEFAULT_POS_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_POS_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_POS_GSM = "AAAAAAAAAA";
    private static final String UPDATED_POS_GSM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointServiceRepository pointServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private PointService pointService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointService createEntity(EntityManager em) {
        PointService pointService = new PointService()
            .nomPos(DEFAULT_NOM_POS)
            .posLon(DEFAULT_POS_LON)
            .posLat(DEFAULT_POS_LAT)
            .posContact(DEFAULT_POS_CONTACT)
            .posGsm(DEFAULT_POS_GSM);
        return pointService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointService createUpdatedEntity(EntityManager em) {
        PointService pointService = new PointService()
            .nomPos(UPDATED_NOM_POS)
            .posLon(UPDATED_POS_LON)
            .posLat(UPDATED_POS_LAT)
            .posContact(UPDATED_POS_CONTACT)
            .posGsm(UPDATED_POS_GSM);
        return pointService;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(PointService.class).block();
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
        pointService = createEntity(em);
    }

    @Test
    void createPointService() throws Exception {
        int databaseSizeBeforeCreate = pointServiceRepository.findAll().collectList().block().size();
        // Create the PointService
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(DEFAULT_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(DEFAULT_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(DEFAULT_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(DEFAULT_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(DEFAULT_POS_GSM);
    }

    @Test
    void createPointServiceWithExistingId() throws Exception {
        // Create the PointService with an existing ID
        pointService.setId(1L);

        int databaseSizeBeforeCreate = pointServiceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPointServicesAsStream() {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        List<PointService> pointServiceList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(PointService.class)
            .getResponseBody()
            .filter(pointService::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(pointServiceList).isNotNull();
        assertThat(pointServiceList).hasSize(1);
        PointService testPointService = pointServiceList.get(0);
        assertThat(testPointService.getNomPos()).isEqualTo(DEFAULT_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(DEFAULT_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(DEFAULT_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(DEFAULT_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(DEFAULT_POS_GSM);
    }

    @Test
    void getAllPointServices() {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        // Get all the pointServiceList
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
            .value(hasItem(pointService.getId().intValue()))
            .jsonPath("$.[*].nomPos")
            .value(hasItem(DEFAULT_NOM_POS))
            .jsonPath("$.[*].posLon")
            .value(hasItem(DEFAULT_POS_LON.doubleValue()))
            .jsonPath("$.[*].posLat")
            .value(hasItem(DEFAULT_POS_LAT.doubleValue()))
            .jsonPath("$.[*].posContact")
            .value(hasItem(DEFAULT_POS_CONTACT))
            .jsonPath("$.[*].posGsm")
            .value(hasItem(DEFAULT_POS_GSM));
    }

    @Test
    void getPointService() {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        // Get the pointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, pointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(pointService.getId().intValue()))
            .jsonPath("$.nomPos")
            .value(is(DEFAULT_NOM_POS))
            .jsonPath("$.posLon")
            .value(is(DEFAULT_POS_LON.doubleValue()))
            .jsonPath("$.posLat")
            .value(is(DEFAULT_POS_LAT.doubleValue()))
            .jsonPath("$.posContact")
            .value(is(DEFAULT_POS_CONTACT))
            .jsonPath("$.posGsm")
            .value(is(DEFAULT_POS_GSM));
    }

    @Test
    void getNonExistingPointService() {
        // Get the pointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPointService() throws Exception {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();

        // Update the pointService
        PointService updatedPointService = pointServiceRepository.findById(pointService.getId()).block();
        updatedPointService
            .nomPos(UPDATED_NOM_POS)
            .posLon(UPDATED_POS_LON)
            .posLat(UPDATED_POS_LAT)
            .posContact(UPDATED_POS_CONTACT)
            .posGsm(UPDATED_POS_GSM);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(UPDATED_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(UPDATED_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(UPDATED_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(UPDATED_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(UPDATED_POS_GSM);
    }

    @Test
    void putNonExistingPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();
        pointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, pointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePointServiceWithPatch() throws Exception {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();

        // Update the pointService using partial update
        PointService partialUpdatedPointService = new PointService();
        partialUpdatedPointService.setId(pointService.getId());

        partialUpdatedPointService.nomPos(UPDATED_NOM_POS).posLat(UPDATED_POS_LAT).posContact(UPDATED_POS_CONTACT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(UPDATED_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(DEFAULT_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(UPDATED_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(UPDATED_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(DEFAULT_POS_GSM);
    }

    @Test
    void fullUpdatePointServiceWithPatch() throws Exception {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();

        // Update the pointService using partial update
        PointService partialUpdatedPointService = new PointService();
        partialUpdatedPointService.setId(pointService.getId());

        partialUpdatedPointService
            .nomPos(UPDATED_NOM_POS)
            .posLon(UPDATED_POS_LON)
            .posLat(UPDATED_POS_LAT)
            .posContact(UPDATED_POS_CONTACT)
            .posGsm(UPDATED_POS_GSM);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(UPDATED_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(UPDATED_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(UPDATED_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(UPDATED_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(UPDATED_POS_GSM);
    }

    @Test
    void patchNonExistingPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();
        pointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, pointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().collectList().block().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePointService() {
        // Initialize the database
        pointServiceRepository.save(pointService).block();

        int databaseSizeBeforeDelete = pointServiceRepository.findAll().collectList().block().size();

        // Delete the pointService
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, pointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<PointService> pointServiceList = pointServiceRepository.findAll().collectList().block();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
