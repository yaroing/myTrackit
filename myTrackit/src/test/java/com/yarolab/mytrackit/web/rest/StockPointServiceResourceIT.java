package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.StockPointService;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.StockPointServiceRepository;
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
 * Integration tests for the {@link StockPointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StockPointServiceResourceIT {

    private static final String DEFAULT_STOCK_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ANNEE = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_MOIS = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_MOIS = "BBBBBBBBBB";

    private static final Double DEFAULT_ENTREE_MOIS = 1D;
    private static final Double UPDATED_ENTREE_MOIS = 2D;

    private static final Double DEFAULT_SORTIE_MOIS = 1D;
    private static final Double UPDATED_SORTIE_MOIS = 2D;

    private static final Double DEFAULT_STOCK_FINMOIS = 1D;
    private static final Double UPDATED_STOCK_FINMOIS = 2D;

    private static final Double DEFAULT_STOCK_DEBUT = 1D;
    private static final Double UPDATED_STOCK_DEBUT = 2D;

    private static final String ENTITY_API_URL = "/api/stock-point-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockPointServiceRepository stockPointServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private StockPointService stockPointService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockPointService createEntity(EntityManager em) {
        StockPointService stockPointService = new StockPointService()
            .stockAnnee(DEFAULT_STOCK_ANNEE)
            .stockMois(DEFAULT_STOCK_MOIS)
            .entreeMois(DEFAULT_ENTREE_MOIS)
            .sortieMois(DEFAULT_SORTIE_MOIS)
            .stockFinmois(DEFAULT_STOCK_FINMOIS)
            .stockDebut(DEFAULT_STOCK_DEBUT);
        return stockPointService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockPointService createUpdatedEntity(EntityManager em) {
        StockPointService stockPointService = new StockPointService()
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);
        return stockPointService;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(StockPointService.class).block();
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
        stockPointService = createEntity(em);
    }

    @Test
    void createStockPointService() throws Exception {
        int databaseSizeBeforeCreate = stockPointServiceRepository.findAll().collectList().block().size();
        // Create the StockPointService
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeCreate + 1);
        StockPointService testStockPointService = stockPointServiceList.get(stockPointServiceList.size() - 1);
        assertThat(testStockPointService.getStockAnnee()).isEqualTo(DEFAULT_STOCK_ANNEE);
        assertThat(testStockPointService.getStockMois()).isEqualTo(DEFAULT_STOCK_MOIS);
        assertThat(testStockPointService.getEntreeMois()).isEqualTo(DEFAULT_ENTREE_MOIS);
        assertThat(testStockPointService.getSortieMois()).isEqualTo(DEFAULT_SORTIE_MOIS);
        assertThat(testStockPointService.getStockFinmois()).isEqualTo(DEFAULT_STOCK_FINMOIS);
        assertThat(testStockPointService.getStockDebut()).isEqualTo(DEFAULT_STOCK_DEBUT);
    }

    @Test
    void createStockPointServiceWithExistingId() throws Exception {
        // Create the StockPointService with an existing ID
        stockPointService.setId(1L);

        int databaseSizeBeforeCreate = stockPointServiceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllStockPointServicesAsStream() {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        List<StockPointService> stockPointServiceList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(StockPointService.class)
            .getResponseBody()
            .filter(stockPointService::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(stockPointServiceList).isNotNull();
        assertThat(stockPointServiceList).hasSize(1);
        StockPointService testStockPointService = stockPointServiceList.get(0);
        assertThat(testStockPointService.getStockAnnee()).isEqualTo(DEFAULT_STOCK_ANNEE);
        assertThat(testStockPointService.getStockMois()).isEqualTo(DEFAULT_STOCK_MOIS);
        assertThat(testStockPointService.getEntreeMois()).isEqualTo(DEFAULT_ENTREE_MOIS);
        assertThat(testStockPointService.getSortieMois()).isEqualTo(DEFAULT_SORTIE_MOIS);
        assertThat(testStockPointService.getStockFinmois()).isEqualTo(DEFAULT_STOCK_FINMOIS);
        assertThat(testStockPointService.getStockDebut()).isEqualTo(DEFAULT_STOCK_DEBUT);
    }

    @Test
    void getAllStockPointServices() {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        // Get all the stockPointServiceList
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
            .value(hasItem(stockPointService.getId().intValue()))
            .jsonPath("$.[*].stockAnnee")
            .value(hasItem(DEFAULT_STOCK_ANNEE))
            .jsonPath("$.[*].stockMois")
            .value(hasItem(DEFAULT_STOCK_MOIS))
            .jsonPath("$.[*].entreeMois")
            .value(hasItem(DEFAULT_ENTREE_MOIS.doubleValue()))
            .jsonPath("$.[*].sortieMois")
            .value(hasItem(DEFAULT_SORTIE_MOIS.doubleValue()))
            .jsonPath("$.[*].stockFinmois")
            .value(hasItem(DEFAULT_STOCK_FINMOIS.doubleValue()))
            .jsonPath("$.[*].stockDebut")
            .value(hasItem(DEFAULT_STOCK_DEBUT.doubleValue()));
    }

    @Test
    void getStockPointService() {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        // Get the stockPointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, stockPointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(stockPointService.getId().intValue()))
            .jsonPath("$.stockAnnee")
            .value(is(DEFAULT_STOCK_ANNEE))
            .jsonPath("$.stockMois")
            .value(is(DEFAULT_STOCK_MOIS))
            .jsonPath("$.entreeMois")
            .value(is(DEFAULT_ENTREE_MOIS.doubleValue()))
            .jsonPath("$.sortieMois")
            .value(is(DEFAULT_SORTIE_MOIS.doubleValue()))
            .jsonPath("$.stockFinmois")
            .value(is(DEFAULT_STOCK_FINMOIS.doubleValue()))
            .jsonPath("$.stockDebut")
            .value(is(DEFAULT_STOCK_DEBUT.doubleValue()));
    }

    @Test
    void getNonExistingStockPointService() {
        // Get the stockPointService
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingStockPointService() throws Exception {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();

        // Update the stockPointService
        StockPointService updatedStockPointService = stockPointServiceRepository.findById(stockPointService.getId()).block();
        updatedStockPointService
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedStockPointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedStockPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
        StockPointService testStockPointService = stockPointServiceList.get(stockPointServiceList.size() - 1);
        assertThat(testStockPointService.getStockAnnee()).isEqualTo(UPDATED_STOCK_ANNEE);
        assertThat(testStockPointService.getStockMois()).isEqualTo(UPDATED_STOCK_MOIS);
        assertThat(testStockPointService.getEntreeMois()).isEqualTo(UPDATED_ENTREE_MOIS);
        assertThat(testStockPointService.getSortieMois()).isEqualTo(UPDATED_SORTIE_MOIS);
        assertThat(testStockPointService.getStockFinmois()).isEqualTo(UPDATED_STOCK_FINMOIS);
        assertThat(testStockPointService.getStockDebut()).isEqualTo(UPDATED_STOCK_DEBUT);
    }

    @Test
    void putNonExistingStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();
        stockPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, stockPointService.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStockPointServiceWithPatch() throws Exception {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();

        // Update the stockPointService using partial update
        StockPointService partialUpdatedStockPointService = new StockPointService();
        partialUpdatedStockPointService.setId(stockPointService.getId());

        partialUpdatedStockPointService
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStockPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
        StockPointService testStockPointService = stockPointServiceList.get(stockPointServiceList.size() - 1);
        assertThat(testStockPointService.getStockAnnee()).isEqualTo(UPDATED_STOCK_ANNEE);
        assertThat(testStockPointService.getStockMois()).isEqualTo(DEFAULT_STOCK_MOIS);
        assertThat(testStockPointService.getEntreeMois()).isEqualTo(DEFAULT_ENTREE_MOIS);
        assertThat(testStockPointService.getSortieMois()).isEqualTo(UPDATED_SORTIE_MOIS);
        assertThat(testStockPointService.getStockFinmois()).isEqualTo(UPDATED_STOCK_FINMOIS);
        assertThat(testStockPointService.getStockDebut()).isEqualTo(UPDATED_STOCK_DEBUT);
    }

    @Test
    void fullUpdateStockPointServiceWithPatch() throws Exception {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();

        // Update the stockPointService using partial update
        StockPointService partialUpdatedStockPointService = new StockPointService();
        partialUpdatedStockPointService.setId(stockPointService.getId());

        partialUpdatedStockPointService
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStockPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPointService))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
        StockPointService testStockPointService = stockPointServiceList.get(stockPointServiceList.size() - 1);
        assertThat(testStockPointService.getStockAnnee()).isEqualTo(UPDATED_STOCK_ANNEE);
        assertThat(testStockPointService.getStockMois()).isEqualTo(UPDATED_STOCK_MOIS);
        assertThat(testStockPointService.getEntreeMois()).isEqualTo(UPDATED_ENTREE_MOIS);
        assertThat(testStockPointService.getSortieMois()).isEqualTo(UPDATED_SORTIE_MOIS);
        assertThat(testStockPointService.getStockFinmois()).isEqualTo(UPDATED_STOCK_FINMOIS);
        assertThat(testStockPointService.getStockDebut()).isEqualTo(UPDATED_STOCK_DEBUT);
    }

    @Test
    void patchNonExistingStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();
        stockPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, stockPointService.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().collectList().block().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPointService))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStockPointService() {
        // Initialize the database
        stockPointServiceRepository.save(stockPointService).block();

        int databaseSizeBeforeDelete = stockPointServiceRepository.findAll().collectList().block().size();

        // Delete the stockPointService
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, stockPointService.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll().collectList().block();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
