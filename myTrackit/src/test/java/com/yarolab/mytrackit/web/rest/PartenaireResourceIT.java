package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Partenaire;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.PartenaireRepository;
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
 * Integration tests for the {@link PartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PartenaireResourceIT {

    private static final String DEFAULT_NOM_PARTENAIRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PARTENAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTRE_NOM = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LOG_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_LOG_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PARTENAIRE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PARTENAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_PARTENAIRE = "AAAAAAAAAA";
    private static final String UPDATED_LOC_PARTENAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartenaireRepository partenaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Partenaire partenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaire createEntity(EntityManager em) {
        Partenaire partenaire = new Partenaire()
            .nomPartenaire(DEFAULT_NOM_PARTENAIRE)
            .autreNom(DEFAULT_AUTRE_NOM)
            .logPhone(DEFAULT_LOG_PHONE)
            .emailPartenaire(DEFAULT_EMAIL_PARTENAIRE)
            .locPartenaire(DEFAULT_LOC_PARTENAIRE);
        return partenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaire createUpdatedEntity(EntityManager em) {
        Partenaire partenaire = new Partenaire()
            .nomPartenaire(UPDATED_NOM_PARTENAIRE)
            .autreNom(UPDATED_AUTRE_NOM)
            .logPhone(UPDATED_LOG_PHONE)
            .emailPartenaire(UPDATED_EMAIL_PARTENAIRE)
            .locPartenaire(UPDATED_LOC_PARTENAIRE);
        return partenaire;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Partenaire.class).block();
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
        partenaire = createEntity(em);
    }

    @Test
    void createPartenaire() throws Exception {
        int databaseSizeBeforeCreate = partenaireRepository.findAll().collectList().block().size();
        // Create the Partenaire
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeCreate + 1);
        Partenaire testPartenaire = partenaireList.get(partenaireList.size() - 1);
        assertThat(testPartenaire.getNomPartenaire()).isEqualTo(DEFAULT_NOM_PARTENAIRE);
        assertThat(testPartenaire.getAutreNom()).isEqualTo(DEFAULT_AUTRE_NOM);
        assertThat(testPartenaire.getLogPhone()).isEqualTo(DEFAULT_LOG_PHONE);
        assertThat(testPartenaire.getEmailPartenaire()).isEqualTo(DEFAULT_EMAIL_PARTENAIRE);
        assertThat(testPartenaire.getLocPartenaire()).isEqualTo(DEFAULT_LOC_PARTENAIRE);
    }

    @Test
    void createPartenaireWithExistingId() throws Exception {
        // Create the Partenaire with an existing ID
        partenaire.setId(1L);

        int databaseSizeBeforeCreate = partenaireRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPartenairesAsStream() {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        List<Partenaire> partenaireList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Partenaire.class)
            .getResponseBody()
            .filter(partenaire::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(partenaireList).isNotNull();
        assertThat(partenaireList).hasSize(1);
        Partenaire testPartenaire = partenaireList.get(0);
        assertThat(testPartenaire.getNomPartenaire()).isEqualTo(DEFAULT_NOM_PARTENAIRE);
        assertThat(testPartenaire.getAutreNom()).isEqualTo(DEFAULT_AUTRE_NOM);
        assertThat(testPartenaire.getLogPhone()).isEqualTo(DEFAULT_LOG_PHONE);
        assertThat(testPartenaire.getEmailPartenaire()).isEqualTo(DEFAULT_EMAIL_PARTENAIRE);
        assertThat(testPartenaire.getLocPartenaire()).isEqualTo(DEFAULT_LOC_PARTENAIRE);
    }

    @Test
    void getAllPartenaires() {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        // Get all the partenaireList
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
            .value(hasItem(partenaire.getId().intValue()))
            .jsonPath("$.[*].nomPartenaire")
            .value(hasItem(DEFAULT_NOM_PARTENAIRE))
            .jsonPath("$.[*].autreNom")
            .value(hasItem(DEFAULT_AUTRE_NOM))
            .jsonPath("$.[*].logPhone")
            .value(hasItem(DEFAULT_LOG_PHONE))
            .jsonPath("$.[*].emailPartenaire")
            .value(hasItem(DEFAULT_EMAIL_PARTENAIRE))
            .jsonPath("$.[*].locPartenaire")
            .value(hasItem(DEFAULT_LOC_PARTENAIRE));
    }

    @Test
    void getPartenaire() {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        // Get the partenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, partenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(partenaire.getId().intValue()))
            .jsonPath("$.nomPartenaire")
            .value(is(DEFAULT_NOM_PARTENAIRE))
            .jsonPath("$.autreNom")
            .value(is(DEFAULT_AUTRE_NOM))
            .jsonPath("$.logPhone")
            .value(is(DEFAULT_LOG_PHONE))
            .jsonPath("$.emailPartenaire")
            .value(is(DEFAULT_EMAIL_PARTENAIRE))
            .jsonPath("$.locPartenaire")
            .value(is(DEFAULT_LOC_PARTENAIRE));
    }

    @Test
    void getNonExistingPartenaire() {
        // Get the partenaire
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPartenaire() throws Exception {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();

        // Update the partenaire
        Partenaire updatedPartenaire = partenaireRepository.findById(partenaire.getId()).block();
        updatedPartenaire
            .nomPartenaire(UPDATED_NOM_PARTENAIRE)
            .autreNom(UPDATED_AUTRE_NOM)
            .logPhone(UPDATED_LOG_PHONE)
            .emailPartenaire(UPDATED_EMAIL_PARTENAIRE)
            .locPartenaire(UPDATED_LOC_PARTENAIRE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPartenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
        Partenaire testPartenaire = partenaireList.get(partenaireList.size() - 1);
        assertThat(testPartenaire.getNomPartenaire()).isEqualTo(UPDATED_NOM_PARTENAIRE);
        assertThat(testPartenaire.getAutreNom()).isEqualTo(UPDATED_AUTRE_NOM);
        assertThat(testPartenaire.getLogPhone()).isEqualTo(UPDATED_LOG_PHONE);
        assertThat(testPartenaire.getEmailPartenaire()).isEqualTo(UPDATED_EMAIL_PARTENAIRE);
        assertThat(testPartenaire.getLocPartenaire()).isEqualTo(UPDATED_LOC_PARTENAIRE);
    }

    @Test
    void putNonExistingPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();
        partenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, partenaire.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();
        partenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();
        partenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePartenaireWithPatch() throws Exception {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();

        // Update the partenaire using partial update
        Partenaire partialUpdatedPartenaire = new Partenaire();
        partialUpdatedPartenaire.setId(partenaire.getId());

        partialUpdatedPartenaire.autreNom(UPDATED_AUTRE_NOM).emailPartenaire(UPDATED_EMAIL_PARTENAIRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
        Partenaire testPartenaire = partenaireList.get(partenaireList.size() - 1);
        assertThat(testPartenaire.getNomPartenaire()).isEqualTo(DEFAULT_NOM_PARTENAIRE);
        assertThat(testPartenaire.getAutreNom()).isEqualTo(UPDATED_AUTRE_NOM);
        assertThat(testPartenaire.getLogPhone()).isEqualTo(DEFAULT_LOG_PHONE);
        assertThat(testPartenaire.getEmailPartenaire()).isEqualTo(UPDATED_EMAIL_PARTENAIRE);
        assertThat(testPartenaire.getLocPartenaire()).isEqualTo(DEFAULT_LOC_PARTENAIRE);
    }

    @Test
    void fullUpdatePartenaireWithPatch() throws Exception {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();

        // Update the partenaire using partial update
        Partenaire partialUpdatedPartenaire = new Partenaire();
        partialUpdatedPartenaire.setId(partenaire.getId());

        partialUpdatedPartenaire
            .nomPartenaire(UPDATED_NOM_PARTENAIRE)
            .autreNom(UPDATED_AUTRE_NOM)
            .logPhone(UPDATED_LOG_PHONE)
            .emailPartenaire(UPDATED_EMAIL_PARTENAIRE)
            .locPartenaire(UPDATED_LOC_PARTENAIRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPartenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPartenaire))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
        Partenaire testPartenaire = partenaireList.get(partenaireList.size() - 1);
        assertThat(testPartenaire.getNomPartenaire()).isEqualTo(UPDATED_NOM_PARTENAIRE);
        assertThat(testPartenaire.getAutreNom()).isEqualTo(UPDATED_AUTRE_NOM);
        assertThat(testPartenaire.getLogPhone()).isEqualTo(UPDATED_LOG_PHONE);
        assertThat(testPartenaire.getEmailPartenaire()).isEqualTo(UPDATED_EMAIL_PARTENAIRE);
        assertThat(testPartenaire.getLocPartenaire()).isEqualTo(UPDATED_LOC_PARTENAIRE);
    }

    @Test
    void patchNonExistingPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();
        partenaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partenaire.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();
        partenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPartenaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().collectList().block().size();
        partenaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partenaire))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Partenaire in the database
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePartenaire() {
        // Initialize the database
        partenaireRepository.save(partenaire).block();

        int databaseSizeBeforeDelete = partenaireRepository.findAll().collectList().block().size();

        // Delete the partenaire
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, partenaire.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Partenaire> partenaireList = partenaireRepository.findAll().collectList().block();
        assertThat(partenaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
