package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Transfert;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.TransfertRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TransfertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TransfertResourceIT {

    private static final Instant DEFAULT_DATE_EXP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_EXP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOM_CHAUFFEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CHAUFFEUR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_REC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_REC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
    private WebTestClient webTestClient;

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
            .nomChauffeur(DEFAULT_NOM_CHAUFFEUR)
            .dateRec(DEFAULT_DATE_REC)
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
            .nomChauffeur(UPDATED_NOM_CHAUFFEUR)
            .dateRec(UPDATED_DATE_REC)
            .cphone(UPDATED_CPHONE);
        return transfert;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Transfert.class).block();
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
        transfert = createEntity(em);
    }

    @Test
    void createTransfert() throws Exception {
        int databaseSizeBeforeCreate = transfertRepository.findAll().collectList().block().size();
        // Create the Transfert
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeCreate + 1);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(DEFAULT_DATE_EXP);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(DEFAULT_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testTransfert.getCphone()).isEqualTo(DEFAULT_CPHONE);
    }

    @Test
    void createTransfertWithExistingId() throws Exception {
        // Create the Transfert with an existing ID
        transfert.setId(1L);

        int databaseSizeBeforeCreate = transfertRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTransfertsAsStream() {
        // Initialize the database
        transfertRepository.save(transfert).block();

        List<Transfert> transfertList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Transfert.class)
            .getResponseBody()
            .filter(transfert::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(transfertList).isNotNull();
        assertThat(transfertList).hasSize(1);
        Transfert testTransfert = transfertList.get(0);
        assertThat(testTransfert.getDateExp()).isEqualTo(DEFAULT_DATE_EXP);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(DEFAULT_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(DEFAULT_DATE_REC);
        assertThat(testTransfert.getCphone()).isEqualTo(DEFAULT_CPHONE);
    }

    @Test
    void getAllTransferts() {
        // Initialize the database
        transfertRepository.save(transfert).block();

        // Get all the transfertList
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
            .value(hasItem(transfert.getId().intValue()))
            .jsonPath("$.[*].dateExp")
            .value(hasItem(DEFAULT_DATE_EXP.toString()))
            .jsonPath("$.[*].nomChauffeur")
            .value(hasItem(DEFAULT_NOM_CHAUFFEUR))
            .jsonPath("$.[*].dateRec")
            .value(hasItem(DEFAULT_DATE_REC.toString()))
            .jsonPath("$.[*].cphone")
            .value(hasItem(DEFAULT_CPHONE));
    }

    @Test
    void getTransfert() {
        // Initialize the database
        transfertRepository.save(transfert).block();

        // Get the transfert
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, transfert.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(transfert.getId().intValue()))
            .jsonPath("$.dateExp")
            .value(is(DEFAULT_DATE_EXP.toString()))
            .jsonPath("$.nomChauffeur")
            .value(is(DEFAULT_NOM_CHAUFFEUR))
            .jsonPath("$.dateRec")
            .value(is(DEFAULT_DATE_REC.toString()))
            .jsonPath("$.cphone")
            .value(is(DEFAULT_CPHONE));
    }

    @Test
    void getNonExistingTransfert() {
        // Get the transfert
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTransfert() throws Exception {
        // Initialize the database
        transfertRepository.save(transfert).block();

        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();

        // Update the transfert
        Transfert updatedTransfert = transfertRepository.findById(transfert.getId()).block();
        updatedTransfert.dateExp(UPDATED_DATE_EXP).nomChauffeur(UPDATED_NOM_CHAUFFEUR).dateRec(UPDATED_DATE_REC).cphone(UPDATED_CPHONE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTransfert.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(UPDATED_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testTransfert.getCphone()).isEqualTo(UPDATED_CPHONE);
    }

    @Test
    void putNonExistingTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();
        transfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, transfert.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTransfertWithPatch() throws Exception {
        // Initialize the database
        transfertRepository.save(transfert).block();

        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();

        // Update the transfert using partial update
        Transfert partialUpdatedTransfert = new Transfert();
        partialUpdatedTransfert.setId(transfert.getId());

        partialUpdatedTransfert.dateExp(UPDATED_DATE_EXP).dateRec(UPDATED_DATE_REC);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(DEFAULT_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testTransfert.getCphone()).isEqualTo(DEFAULT_CPHONE);
    }

    @Test
    void fullUpdateTransfertWithPatch() throws Exception {
        // Initialize the database
        transfertRepository.save(transfert).block();

        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();

        // Update the transfert using partial update
        Transfert partialUpdatedTransfert = new Transfert();
        partialUpdatedTransfert.setId(transfert.getId());

        partialUpdatedTransfert
            .dateExp(UPDATED_DATE_EXP)
            .nomChauffeur(UPDATED_NOM_CHAUFFEUR)
            .dateRec(UPDATED_DATE_REC)
            .cphone(UPDATED_CPHONE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
        Transfert testTransfert = transfertList.get(transfertList.size() - 1);
        assertThat(testTransfert.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testTransfert.getNomChauffeur()).isEqualTo(UPDATED_NOM_CHAUFFEUR);
        assertThat(testTransfert.getDateRec()).isEqualTo(UPDATED_DATE_REC);
        assertThat(testTransfert.getCphone()).isEqualTo(UPDATED_CPHONE);
    }

    @Test
    void patchNonExistingTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();
        transfert.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, transfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTransfert() throws Exception {
        int databaseSizeBeforeUpdate = transfertRepository.findAll().collectList().block().size();
        transfert.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(transfert))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Transfert in the database
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTransfert() {
        // Initialize the database
        transfertRepository.save(transfert).block();

        int databaseSizeBeforeDelete = transfertRepository.findAll().collectList().block().size();

        // Delete the transfert
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, transfert.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Transfert> transfertList = transfertRepository.findAll().collectList().block();
        assertThat(transfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
