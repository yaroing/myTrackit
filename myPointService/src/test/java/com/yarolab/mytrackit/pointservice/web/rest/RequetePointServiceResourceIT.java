package com.yarolab.mytrackit.pointservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.pointservice.IntegrationTest;
import com.yarolab.mytrackit.pointservice.domain.RequetePointService;
import com.yarolab.mytrackit.pointservice.repository.RequetePointServiceRepository;
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
 * Integration tests for the {@link RequetePointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restRequetePointServiceMockMvc;

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

    @BeforeEach
    public void initTest() {
        requetePointService = createEntity(em);
    }

    @Test
    @Transactional
    void createRequetePointService() throws Exception {
        int databaseSizeBeforeCreate = requetePointServiceRepository.findAll().size();
        // Create the RequetePointService
        restRequetePointServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isCreated());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
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
    @Transactional
    void createRequetePointServiceWithExistingId() throws Exception {
        // Create the RequetePointService with an existing ID
        requetePointService.setId(1L);

        int databaseSizeBeforeCreate = requetePointServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequetePointServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequetePointServices() throws Exception {
        // Initialize the database
        requetePointServiceRepository.saveAndFlush(requetePointService);

        // Get all the requetePointServiceList
        restRequetePointServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requetePointService.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockDisponible").value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantDem").value(hasItem(DEFAULT_QUANT_DEM.doubleValue())))
            .andExpect(jsonPath("$.[*].quantTrs").value(hasItem(DEFAULT_QUANT_TRS.doubleValue())))
            .andExpect(jsonPath("$.[*].quantRec").value(hasItem(DEFAULT_QUANT_REC.doubleValue())))
            .andExpect(jsonPath("$.[*].reqTraitee").value(hasItem(DEFAULT_REQ_TRAITEE)))
            .andExpect(jsonPath("$.[*].dateReq").value(hasItem(DEFAULT_DATE_REQ.toString())))
            .andExpect(jsonPath("$.[*].dateRec").value(hasItem(DEFAULT_DATE_REC.toString())))
            .andExpect(jsonPath("$.[*].dateTransfert").value(hasItem(DEFAULT_DATE_TRANSFERT.toString())));
    }

    @Test
    @Transactional
    void getRequetePointService() throws Exception {
        // Initialize the database
        requetePointServiceRepository.saveAndFlush(requetePointService);

        // Get the requetePointService
        restRequetePointServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, requetePointService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requetePointService.getId().intValue()))
            .andExpect(jsonPath("$.stockDisponible").value(DEFAULT_STOCK_DISPONIBLE.doubleValue()))
            .andExpect(jsonPath("$.quantDem").value(DEFAULT_QUANT_DEM.doubleValue()))
            .andExpect(jsonPath("$.quantTrs").value(DEFAULT_QUANT_TRS.doubleValue()))
            .andExpect(jsonPath("$.quantRec").value(DEFAULT_QUANT_REC.doubleValue()))
            .andExpect(jsonPath("$.reqTraitee").value(DEFAULT_REQ_TRAITEE))
            .andExpect(jsonPath("$.dateReq").value(DEFAULT_DATE_REQ.toString()))
            .andExpect(jsonPath("$.dateRec").value(DEFAULT_DATE_REC.toString()))
            .andExpect(jsonPath("$.dateTransfert").value(DEFAULT_DATE_TRANSFERT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRequetePointService() throws Exception {
        // Get the requetePointService
        restRequetePointServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequetePointService() throws Exception {
        // Initialize the database
        requetePointServiceRepository.saveAndFlush(requetePointService);

        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();

        // Update the requetePointService
        RequetePointService updatedRequetePointService = requetePointServiceRepository.findById(requetePointService.getId()).get();
        // Disconnect from session so that the updates on updatedRequetePointService are not directly saved in db
        em.detach(updatedRequetePointService);
        updatedRequetePointService
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .quantDem(UPDATED_QUANT_DEM)
            .quantTrs(UPDATED_QUANT_TRS)
            .quantRec(UPDATED_QUANT_REC)
            .reqTraitee(UPDATED_REQ_TRAITEE)
            .dateReq(UPDATED_DATE_REQ)
            .dateRec(UPDATED_DATE_REC)
            .dateTransfert(UPDATED_DATE_TRANSFERT);

        restRequetePointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequetePointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRequetePointService))
            )
            .andExpect(status().isOk());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
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
    @Transactional
    void putNonExistingRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();
        requetePointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequetePointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requetePointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePointServiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequetePointServiceWithPatch() throws Exception {
        // Initialize the database
        requetePointServiceRepository.saveAndFlush(requetePointService);

        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();

        // Update the requetePointService using partial update
        RequetePointService partialUpdatedRequetePointService = new RequetePointService();
        partialUpdatedRequetePointService.setId(requetePointService.getId());

        partialUpdatedRequetePointService
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .quantDem(UPDATED_QUANT_DEM)
            .quantRec(UPDATED_QUANT_REC)
            .dateReq(UPDATED_DATE_REQ)
            .dateTransfert(UPDATED_DATE_TRANSFERT);

        restRequetePointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequetePointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePointService))
            )
            .andExpect(status().isOk());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
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
    @Transactional
    void fullUpdateRequetePointServiceWithPatch() throws Exception {
        // Initialize the database
        requetePointServiceRepository.saveAndFlush(requetePointService);

        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();

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

        restRequetePointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequetePointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePointService))
            )
            .andExpect(status().isOk());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
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
    @Transactional
    void patchNonExistingRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();
        requetePointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequetePointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requetePointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequetePointService() throws Exception {
        int databaseSizeBeforeUpdate = requetePointServiceRepository.findAll().size();
        requetePointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequetePointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requetePointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequetePointService in the database
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequetePointService() throws Exception {
        // Initialize the database
        requetePointServiceRepository.saveAndFlush(requetePointService);

        int databaseSizeBeforeDelete = requetePointServiceRepository.findAll().size();

        // Delete the requetePointService
        restRequetePointServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, requetePointService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequetePointService> requetePointServiceList = requetePointServiceRepository.findAll();
        assertThat(requetePointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
