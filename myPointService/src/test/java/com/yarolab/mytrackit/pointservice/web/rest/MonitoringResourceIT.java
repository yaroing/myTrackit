package com.yarolab.mytrackit.pointservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.pointservice.IntegrationTest;
import com.yarolab.mytrackit.pointservice.domain.Monitoring;
import com.yarolab.mytrackit.pointservice.repository.MonitoringRepository;
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
 * Integration tests for the {@link MonitoringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MonitoringResourceIT {

    private static final Integer DEFAULT_POINT_OS_ID = 1;
    private static final Integer UPDATED_POINT_OS_ID = 2;

    private static final Integer DEFAULT_ATPE_ANNEE = 1;
    private static final Integer UPDATED_ATPE_ANNEE = 2;

    private static final Integer DEFAULT_ATPE_MOIS = 1;
    private static final Integer UPDATED_ATPE_MOIS = 2;

    private static final String DEFAULT_ATPE_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_ATPE_STOCK = "BBBBBBBBBB";

    private static final Double DEFAULT_ATPE_DISPO = 1D;
    private static final Double UPDATED_ATPE_DISPO = 2D;

    private static final Double DEFAULT_ATPE_ENDOM = 1D;
    private static final Double UPDATED_ATPE_ENDOM = 2D;

    private static final Double DEFAULT_ATPE_PERIME = 1D;
    private static final Double UPDATED_ATPE_PERIME = 2D;

    private static final String DEFAULT_ATPE_RUPTURE = "AAAAAAAAAA";
    private static final String UPDATED_ATPE_RUPTURE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATPE_NJOUR = 1;
    private static final Integer UPDATED_ATPE_NJOUR = 2;

    private static final String DEFAULT_ATPE_MAGASIN = "AAAAAAAAAA";
    private static final String UPDATED_ATPE_MAGASIN = "BBBBBBBBBB";

    private static final String DEFAULT_ATPE_PALETTE = "AAAAAAAAAA";
    private static final String UPDATED_ATPE_PALETTE = "BBBBBBBBBB";

    private static final String DEFAULT_ATPE_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_ATPE_POSITION = "BBBBBBBBBB";

    private static final Double DEFAULT_ATPE_HAUTEUR = 1D;
    private static final Double UPDATED_ATPE_HAUTEUR = 2D;

    private static final String DEFAULT_ATPE_PERSONNEL = "AAAAAAAAAA";
    private static final String UPDATED_ATPE_PERSONNEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATPE_ADMISSION = 1;
    private static final Integer UPDATED_ATPE_ADMISSION = 2;

    private static final Integer DEFAULT_ATPE_SORTIE = 1;
    private static final Integer UPDATED_ATPE_SORTIE = 2;

    private static final Integer DEFAULT_ATPE_GUERIS = 1;
    private static final Integer UPDATED_ATPE_GUERIS = 2;

    private static final Integer DEFAULT_ATPE_ABANDON = 1;
    private static final Integer UPDATED_ATPE_ABANDON = 2;

    private static final Integer DEFAULT_ATPE_POIDS = 1;
    private static final Integer UPDATED_ATPE_POIDS = 2;

    private static final Integer DEFAULT_ATPE_TRASNSFERT = 1;
    private static final Integer UPDATED_ATPE_TRASNSFERT = 2;

    private static final Integer DEFAULT_ATPE_PARENT = 1;
    private static final Integer UPDATED_ATPE_PARENT = 2;

    private static final String ENTITY_API_URL = "/api/monitorings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonitoringMockMvc;

    private Monitoring monitoring;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monitoring createEntity(EntityManager em) {
        Monitoring monitoring = new Monitoring()
            .pointOsId(DEFAULT_POINT_OS_ID)
            .atpeAnnee(DEFAULT_ATPE_ANNEE)
            .atpeMois(DEFAULT_ATPE_MOIS)
            .atpeStock(DEFAULT_ATPE_STOCK)
            .atpeDispo(DEFAULT_ATPE_DISPO)
            .atpeEndom(DEFAULT_ATPE_ENDOM)
            .atpePerime(DEFAULT_ATPE_PERIME)
            .atpeRupture(DEFAULT_ATPE_RUPTURE)
            .atpeNjour(DEFAULT_ATPE_NJOUR)
            .atpeMagasin(DEFAULT_ATPE_MAGASIN)
            .atpePalette(DEFAULT_ATPE_PALETTE)
            .atpePosition(DEFAULT_ATPE_POSITION)
            .atpeHauteur(DEFAULT_ATPE_HAUTEUR)
            .atpePersonnel(DEFAULT_ATPE_PERSONNEL)
            .atpeAdmission(DEFAULT_ATPE_ADMISSION)
            .atpeSortie(DEFAULT_ATPE_SORTIE)
            .atpeGueris(DEFAULT_ATPE_GUERIS)
            .atpeAbandon(DEFAULT_ATPE_ABANDON)
            .atpePoids(DEFAULT_ATPE_POIDS)
            .atpeTrasnsfert(DEFAULT_ATPE_TRASNSFERT)
            .atpeParent(DEFAULT_ATPE_PARENT);
        return monitoring;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monitoring createUpdatedEntity(EntityManager em) {
        Monitoring monitoring = new Monitoring()
            .pointOsId(UPDATED_POINT_OS_ID)
            .atpeAnnee(UPDATED_ATPE_ANNEE)
            .atpeMois(UPDATED_ATPE_MOIS)
            .atpeStock(UPDATED_ATPE_STOCK)
            .atpeDispo(UPDATED_ATPE_DISPO)
            .atpeEndom(UPDATED_ATPE_ENDOM)
            .atpePerime(UPDATED_ATPE_PERIME)
            .atpeRupture(UPDATED_ATPE_RUPTURE)
            .atpeNjour(UPDATED_ATPE_NJOUR)
            .atpeMagasin(UPDATED_ATPE_MAGASIN)
            .atpePalette(UPDATED_ATPE_PALETTE)
            .atpePosition(UPDATED_ATPE_POSITION)
            .atpeHauteur(UPDATED_ATPE_HAUTEUR)
            .atpePersonnel(UPDATED_ATPE_PERSONNEL)
            .atpeAdmission(UPDATED_ATPE_ADMISSION)
            .atpeSortie(UPDATED_ATPE_SORTIE)
            .atpeGueris(UPDATED_ATPE_GUERIS)
            .atpeAbandon(UPDATED_ATPE_ABANDON)
            .atpePoids(UPDATED_ATPE_POIDS)
            .atpeTrasnsfert(UPDATED_ATPE_TRASNSFERT)
            .atpeParent(UPDATED_ATPE_PARENT);
        return monitoring;
    }

    @BeforeEach
    public void initTest() {
        monitoring = createEntity(em);
    }

    @Test
    @Transactional
    void createMonitoring() throws Exception {
        int databaseSizeBeforeCreate = monitoringRepository.findAll().size();
        // Create the Monitoring
        restMonitoringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monitoring)))
            .andExpect(status().isCreated());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeCreate + 1);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
        assertThat(testMonitoring.getPointOsId()).isEqualTo(DEFAULT_POINT_OS_ID);
        assertThat(testMonitoring.getAtpeAnnee()).isEqualTo(DEFAULT_ATPE_ANNEE);
        assertThat(testMonitoring.getAtpeMois()).isEqualTo(DEFAULT_ATPE_MOIS);
        assertThat(testMonitoring.getAtpeStock()).isEqualTo(DEFAULT_ATPE_STOCK);
        assertThat(testMonitoring.getAtpeDispo()).isEqualTo(DEFAULT_ATPE_DISPO);
        assertThat(testMonitoring.getAtpeEndom()).isEqualTo(DEFAULT_ATPE_ENDOM);
        assertThat(testMonitoring.getAtpePerime()).isEqualTo(DEFAULT_ATPE_PERIME);
        assertThat(testMonitoring.getAtpeRupture()).isEqualTo(DEFAULT_ATPE_RUPTURE);
        assertThat(testMonitoring.getAtpeNjour()).isEqualTo(DEFAULT_ATPE_NJOUR);
        assertThat(testMonitoring.getAtpeMagasin()).isEqualTo(DEFAULT_ATPE_MAGASIN);
        assertThat(testMonitoring.getAtpePalette()).isEqualTo(DEFAULT_ATPE_PALETTE);
        assertThat(testMonitoring.getAtpePosition()).isEqualTo(DEFAULT_ATPE_POSITION);
        assertThat(testMonitoring.getAtpeHauteur()).isEqualTo(DEFAULT_ATPE_HAUTEUR);
        assertThat(testMonitoring.getAtpePersonnel()).isEqualTo(DEFAULT_ATPE_PERSONNEL);
        assertThat(testMonitoring.getAtpeAdmission()).isEqualTo(DEFAULT_ATPE_ADMISSION);
        assertThat(testMonitoring.getAtpeSortie()).isEqualTo(DEFAULT_ATPE_SORTIE);
        assertThat(testMonitoring.getAtpeGueris()).isEqualTo(DEFAULT_ATPE_GUERIS);
        assertThat(testMonitoring.getAtpeAbandon()).isEqualTo(DEFAULT_ATPE_ABANDON);
        assertThat(testMonitoring.getAtpePoids()).isEqualTo(DEFAULT_ATPE_POIDS);
        assertThat(testMonitoring.getAtpeTrasnsfert()).isEqualTo(DEFAULT_ATPE_TRASNSFERT);
        assertThat(testMonitoring.getAtpeParent()).isEqualTo(DEFAULT_ATPE_PARENT);
    }

    @Test
    @Transactional
    void createMonitoringWithExistingId() throws Exception {
        // Create the Monitoring with an existing ID
        monitoring.setId(1L);

        int databaseSizeBeforeCreate = monitoringRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonitoringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monitoring)))
            .andExpect(status().isBadRequest());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMonitorings() throws Exception {
        // Initialize the database
        monitoringRepository.saveAndFlush(monitoring);

        // Get all the monitoringList
        restMonitoringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monitoring.getId().intValue())))
            .andExpect(jsonPath("$.[*].pointOsId").value(hasItem(DEFAULT_POINT_OS_ID)))
            .andExpect(jsonPath("$.[*].atpeAnnee").value(hasItem(DEFAULT_ATPE_ANNEE)))
            .andExpect(jsonPath("$.[*].atpeMois").value(hasItem(DEFAULT_ATPE_MOIS)))
            .andExpect(jsonPath("$.[*].atpeStock").value(hasItem(DEFAULT_ATPE_STOCK)))
            .andExpect(jsonPath("$.[*].atpeDispo").value(hasItem(DEFAULT_ATPE_DISPO.doubleValue())))
            .andExpect(jsonPath("$.[*].atpeEndom").value(hasItem(DEFAULT_ATPE_ENDOM.doubleValue())))
            .andExpect(jsonPath("$.[*].atpePerime").value(hasItem(DEFAULT_ATPE_PERIME.doubleValue())))
            .andExpect(jsonPath("$.[*].atpeRupture").value(hasItem(DEFAULT_ATPE_RUPTURE)))
            .andExpect(jsonPath("$.[*].atpeNjour").value(hasItem(DEFAULT_ATPE_NJOUR)))
            .andExpect(jsonPath("$.[*].atpeMagasin").value(hasItem(DEFAULT_ATPE_MAGASIN)))
            .andExpect(jsonPath("$.[*].atpePalette").value(hasItem(DEFAULT_ATPE_PALETTE)))
            .andExpect(jsonPath("$.[*].atpePosition").value(hasItem(DEFAULT_ATPE_POSITION)))
            .andExpect(jsonPath("$.[*].atpeHauteur").value(hasItem(DEFAULT_ATPE_HAUTEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].atpePersonnel").value(hasItem(DEFAULT_ATPE_PERSONNEL)))
            .andExpect(jsonPath("$.[*].atpeAdmission").value(hasItem(DEFAULT_ATPE_ADMISSION)))
            .andExpect(jsonPath("$.[*].atpeSortie").value(hasItem(DEFAULT_ATPE_SORTIE)))
            .andExpect(jsonPath("$.[*].atpeGueris").value(hasItem(DEFAULT_ATPE_GUERIS)))
            .andExpect(jsonPath("$.[*].atpeAbandon").value(hasItem(DEFAULT_ATPE_ABANDON)))
            .andExpect(jsonPath("$.[*].atpePoids").value(hasItem(DEFAULT_ATPE_POIDS)))
            .andExpect(jsonPath("$.[*].atpeTrasnsfert").value(hasItem(DEFAULT_ATPE_TRASNSFERT)))
            .andExpect(jsonPath("$.[*].atpeParent").value(hasItem(DEFAULT_ATPE_PARENT)));
    }

    @Test
    @Transactional
    void getMonitoring() throws Exception {
        // Initialize the database
        monitoringRepository.saveAndFlush(monitoring);

        // Get the monitoring
        restMonitoringMockMvc
            .perform(get(ENTITY_API_URL_ID, monitoring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monitoring.getId().intValue()))
            .andExpect(jsonPath("$.pointOsId").value(DEFAULT_POINT_OS_ID))
            .andExpect(jsonPath("$.atpeAnnee").value(DEFAULT_ATPE_ANNEE))
            .andExpect(jsonPath("$.atpeMois").value(DEFAULT_ATPE_MOIS))
            .andExpect(jsonPath("$.atpeStock").value(DEFAULT_ATPE_STOCK))
            .andExpect(jsonPath("$.atpeDispo").value(DEFAULT_ATPE_DISPO.doubleValue()))
            .andExpect(jsonPath("$.atpeEndom").value(DEFAULT_ATPE_ENDOM.doubleValue()))
            .andExpect(jsonPath("$.atpePerime").value(DEFAULT_ATPE_PERIME.doubleValue()))
            .andExpect(jsonPath("$.atpeRupture").value(DEFAULT_ATPE_RUPTURE))
            .andExpect(jsonPath("$.atpeNjour").value(DEFAULT_ATPE_NJOUR))
            .andExpect(jsonPath("$.atpeMagasin").value(DEFAULT_ATPE_MAGASIN))
            .andExpect(jsonPath("$.atpePalette").value(DEFAULT_ATPE_PALETTE))
            .andExpect(jsonPath("$.atpePosition").value(DEFAULT_ATPE_POSITION))
            .andExpect(jsonPath("$.atpeHauteur").value(DEFAULT_ATPE_HAUTEUR.doubleValue()))
            .andExpect(jsonPath("$.atpePersonnel").value(DEFAULT_ATPE_PERSONNEL))
            .andExpect(jsonPath("$.atpeAdmission").value(DEFAULT_ATPE_ADMISSION))
            .andExpect(jsonPath("$.atpeSortie").value(DEFAULT_ATPE_SORTIE))
            .andExpect(jsonPath("$.atpeGueris").value(DEFAULT_ATPE_GUERIS))
            .andExpect(jsonPath("$.atpeAbandon").value(DEFAULT_ATPE_ABANDON))
            .andExpect(jsonPath("$.atpePoids").value(DEFAULT_ATPE_POIDS))
            .andExpect(jsonPath("$.atpeTrasnsfert").value(DEFAULT_ATPE_TRASNSFERT))
            .andExpect(jsonPath("$.atpeParent").value(DEFAULT_ATPE_PARENT));
    }

    @Test
    @Transactional
    void getNonExistingMonitoring() throws Exception {
        // Get the monitoring
        restMonitoringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMonitoring() throws Exception {
        // Initialize the database
        monitoringRepository.saveAndFlush(monitoring);

        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();

        // Update the monitoring
        Monitoring updatedMonitoring = monitoringRepository.findById(monitoring.getId()).get();
        // Disconnect from session so that the updates on updatedMonitoring are not directly saved in db
        em.detach(updatedMonitoring);
        updatedMonitoring
            .pointOsId(UPDATED_POINT_OS_ID)
            .atpeAnnee(UPDATED_ATPE_ANNEE)
            .atpeMois(UPDATED_ATPE_MOIS)
            .atpeStock(UPDATED_ATPE_STOCK)
            .atpeDispo(UPDATED_ATPE_DISPO)
            .atpeEndom(UPDATED_ATPE_ENDOM)
            .atpePerime(UPDATED_ATPE_PERIME)
            .atpeRupture(UPDATED_ATPE_RUPTURE)
            .atpeNjour(UPDATED_ATPE_NJOUR)
            .atpeMagasin(UPDATED_ATPE_MAGASIN)
            .atpePalette(UPDATED_ATPE_PALETTE)
            .atpePosition(UPDATED_ATPE_POSITION)
            .atpeHauteur(UPDATED_ATPE_HAUTEUR)
            .atpePersonnel(UPDATED_ATPE_PERSONNEL)
            .atpeAdmission(UPDATED_ATPE_ADMISSION)
            .atpeSortie(UPDATED_ATPE_SORTIE)
            .atpeGueris(UPDATED_ATPE_GUERIS)
            .atpeAbandon(UPDATED_ATPE_ABANDON)
            .atpePoids(UPDATED_ATPE_POIDS)
            .atpeTrasnsfert(UPDATED_ATPE_TRASNSFERT)
            .atpeParent(UPDATED_ATPE_PARENT);

        restMonitoringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMonitoring.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMonitoring))
            )
            .andExpect(status().isOk());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
        assertThat(testMonitoring.getPointOsId()).isEqualTo(UPDATED_POINT_OS_ID);
        assertThat(testMonitoring.getAtpeAnnee()).isEqualTo(UPDATED_ATPE_ANNEE);
        assertThat(testMonitoring.getAtpeMois()).isEqualTo(UPDATED_ATPE_MOIS);
        assertThat(testMonitoring.getAtpeStock()).isEqualTo(UPDATED_ATPE_STOCK);
        assertThat(testMonitoring.getAtpeDispo()).isEqualTo(UPDATED_ATPE_DISPO);
        assertThat(testMonitoring.getAtpeEndom()).isEqualTo(UPDATED_ATPE_ENDOM);
        assertThat(testMonitoring.getAtpePerime()).isEqualTo(UPDATED_ATPE_PERIME);
        assertThat(testMonitoring.getAtpeRupture()).isEqualTo(UPDATED_ATPE_RUPTURE);
        assertThat(testMonitoring.getAtpeNjour()).isEqualTo(UPDATED_ATPE_NJOUR);
        assertThat(testMonitoring.getAtpeMagasin()).isEqualTo(UPDATED_ATPE_MAGASIN);
        assertThat(testMonitoring.getAtpePalette()).isEqualTo(UPDATED_ATPE_PALETTE);
        assertThat(testMonitoring.getAtpePosition()).isEqualTo(UPDATED_ATPE_POSITION);
        assertThat(testMonitoring.getAtpeHauteur()).isEqualTo(UPDATED_ATPE_HAUTEUR);
        assertThat(testMonitoring.getAtpePersonnel()).isEqualTo(UPDATED_ATPE_PERSONNEL);
        assertThat(testMonitoring.getAtpeAdmission()).isEqualTo(UPDATED_ATPE_ADMISSION);
        assertThat(testMonitoring.getAtpeSortie()).isEqualTo(UPDATED_ATPE_SORTIE);
        assertThat(testMonitoring.getAtpeGueris()).isEqualTo(UPDATED_ATPE_GUERIS);
        assertThat(testMonitoring.getAtpeAbandon()).isEqualTo(UPDATED_ATPE_ABANDON);
        assertThat(testMonitoring.getAtpePoids()).isEqualTo(UPDATED_ATPE_POIDS);
        assertThat(testMonitoring.getAtpeTrasnsfert()).isEqualTo(UPDATED_ATPE_TRASNSFERT);
        assertThat(testMonitoring.getAtpeParent()).isEqualTo(UPDATED_ATPE_PARENT);
    }

    @Test
    @Transactional
    void putNonExistingMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();
        monitoring.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonitoringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monitoring.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monitoring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonitoringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monitoring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonitoringMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(monitoring)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMonitoringWithPatch() throws Exception {
        // Initialize the database
        monitoringRepository.saveAndFlush(monitoring);

        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();

        // Update the monitoring using partial update
        Monitoring partialUpdatedMonitoring = new Monitoring();
        partialUpdatedMonitoring.setId(monitoring.getId());

        partialUpdatedMonitoring
            .pointOsId(UPDATED_POINT_OS_ID)
            .atpeAnnee(UPDATED_ATPE_ANNEE)
            .atpePerime(UPDATED_ATPE_PERIME)
            .atpeRupture(UPDATED_ATPE_RUPTURE)
            .atpeNjour(UPDATED_ATPE_NJOUR)
            .atpePosition(UPDATED_ATPE_POSITION)
            .atpeHauteur(UPDATED_ATPE_HAUTEUR)
            .atpeAdmission(UPDATED_ATPE_ADMISSION)
            .atpeGueris(UPDATED_ATPE_GUERIS)
            .atpePoids(UPDATED_ATPE_POIDS)
            .atpeTrasnsfert(UPDATED_ATPE_TRASNSFERT)
            .atpeParent(UPDATED_ATPE_PARENT);

        restMonitoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonitoring.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonitoring))
            )
            .andExpect(status().isOk());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
        assertThat(testMonitoring.getPointOsId()).isEqualTo(UPDATED_POINT_OS_ID);
        assertThat(testMonitoring.getAtpeAnnee()).isEqualTo(UPDATED_ATPE_ANNEE);
        assertThat(testMonitoring.getAtpeMois()).isEqualTo(DEFAULT_ATPE_MOIS);
        assertThat(testMonitoring.getAtpeStock()).isEqualTo(DEFAULT_ATPE_STOCK);
        assertThat(testMonitoring.getAtpeDispo()).isEqualTo(DEFAULT_ATPE_DISPO);
        assertThat(testMonitoring.getAtpeEndom()).isEqualTo(DEFAULT_ATPE_ENDOM);
        assertThat(testMonitoring.getAtpePerime()).isEqualTo(UPDATED_ATPE_PERIME);
        assertThat(testMonitoring.getAtpeRupture()).isEqualTo(UPDATED_ATPE_RUPTURE);
        assertThat(testMonitoring.getAtpeNjour()).isEqualTo(UPDATED_ATPE_NJOUR);
        assertThat(testMonitoring.getAtpeMagasin()).isEqualTo(DEFAULT_ATPE_MAGASIN);
        assertThat(testMonitoring.getAtpePalette()).isEqualTo(DEFAULT_ATPE_PALETTE);
        assertThat(testMonitoring.getAtpePosition()).isEqualTo(UPDATED_ATPE_POSITION);
        assertThat(testMonitoring.getAtpeHauteur()).isEqualTo(UPDATED_ATPE_HAUTEUR);
        assertThat(testMonitoring.getAtpePersonnel()).isEqualTo(DEFAULT_ATPE_PERSONNEL);
        assertThat(testMonitoring.getAtpeAdmission()).isEqualTo(UPDATED_ATPE_ADMISSION);
        assertThat(testMonitoring.getAtpeSortie()).isEqualTo(DEFAULT_ATPE_SORTIE);
        assertThat(testMonitoring.getAtpeGueris()).isEqualTo(UPDATED_ATPE_GUERIS);
        assertThat(testMonitoring.getAtpeAbandon()).isEqualTo(DEFAULT_ATPE_ABANDON);
        assertThat(testMonitoring.getAtpePoids()).isEqualTo(UPDATED_ATPE_POIDS);
        assertThat(testMonitoring.getAtpeTrasnsfert()).isEqualTo(UPDATED_ATPE_TRASNSFERT);
        assertThat(testMonitoring.getAtpeParent()).isEqualTo(UPDATED_ATPE_PARENT);
    }

    @Test
    @Transactional
    void fullUpdateMonitoringWithPatch() throws Exception {
        // Initialize the database
        monitoringRepository.saveAndFlush(monitoring);

        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();

        // Update the monitoring using partial update
        Monitoring partialUpdatedMonitoring = new Monitoring();
        partialUpdatedMonitoring.setId(monitoring.getId());

        partialUpdatedMonitoring
            .pointOsId(UPDATED_POINT_OS_ID)
            .atpeAnnee(UPDATED_ATPE_ANNEE)
            .atpeMois(UPDATED_ATPE_MOIS)
            .atpeStock(UPDATED_ATPE_STOCK)
            .atpeDispo(UPDATED_ATPE_DISPO)
            .atpeEndom(UPDATED_ATPE_ENDOM)
            .atpePerime(UPDATED_ATPE_PERIME)
            .atpeRupture(UPDATED_ATPE_RUPTURE)
            .atpeNjour(UPDATED_ATPE_NJOUR)
            .atpeMagasin(UPDATED_ATPE_MAGASIN)
            .atpePalette(UPDATED_ATPE_PALETTE)
            .atpePosition(UPDATED_ATPE_POSITION)
            .atpeHauteur(UPDATED_ATPE_HAUTEUR)
            .atpePersonnel(UPDATED_ATPE_PERSONNEL)
            .atpeAdmission(UPDATED_ATPE_ADMISSION)
            .atpeSortie(UPDATED_ATPE_SORTIE)
            .atpeGueris(UPDATED_ATPE_GUERIS)
            .atpeAbandon(UPDATED_ATPE_ABANDON)
            .atpePoids(UPDATED_ATPE_POIDS)
            .atpeTrasnsfert(UPDATED_ATPE_TRASNSFERT)
            .atpeParent(UPDATED_ATPE_PARENT);

        restMonitoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonitoring.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonitoring))
            )
            .andExpect(status().isOk());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
        assertThat(testMonitoring.getPointOsId()).isEqualTo(UPDATED_POINT_OS_ID);
        assertThat(testMonitoring.getAtpeAnnee()).isEqualTo(UPDATED_ATPE_ANNEE);
        assertThat(testMonitoring.getAtpeMois()).isEqualTo(UPDATED_ATPE_MOIS);
        assertThat(testMonitoring.getAtpeStock()).isEqualTo(UPDATED_ATPE_STOCK);
        assertThat(testMonitoring.getAtpeDispo()).isEqualTo(UPDATED_ATPE_DISPO);
        assertThat(testMonitoring.getAtpeEndom()).isEqualTo(UPDATED_ATPE_ENDOM);
        assertThat(testMonitoring.getAtpePerime()).isEqualTo(UPDATED_ATPE_PERIME);
        assertThat(testMonitoring.getAtpeRupture()).isEqualTo(UPDATED_ATPE_RUPTURE);
        assertThat(testMonitoring.getAtpeNjour()).isEqualTo(UPDATED_ATPE_NJOUR);
        assertThat(testMonitoring.getAtpeMagasin()).isEqualTo(UPDATED_ATPE_MAGASIN);
        assertThat(testMonitoring.getAtpePalette()).isEqualTo(UPDATED_ATPE_PALETTE);
        assertThat(testMonitoring.getAtpePosition()).isEqualTo(UPDATED_ATPE_POSITION);
        assertThat(testMonitoring.getAtpeHauteur()).isEqualTo(UPDATED_ATPE_HAUTEUR);
        assertThat(testMonitoring.getAtpePersonnel()).isEqualTo(UPDATED_ATPE_PERSONNEL);
        assertThat(testMonitoring.getAtpeAdmission()).isEqualTo(UPDATED_ATPE_ADMISSION);
        assertThat(testMonitoring.getAtpeSortie()).isEqualTo(UPDATED_ATPE_SORTIE);
        assertThat(testMonitoring.getAtpeGueris()).isEqualTo(UPDATED_ATPE_GUERIS);
        assertThat(testMonitoring.getAtpeAbandon()).isEqualTo(UPDATED_ATPE_ABANDON);
        assertThat(testMonitoring.getAtpePoids()).isEqualTo(UPDATED_ATPE_POIDS);
        assertThat(testMonitoring.getAtpeTrasnsfert()).isEqualTo(UPDATED_ATPE_TRASNSFERT);
        assertThat(testMonitoring.getAtpeParent()).isEqualTo(UPDATED_ATPE_PARENT);
    }

    @Test
    @Transactional
    void patchNonExistingMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();
        monitoring.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonitoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, monitoring.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monitoring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonitoringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monitoring))
            )
            .andExpect(status().isBadRequest());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonitoringMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(monitoring))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMonitoring() throws Exception {
        // Initialize the database
        monitoringRepository.saveAndFlush(monitoring);

        int databaseSizeBeforeDelete = monitoringRepository.findAll().size();

        // Delete the monitoring
        restMonitoringMockMvc
            .perform(delete(ENTITY_API_URL_ID, monitoring.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Monitoring> monitoringList = monitoringRepository.findAll();
        assertThat(monitoringList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
