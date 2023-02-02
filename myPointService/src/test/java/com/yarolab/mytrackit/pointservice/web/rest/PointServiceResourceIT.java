package com.yarolab.mytrackit.pointservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.pointservice.IntegrationTest;
import com.yarolab.mytrackit.pointservice.domain.PointService;
import com.yarolab.mytrackit.pointservice.repository.PointServiceRepository;
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
 * Integration tests for the {@link PointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointServiceResourceIT {

    private static final String DEFAULT_NOM_POS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_POS = "BBBBBBBBBB";

    private static final Double DEFAULT_POS_LON = 1D;
    private static final Double UPDATED_POS_LON = 2D;

    private static final Double DEFAULT_POS_LAT = 1D;
    private static final Double UPDATED_POS_LAT = 2D;

    private static final String DEFAULT_POS_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_POS_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_POS_GSM = "AAAAAAAAAA";
    private static final String UPDATED_POS_GSM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointServiceRepository pointServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointServiceMockMvc;

    private PointService pointService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointService createEntity(EntityManager em) {
        PointService pointService = new PointService()
            .nomPos(DEFAULT_NOM_POS)
            .posLon(DEFAULT_POS_LON)
            .posLat(DEFAULT_POS_LAT)
            .posContact(DEFAULT_POS_CONTACT)
            .posGsm(DEFAULT_POS_GSM);
        return pointService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointService createUpdatedEntity(EntityManager em) {
        PointService pointService = new PointService()
            .nomPos(UPDATED_NOM_POS)
            .posLon(UPDATED_POS_LON)
            .posLat(UPDATED_POS_LAT)
            .posContact(UPDATED_POS_CONTACT)
            .posGsm(UPDATED_POS_GSM);
        return pointService;
    }

    @BeforeEach
    public void initTest() {
        pointService = createEntity(em);
    }

    @Test
    @Transactional
    void createPointService() throws Exception {
        int databaseSizeBeforeCreate = pointServiceRepository.findAll().size();
        // Create the PointService
        restPointServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointService)))
            .andExpect(status().isCreated());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(DEFAULT_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(DEFAULT_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(DEFAULT_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(DEFAULT_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(DEFAULT_POS_GSM);
    }

    @Test
    @Transactional
    void createPointServiceWithExistingId() throws Exception {
        // Create the PointService with an existing ID
        pointService.setId(1L);

        int databaseSizeBeforeCreate = pointServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointService)))
            .andExpect(status().isBadRequest());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPointServices() throws Exception {
        // Initialize the database
        pointServiceRepository.saveAndFlush(pointService);

        // Get all the pointServiceList
        restPointServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointService.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPos").value(hasItem(DEFAULT_NOM_POS)))
            .andExpect(jsonPath("$.[*].posLon").value(hasItem(DEFAULT_POS_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].posLat").value(hasItem(DEFAULT_POS_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].posContact").value(hasItem(DEFAULT_POS_CONTACT)))
            .andExpect(jsonPath("$.[*].posGsm").value(hasItem(DEFAULT_POS_GSM)));
    }

    @Test
    @Transactional
    void getPointService() throws Exception {
        // Initialize the database
        pointServiceRepository.saveAndFlush(pointService);

        // Get the pointService
        restPointServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, pointService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointService.getId().intValue()))
            .andExpect(jsonPath("$.nomPos").value(DEFAULT_NOM_POS))
            .andExpect(jsonPath("$.posLon").value(DEFAULT_POS_LON.doubleValue()))
            .andExpect(jsonPath("$.posLat").value(DEFAULT_POS_LAT.doubleValue()))
            .andExpect(jsonPath("$.posContact").value(DEFAULT_POS_CONTACT))
            .andExpect(jsonPath("$.posGsm").value(DEFAULT_POS_GSM));
    }

    @Test
    @Transactional
    void getNonExistingPointService() throws Exception {
        // Get the pointService
        restPointServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointService() throws Exception {
        // Initialize the database
        pointServiceRepository.saveAndFlush(pointService);

        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();

        // Update the pointService
        PointService updatedPointService = pointServiceRepository.findById(pointService.getId()).get();
        // Disconnect from session so that the updates on updatedPointService are not directly saved in db
        em.detach(updatedPointService);
        updatedPointService
            .nomPos(UPDATED_NOM_POS)
            .posLon(UPDATED_POS_LON)
            .posLat(UPDATED_POS_LAT)
            .posContact(UPDATED_POS_CONTACT)
            .posGsm(UPDATED_POS_GSM);

        restPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPointService))
            )
            .andExpect(status().isOk());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(UPDATED_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(UPDATED_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(UPDATED_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(UPDATED_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(UPDATED_POS_GSM);
    }

    @Test
    @Transactional
    void putNonExistingPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();
        pointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointService)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointServiceWithPatch() throws Exception {
        // Initialize the database
        pointServiceRepository.saveAndFlush(pointService);

        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();

        // Update the pointService using partial update
        PointService partialUpdatedPointService = new PointService();
        partialUpdatedPointService.setId(pointService.getId());

        partialUpdatedPointService.nomPos(UPDATED_NOM_POS).posLat(UPDATED_POS_LAT).posContact(UPDATED_POS_CONTACT);

        restPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointService))
            )
            .andExpect(status().isOk());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(UPDATED_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(DEFAULT_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(UPDATED_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(UPDATED_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(DEFAULT_POS_GSM);
    }

    @Test
    @Transactional
    void fullUpdatePointServiceWithPatch() throws Exception {
        // Initialize the database
        pointServiceRepository.saveAndFlush(pointService);

        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();

        // Update the pointService using partial update
        PointService partialUpdatedPointService = new PointService();
        partialUpdatedPointService.setId(pointService.getId());

        partialUpdatedPointService
            .nomPos(UPDATED_NOM_POS)
            .posLon(UPDATED_POS_LON)
            .posLat(UPDATED_POS_LAT)
            .posContact(UPDATED_POS_CONTACT)
            .posGsm(UPDATED_POS_GSM);

        restPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointService))
            )
            .andExpect(status().isOk());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointService testPointService = pointServiceList.get(pointServiceList.size() - 1);
        assertThat(testPointService.getNomPos()).isEqualTo(UPDATED_NOM_POS);
        assertThat(testPointService.getPosLon()).isEqualTo(UPDATED_POS_LON);
        assertThat(testPointService.getPosLat()).isEqualTo(UPDATED_POS_LAT);
        assertThat(testPointService.getPosContact()).isEqualTo(UPDATED_POS_CONTACT);
        assertThat(testPointService.getPosGsm()).isEqualTo(UPDATED_POS_GSM);
    }

    @Test
    @Transactional
    void patchNonExistingPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();
        pointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointServiceRepository.findAll().size();
        pointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointService in the database
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointService() throws Exception {
        // Initialize the database
        pointServiceRepository.saveAndFlush(pointService);

        int databaseSizeBeforeDelete = pointServiceRepository.findAll().size();

        // Delete the pointService
        restPointServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointService> pointServiceList = pointServiceRepository.findAll();
        assertThat(pointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
