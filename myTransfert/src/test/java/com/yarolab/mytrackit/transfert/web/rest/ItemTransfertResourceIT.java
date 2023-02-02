package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.ItemTransfert;
import com.yarolab.mytrackit.transfert.repository.ItemTransfertRepository;
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

/**
 * Integration tests for the {@link ItemTransfertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemTransfertResourceIT {

    private static final Integer DEFAULT_WAYB_ID = 1;
    private static final Integer UPDATED_WAYB_ID = 2;

    private static final Integer DEFAULT_RO_ID = 1;
    private static final Integer UPDATED_RO_ID = 2;

    private static final Instant DEFAULT_RO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MATERIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_ID = "BBBBBBBBBB";

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
    private MockMvc restItemTransfertMockMvc;

    private ItemTransfert itemTransfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemTransfert createEntity(EntityManager em) {
        ItemTransfert itemTransfert = new ItemTransfert()
            .waybId(DEFAULT_WAYB_ID)
            .roId(DEFAULT_RO_ID)
            .roDate(DEFAULT_RO_DATE)
            .materialId(DEFAULT_MATERIAL_ID)
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
            .waybId(UPDATED_WAYB_ID)
            .roId(UPDATED_RO_ID)
            .roDate(UPDATED_RO_DATE)
            .materialId(UPDATED_MATERIAL_ID)
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

    @BeforeEach
    public void initTest() {
        itemTransfert = createEntity(em);
    }

    @Test
    @Transactional
    void createItemTransfert() throws Exception {
        int databaseSizeBeforeCreate = itemTransfertRepository.findAll().size();
        // Create the ItemTransfert
        restItemTransfertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemTransfert)))
            .andExpect(status().isCreated());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getWaybId()).isEqualTo(DEFAULT_WAYB_ID);
        assertThat(testItemTransfert.getRoId()).isEqualTo(DEFAULT_RO_ID);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(DEFAULT_RO_DATE);
        assertThat(testItemTransfert.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
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
    @Transactional
    void createItemTransfertWithExistingId() throws Exception {
        // Create the ItemTransfert with an existing ID
        itemTransfert.setId(1L);

        int databaseSizeBeforeCreate = itemTransfertRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemTransfertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItemTransferts() throws Exception {
        // Initialize the database
        itemTransfertRepository.saveAndFlush(itemTransfert);

        // Get all the itemTransfertList
        restItemTransfertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].waybId").value(hasItem(DEFAULT_WAYB_ID)))
            .andExpect(jsonPath("$.[*].roId").value(hasItem(DEFAULT_RO_ID)))
            .andExpect(jsonPath("$.[*].roDate").value(hasItem(DEFAULT_RO_DATE.toString())))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].matDesc").value(hasItem(DEFAULT_MAT_DESC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].delQty").value(hasItem(DEFAULT_DEL_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].batch").value(hasItem(DEFAULT_BATCH)))
            .andExpect(jsonPath("$.[*].bbDate").value(hasItem(DEFAULT_BB_DATE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].recQty").value(hasItem(DEFAULT_REC_QTY.doubleValue())));
    }

    @Test
    @Transactional
    void getItemTransfert() throws Exception {
        // Initialize the database
        itemTransfertRepository.saveAndFlush(itemTransfert);

        // Get the itemTransfert
        restItemTransfertMockMvc
            .perform(get(ENTITY_API_URL_ID, itemTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemTransfert.getId().intValue()))
            .andExpect(jsonPath("$.waybId").value(DEFAULT_WAYB_ID))
            .andExpect(jsonPath("$.roId").value(DEFAULT_RO_ID))
            .andExpect(jsonPath("$.roDate").value(DEFAULT_RO_DATE.toString()))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID))
            .andExpect(jsonPath("$.matDesc").value(DEFAULT_MAT_DESC))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.delQty").value(DEFAULT_DEL_QTY.doubleValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.batch").value(DEFAULT_BATCH))
            .andExpect(jsonPath("$.bbDate").value(DEFAULT_BB_DATE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.recQty").value(DEFAULT_REC_QTY.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingItemTransfert() throws Exception {
        // Get the itemTransfert
        restItemTransfertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItemTransfert() throws Exception {
        // Initialize the database
        itemTransfertRepository.saveAndFlush(itemTransfert);

        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();

        // Update the itemTransfert
        ItemTransfert updatedItemTransfert = itemTransfertRepository.findById(itemTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedItemTransfert are not directly saved in db
        em.detach(updatedItemTransfert);
        updatedItemTransfert
            .waybId(UPDATED_WAYB_ID)
            .roId(UPDATED_RO_ID)
            .roDate(UPDATED_RO_DATE)
            .materialId(UPDATED_MATERIAL_ID)
            .matDesc(UPDATED_MAT_DESC)
            .unit(UPDATED_UNIT)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .recQty(UPDATED_REC_QTY);

        restItemTransfertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItemTransfert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItemTransfert))
            )
            .andExpect(status().isOk());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getWaybId()).isEqualTo(UPDATED_WAYB_ID);
        assertThat(testItemTransfert.getRoId()).isEqualTo(UPDATED_RO_ID);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testItemTransfert.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
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
    @Transactional
    void putNonExistingItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();
        itemTransfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemTransfertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemTransfert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemTransfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemTransfertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemTransfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemTransfertMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemTransfert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemTransfertWithPatch() throws Exception {
        // Initialize the database
        itemTransfertRepository.saveAndFlush(itemTransfert);

        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();

        // Update the itemTransfert using partial update
        ItemTransfert partialUpdatedItemTransfert = new ItemTransfert();
        partialUpdatedItemTransfert.setId(itemTransfert.getId());

        partialUpdatedItemTransfert.roDate(UPDATED_RO_DATE).matDesc(UPDATED_MAT_DESC).recQty(UPDATED_REC_QTY);

        restItemTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemTransfert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemTransfert))
            )
            .andExpect(status().isOk());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getWaybId()).isEqualTo(DEFAULT_WAYB_ID);
        assertThat(testItemTransfert.getRoId()).isEqualTo(DEFAULT_RO_ID);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testItemTransfert.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testItemTransfert.getMatDesc()).isEqualTo(UPDATED_MAT_DESC);
        assertThat(testItemTransfert.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testItemTransfert.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testItemTransfert.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testItemTransfert.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testItemTransfert.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testItemTransfert.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testItemTransfert.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testItemTransfert.getRecQty()).isEqualTo(UPDATED_REC_QTY);
    }

    @Test
    @Transactional
    void fullUpdateItemTransfertWithPatch() throws Exception {
        // Initialize the database
        itemTransfertRepository.saveAndFlush(itemTransfert);

        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();

        // Update the itemTransfert using partial update
        ItemTransfert partialUpdatedItemTransfert = new ItemTransfert();
        partialUpdatedItemTransfert.setId(itemTransfert.getId());

        partialUpdatedItemTransfert
            .waybId(UPDATED_WAYB_ID)
            .roId(UPDATED_RO_ID)
            .roDate(UPDATED_RO_DATE)
            .materialId(UPDATED_MATERIAL_ID)
            .matDesc(UPDATED_MAT_DESC)
            .unit(UPDATED_UNIT)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .recQty(UPDATED_REC_QTY);

        restItemTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemTransfert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemTransfert))
            )
            .andExpect(status().isOk());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
        ItemTransfert testItemTransfert = itemTransfertList.get(itemTransfertList.size() - 1);
        assertThat(testItemTransfert.getWaybId()).isEqualTo(UPDATED_WAYB_ID);
        assertThat(testItemTransfert.getRoId()).isEqualTo(UPDATED_RO_ID);
        assertThat(testItemTransfert.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testItemTransfert.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
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
    @Transactional
    void patchNonExistingItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();
        itemTransfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemTransfert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemTransfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemTransfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemTransfert() throws Exception {
        int databaseSizeBeforeUpdate = itemTransfertRepository.findAll().size();
        itemTransfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemTransfert))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemTransfert in the database
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemTransfert() throws Exception {
        // Initialize the database
        itemTransfertRepository.saveAndFlush(itemTransfert);

        int databaseSizeBeforeDelete = itemTransfertRepository.findAll().size();

        // Delete the itemTransfert
        restItemTransfertMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemTransfert.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemTransfert> itemTransfertList = itemTransfertRepository.findAll();
        assertThat(itemTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
