package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.RequetePartenaire;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.RequetePartenaireRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RequetePartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RequetePartenaireResourceIT {

    private static final Instant DEFAULT_REQUETE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUETE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_FICHIER_ATACHE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_ATACHE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_ATACHE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_ATACHE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REQUETE_OBS = "AAAAAAAAAA";
    private static final String UPDATED_REQUETE_OBS = "BBBBBBBBBB";

    private static final Integer DEFAULT_REQ_TRAITEE = 1;
    private static final Integer UPDATED_REQ_TRAITEE = 2;

    private static final String ENTITY_API_URL = "/api/requete-partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequetePartenaireRepository requetePartenaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RequetePartenaire requetePartenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequetePartenaire createEntity(EntityManager em) {
        RequetePartenaire requetePartenaire = new RequetePartenaire()
            .requeteDate(DEFAULT_REQUETE_DATE)
            .fichierAtache(DEFAULT_FICHIER_ATACHE)
            .fichierAtacheContentType(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(DEFAULT_REQUETE_OBS)
            .reqTraitee(DEFAULT_REQ_TRAITEE);
        return requetePartenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequetePartenaire createUpdatedEntity(EntityManager em) {
        RequetePartenaire requetePartenaire = new RequetePartenaire()
            .requeteDate(UPDATED_REQUETE_DATE)
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);
        return requetePartenaire;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RequetePartenaire.class).block();
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
        requetePartenaire = createEntity(em);
    }

    @Test
    void createRequetePartenaire() throws Exception {
        int databaseSizeBeforeCreate = requetePartenaireRepository.findAll().collectList().block().size();
        // Create the RequetePartenaire
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeCreate + 1);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(DEFAULT_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(DEFAULT_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(DEFAULT_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(DEFAULT_REQ_TRAITEE);
    }

    @Test
    void createRequetePartenaireWithExistingId() throws Exception {
        // Create the RequetePartenaire with an existing ID
        requetePartenaire.setId(1L);

        int databaseSizeBeforeCreate = requetePartenaireRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRequetePartenairesAsStream() {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        List<RequetePartenaire> requetePartenaireList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(RequetePartenaire.class)
            .getResponseBody()
            .filter(requetePartenaire::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(requetePartenaireList).isNotNull();
        assertThat(requetePartenaireList).hasSize(1);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(0);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(DEFAULT_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(DEFAULT_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(DEFAULT_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(DEFAULT_REQ_TRAITEE);
    }

    @Test
    void getAllRequetePartenaires() {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        // Get all the requetePartenaireList
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
            .value(hasItem(requetePartenaire.getId().intValue()))
            .jsonPath("$.[*].requeteDate")
            .value(hasItem(DEFAULT_REQUETE_DATE.toString()))
            .jsonPath("$.[*].fichierAtacheContentType")
            .value(hasItem(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE))
            .jsonPath("$.[*].fichierAtache")
            .value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_ATACHE)))
            .jsonPath("$.[*].requeteObs")
            .value(hasItem(DEFAULT_REQUETE_OBS.toString()))
            .jsonPath("$.[*].reqTraitee")
            .value(hasItem(DEFAULT_REQ_TRAITEE));
    }

    @Test
    void getRequetePartenaire() {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        // Get the requetePartenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, requetePartenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(requetePartenaire.getId().intValue()))
            .jsonPath("$.requeteDate")
            .value(is(DEFAULT_REQUETE_DATE.toString()))
            .jsonPath("$.fichierAtacheContentType")
            .value(is(DEFAULT_FICHIER_ATACHE_CONTENT_TYPE))
            .jsonPath("$.fichierAtache")
            .value(is(Base64Utils.encodeToString(DEFAULT_FICHIER_ATACHE)))
            .jsonPath("$.requeteObs")
            .value(is(DEFAULT_REQUETE_OBS.toString()))
            .jsonPath("$.reqTraitee")
            .value(is(DEFAULT_REQ_TRAITEE));
    }

    @Test
    void getNonExistingRequetePartenaire() {
        // Get the requetePartenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRequetePartenaire() throws Exception {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();

        // Update the requetePartenaire
        RequetePartenaire updatedRequetePartenaire = requetePartenaireRepository.findById(requetePartenaire.getId()).block();
        updatedRequetePartenaire
            .requeteDate(UPDATED_REQUETE_DATE)
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedRequetePartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedRequetePartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(UPDATED_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(UPDATED_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(UPDATED_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(UPDATED_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
    }

    @Test
    void putNonExistingRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, requetePartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRequetePartenaireWithPatch() throws Exception {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();

        // Update the requetePartenaire using partial update
        RequetePartenaire partialUpdatedRequetePartenaire = new RequetePartenaire();
        partialUpdatedRequetePartenaire.setId(requetePartenaire.getId());

        partialUpdatedRequetePartenaire
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRequetePartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(DEFAULT_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(UPDATED_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(UPDATED_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(UPDATED_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
    }

    @Test
    void fullUpdateRequetePartenaireWithPatch() throws Exception {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();

        // Update the requetePartenaire using partial update
        RequetePartenaire partialUpdatedRequetePartenaire = new RequetePartenaire();
        partialUpdatedRequetePartenaire.setId(requetePartenaire.getId());

        partialUpdatedRequetePartenaire
            .requeteDate(UPDATED_REQUETE_DATE)
            .fichierAtache(UPDATED_FICHIER_ATACHE)
            .fichierAtacheContentType(UPDATED_FICHIER_ATACHE_CONTENT_TYPE)
            .requeteObs(UPDATED_REQUETE_OBS)
            .reqTraitee(UPDATED_REQ_TRAITEE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRequetePartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRequetePartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
        RequetePartenaire testRequetePartenaire = requetePartenaireList.get(requetePartenaireList.size() - 1);
        assertThat(testRequetePartenaire.getRequeteDate()).isEqualTo(UPDATED_REQUETE_DATE);
        assertThat(testRequetePartenaire.getFichierAtache()).isEqualTo(UPDATED_FICHIER_ATACHE);
        assertThat(testRequetePartenaire.getFichierAtacheContentType()).isEqualTo(UPDATED_FICHIER_ATACHE_CONTENT_TYPE);
        assertThat(testRequetePartenaire.getRequeteObs()).isEqualTo(UPDATED_REQUETE_OBS);
        assertThat(testRequetePartenaire.getReqTraitee()).isEqualTo(UPDATED_REQ_TRAITEE);
    }

    @Test
    void patchNonExistingRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, requetePartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRequetePartenaire() throws Exception {
        int databaseSizeBeforeUpdate = requetePartenaireRepository.findAll().collectList().block().size();
        requetePartenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(requetePartenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RequetePartenaire in the database
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRequetePartenaire() {
        // Initialize the database
        requetePartenaireRepository.save(requetePartenaire).block();

        int databaseSizeBeforeDelete = requetePartenaireRepository.findAll().collectList().block().size();

        // Delete the requetePartenaire
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, requetePartenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RequetePartenaire> requetePartenaireList = requetePartenaireRepository.findAll().collectList().block();
        assertThat(requetePartenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
