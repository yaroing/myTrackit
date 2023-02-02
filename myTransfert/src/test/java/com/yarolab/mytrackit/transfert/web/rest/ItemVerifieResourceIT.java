package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.ItemVerifie;
import com.yarolab.mytrackit.transfert.repository.ItemVerifieRepository;
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
 * Integration tests for the {@link ItemVerifieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemVerifieResourceIT {

    private static final Integer DEFAULT_MISSION_ID = 1;
    private static final Integer UPDATED_MISSION_ID = 2;

    private static final String DEFAULT_CATALOGUE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGUE_ID = "BBBBBBBBBB";

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
    private MockMvc restItemVerifieMockMvc;

    private ItemVerifie itemVerifie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemVerifie createEntity(EntityManager em) {
        ItemVerifie itemVerifie = new ItemVerifie()
            .missionId(DEFAULT_MISSION_ID)
            .catalogueId(DEFAULT_CATALOGUE_ID)
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
            .missionId(UPDATED_MISSION_ID)
            .catalogueId(UPDATED_CATALOGUE_ID)
            .quantiteTransfert(UPDATED_QUANTITE_TRANSFERT)
            .quantiteRecu(UPDATED_QUANTITE_RECU)
            .quantiteUtilisee(UPDATED_QUANTITE_UTILISEE)
            .quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE)
            .quantiteEcart(UPDATED_QUANTITE_ECART);
        return itemVerifie;
    }

    @BeforeEach
    public void initTest() {
        itemVerifie = createEntity(em);
    }

    @Test
    @Transactional
    void createItemVerifie() throws Exception {
        int databaseSizeBeforeCreate = itemVerifieRepository.findAll().size();
        // Create the ItemVerifie
        restItemVerifieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVerifie)))
            .andExpect(status().isCreated());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeCreate + 1);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getMissionId()).isEqualTo(DEFAULT_MISSION_ID);
        assertThat(testItemVerifie.getCatalogueId()).isEqualTo(DEFAULT_CATALOGUE_ID);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(DEFAULT_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(DEFAULT_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(DEFAULT_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(DEFAULT_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(DEFAULT_QUANTITE_ECART);
    }

    @Test
    @Transactional
    void createItemVerifieWithExistingId() throws Exception {
        // Create the ItemVerifie with an existing ID
        itemVerifie.setId(1L);

        int databaseSizeBeforeCreate = itemVerifieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemVerifieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVerifie)))
            .andExpect(status().isBadRequest());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItemVerifies() throws Exception {
        // Initialize the database
        itemVerifieRepository.saveAndFlush(itemVerifie);

        // Get all the itemVerifieList
        restItemVerifieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemVerifie.getId().intValue())))
            .andExpect(jsonPath("$.[*].missionId").value(hasItem(DEFAULT_MISSION_ID)))
            .andExpect(jsonPath("$.[*].catalogueId").value(hasItem(DEFAULT_CATALOGUE_ID)))
            .andExpect(jsonPath("$.[*].quantiteTransfert").value(hasItem(DEFAULT_QUANTITE_TRANSFERT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteRecu").value(hasItem(DEFAULT_QUANTITE_RECU.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteUtilisee").value(hasItem(DEFAULT_QUANTITE_UTILISEE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteDisponible").value(hasItem(DEFAULT_QUANTITE_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteEcart").value(hasItem(DEFAULT_QUANTITE_ECART.doubleValue())));
    }

    @Test
    @Transactional
    void getItemVerifie() throws Exception {
        // Initialize the database
        itemVerifieRepository.saveAndFlush(itemVerifie);

        // Get the itemVerifie
        restItemVerifieMockMvc
            .perform(get(ENTITY_API_URL_ID, itemVerifie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemVerifie.getId().intValue()))
            .andExpect(jsonPath("$.missionId").value(DEFAULT_MISSION_ID))
            .andExpect(jsonPath("$.catalogueId").value(DEFAULT_CATALOGUE_ID))
            .andExpect(jsonPath("$.quantiteTransfert").value(DEFAULT_QUANTITE_TRANSFERT.doubleValue()))
            .andExpect(jsonPath("$.quantiteRecu").value(DEFAULT_QUANTITE_RECU.doubleValue()))
            .andExpect(jsonPath("$.quantiteUtilisee").value(DEFAULT_QUANTITE_UTILISEE.doubleValue()))
            .andExpect(jsonPath("$.quantiteDisponible").value(DEFAULT_QUANTITE_DISPONIBLE.doubleValue()))
            .andExpect(jsonPath("$.quantiteEcart").value(DEFAULT_QUANTITE_ECART.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingItemVerifie() throws Exception {
        // Get the itemVerifie
        restItemVerifieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItemVerifie() throws Exception {
        // Initialize the database
        itemVerifieRepository.saveAndFlush(itemVerifie);

        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();

        // Update the itemVerifie
        ItemVerifie updatedItemVerifie = itemVerifieRepository.findById(itemVerifie.getId()).get();
        // Disconnect from session so that the updates on updatedItemVerifie are not directly saved in db
        em.detach(updatedItemVerifie);
        updatedItemVerifie
            .missionId(UPDATED_MISSION_ID)
            .catalogueId(UPDATED_CATALOGUE_ID)
            .quantiteTransfert(UPDATED_QUANTITE_TRANSFERT)
            .quantiteRecu(UPDATED_QUANTITE_RECU)
            .quantiteUtilisee(UPDATED_QUANTITE_UTILISEE)
            .quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE)
            .quantiteEcart(UPDATED_QUANTITE_ECART);

        restItemVerifieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItemVerifie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItemVerifie))
            )
            .andExpect(status().isOk());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getMissionId()).isEqualTo(UPDATED_MISSION_ID);
        assertThat(testItemVerifie.getCatalogueId()).isEqualTo(UPDATED_CATALOGUE_ID);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(UPDATED_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(UPDATED_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(UPDATED_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(UPDATED_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(UPDATED_QUANTITE_ECART);
    }

    @Test
    @Transactional
    void putNonExistingItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();
        itemVerifie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemVerifieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemVerifie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemVerifie))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVerifieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemVerifie))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVerifieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemVerifie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemVerifieWithPatch() throws Exception {
        // Initialize the database
        itemVerifieRepository.saveAndFlush(itemVerifie);

        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();

        // Update the itemVerifie using partial update
        ItemVerifie partialUpdatedItemVerifie = new ItemVerifie();
        partialUpdatedItemVerifie.setId(itemVerifie.getId());

        partialUpdatedItemVerifie.quantiteRecu(UPDATED_QUANTITE_RECU).quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE);

        restItemVerifieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemVerifie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemVerifie))
            )
            .andExpect(status().isOk());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getMissionId()).isEqualTo(DEFAULT_MISSION_ID);
        assertThat(testItemVerifie.getCatalogueId()).isEqualTo(DEFAULT_CATALOGUE_ID);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(DEFAULT_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(UPDATED_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(DEFAULT_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(UPDATED_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(DEFAULT_QUANTITE_ECART);
    }

    @Test
    @Transactional
    void fullUpdateItemVerifieWithPatch() throws Exception {
        // Initialize the database
        itemVerifieRepository.saveAndFlush(itemVerifie);

        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();

        // Update the itemVerifie using partial update
        ItemVerifie partialUpdatedItemVerifie = new ItemVerifie();
        partialUpdatedItemVerifie.setId(itemVerifie.getId());

        partialUpdatedItemVerifie
            .missionId(UPDATED_MISSION_ID)
            .catalogueId(UPDATED_CATALOGUE_ID)
            .quantiteTransfert(UPDATED_QUANTITE_TRANSFERT)
            .quantiteRecu(UPDATED_QUANTITE_RECU)
            .quantiteUtilisee(UPDATED_QUANTITE_UTILISEE)
            .quantiteDisponible(UPDATED_QUANTITE_DISPONIBLE)
            .quantiteEcart(UPDATED_QUANTITE_ECART);

        restItemVerifieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemVerifie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemVerifie))
            )
            .andExpect(status().isOk());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
        ItemVerifie testItemVerifie = itemVerifieList.get(itemVerifieList.size() - 1);
        assertThat(testItemVerifie.getMissionId()).isEqualTo(UPDATED_MISSION_ID);
        assertThat(testItemVerifie.getCatalogueId()).isEqualTo(UPDATED_CATALOGUE_ID);
        assertThat(testItemVerifie.getQuantiteTransfert()).isEqualTo(UPDATED_QUANTITE_TRANSFERT);
        assertThat(testItemVerifie.getQuantiteRecu()).isEqualTo(UPDATED_QUANTITE_RECU);
        assertThat(testItemVerifie.getQuantiteUtilisee()).isEqualTo(UPDATED_QUANTITE_UTILISEE);
        assertThat(testItemVerifie.getQuantiteDisponible()).isEqualTo(UPDATED_QUANTITE_DISPONIBLE);
        assertThat(testItemVerifie.getQuantiteEcart()).isEqualTo(UPDATED_QUANTITE_ECART);
    }

    @Test
    @Transactional
    void patchNonExistingItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();
        itemVerifie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemVerifieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemVerifie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemVerifie))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVerifieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemVerifie))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemVerifie() throws Exception {
        int databaseSizeBeforeUpdate = itemVerifieRepository.findAll().size();
        itemVerifie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemVerifieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemVerifie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemVerifie in the database
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemVerifie() throws Exception {
        // Initialize the database
        itemVerifieRepository.saveAndFlush(itemVerifie);

        int databaseSizeBeforeDelete = itemVerifieRepository.findAll().size();

        // Delete the itemVerifie
        restItemVerifieMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemVerifie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemVerifie> itemVerifieList = itemVerifieRepository.findAll();
        assertThat(itemVerifieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
