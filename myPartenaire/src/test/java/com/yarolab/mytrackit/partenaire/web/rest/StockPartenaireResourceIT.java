package com.yarolab.mytrackit.partenaire.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.partenaire.IntegrationTest;
import com.yarolab.mytrackit.partenaire.domain.StockPartenaire;
import com.yarolab.mytrackit.partenaire.repository.StockPartenaireRepository;
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
 * Integration tests for the {@link StockPartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restStockPartenaireMockMvc;

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

    @BeforeEach
    public void initTest() {
        stockPartenaire = createEntity(em);
    }

    @Test
    @Transactional
    void createStockPartenaire() throws Exception {
        int databaseSizeBeforeCreate = stockPartenaireRepository.findAll().size();
        // Create the StockPartenaire
        restStockPartenaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isCreated());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
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
    @Transactional
    void createStockPartenaireWithExistingId() throws Exception {
        // Create the StockPartenaire with an existing ID
        stockPartenaire.setId(1L);

        int databaseSizeBeforeCreate = stockPartenaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockPartenaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStockPartenaires() throws Exception {
        // Initialize the database
        stockPartenaireRepository.saveAndFlush(stockPartenaire);

        // Get all the stockPartenaireList
        restStockPartenaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockPartenaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockAnnee").value(hasItem(DEFAULT_STOCK_ANNEE)))
            .andExpect(jsonPath("$.[*].stockMois").value(hasItem(DEFAULT_STOCK_MOIS)))
            .andExpect(jsonPath("$.[*].entreeMois").value(hasItem(DEFAULT_ENTREE_MOIS.doubleValue())))
            .andExpect(jsonPath("$.[*].sortieMois").value(hasItem(DEFAULT_SORTIE_MOIS.doubleValue())))
            .andExpect(jsonPath("$.[*].stockFinmois").value(hasItem(DEFAULT_STOCK_FINMOIS.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDebut").value(hasItem(DEFAULT_STOCK_DEBUT.doubleValue())));
    }

    @Test
    @Transactional
    void getStockPartenaire() throws Exception {
        // Initialize the database
        stockPartenaireRepository.saveAndFlush(stockPartenaire);

        // Get the stockPartenaire
        restStockPartenaireMockMvc
            .perform(get(ENTITY_API_URL_ID, stockPartenaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockPartenaire.getId().intValue()))
            .andExpect(jsonPath("$.stockAnnee").value(DEFAULT_STOCK_ANNEE))
            .andExpect(jsonPath("$.stockMois").value(DEFAULT_STOCK_MOIS))
            .andExpect(jsonPath("$.entreeMois").value(DEFAULT_ENTREE_MOIS.doubleValue()))
            .andExpect(jsonPath("$.sortieMois").value(DEFAULT_SORTIE_MOIS.doubleValue()))
            .andExpect(jsonPath("$.stockFinmois").value(DEFAULT_STOCK_FINMOIS.doubleValue()))
            .andExpect(jsonPath("$.stockDebut").value(DEFAULT_STOCK_DEBUT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingStockPartenaire() throws Exception {
        // Get the stockPartenaire
        restStockPartenaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockPartenaire() throws Exception {
        // Initialize the database
        stockPartenaireRepository.saveAndFlush(stockPartenaire);

        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();

        // Update the stockPartenaire
        StockPartenaire updatedStockPartenaire = stockPartenaireRepository.findById(stockPartenaire.getId()).get();
        // Disconnect from session so that the updates on updatedStockPartenaire are not directly saved in db
        em.detach(updatedStockPartenaire);
        updatedStockPartenaire
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .entreeMois(UPDATED_ENTREE_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        restStockPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStockPartenaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStockPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
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
    @Transactional
    void putNonExistingStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockPartenaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockPartenaireWithPatch() throws Exception {
        // Initialize the database
        stockPartenaireRepository.saveAndFlush(stockPartenaire);

        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();

        // Update the stockPartenaire using partial update
        StockPartenaire partialUpdatedStockPartenaire = new StockPartenaire();
        partialUpdatedStockPartenaire.setId(stockPartenaire.getId());

        partialUpdatedStockPartenaire
            .stockAnnee(UPDATED_STOCK_ANNEE)
            .stockMois(UPDATED_STOCK_MOIS)
            .sortieMois(UPDATED_SORTIE_MOIS)
            .stockFinmois(UPDATED_STOCK_FINMOIS)
            .stockDebut(UPDATED_STOCK_DEBUT);

        restStockPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
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
    @Transactional
    void fullUpdateStockPartenaireWithPatch() throws Exception {
        // Initialize the database
        stockPartenaireRepository.saveAndFlush(stockPartenaire);

        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();

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

        restStockPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
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
    @Transactional
    void patchNonExistingStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = stockPartenaireRepository.findAll().size();
        stockPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockPartenaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockPartenaire in the database
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockPartenaire() throws Exception {
        // Initialize the database
        stockPartenaireRepository.saveAndFlush(stockPartenaire);

        int databaseSizeBeforeDelete = stockPartenaireRepository.findAll().size();

        // Delete the stockPartenaire
        restStockPartenaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockPartenaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockPartenaire> stockPartenaireList = stockPartenaireRepository.findAll();
        assertThat(stockPartenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
