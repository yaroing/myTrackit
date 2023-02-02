package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.PointFocalPartenaire;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.PointFocalPartenaireRepository;
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
 * Integration tests for the {@link PointFocalPartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PointFocalPartenaireResourceIT {

    private static final String DEFAULT_NOM_PF = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_PF = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_PF = "BBBBBBBBBB";

    private static final String DEFAULT_GSM_PF = "AAAAAAAAAA";
    private static final String UPDATED_GSM_PF = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PF = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/point-focal-partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointFocalPartenaireRepository pointFocalPartenaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private PointFocalPartenaire pointFocalPartenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPartenaire createEntity(EntityManager em) {
        PointFocalPartenaire pointFocalPartenaire = new PointFocalPartenaire()
            .nomPf(DEFAULT_NOM_PF)
            .fonctionPf(DEFAULT_FONCTION_PF)
            .gsmPf(DEFAULT_GSM_PF)
            .emailPf(DEFAULT_EMAIL_PF);
        return pointFocalPartenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointFocalPartenaire createUpdatedEntity(EntityManager em) {
        PointFocalPartenaire pointFocalPartenaire = new PointFocalPartenaire()
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);
        return pointFocalPartenaire;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(PointFocalPartenaire.class).block();
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
        pointFocalPartenaire = createEntity(em);
    }

    @Test
    void createPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeCreate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        // Create the PointFocalPartenaire
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeCreate + 1);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(DEFAULT_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    void createPointFocalPartenaireWithExistingId() throws Exception {
        // Create the PointFocalPartenaire with an existing ID
        pointFocalPartenaire.setId(1L);

        int databaseSizeBeforeCreate = pointFocalPartenaireRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPointFocalPartenairesAsStream() {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        List<PointFocalPartenaire> pointFocalPartenaireList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(PointFocalPartenaire.class)
            .getResponseBody()
            .filter(pointFocalPartenaire::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(pointFocalPartenaireList).isNotNull();
        assertThat(pointFocalPartenaireList).hasSize(1);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(0);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(DEFAULT_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    void getAllPointFocalPartenaires() {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        // Get all the pointFocalPartenaireList
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
            .value(hasItem(pointFocalPartenaire.getId().intValue()))
            .jsonPath("$.[*].nomPf")
            .value(hasItem(DEFAULT_NOM_PF))
            .jsonPath("$.[*].fonctionPf")
            .value(hasItem(DEFAULT_FONCTION_PF))
            .jsonPath("$.[*].gsmPf")
            .value(hasItem(DEFAULT_GSM_PF))
            .jsonPath("$.[*].emailPf")
            .value(hasItem(DEFAULT_EMAIL_PF));
    }

    @Test
    void getPointFocalPartenaire() {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        // Get the pointFocalPartenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, pointFocalPartenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(pointFocalPartenaire.getId().intValue()))
            .jsonPath("$.nomPf")
            .value(is(DEFAULT_NOM_PF))
            .jsonPath("$.fonctionPf")
            .value(is(DEFAULT_FONCTION_PF))
            .jsonPath("$.gsmPf")
            .value(is(DEFAULT_GSM_PF))
            .jsonPath("$.emailPf")
            .value(is(DEFAULT_EMAIL_PF));
    }

    @Test
    void getNonExistingPointFocalPartenaire() {
        // Get the pointFocalPartenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPointFocalPartenaire() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();

        // Update the pointFocalPartenaire
        PointFocalPartenaire updatedPointFocalPartenaire = pointFocalPartenaireRepository.findById(pointFocalPartenaire.getId()).block();
        updatedPointFocalPartenaire.nomPf(UPDATED_NOM_PF).fonctionPf(UPDATED_FONCTION_PF).gsmPf(UPDATED_GSM_PF).emailPf(UPDATED_EMAIL_PF);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPointFocalPartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    void putNonExistingPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, pointFocalPartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePointFocalPartenaireWithPatch() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();

        // Update the pointFocalPartenaire using partial update
        PointFocalPartenaire partialUpdatedPointFocalPartenaire = new PointFocalPartenaire();
        partialUpdatedPointFocalPartenaire.setId(pointFocalPartenaire.getId());

        partialUpdatedPointFocalPartenaire.gsmPf(UPDATED_GSM_PF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPointFocalPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(DEFAULT_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(DEFAULT_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(DEFAULT_EMAIL_PF);
    }

    @Test
    void fullUpdatePointFocalPartenaireWithPatch() throws Exception {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();

        // Update the pointFocalPartenaire using partial update
        PointFocalPartenaire partialUpdatedPointFocalPartenaire = new PointFocalPartenaire();
        partialUpdatedPointFocalPartenaire.setId(pointFocalPartenaire.getId());

        partialUpdatedPointFocalPartenaire
            .nomPf(UPDATED_NOM_PF)
            .fonctionPf(UPDATED_FONCTION_PF)
            .gsmPf(UPDATED_GSM_PF)
            .emailPf(UPDATED_EMAIL_PF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPointFocalPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
        PointFocalPartenaire testPointFocalPartenaire = pointFocalPartenaireList.get(pointFocalPartenaireList.size() - 1);
        assertThat(testPointFocalPartenaire.getNomPf()).isEqualTo(UPDATED_NOM_PF);
        assertThat(testPointFocalPartenaire.getFonctionPf()).isEqualTo(UPDATED_FONCTION_PF);
        assertThat(testPointFocalPartenaire.getGsmPf()).isEqualTo(UPDATED_GSM_PF);
        assertThat(testPointFocalPartenaire.getEmailPf()).isEqualTo(UPDATED_EMAIL_PF);
    }

    @Test
    void patchNonExistingPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, pointFocalPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPointFocalPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = pointFocalPartenaireRepository.findAll().collectList().block().size();
        pointFocalPartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(pointFocalPartenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PointFocalPartenaire in the database
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePointFocalPartenaire() {
        // Initialize the database
        pointFocalPartenaireRepository.save(pointFocalPartenaire).block();

        int databaseSizeBeforeDelete = pointFocalPartenaireRepository.findAll().collectList().block().size();

        // Delete the pointFocalPartenaire
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, pointFocalPartenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<PointFocalPartenaire> pointFocalPartenaireList = pointFocalPartenaireRepository.findAll().collectList().block();
        assertThat(pointFocalPartenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
