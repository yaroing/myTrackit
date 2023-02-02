package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.ItemVerifie;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.ItemVerifieRepository;
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
 * Integration tests for the {@link ItemVerifieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ItemVerifieResourceIT {

    private static final Double DEFAULT_QUANTITE_TRANSFERT = 1D;
    private static final Double UPDATED_QUANTITE_TRANSFERT = 2D;

    private static final Double DEFAULT_QUANTITE_RECU = 1D;
    private static final Double UPDATED_QUANTITE_RECU = 2D;

    private static final Double DEFAULT_QUANTITE_UTILISEE = 1D;
    private static final Double UPDATED_QUANTITE_UTILISEE = 2D;

    private static final Double DEFAULT_QUANTITE_DISPONIBLE = 1D;
    private static final Double UPDATED_QUANTITE_DISPONIBLE = 2D;

    private static final Double DEFAULT_QUANTITE_ECART = 1D;
    private static final Double UPDATED_QUANTITE_ECART = 2D;

    private static final String ENTITY_API_URL = "/api/item-verifies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemVerifieRepository itemVerifieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ItemVerifie itemVerifie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemVerifie createEntity(EntityManager em) {
        ItemVerifie itemVerifie = new ItemVerifie()
            .quantiteTransfert(DEFAULT_QUANTITE_TRANSFERT)
            .quantiteRecu(DEFAULT_QUANTITE_RECU)
            .quantiteUtilisee(DEFAULT_QUANTITE_UTILISEE)
            .quantiteDisponible(DEFAULT_QUANTITE_DISPONIBLE)
            .quantiteEcart(DEFAULT_QUANTITE_ECART);
        return itemVerifie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemVerifie createUpdatedEntity(EntityManager em) {
        ItemVerifie itemVerifie = new ItemVerifie()
            .quantiteTransfert(UPDATED_QUANTITE_TRANSFERT)
            .quantiteRecu(UPDATED_QUANTITE_RECU)
            .quantiteUtilisee(UPDATED_QUANTITE_UTILISEE)
            .quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE)
            .quantiteEcart(UPDATED_QUANTITE_ECART);
        return itemVerifie;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ItemVerifie.class).block();
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
        itemVerifie = createEntity(em);
    }

    @Test
    void createItemVerifie() throws Exception {
        int databaseSizeBeforeCreate = itemVerifieRepository.findAll().collectList().block().size();
        // Create the ItemVerifie
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeCreate + 1);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(DEFAULT_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(DEFAULT_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(DEFAULT_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(DEFAULT_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(DEFAULT_QUANTITE_ECART);
    }

    @Test
    void createItemVerifieWithExistingId() throws Exception {
        // Create the ItemVerifie with an existing ID
        itemVerifie.setId(1L);

        int databaseSizeBeforeCreate = itemVerifieRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllItemVerifiesAsStream() {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        List<ItemVerifie> itemVerifieList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ItemVerifie.class)
            .getResponseBody()
            .filter(itemVerifie::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(itemVerifieList).isNotNull();
        assertThat(itemVerifieList).hasSize(1);
        ItemVerifie testItemVerifie = itemVerifieList.get(0);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(DEFAULT_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(DEFAULT_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(DEFAULT_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(DEFAULT_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(DEFAULT_QUANTITE_ECART);
    }

    @Test
    void getAllItemVerifies() {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        // Get all the itemVerifieList
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
            .value(hasItem(itemVerifie.getId().intValue()))
            .jsonPath("$.[*].quantiteTransfert")
            .value(hasItem(DEFAULT_QUANTITE_TRANSFERT.doubleValue()))
            .jsonPath("$.[*].quantiteRecu")
            .value(hasItem(DEFAULT_QUANTITE_RECU.doubleValue()))
            .jsonPath("$.[*].quantiteUtilisee")
            .value(hasItem(DEFAULT_QUANTITE_UTILISEE.doubleValue()))
            .jsonPath("$.[*].quantiteDisponible")
            .value(hasItem(DEFAULT_QUANTITE_DISPONIBLE.doubleValue()))
            .jsonPath("$.[*].quantiteEcart")
            .value(hasItem(DEFAULT_QUANTITE_ECART.doubleValue()));
    }

    @Test
    void getItemVerifie() {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        // Get the itemVerifie
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, itemVerifie.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(itemVerifie.getId().intValue()))
            .jsonPath("$.quantiteTransfert")
            .value(is(DEFAULT_QUANTITE_TRANSFERT.doubleValue()))
            .jsonPath("$.quantiteRecu")
            .value(is(DEFAULT_QUANTITE_RECU.doubleValue()))
            .jsonPath("$.quantiteUtilisee")
            .value(is(DEFAULT_QUANTITE_UTILISEE.doubleValue()))
            .jsonPath("$.quantiteDisponible")
            .value(is(DEFAULT_QUANTITE_DISPONIBLE.doubleValue()))
            .jsonPath("$.quantiteEcart")
            .value(is(DEFAULT_QUANTITE_ECART.doubleValue()));
    }

    @Test
    void getNonExistingItemVerifie() {
        // Get the itemVerifie
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingItemVerifie() throws Exception {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();

        // Update the itemVerifie
        ItemVerifie updatedItemVerifie = itemVerifieRepository.findById(itemVerifie.getId()).block();
        updatedItemVerifie
            .quantiteTransfert(UPDATED_QUANTITE_TRANSFERT)
            .quantiteRecu(UPDATED_QUANTITE_RECU)
            .quantiteUtilisee(UPDATED_QUANTITE_UTILISEE)
            .quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE)
            .quantiteEcart(UPDATED_QUANTITE_ECART);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedItemVerifie.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedItemVerifie))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(UPDATED_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(UPDATED_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(UPDATED_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(UPDATED_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(UPDATED_QUANTITE_ECART);
    }

    @Test
    void putNonExistingItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();
        itemVerifie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, itemVerifie.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateItemVerifieWithPatch() throws Exception {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();

        // Update the itemVerifie using partial update
        ItemVerifie partialUpdatedItemVerifie = new ItemVerifie();
        partialUpdatedItemVerifie.setId(itemVerifie.getId());

        partialUpdatedItemVerifie.quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedItemVerifie.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedItemVerifie))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(DEFAULT_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(DEFAULT_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(DEFAULT_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(UPDATED_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(DEFAULT_QUANTITE_ECART);
    }

    @Test
    void fullUpdateItemVerifieWithPatch() throws Exception {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();

        // Update the itemVerifie using partial update
        ItemVerifie partialUpdatedItemVerifie = new ItemVerifie();
        partialUpdatedItemVerifie.setId(itemVerifie.getId());

        partialUpdatedItemVerifie
            .quantiteTransfert(UPDATED_QUANTITE_TRANSFERT)
            .quantiteRecu(UPDATED_QUANTITE_RECU)
            .quantiteUtilisee(UPDATED_QUANTITE_UTILISEE)
            .quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE)
            .quantiteEcart(UPDATED_QUANTITE_ECART);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedItemVerifie.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedItemVerifie))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(UPDATED_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(UPDATED_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(UPDATED_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(UPDATED_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(UPDATED_QUANTITE_ECART);
    }

    @Test
    void patchNonExistingItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();
        itemVerifie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, itemVerifie.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().collectList().block().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemVerifie))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteItemVerifie() {
        // Initialize the database
        itemVerifieRepository.save(itemVerifie).block();

        int databaseSizeBeforeDelete = itemVerifieRepository.findAll().collectList().block().size();

        // Delete the itemVerifie
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, itemVerifie.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll().collectList().block();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
