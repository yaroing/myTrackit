package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Monitoring;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.MonitoringRepository;
import java.time.Duration;
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
 * Integration tests for the {@link MonitoringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MonitoringResourceIT {

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
    private WebTestClient webTestClient;

    private Monitoring monitoring;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monitoring createEntity(EntityManager em) {
        Monitoring monitoring = new Monitoring()
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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Monitoring.class).block();
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
        monitoring = createEntity(em);
    }

    @Test
    void createMonitoring() throws Exception {
        int databaseSizeBeforeCreate = monitoringRepository.findAll().collectList().block().size();
        // Create the Monitoring
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeCreate + 1);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
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
    void createMonitoringWithExistingId() throws Exception {
        // Create the Monitoring with an existing ID
        monitoring.setId(1L);

        int databaseSizeBeforeCreate = monitoringRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMonitoringsAsStream() {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        List<Monitoring> monitoringList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Monitoring.class)
            .getResponseBody()
            .filter(monitoring::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(monitoringList).isNotNull();
        assertThat(monitoringList).hasSize(1);
        Monitoring testMonitoring = monitoringList.get(0);
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
    void getAllMonitorings() {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        // Get all the monitoringList
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
            .value(hasItem(monitoring.getId().intValue()))
            .jsonPath("$.[*].atpeAnnee")
            .value(hasItem(DEFAULT_ATPE_ANNEE))
            .jsonPath("$.[*].atpeMois")
            .value(hasItem(DEFAULT_ATPE_MOIS))
            .jsonPath("$.[*].atpeStock")
            .value(hasItem(DEFAULT_ATPE_STOCK))
            .jsonPath("$.[*].atpeDispo")
            .value(hasItem(DEFAULT_ATPE_DISPO.doubleValue()))
            .jsonPath("$.[*].atpeEndom")
            .value(hasItem(DEFAULT_ATPE_ENDOM.doubleValue()))
            .jsonPath("$.[*].atpePerime")
            .value(hasItem(DEFAULT_ATPE_PERIME.doubleValue()))
            .jsonPath("$.[*].atpeRupture")
            .value(hasItem(DEFAULT_ATPE_RUPTURE))
            .jsonPath("$.[*].atpeNjour")
            .value(hasItem(DEFAULT_ATPE_NJOUR))
            .jsonPath("$.[*].atpeMagasin")
            .value(hasItem(DEFAULT_ATPE_MAGASIN))
            .jsonPath("$.[*].atpePalette")
            .value(hasItem(DEFAULT_ATPE_PALETTE))
            .jsonPath("$.[*].atpePosition")
            .value(hasItem(DEFAULT_ATPE_POSITION))
            .jsonPath("$.[*].atpeHauteur")
            .value(hasItem(DEFAULT_ATPE_HAUTEUR.doubleValue()))
            .jsonPath("$.[*].atpePersonnel")
            .value(hasItem(DEFAULT_ATPE_PERSONNEL))
            .jsonPath("$.[*].atpeAdmission")
            .value(hasItem(DEFAULT_ATPE_ADMISSION))
            .jsonPath("$.[*].atpeSortie")
            .value(hasItem(DEFAULT_ATPE_SORTIE))
            .jsonPath("$.[*].atpeGueris")
            .value(hasItem(DEFAULT_ATPE_GUERIS))
            .jsonPath("$.[*].atpeAbandon")
            .value(hasItem(DEFAULT_ATPE_ABANDON))
            .jsonPath("$.[*].atpePoids")
            .value(hasItem(DEFAULT_ATPE_POIDS))
            .jsonPath("$.[*].atpeTrasnsfert")
            .value(hasItem(DEFAULT_ATPE_TRASNSFERT))
            .jsonPath("$.[*].atpeParent")
            .value(hasItem(DEFAULT_ATPE_PARENT));
    }

    @Test
    void getMonitoring() {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        // Get the monitoring
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, monitoring.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(monitoring.getId().intValue()))
            .jsonPath("$.atpeAnnee")
            .value(is(DEFAULT_ATPE_ANNEE))
            .jsonPath("$.atpeMois")
            .value(is(DEFAULT_ATPE_MOIS))
            .jsonPath("$.atpeStock")
            .value(is(DEFAULT_ATPE_STOCK))
            .jsonPath("$.atpeDispo")
            .value(is(DEFAULT_ATPE_DISPO.doubleValue()))
            .jsonPath("$.atpeEndom")
            .value(is(DEFAULT_ATPE_ENDOM.doubleValue()))
            .jsonPath("$.atpePerime")
            .value(is(DEFAULT_ATPE_PERIME.doubleValue()))
            .jsonPath("$.atpeRupture")
            .value(is(DEFAULT_ATPE_RUPTURE))
            .jsonPath("$.atpeNjour")
            .value(is(DEFAULT_ATPE_NJOUR))
            .jsonPath("$.atpeMagasin")
            .value(is(DEFAULT_ATPE_MAGASIN))
            .jsonPath("$.atpePalette")
            .value(is(DEFAULT_ATPE_PALETTE))
            .jsonPath("$.atpePosition")
            .value(is(DEFAULT_ATPE_POSITION))
            .jsonPath("$.atpeHauteur")
            .value(is(DEFAULT_ATPE_HAUTEUR.doubleValue()))
            .jsonPath("$.atpePersonnel")
            .value(is(DEFAULT_ATPE_PERSONNEL))
            .jsonPath("$.atpeAdmission")
            .value(is(DEFAULT_ATPE_ADMISSION))
            .jsonPath("$.atpeSortie")
            .value(is(DEFAULT_ATPE_SORTIE))
            .jsonPath("$.atpeGueris")
            .value(is(DEFAULT_ATPE_GUERIS))
            .jsonPath("$.atpeAbandon")
            .value(is(DEFAULT_ATPE_ABANDON))
            .jsonPath("$.atpePoids")
            .value(is(DEFAULT_ATPE_POIDS))
            .jsonPath("$.atpeTrasnsfert")
            .value(is(DEFAULT_ATPE_TRASNSFERT))
            .jsonPath("$.atpeParent")
            .value(is(DEFAULT_ATPE_PARENT));
    }

    @Test
    void getNonExistingMonitoring() {
        // Get the monitoring
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMonitoring() throws Exception {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();

        // Update the monitoring
        Monitoring updatedMonitoring = monitoringRepository.findById(monitoring.getId()).block();
        updatedMonitoring
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedMonitoring.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedMonitoring))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
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
    void putNonExistingMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();
        monitoring.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, monitoring.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMonitoringWithPatch() throws Exception {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();

        // Update the monitoring using partial update
        Monitoring partialUpdatedMonitoring = new Monitoring();
        partialUpdatedMonitoring.setId(monitoring.getId());

        partialUpdatedMonitoring
            .atpeAnnee(UPDATED_ATPE_ANNEE)
            .atpeMois(UPDATED_ATPE_MOIS)
            .atpeRupture(UPDATED_ATPE_RUPTURE)
            .atpeNjour(UPDATED_ATPE_NJOUR)
            .atpeMagasin(UPDATED_ATPE_MAGASIN)
            .atpeHauteur(UPDATED_ATPE_HAUTEUR)
            .atpePersonnel(UPDATED_ATPE_PERSONNEL)
            .atpeSortie(UPDATED_ATPE_SORTIE)
            .atpeAbandon(UPDATED_ATPE_ABANDON)
            .atpeTrasnsfert(UPDATED_ATPE_TRASNSFERT)
            .atpeParent(UPDATED_ATPE_PARENT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMonitoring.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMonitoring))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
        assertThat(testMonitoring.getAtpeAnnee()).isEqualTo(UPDATED_ATPE_ANNEE);
        assertThat(testMonitoring.getAtpeMois()).isEqualTo(UPDATED_ATPE_MOIS);
        assertThat(testMonitoring.getAtpeStock()).isEqualTo(DEFAULT_ATPE_STOCK);
        assertThat(testMonitoring.getAtpeDispo()).isEqualTo(DEFAULT_ATPE_DISPO);
        assertThat(testMonitoring.getAtpeEndom()).isEqualTo(DEFAULT_ATPE_ENDOM);
        assertThat(testMonitoring.getAtpePerime()).isEqualTo(DEFAULT_ATPE_PERIME);
        assertThat(testMonitoring.getAtpeRupture()).isEqualTo(UPDATED_ATPE_RUPTURE);
        assertThat(testMonitoring.getAtpeNjour()).isEqualTo(UPDATED_ATPE_NJOUR);
        assertThat(testMonitoring.getAtpeMagasin()).isEqualTo(UPDATED_ATPE_MAGASIN);
        assertThat(testMonitoring.getAtpePalette()).isEqualTo(DEFAULT_ATPE_PALETTE);
        assertThat(testMonitoring.getAtpePosition()).isEqualTo(DEFAULT_ATPE_POSITION);
        assertThat(testMonitoring.getAtpeHauteur()).isEqualTo(UPDATED_ATPE_HAUTEUR);
        assertThat(testMonitoring.getAtpePersonnel()).isEqualTo(UPDATED_ATPE_PERSONNEL);
        assertThat(testMonitoring.getAtpeAdmission()).isEqualTo(DEFAULT_ATPE_ADMISSION);
        assertThat(testMonitoring.getAtpeSortie()).isEqualTo(UPDATED_ATPE_SORTIE);
        assertThat(testMonitoring.getAtpeGueris()).isEqualTo(DEFAULT_ATPE_GUERIS);
        assertThat(testMonitoring.getAtpeAbandon()).isEqualTo(UPDATED_ATPE_ABANDON);
        assertThat(testMonitoring.getAtpePoids()).isEqualTo(DEFAULT_ATPE_POIDS);
        assertThat(testMonitoring.getAtpeTrasnsfert()).isEqualTo(UPDATED_ATPE_TRASNSFERT);
        assertThat(testMonitoring.getAtpeParent()).isEqualTo(UPDATED_ATPE_PARENT);
    }

    @Test
    void fullUpdateMonitoringWithPatch() throws Exception {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();

        // Update the monitoring using partial update
        Monitoring partialUpdatedMonitoring = new Monitoring();
        partialUpdatedMonitoring.setId(monitoring.getId());

        partialUpdatedMonitoring
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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMonitoring.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMonitoring))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
        Monitoring testMonitoring = monitoringList.get(monitoringList.size() - 1);
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
    void patchNonExistingMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();
        monitoring.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, monitoring.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMonitoring() throws Exception {
        int databaseSizeBeforeUpdate = monitoringRepository.findAll().collectList().block().size();
        monitoring.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(monitoring))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Monitoring in the database
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMonitoring() {
        // Initialize the database
        monitoringRepository.save(monitoring).block();

        int databaseSizeBeforeDelete = monitoringRepository.findAll().collectList().block().size();

        // Delete the monitoring
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, monitoring.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Monitoring> monitoringList = monitoringRepository.findAll().collectList().block();
        assertThat(monitoringList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
