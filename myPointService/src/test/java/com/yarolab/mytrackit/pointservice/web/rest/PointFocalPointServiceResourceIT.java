package com.yarolab.mytrackit.pointservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.pointservice.IntegrationTest;
import com.yarolab.mytrackit.pointservice.domain.PointFocalPointService;
import com.yarolab.mytrackit.pointservice.repository.PointFocalPointServiceRepository;
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
 * Integration tests for the {@link PointFocalPointServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointFocalPointServiceResourceIT {

    private static final Integer DEFAULT_POINT_OS_ID = 1;
    private static final Integer UPDATED_POINT_OS_ID = 2;

    private static final String DEFAULT_NOM_PF = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_PF = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_PF = "BBBBBBBBBB";

    private static final String DEFAULT_GSM_PF = "AAAAAAAAAA";
    private static final String UPDATED_GSM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PF = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-focal-point-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointFocalPointServiceRepository pointFocalPointServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointFocalPointServiceMockMvc;

    private PointFocalPointService pointFocalPointService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPointService createEntity(EntityManager em) {
        PointFocalPointService pointFocalPointService = new PointFocalPointService()
            .pointOsId(DEFAULT_POINT_OS_ID)
            .nomPf(DEFAULT_NOM_PF)
            .fonctionPf(DEFAULT_FONCTION_PF)
            .gsmPf(DEFAULT_GSM_PF)
            .emailPf(DEFAULT_EMAIL_PF);
        return pointFocalPointService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPointService createUpdatedEntity(EntityManager em) {
        PointFocalPointService pointFocalPointService = new PointFocalPointService()
            .pointOsId(UPDATED_POINT_OS_ID)
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);
        return pointFocalPointService;
    }

    @BeforeEach
    public void initTest() {
        pointFocalPointService = createEntity(em);
    }

    @Test
    @Transactional
    void createPointFocalPointService() throws Exception {
        int databaseSizeBeforeCreate = pointFocalPointServiceRepository.findAll().size();
        // Create the PointFocalPointService
        restPointFocalPointServiceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isCreated());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getPointOsId()).isEqualTo(DEFAULT_POINT_OS_ID);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(DEFAULT_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    @Transactional
    void createPointFocalPointServiceWithExistingId() throws Exception {
        // Create the PointFocalPointService with an existing ID
        pointFocalPointService.setId(1L);

        int databaseSizeBeforeCreate = pointFocalPointServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointFocalPointServiceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPointFocalPointServices() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.saveAndFlush(pointFocalPointService);

        // Get all the pointFocalPointServiceList
        restPointFocalPointServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointFocalPointService.getId().intValue())))
            .andExpect(jsonPath("$.[*].pointOsId").value(hasItem(DEFAULT_POINT_OS_ID)))
            .andExpect(jsonPath("$.[*].nomPf").value(hasItem(DEFAULT_NOM_PF)))
            .andExpect(jsonPath("$.[*].fonctionPf").value(hasItem(DEFAULT_FONCTION_PF)))
            .andExpect(jsonPath("$.[*].gsmPf").value(hasItem(DEFAULT_GSM_PF)))
            .andExpect(jsonPath("$.[*].emailPf").value(hasItem(DEFAULT_EMAIL_PF)));
    }

    @Test
    @Transactional
    void getPointFocalPointService() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.saveAndFlush(pointFocalPointService);

        // Get the pointFocalPointService
        restPointFocalPointServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, pointFocalPointService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointFocalPointService.getId().intValue()))
            .andExpect(jsonPath("$.pointOsId").value(DEFAULT_POINT_OS_ID))
            .andExpect(jsonPath("$.nomPf").value(DEFAULT_NOM_PF))
            .andExpect(jsonPath("$.fonctionPf").value(DEFAULT_FONCTION_PF))
            .andExpect(jsonPath("$.gsmPf").value(DEFAULT_GSM_PF))
            .andExpect(jsonPath("$.emailPf").value(DEFAULT_EMAIL_PF));
    }

    @Test
    @Transactional
    void getNonExistingPointFocalPointService() throws Exception {
        // Get the pointFocalPointService
        restPointFocalPointServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointFocalPointService() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.saveAndFlush(pointFocalPointService);

        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();

        // Update the pointFocalPointService
        PointFocalPointService updatedPointFocalPointService = pointFocalPointServiceRepository
            .findById(pointFocalPointService.getId())
            .get();
        // Disconnect from session so that the updates on updatedPointFocalPointService are not directly saved in db
        em.detach(updatedPointFocalPointService);
        updatedPointFocalPointService
            .pointOsId(UPDATED_POINT_OS_ID)
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);

        restPointFocalPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPointFocalPointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPointFocalPointService))
            )
            .andExpect(status().isOk());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getPointOsId()).isEqualTo(UPDATED_POINT_OS_ID);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    @Transactional
    void putNonExistingPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointFocalPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointFocalPointService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPointServiceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointFocalPointServiceWithPatch() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.saveAndFlush(pointFocalPointService);

        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();

        // Update the pointFocalPointService using partial update
        PointFocalPointService partialUpdatedPointFocalPointService = new PointFocalPointService();
        partialUpdatedPointFocalPointService.setId(pointFocalPointService.getId());

        partialUpdatedPointFocalPointService.fonctionPf(UPDATED_FONCTION_PF).gsmPf(UPDATED_GSM_PF).emailPf(UPDATED_EMAIL_PF);

        restPointFocalPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointFocalPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPointService))
            )
            .andExpect(status().isOk());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getPointOsId()).isEqualTo(DEFAULT_POINT_OS_ID);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    @Transactional
    void fullUpdatePointFocalPointServiceWithPatch() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.saveAndFlush(pointFocalPointService);

        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();

        // Update the pointFocalPointService using partial update
        PointFocalPointService partialUpdatedPointFocalPointService = new PointFocalPointService();
        partialUpdatedPointFocalPointService.setId(pointFocalPointService.getId());

        partialUpdatedPointFocalPointService
            .pointOsId(UPDATED_POINT_OS_ID)
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);

        restPointFocalPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointFocalPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPointService))
            )
            .andExpect(status().isOk());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPointService testPointFocalPointService = pointFocalPointServiceList.get(pointFocalPointServiceList.size() - 1);
        assertThat(testPointFocalPointService.getPointOsId()).isEqualTo(UPDATED_POINT_OS_ID);
        assertThat(testPointFocalPointService.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPointService.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPointService.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPointService.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    @Transactional
    void patchNonExistingPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointFocalPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointFocalPointService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointFocalPointService() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPointServiceRepository.findAll().size();
        pointFocalPointService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointFocalPointServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointFocalPointService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointFocalPointService in the database
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointFocalPointService() throws Exception {
        // Initialize the database
        pointFocalPointServiceRepository.saveAndFlush(pointFocalPointService);

        int databaseSizeBeforeDelete = pointFocalPointServiceRepository.findAll().size();

        // Delete the pointFocalPointService
        restPointFocalPointServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointFocalPointService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointFocalPointService> pointFocalPointServiceList = pointFocalPointServiceRepository.findAll();
        assertThat(pointFocalPointServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
