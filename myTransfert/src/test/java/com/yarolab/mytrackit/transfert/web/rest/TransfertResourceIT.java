package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.Transfert;
import com.yarolab.mytrackit.transfert.repository.TransfertRepository;
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
 * Integration tests for the {@link TransfertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransfertResourceIT {

    private static final Instant DEFAULT_DATE_EXP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_EXP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SECTION_ID = 1;
    private static final Integer UPDATED_SECTION_ID = 2;

    private static final Integer DEFAULT_STAFF_ID = 1;
    private static final Integer UPDATED_STAFF_ID = 2;

    private static final String DEFAULT_PARTENAIRE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARTENAIRE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPORTEUR_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORTEUR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CHAUFFEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CHAUFFEUR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_REC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_REC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUT_REC_ID = 1;
    private static final Integer UPDATED_STATUT_REC_ID = 2;

    private static final String DEFAULT_CPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CPHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transferts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransfertRepository transfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransfertMockMvc;

    private Transfert transfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfert createEntity(EntityManager em) {
        Transfert transfert = new Transfert()
            .dateExp(DEFAULT_DATE_EXP)
            .sectionId(DEFAULT_SECTION_ID)
            .staffId(DEFAULT_STAFF_ID)
            .partenaireId(DEFAULT_PARTENAIRE_ID)
            .transporteurId(DEFAULT_TRANSPORTEUR_ID)
            .nomChauffeur(DEFAULT_NOM_CHAUFFEUR)
            .dateRec(DEFAULT_DATE_REC)
            .statutRecId(DEFAULT_STATUT_REC_ID)
            .cphone(DEFAULT_CPHONE);
        return transfert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfert createUpdatedEntity(EntityManager em) {
        Transfert transfert = new Transfert()
            .dateExp(UPDATED_DATE_EXP)
            .sectionId(UPDATED_SECTION_ID)
            .staffId(UPDATED_STAFF_ID)
            .partenaireId(UPDATED_PARTENAIRE_ID)
            .transporteurId(UPDATED_TRANSPORTEUR_ID)
            .nomChauffeur(UPDATED_NOM_CHAUFFEUR)
            .dateRec(UPDATED_DATE_REC)
            .statutRecId(UPDATED_STATUT_REC_ID)
            .cphone(UPDATED_CPHONE);
        return transfert;
    }

    @BeforeEach
    public void initTest() {
        transfert = createEntity(em);
    }

    @Test
    @Transactional
    void createTransfert() throws Exception {
        int databaseSizeBeforeCreate = transfertRepository.findAll().size();
        // Create the Transfert
        restTransfertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfert)))
            .andExpect(status().isCreated());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeCreate + 1);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(DEFAULT_DATE_EXP);
        assertThat(testTransfert.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testTransfert.getStaffId()).isEqualTo(DEFAULT_STAFF_ID);
        assertThat(testTransfert.getPartenaireId()).isEqualTo(DEFAULT_PARTENAIRE_ID);
        assertThat(testTransfert.getTransporteurId()).isEqualTo(DEFAULT_TRANSPORTEUR_ID);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(DEFAULT_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testTransfert.getStatutRecId()).isEqualTo(DEFAULT_STATUT_REC_ID);
        assertThat(testTransfert.getCphone()).isEqualTo(DEFAULT_CPHONE);
    }

    @Test
    @Transactional
    void createTransfertWithExistingId() throws Exception {
        // Create the Transfert with an existing ID
        transfert.setId(1L);

        int databaseSizeBeforeCreate = transfertRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransfertMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfert)))
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransferts() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        // Get all the transfertList
        restTransfertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateExp").value(hasItem(DEFAULT_DATE_EXP.toString())))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID)))
            .andExpect(jsonPath("$.[*].staffId").value(hasItem(DEFAULT_STAFF_ID)))
            .andExpect(jsonPath("$.[*].partenaireId").value(hasItem(DEFAULT_PARTENAIRE_ID)))
            .andExpect(jsonPath("$.[*].transporteurId").value(hasItem(DEFAULT_TRANSPORTEUR_ID)))
            .andExpect(jsonPath("$.[*].nomChauffeur").value(hasItem(DEFAULT_NOM_CHAUFFEUR)))
            .andExpect(jsonPath("$.[*].dateRec").value(hasItem(DEFAULT_DATE_REC.toString())))
            .andExpect(jsonPath("$.[*].statutRecId").value(hasItem(DEFAULT_STATUT_REC_ID)))
            .andExpect(jsonPath("$.[*].cphone").value(hasItem(DEFAULT_CPHONE)));
    }

    @Test
    @Transactional
    void getTransfert() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        // Get the transfert
        restTransfertMockMvc
            .perform(get(ENTITY_API_URL_ID, transfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transfert.getId().intValue()))
            .andExpect(jsonPath("$.dateExp").value(DEFAULT_DATE_EXP.toString()))
            .andExpect(jsonPath("$.sectionId").value(DEFAULT_SECTION_ID))
            .andExpect(jsonPath("$.staffId").value(DEFAULT_STAFF_ID))
            .andExpect(jsonPath("$.partenaireId").value(DEFAULT_PARTENAIRE_ID))
            .andExpect(jsonPath("$.transporteurId").value(DEFAULT_TRANSPORTEUR_ID))
            .andExpect(jsonPath("$.nomChauffeur").value(DEFAULT_NOM_CHAUFFEUR))
            .andExpect(jsonPath("$.dateRec").value(DEFAULT_DATE_REC.toString()))
            .andExpect(jsonPath("$.statutRecId").value(DEFAULT_STATUT_REC_ID))
            .andExpect(jsonPath("$.cphone").value(DEFAULT_CPHONE));
    }

    @Test
    @Transactional
    void getNonExistingTransfert() throws Exception {
        // Get the transfert
        restTransfertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransfert() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();

        // Update the transfert
        Transfert updatedTransfert = transfertRepository.findById(transfert.getId()).get();
        // Disconnect from session so that the updates on updatedTransfert are not directly saved in db
        em.detach(updatedTransfert);
        updatedTransfert
            .dateExp(UPDATED_DATE_EXP)
            .sectionId(UPDATED_SECTION_ID)
            .staffId(UPDATED_STAFF_ID)
            .partenaireId(UPDATED_PARTENAIRE_ID)
            .transporteurId(UPDATED_TRANSPORTEUR_ID)
            .nomChauffeur(UPDATED_NOM_CHAUFFEUR)
            .dateRec(UPDATED_DATE_REC)
            .statutRecId(UPDATED_STATUT_REC_ID)
            .cphone(UPDATED_CPHONE);

        restTransfertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTransfert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTransfert))
            )
            .andExpect(status().isOk());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testTransfert.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testTransfert.getStaffId()).isEqualTo(UPDATED_STAFF_ID);
        assertThat(testTransfert.getPartenaireId()).isEqualTo(UPDATED_PARTENAIRE_ID);
        assertThat(testTransfert.getTransporteurId()).isEqualTo(UPDATED_TRANSPORTEUR_ID);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(UPDATED_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testTransfert.getStatutRecId()).isEqualTo(UPDATED_STATUT_REC_ID);
        assertThat(testTransfert.getCphone()).isEqualTo(UPDATED_CPHONE);
    }

    @Test
    @Transactional
    void putNonExistingTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();
        transfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransfertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transfert.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransfertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransfertMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransfertWithPatch() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();

        // Update the transfert using partial update
        Transfert partialUpdatedTransfert = new Transfert();
        partialUpdatedTransfert.setId(transfert.getId());

        partialUpdatedTransfert
            .dateExp(UPDATED_DATE_EXP)
            .staffId(UPDATED_STAFF_ID)
            .transporteurId(UPDATED_TRANSPORTEUR_ID)
            .statutRecId(UPDATED_STATUT_REC_ID)
            .cphone(UPDATED_CPHONE);

        restTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfert))
            )
            .andExpect(status().isOk());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testTransfert.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testTransfert.getStaffId()).isEqualTo(UPDATED_STAFF_ID);
        assertThat(testTransfert.getPartenaireId()).isEqualTo(DEFAULT_PARTENAIRE_ID);
        assertThat(testTransfert.getTransporteurId()).isEqualTo(UPDATED_TRANSPORTEUR_ID);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(DEFAULT_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testTransfert.getStatutRecId()).isEqualTo(UPDATED_STATUT_REC_ID);
        assertThat(testTransfert.getCphone()).isEqualTo(UPDATED_CPHONE);
    }

    @Test
    @Transactional
    void fullUpdateTransfertWithPatch() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();

        // Update the transfert using partial update
        Transfert partialUpdatedTransfert = new Transfert();
        partialUpdatedTransfert.setId(transfert.getId());

        partialUpdatedTransfert
            .dateExp(UPDATED_DATE_EXP)
            .sectionId(UPDATED_SECTION_ID)
            .staffId(UPDATED_STAFF_ID)
            .partenaireId(UPDATED_PARTENAIRE_ID)
            .transporteurId(UPDATED_TRANSPORTEUR_ID)
            .nomChauffeur(UPDATED_NOM_CHAUFFEUR)
            .dateRec(UPDATED_DATE_REC)
            .statutRecId(UPDATED_STATUT_REC_ID)
            .cphone(UPDATED_CPHONE);

        restTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfert))
            )
            .andExpect(status().isOk());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testTransfert.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testTransfert.getStaffId()).isEqualTo(UPDATED_STAFF_ID);
        assertThat(testTransfert.getPartenaireId()).isEqualTo(UPDATED_PARTENAIRE_ID);
        assertThat(testTransfert.getTransporteurId()).isEqualTo(UPDATED_TRANSPORTEUR_ID);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(UPDATED_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testTransfert.getStatutRecId()).isEqualTo(UPDATED_STATUT_REC_ID);
        assertThat(testTransfert.getCphone()).isEqualTo(UPDATED_CPHONE);
    }

    @Test
    @Transactional
    void patchNonExistingTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();
        transfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transfert.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transfert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransfertMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transfert))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransfert() throws Exception {
        // Initialize the database
        transfertRepository.saveAndFlush(transfert);

        int databaseSizeBeforeDelete = transfertRepository.findAll().size();

        // Delete the transfert
        restTransfertMockMvc
            .perform(delete(ENTITY_API_URL_ID, transfert.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfert> transfertList = transfertRepository.findAll();
        assertThat(transfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
