package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.StockPartenaire;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.StockPartenaireRepository;
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
 * Integration tests for the {@link StockPartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StockPartenaireResourceIT {

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

    private static final String ENTITY_API_URL = "/api/stock-partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockPartenaireRepository stockPartenaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private StockPartenaire stockPartenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockPartenaire createEntity(EntityManager em) {
        StockPartenaire stockPartenaire = new StockPartenaire()
            .stockAnnee(DEFAULT_STOCK_ANNEE)
            .stockMois(DEFAULT_STOCK_MOIS)
            .entreeMois(DEFAULT_ENTREE_MOIS)
            .sortieMois(DEFAULT_SORTIE_MOIS)
            .stockFinmois(DEFAULT_STOCK_FINMOIS)
            .stockDebut(DEFAULT_STOCK_DEBUT);
        return stockPartenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockPartenaire createUpdatedEntity(EntityManager em) {
        StockPartenaire stockPartenaire = new StockPartenaire()
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);
        return stockPartenaire;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(StockPartenaire.class).block();
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
        stockPartenaire = createEntity(em);
    }

    @Test
    void createStockPartenaire() throws Exception {
        int databaseSizeBeforeCreate = stockPartenaireRepository.findAll().collectList().block().size();
        // Create the StockPartenaire
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeCreate + 1);
        StockPartenaire testStockPartenaire = stockPartenaireList.get(stockPartenaireList.size() - 1);
        assertThat(testStockPartenaire.getStockAnnee()).isEqualTo(DEFAULT_STOCK_ANNEE);
        assertThat(testStockPartenaire.getStockMois()).isEqualTo(DEFAULT_STOCK_MOIS);
        assertThat(testStockPartenaire.getEntreeMois()).isEqualTo(DEFAULT_ENTREE_MOIS);
        assertThat(testStockPartenaire.getSortieMois()).isEqualTo(DEFAULT_SORTIE_MOIS);
        assertThat(testStockPartenaire.getStockFinmois()).isEqualTo(DEFAULT_STOCK_FINMOIS);
        assertThat(testStockPartenaire.getStockDebut()).isEqualTo(DEFAULT_STOCK_DEBUT);
    }

    @Test
    void createStockPartenaireWithExistingId() throws Exception {
        // Create the StockPartenaire with an existing ID
        stockPartenaire.setId(1L);

        int databaseSizeBeforeCreate = stockPartenaireRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllStockPartenairesAsStream() {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        List<StockPartenaire> stockPartenaireList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(StockPartenaire.class)
            .getResponseBody()
            .filter(stockPartenaire::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(stockPartenaireList).isNotNull();
        assertThat(stockPartenaireList).hasSize(1);
        StockPartenaire testStockPartenaire = stockPartenaireList.get(0);
        assertThat(testStockPartenaire.getStockAnnee()).isEqualTo(DEFAULT_STOCK_ANNEE);
        assertThat(testStockPartenaire.getStockMois()).isEqualTo(DEFAULT_STOCK_MOIS);
        assertThat(testStockPartenaire.getEntreeMois()).isEqualTo(DEFAULT_ENTREE_MOIS);
        assertThat(testStockPartenaire.getSortieMois()).isEqualTo(DEFAULT_SORTIE_MOIS);
        assertThat(testStockPartenaire.getStockFinmois()).isEqualTo(DEFAULT_STOCK_FINMOIS);
        assertThat(testStockPartenaire.getStockDebut()).isEqualTo(DEFAULT_STOCK_DEBUT);
    }

    @Test
    void getAllStockPartenaires() {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        // Get all the stockPartenaireList
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
            .value(hasItem(stockPartenaire.getId().intValue()))
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
    void getStockPartenaire() {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        // Get the stockPartenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, stockPartenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(stockPartenaire.getId().intValue()))
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
    void getNonExistingStockPartenaire() {
        // Get the stockPartenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingStockPartenaire() throws Exception {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();

        // Update the stockPartenaire
        StockPartenaire updatedStockPartenaire = stockPartenaireRepository.findById(stockPartenaire.getId()).block();
        updatedStockPartenaire
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedStockPartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedStockPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
        StockPartenaire testStockPartenaire = stockPartenaireList.get(stockPartenaireList.size() - 1);
        assertThat(testStockPartenaire.getStockAnnee()).isEqualTo(UPDATED_STOCK_ANNEE);
        assertThat(testStockPartenaire.getStockMois()).isEqualTo(UPDATED_STOCK_MOIS);
        assertThat(testStockPartenaire.getEntreeMois()).isEqualTo(UPDATED_ENTREE_MOIS);
        assertThat(testStockPartenaire.getSortieMois()).isEqualTo(UPDATED_SORTIE_MOIS);
        assertThat(testStockPartenaire.getStockFinmois()).isEqualTo(UPDATED_STOCK_FINMOIS);
        assertThat(testStockPartenaire.getStockDebut()).isEqualTo(UPDATED_STOCK_DEBUT);
    }

    @Test
    void putNonExistingStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, stockPartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStockPartenaireWithPatch() throws Exception {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();

        // Update the stockPartenaire using partial update
        StockPartenaire partialUpdatedStockPartenaire = new StockPartenaire();
        partialUpdatedStockPartenaire.setId(stockPartenaire.getId());

        partialUpdatedStockPartenaire
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStockPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
        StockPartenaire testStockPartenaire = stockPartenaireList.get(stockPartenaireList.size() - 1);
        assertThat(testStockPartenaire.getStockAnnee()).isEqualTo(UPDATED_STOCK_ANNEE);
        assertThat(testStockPartenaire.getStockMois()).isEqualTo(UPDATED_STOCK_MOIS);
        assertThat(testStockPartenaire.getEntreeMois()).isEqualTo(DEFAULT_ENTREE_MOIS);
        assertThat(testStockPartenaire.getSortieMois()).isEqualTo(UPDATED_SORTIE_MOIS);
        assertThat(testStockPartenaire.getStockFinmois()).isEqualTo(UPDATED_STOCK_FINMOIS);
        assertThat(testStockPartenaire.getStockDebut()).isEqualTo(UPDATED_STOCK_DEBUT);
    }

    @Test
    void fullUpdateStockPartenaireWithPatch() throws Exception {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();

        // Update the stockPartenaire using partial update
        StockPartenaire partialUpdatedStockPartenaire = new StockPartenaire();
        partialUpdatedStockPartenaire.setId(stockPartenaire.getId());

        partialUpdatedStockPartenaire
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStockPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
        StockPartenaire testStockPartenaire = stockPartenaireList.get(stockPartenaireList.size() - 1);
        assertThat(testStockPartenaire.getStockAnnee()).isEqualTo(UPDATED_STOCK_ANNEE);
        assertThat(testStockPartenaire.getStockMois()).isEqualTo(UPDATED_STOCK_MOIS);
        assertThat(testStockPartenaire.getEntreeMois()).isEqualTo(UPDATED_ENTREE_MOIS);
        assertThat(testStockPartenaire.getSortieMois()).isEqualTo(UPDATED_SORTIE_MOIS);
        assertThat(testStockPartenaire.getStockFinmois()).isEqualTo(UPDATED_STOCK_FINMOIS);
        assertThat(testStockPartenaire.getStockDebut()).isEqualTo(UPDATED_STOCK_DEBUT);
    }

    @Test
    void patchNonExistingStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, stockPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().collectList().block().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStockPartenaire() {
        // Initialize the database
        stockPartenaireRepository.save(stockPartenaire).block();

        int databaseSizeBeforeDelete = stockPartenaireRepository.findAll().collectList().block().size();

        // Delete the stockPartenaire
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, stockPartenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll().collectList().block();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
