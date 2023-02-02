package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.ItemTransfert;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.ItemTransfertRepository;
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
 * Integration tests for the {@link ItemTransfertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ItemTransfertResourceIT {

    private static final Instant DEFAULT_RO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MAT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_MAT_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Double DEFAULT_DEL_QTY = 1D;
    private static final Double UPDATED_DEL_QTY = 2D;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final String DEFAULT_BATCH = "AAAAAAAAAA";
    private static final String UPDATED_BATCH = "BBBBBBBBBB";

    private static final Instant DEFAULT_BB_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BB_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    private static final Double DEFAULT_REC_QTY = 1D;
    private static final Double UPDATED_REC_QTY = 2D;

    private static final String ENTITY_API_URL = "/api/item-transferts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemTransfertRepository itemTransfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ItemTransfert itemTransfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemTransfert createEntity(EntityManager em) {
        ItemTransfert itemTransfert = new ItemTransfert()
            .roDate(DEFAULT_RO_DATE)
            .matDesc(DEFAULT_MAT_DESC)
            .unit(DEFAULT_UNIT)
            .delQty(DEFAULT_DEL_QTY)
            .value(DEFAULT_VALUE)
            .batch(DEFAULT_BATCH)
            .bbDate(DEFAULT_BB_DATE)
            .weight(DEFAULT_WEIGHT)
            .volume(DEFAULT_VOLUME)
            .recQty(DEFAULT_REC_QTY);
        return itemTransfert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemTransfert createUpdatedEntity(EntityManager em) {
        ItemTransfert itemTransfert = new ItemTransfert()
            .roDate(UPDATED_RO_DATE)
            .matDesc(UPDATED_MAT_DESC)
            .unit(UPDATED_UNIT)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .recQty(UPDATED_REC_QTY);
        return itemTransfert;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ItemTransfert.class).block();
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
        itemTransfert = createEntity(em);
    }

    @Test
    void createItemTransfert() throws Exception {
        int databaseSizeBeforeCreate = itemTransfertRepository.findAll().collectList().block().size();
        // Create the ItemTransfert
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(DEFAULT_RO_DATE);
        assertThat(testItemTransfert.getMatDesc()).isEqualTo(DEFAULT_MAT_DESC);
        assertThat(testItemTransfert.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testItemTransfert.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testItemTransfert.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testItemTransfert.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testItemTransfert.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testItemTransfert.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testItemTransfert.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testItemTransfert.getRecQty()).isEqualTo(DEFAULT_REC_QTY);
    }

    @Test
    void createItemTransfertWithExistingId() throws Exception {
        // Create the ItemTransfert with an existing ID
        itemTransfert.setId(1L);

        int databaseSizeBeforeCreate = itemTransfertRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllItemTransfertsAsStream() {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        List<ItemTransfert> itemTransfertList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ItemTransfert.class)
            .getResponseBody()
            .filter(itemTransfert::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(itemTransfertList).isNotNull();
        assertThat(itemTransfertList).hasSize(1);
        ItemTransfert testItemTransfert = itemTransfertList.get(0);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(DEFAULT_RO_DATE);
        assertThat(testItemTransfert.getMatDesc()).isEqualTo(DEFAULT_MAT_DESC);
        assertThat(testItemTransfert.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testItemTransfert.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testItemTransfert.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testItemTransfert.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testItemTransfert.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testItemTransfert.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testItemTransfert.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testItemTransfert.getRecQty()).isEqualTo(DEFAULT_REC_QTY);
    }

    @Test
    void getAllItemTransferts() {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        // Get all the itemTransfertList
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
            .value(hasItem(itemTransfert.getId().intValue()))
            .jsonPath("$.[*].roDate")
            .value(hasItem(DEFAULT_RO_DATE.toString()))
            .jsonPath("$.[*].matDesc")
            .value(hasItem(DEFAULT_MAT_DESC))
            .jsonPath("$.[*].unit")
            .value(hasItem(DEFAULT_UNIT))
            .jsonPath("$.[*].delQty")
            .value(hasItem(DEFAULT_DEL_QTY.doubleValue()))
            .jsonPath("$.[*].value")
            .value(hasItem(DEFAULT_VALUE.doubleValue()))
            .jsonPath("$.[*].batch")
            .value(hasItem(DEFAULT_BATCH))
            .jsonPath("$.[*].bbDate")
            .value(hasItem(DEFAULT_BB_DATE.toString()))
            .jsonPath("$.[*].weight")
            .value(hasItem(DEFAULT_WEIGHT.doubleValue()))
            .jsonPath("$.[*].volume")
            .value(hasItem(DEFAULT_VOLUME.doubleValue()))
            .jsonPath("$.[*].recQty")
            .value(hasItem(DEFAULT_REC_QTY.doubleValue()));
    }

    @Test
    void getItemTransfert() {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        // Get the itemTransfert
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, itemTransfert.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(itemTransfert.getId().intValue()))
            .jsonPath("$.roDate")
            .value(is(DEFAULT_RO_DATE.toString()))
            .jsonPath("$.matDesc")
            .value(is(DEFAULT_MAT_DESC))
            .jsonPath("$.unit")
            .value(is(DEFAULT_UNIT))
            .jsonPath("$.delQty")
            .value(is(DEFAULT_DEL_QTY.doubleValue()))
            .jsonPath("$.value")
            .value(is(DEFAULT_VALUE.doubleValue()))
            .jsonPath("$.batch")
            .value(is(DEFAULT_BATCH))
            .jsonPath("$.bbDate")
            .value(is(DEFAULT_BB_DATE.toString()))
            .jsonPath("$.weight")
            .value(is(DEFAULT_WEIGHT.doubleValue()))
            .jsonPath("$.volume")
            .value(is(DEFAULT_VOLUME.doubleValue()))
            .jsonPath("$.recQty")
            .value(is(DEFAULT_REC_QTY.doubleValue()));
    }

    @Test
    void getNonExistingItemTransfert() {
        // Get the itemTransfert
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingItemTransfert() throws Exception {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();

        // Update the itemTransfert
        ItemTransfert updatedItemTransfert = itemTransfertRepository.findById(itemTransfert.getId()).block();
        updatedItemTransfert
            .roDate(UPDATED_RO_DATE)
            .matDesc(UPDATED_MAT_DESC)
            .unit(UPDATED_UNIT)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .recQty(UPDATED_REC_QTY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedItemTransfert.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedItemTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testItemTransfert.getMatDesc()).isEqualTo(UPDATED_MAT_DESC);
        assertThat(testItemTransfert.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testItemTransfert.getDelQty()).isEqualTo(UPDATED_DEL_QTY);
        assertThat(testItemTransfert.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testItemTransfert.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testItemTransfert.getBbDate()).isEqualTo(UPDATED_BB_DATE);
        assertThat(testItemTransfert.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testItemTransfert.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testItemTransfert.getRecQty()).isEqualTo(UPDATED_REC_QTY);
    }

    @Test
    void putNonExistingItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();
        itemTransfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, itemTransfert.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateItemTransfertWithPatch() throws Exception {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();

        // Update the itemTransfert using partial update
        ItemTransfert partialUpdatedItemTransfert = new ItemTransfert();
        partialUpdatedItemTransfert.setId(itemTransfert.getId());

        partialUpdatedItemTransfert.unit(UPDATED_UNIT).value(UPDATED_VALUE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedItemTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedItemTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(DEFAULT_RO_DATE);
        assertThat(testItemTransfert.getMatDesc()).isEqualTo(DEFAULT_MAT_DESC);
        assertThat(testItemTransfert.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testItemTransfert.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testItemTransfert.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testItemTransfert.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testItemTransfert.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testItemTransfert.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testItemTransfert.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testItemTransfert.getRecQty()).isEqualTo(DEFAULT_REC_QTY);
    }

    @Test
    void fullUpdateItemTransfertWithPatch() throws Exception {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();

        // Update the itemTransfert using partial update
        ItemTransfert partialUpdatedItemTransfert = new ItemTransfert();
        partialUpdatedItemTransfert.setId(itemTransfert.getId());

        partialUpdatedItemTransfert
            .roDate(UPDATED_RO_DATE)
            .matDesc(UPDATED_MAT_DESC)
            .unit(UPDATED_UNIT)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .recQty(UPDATED_REC_QTY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedItemTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedItemTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testItemTransfert.getMatDesc()).isEqualTo(UPDATED_MAT_DESC);
        assertThat(testItemTransfert.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testItemTransfert.getDelQty()).isEqualTo(UPDATED_DEL_QTY);
        assertThat(testItemTransfert.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testItemTransfert.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testItemTransfert.getBbDate()).isEqualTo(UPDATED_BB_DATE);
        assertThat(testItemTransfert.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testItemTransfert.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testItemTransfert.getRecQty()).isEqualTo(UPDATED_REC_QTY);
    }

    @Test
    void patchNonExistingItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();
        itemTransfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, itemTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().collectList().block().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(itemTransfert))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteItemTransfert() {
        // Initialize the database
        itemTransfertRepository.save(itemTransfert).block();

        int databaseSizeBeforeDelete = itemTransfertRepository.findAll().collectList().block().size();

        // Delete the itemTransfert
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, itemTransfert.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll().collectList().block();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
