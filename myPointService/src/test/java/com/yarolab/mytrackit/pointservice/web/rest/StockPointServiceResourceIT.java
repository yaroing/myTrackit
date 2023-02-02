package com.yarolab.mytrackit.pointservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.pointservice.IntegrationTest;
import com.yarolab.mytrackit.pointservice.domain.StockPointService;
import com.yarolab.mytrackit.pointservice.repository.StockPointServiceRepository;
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
 * Integration tests for the {@link StockPointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restStockPointServiceMockMvc;

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

    @BeforeEach
    public void initTest() {
        stockPointService = createEntity(em);
    }

    @Test
    @Transactional
    void createStockPointService() throws Exception {
        int databaseSizeBeforeCreate = stockPointServiceRepository.findAll().size();
        // Create the StockPointService
        restStockPointServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isCreated());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
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
    @Transactional
    void createStockPointServiceWithExistingId() throws Exception {
        // Create the StockPointService with an existing ID
        stockPointService.setId(1L);

        int databaseSizeBeforeCreate = stockPointServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockPointServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStockPointServices() throws Exception {
        // Initialize the database
        stockPointServiceRepository.saveAndFlush(stockPointService);

        // Get all the stockPointServiceList
        restStockPointServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockPointService.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockAnnee").value(hasItem(DEFAULT_STOCK_ANNEE)))
            .andExpect(jsonPath("$.[*].stockMois").value(hasItem(DEFAULT_STOCK_MOIS)))
            .andExpect(jsonPath("$.[*].entreeMois").value(hasItem(DEFAULT_ENTREE_MOIS.doubleValue())))
            .andExpect(jsonPath("$.[*].sortieMois").value(hasItem(DEFAULT_SORTIE_MOIS.doubleValue())))
            .andExpect(jsonPath("$.[*].stockFinmois").value(hasItem(DEFAULT_STOCK_FINMOIS.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDebut").value(hasItem(DEFAULT_STOCK_DEBUT.doubleValue())));
    }

    @Test
    @Transactional
    void getStockPointService() throws Exception {
        // Initialize the database
        stockPointServiceRepository.saveAndFlush(stockPointService);

        // Get the stockPointService
        restStockPointServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, stockPointService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockPointService.getId().intValue()))
            .andExpect(jsonPath("$.stockAnnee").value(DEFAULT_STOCK_ANNEE))
            .andExpect(jsonPath("$.stockMois").value(DEFAULT_STOCK_MOIS))
            .andExpect(jsonPath("$.entreeMois").value(DEFAULT_ENTREE_MOIS.doubleValue()))
            .andExpect(jsonPath("$.sortieMois").value(DEFAULT_SORTIE_MOIS.doubleValue()))
            .andExpect(jsonPath("$.stockFinmois").value(DEFAULT_STOCK_FINMOIS.doubleValue()))
            .andExpect(jsonPath("$.stockDebut").value(DEFAULT_STOCK_DEBUT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingStockPointService() throws Exception {
        // Get the stockPointService
        restStockPointServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockPointService() throws Exception {
        // Initialize the database
        stockPointServiceRepository.saveAndFlush(stockPointService);

        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();

        // Update the stockPointService
        StockPointService updatedStockPointService = stockPointServiceRepository.findById(stockPointService.getId()).get();
        // Disconnect from session so that the updates on updatedStockPointService are not directly saved in db
        em.detach(updatedStockPointService);
        updatedStockPointService
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        restStockPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStockPointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStockPointService))
            )
            .andExpect(status().isOk());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
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
    @Transactional
    void putNonExistingStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();
        stockPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockPointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockPointServiceWithPatch() throws Exception {
        // Initialize the database
        stockPointServiceRepository.saveAndFlush(stockPointService);

        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();

        // Update the stockPointService using partial update
        StockPointService partialUpdatedStockPointService = new StockPointService();
        partialUpdatedStockPointService.setId(stockPointService.getId());

        partialUpdatedStockPointService
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        restStockPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPointService))
            )
            .andExpect(status().isOk());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
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
    @Transactional
    void fullUpdateStockPointServiceWithPatch() throws Exception {
        // Initialize the database
        stockPointServiceRepository.saveAndFlush(stockPointService);

        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();

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

        restStockPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPointService))
            )
            .andExpect(status().isOk());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
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
    @Transactional
    void patchNonExistingStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();
        stockPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockPointService() throws Exception {
        int databaseSizeBeforeUpdate = stockPointServiceRepository.findAll().size();
        stockPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockPointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockPointService in the database
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockPointService() throws Exception {
        // Initialize the database
        stockPointServiceRepository.saveAndFlush(stockPointService);

        int databaseSizeBeforeDelete = stockPointServiceRepository.findAll().size();

        // Delete the stockPointService
        restStockPointServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockPointService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockPointService> stockPointServiceList = stockPointServiceRepository.findAll();
        assertThat(stockPointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
