package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Transporteur;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.TransporteurRepository;
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
 * Integration tests for the {@link TransporteurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TransporteurResourceIT {

    private static final String DEFAULT_NOM_TRANSPORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TRANSPORTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DIRECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_TRANSPORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_TRANSPORTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_TRANSPORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_TRANSPORTEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transporteurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransporteurRepository transporteurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Transporteur transporteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transporteur createEntity(EntityManager em) {
        Transporteur transporteur = new Transporteur()
            .nomTransporteur(DEFAULT_NOM_TRANSPORTEUR)
            .nomDirecteur(DEFAULT_NOM_DIRECTEUR)
            .phoneTransporteur(DEFAULT_PHONE_TRANSPORTEUR)
            .emailTransporteur(DEFAULT_EMAIL_TRANSPORTEUR);
        return transporteur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transporteur createUpdatedEntity(EntityManager em) {
        Transporteur transporteur = new Transporteur()
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);
        return transporteur;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Transporteur.class).block();
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
        transporteur = createEntity(em);
    }

    @Test
    void createTransporteur() throws Exception {
        int databaseSizeBeforeCreate = transporteurRepository.findAll().collectList().block().size();
        // Create the Transporteur
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeCreate + 1);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(DEFAULT_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(DEFAULT_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(DEFAULT_EMAIL_TRANSPORTEUR);
    }

    @Test
    void createTransporteurWithExistingId() throws Exception {
        // Create the Transporteur with an existing ID
        transporteur.setId(1L);

        int databaseSizeBeforeCreate = transporteurRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTransporteursAsStream() {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        List<Transporteur> transporteurList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Transporteur.class)
            .getResponseBody()
            .filter(transporteur::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(transporteurList).isNotNull();
        assertThat(transporteurList).hasSize(1);
        Transporteur testTransporteur = transporteurList.get(0);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(DEFAULT_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(DEFAULT_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(DEFAULT_EMAIL_TRANSPORTEUR);
    }

    @Test
    void getAllTransporteurs() {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        // Get all the transporteurList
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
            .value(hasItem(transporteur.getId().intValue()))
            .jsonPath("$.[*].nomTransporteur")
            .value(hasItem(DEFAULT_NOM_TRANSPORTEUR))
            .jsonPath("$.[*].nomDirecteur")
            .value(hasItem(DEFAULT_NOM_DIRECTEUR))
            .jsonPath("$.[*].phoneTransporteur")
            .value(hasItem(DEFAULT_PHONE_TRANSPORTEUR))
            .jsonPath("$.[*].emailTransporteur")
            .value(hasItem(DEFAULT_EMAIL_TRANSPORTEUR));
    }

    @Test
    void getTransporteur() {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        // Get the transporteur
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, transporteur.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(transporteur.getId().intValue()))
            .jsonPath("$.nomTransporteur")
            .value(is(DEFAULT_NOM_TRANSPORTEUR))
            .jsonPath("$.nomDirecteur")
            .value(is(DEFAULT_NOM_DIRECTEUR))
            .jsonPath("$.phoneTransporteur")
            .value(is(DEFAULT_PHONE_TRANSPORTEUR))
            .jsonPath("$.emailTransporteur")
            .value(is(DEFAULT_EMAIL_TRANSPORTEUR));
    }

    @Test
    void getNonExistingTransporteur() {
        // Get the transporteur
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTransporteur() throws Exception {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();

        // Update the transporteur
        Transporteur updatedTransporteur = transporteurRepository.findById(transporteur.getId()).block();
        updatedTransporteur
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTransporteur.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTransporteur))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(UPDATED_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(UPDATED_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(UPDATED_EMAIL_TRANSPORTEUR);
    }

    @Test
    void putNonExistingTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();
        transporteur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, transporteur.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTransporteurWithPatch() throws Exception {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();

        // Update the transporteur using partial update
        Transporteur partialUpdatedTransporteur = new Transporteur();
        partialUpdatedTransporteur.setId(transporteur.getId());

        partialUpdatedTransporteur
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTransporteur.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTransporteur))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(UPDATED_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(UPDATED_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(UPDATED_EMAIL_TRANSPORTEUR);
    }

    @Test
    void fullUpdateTransporteurWithPatch() throws Exception {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();

        // Update the transporteur using partial update
        Transporteur partialUpdatedTransporteur = new Transporteur();
        partialUpdatedTransporteur.setId(transporteur.getId());

        partialUpdatedTransporteur
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTransporteur.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTransporteur))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(UPDATED_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(UPDATED_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(UPDATED_EMAIL_TRANSPORTEUR);
    }

    @Test
    void patchNonExistingTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();
        transporteur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, transporteur.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().collectList().block().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(transporteur))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTransporteur() {
        // Initialize the database
        transporteurRepository.save(transporteur).block();

        int databaseSizeBeforeDelete = transporteurRepository.findAll().collectList().block().size();

        // Delete the transporteur
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, transporteur.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Transporteur> transporteurList = transporteurRepository.findAll().collectList().block();
        assertThat(transporteurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
