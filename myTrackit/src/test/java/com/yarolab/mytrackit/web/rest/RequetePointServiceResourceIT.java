package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.RequetePointService;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.RequetePointServiceRepository;
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

/**
 * Integration tests for the {@link RequetePointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RequetePointServiceResourceIT {

    private static final Double DEFAULT_STOCK_DISPONIBLE = 1D;
    private static final Double UPDATED_STOCK_DISPONIBLE = 2D;

    private static final Double DEFAULT_QUANT_DEM = 1D;
    private static final Double UPDATED_QUANT_DEM = 2D;

    private static final Double DEFAULT_QUANT_TRS = 1D;
    private static final Double UPDATED_QUANT_TRS = 2D;

    private static final Double DEFAULT_QUANT_REC = 1D;
    private static final Double UPDATED_QUANT_REC = 2D;

    private static final Integer DEFAULT_REQ_TRAITEE = 1;
    private static final Integer UPDATED_REQ_TRAITEE = 2;

    private static final Instant DEFAULT_DATE_REQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_REQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_REC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_REC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_TRANSFERT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TRANSFERT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/requete-point-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequetePointServiceRepository requetePointServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RequetePointService requetePointService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequetePointService createEntity(EntityManager em) {
        RequetePointService requetePointService = new RequetePointService()
            .stockDisponible(DEFAULT_STOCK_DISPONIBLE)
            .quantDem(DEFAULT_QUANT_DEM)
            .quantTrs(DEFAULT_QUANT_TRS)
            .quantRec(DEFAULT_QUANT_REC)
            .reqTraitee(DEFAULT_REQ_TRAITEE)
            .dateReq(DEFAULT_DATE_REQ)
            .dateRec(DEFAULT_DATE_REC)
            .dateTransfert(DEFAULT_DATE_TRANSFERT);
        return requetePointService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequetePointService createUpdatedEntity(EntityManager em) {
        RequetePointService requetePointService = new RequetePointService()
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .quantDem(UPDATED_QUANT_DEM)
            .quantTrs(UPDATED_QUANT_TRS)
            .quantRec(UPDATED_QUANT_REC)
            .reqTraitee(UPDATED_REQ_TRAITEE)
            .dateReq(UPDATED_DATE_REQ)
            .dateRec(UPDATED_DATE_REC)
            .dateTransfert(UPDATED_DATE_TRANSFERT);
        return requetePointService;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RequetePointService.class).block();
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
        requetePointService = createEntity(em);
    }

    @Test
    void createRequetePointService() throws Exception {
        int databaseSizeBeforeCreate = requetePointServiceRepository.findAll().collectList().block().size();
        // Create the RequetePointService
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeCreate + 1);
        RequetePointService testRequetePointService = requetePointServiceList.get(requetePointServiceList.size() - 1);
        assertThat(testRequetePointService.getStockDisponible()).isEqualTo(DEFAULT_STOCK_DISPONIBLE);
        assertThat(testRequetePointService.getQuantDem()).isEqualTo(DEFAULT_QUANT_DEM);
        assertThat(testRequetePointService.getQuantTrs()).isEqualTo(DEFAULT_QUANT_TRS);
        assertThat(testRequetePointService.getQuantRec()).isEqualTo(DEFAULT_QUANT_REC);
        assertThat(testRequetePointService.getReqTraitee()).isEqualTo(DEFAULT_REQ_TRAITEE);
        assertThat(testRequetePointService.getDateReq()).isEqualTo(DEFAULT_DATE_REQ);
        assertThat(testRequetePointService.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testRequetePointService.getDateTransfert()).isEqualTo(DEFAULT_DATE_TRANSFERT);
    }

    @Test
    void createRequetePointServiceWithExistingId() throws Exception {
        // Create the RequetePointService with an existing ID
        requetePointService.setId(1L);

        int databaseSizeBeforeCreate = requetePointServiceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRequetePointServicesAsStream() {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        List<RequetePointService> requetePointServiceList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(RequetePointService.class)
            .getResponseBody()
            .filter(requetePointService::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(requetePointServiceList).isNotNull();
        assertThat(requetePointServiceList).hasSize(1);
        RequetePointService testRequetePointService = requetePointServiceList.get(0);
        assertThat(testRequetePointService.getStockDisponible()).isEqualTo(DEFAULT_STOCK_DISPONIBLE);
        assertThat(testRequetePointService.getQuantDem()).isEqualTo(DEFAULT_QUANT_DEM);
        assertThat(testRequetePointService.getQuantTrs()).isEqualTo(DEFAULT_QUANT_TRS);
        assertThat(testRequetePointService.getQuantRec()).isEqualTo(DEFAULT_QUANT_REC);
        assertThat(testRequetePointService.getReqTraitee()).isEqualTo(DEFAULT_REQ_TRAITEE);
        assertThat(testRequetePointService.getDateReq()).isEqualTo(DEFAULT_DATE_REQ);
        assertThat(testRequetePointService.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testRequetePointService.getDateTransfert()).isEqualTo(DEFAULT_DATE_TRANSFERT);
    }

    @Test
    void getAllRequetePointServices() {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        // Get all the requetePointServiceList
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
            .value(hasItem(requetePointService.getId().intValue()))
            .jsonPath("$.[*].stockDisponible")
            .value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue()))
            .jsonPath("$.[*].quantDem")
            .value(hasItem(DEFAULT_QUANT_DEM.doubleValue()))
            .jsonPath("$.[*].quantTrs")
            .value(hasItem(DEFAULT_QUANT_TRS.doubleValue()))
            .jsonPath("$.[*].quantRec")
            .value(hasItem(DEFAULT_QUANT_REC.doubleValue()))
            .jsonPath("$.[*].reqTraitee")
            .value(hasItem(DEFAULT_REQ_TRAITEE))
            .jsonPath("$.[*].dateReq")
            .value(hasItem(DEFAULT_DATE_REQ.toString()))
            .jsonPath("$.[*].dateRec")
            .value(hasItem(DEFAULT_DATE_REC.toString()))
            .jsonPath("$.[*].dateTransfert")
            .value(hasItem(DEFAULT_DATE_TRANSFERT.toString()));
    }

    @Test
    void getRequetePointService() {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        // Get the requetePointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, requetePointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(requetePointService.getId().intValue()))
            .jsonPath("$.stockDisponible")
            .value(is(DEFAULT_STOCK_DISPONIBLE.doubleValue()))
            .jsonPath("$.quantDem")
            .value(is(DEFAULT_QUANT_DEM.doubleValue()))
            .jsonPath("$.quantTrs")
            .value(is(DEFAULT_QUANT_TRS.doubleValue()))
            .jsonPath("$.quantRec")
            .value(is(DEFAULT_QUANT_REC.doubleValue()))
            .jsonPath("$.reqTraitee")
            .value(is(DEFAULT_REQ_TRAITEE))
            .jsonPath("$.dateReq")
            .value(is(DEFAULT_DATE_REQ.toString()))
            .jsonPath("$.dateRec")
            .value(is(DEFAULT_DATE_REC.toString()))
            .jsonPath("$.dateTransfert")
            .value(is(DEFAULT_DATE_TRANSFERT.toString()));
    }

    @Test
    void getNonExistingRequetePointService() {
        // Get the requetePointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRequetePointService() throws Exception {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();

        // Update the requetePointService
        RequetePointService updatedRequetePointService = requetePointServiceRepository.findById(requetePointService.getId()).block();
        updatedRequetePointService
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .quantDem(UPDATED_QUANT_DEM)
            .quantTrs(UPDATED_QUANT_TRS)
            .quantRec(UPDATED_QUANT_REC)
            .reqTraitee(UPDATED_REQ_TRAITEE)
            .dateReq(UPDATED_DATE_REQ)
            .dateRec(UPDATED_DATE_REC)
            .dateTransfert(UPDATED_DATE_TRANSFERT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRequetePointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRequetePointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
        RequetePointService testRequetePointService = requetePointServiceList.get(requetePointServiceList.size() - 1);
        assertThat(testRequetePointService.getStockDisponible()).isEqualTo(UPDATED_STOCK_DISPONIBLE);
        assertThat(testRequetePointService.getQuantDem()).isEqualTo(UPDATED_QUANT_DEM);
        assertThat(testRequetePointService.getQuantTrs()).isEqualTo(UPDATED_QUANT_TRS);
        assertThat(testRequetePointService.getQuantRec()).isEqualTo(UPDATED_QUANT_REC);
        assertThat(testRequetePointService.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
        assertThat(testRequetePointService.getDateReq()).isEqualTo(UPDATED_DATE_REQ);
        assertThat(testRequetePointService.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testRequetePointService.getDateTransfert()).isEqualTo(UPDATED_DATE_TRANSFERT);
    }

    @Test
    void putNonExistingRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();
        requetePointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, requetePointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRequetePointServiceWithPatch() throws Exception {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();

        // Update the requetePointService using partial update
        RequetePointService partialUpdatedRequetePointService = new RequetePointService();
        partialUpdatedRequetePointService.setId(requetePointService.getId());

        partialUpdatedRequetePointService
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .quantDem(UPDATED_QUANT_DEM)
            .quantRec(UPDATED_QUANT_REC)
            .dateReq(UPDATED_DATE_REQ)
            .dateTransfert(UPDATED_DATE_TRANSFERT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRequetePointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
        RequetePointService testRequetePointService = requetePointServiceList.get(requetePointServiceList.size() - 1);
        assertThat(testRequetePointService.getStockDisponible()).isEqualTo(UPDATED_STOCK_DISPONIBLE);
        assertThat(testRequetePointService.getQuantDem()).isEqualTo(UPDATED_QUANT_DEM);
        assertThat(testRequetePointService.getQuantTrs()).isEqualTo(DEFAULT_QUANT_TRS);
        assertThat(testRequetePointService.getQuantRec()).isEqualTo(UPDATED_QUANT_REC);
        assertThat(testRequetePointService.getReqTraitee()).isEqualTo(DEFAULT_REQ_TRAITEE);
        assertThat(testRequetePointService.getDateReq()).isEqualTo(UPDATED_DATE_REQ);
        assertThat(testRequetePointService.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testRequetePointService.getDateTransfert()).isEqualTo(UPDATED_DATE_TRANSFERT);
    }

    @Test
    void fullUpdateRequetePointServiceWithPatch() throws Exception {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();

        // Update the requetePointService using partial update
        RequetePointService partialUpdatedRequetePointService = new RequetePointService();
        partialUpdatedRequetePointService.setId(requetePointService.getId());

        partialUpdatedRequetePointService
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .quantDem(UPDATED_QUANT_DEM)
            .quantTrs(UPDATED_QUANT_TRS)
            .quantRec(UPDATED_QUANT_REC)
            .reqTraitee(UPDATED_REQ_TRAITEE)
            .dateReq(UPDATED_DATE_REQ)
            .dateRec(UPDATED_DATE_REC)
            .dateTransfert(UPDATED_DATE_TRANSFERT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRequetePointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
        RequetePointService testRequetePointService = requetePointServiceList.get(requetePointServiceList.size() - 1);
        assertThat(testRequetePointService.getStockDisponible()).isEqualTo(UPDATED_STOCK_DISPONIBLE);
        assertThat(testRequetePointService.getQuantDem()).isEqualTo(UPDATED_QUANT_DEM);
        assertThat(testRequetePointService.getQuantTrs()).isEqualTo(UPDATED_QUANT_TRS);
        assertThat(testRequetePointService.getQuantRec()).isEqualTo(UPDATED_QUANT_REC);
        assertThat(testRequetePointService.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
        assertThat(testRequetePointService.getDateReq()).isEqualTo(UPDATED_DATE_REQ);
        assertThat(testRequetePointService.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testRequetePointService.getDateTransfert()).isEqualTo(UPDATED_DATE_TRANSFERT);
    }

    @Test
    void patchNonExistingRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();
        requetePointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, requetePointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().collectList().block().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRequetePointService() {
        // Initialize the database
        requetePointServiceRepository.save(requetePointService).block();

        int databaseSizeBeforeDelete = requetePointServiceRepository.findAll().collectList().block().size();

        // Delete the requetePointService
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, requetePointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll().collectList().block();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
