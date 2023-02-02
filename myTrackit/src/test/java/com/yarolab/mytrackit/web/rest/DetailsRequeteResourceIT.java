package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.DetailsRequete;
import com.yarolab.mytrackit.repository.DetailsRequeteRepository;
import com.yarolab.mytrackit.repository.EntityManager;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DetailsRequeteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DetailsRequeteResourceIT {

    private static final Double DEFAULT_QUANTITE_DEMANDEE = 1D;
    private static final Double UPDATED_QUANTITE_DEMANDEE = 2D;

    private static final Double DEFAULT_QUANTITE_APPROUVEE = 1D;
    private static final Double UPDATED_QUANTITE_APPROUVEE = 2D;

    private static final Double DEFAULT_QUANTITE_RECUE = 1D;
    private static final Double UPDATED_QUANTITE_RECUE = 2D;

    private static final String DEFAULT_ITEM_OBS = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_OBS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/details-requetes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailsRequeteRepository detailsRequeteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private DetailsRequete detailsRequete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsRequete createEntity(EntityManager em) {
        DetailsRequete detailsRequete = new DetailsRequete()
            .quantiteDemandee(DEFAULT_QUANTITE_DEMANDEE)
            .quantiteApprouvee(DEFAULT_QUANTITE_APPROUVEE)
            .quantiteRecue(DEFAULT_QUANTITE_RECUE)
            .itemObs(DEFAULT_ITEM_OBS);
        return detailsRequete;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsRequete createUpdatedEntity(EntityManager em) {
        DetailsRequete detailsRequete = new DetailsRequete()
            .quantiteDemandee(UPDATED_QUANTITE_DEMANDEE)
            .quantiteApprouvee(UPDATED_QUANTITE_APPROUVEE)
            .quantiteRecue(UPDATED_QUANTITE_RECUE)
            .itemObs(UPDATED_ITEM_OBS);
        return detailsRequete;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(DetailsRequete.class).block();
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
        detailsRequete = createEntity(em);
    }

    @Test
    void createDetailsRequete() throws Exception {
        int databaseSizeBeforeCreate = detailsRequeteRepository.findAll().collectList().block().size();
        // Create the DetailsRequete
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeCreate + 1);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(DEFAULT_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(DEFAULT_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(DEFAULT_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(DEFAULT_ITEM_OBS);
    }

    @Test
    void createDetailsRequeteWithExistingId() throws Exception {
        // Create the DetailsRequete with an existing ID
        detailsRequete.setId(1L);

        int databaseSizeBeforeCreate = detailsRequeteRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDetailsRequetesAsStream() {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        List<DetailsRequete> detailsRequeteList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DetailsRequete.class)
            .getResponseBody()
            .filter(detailsRequete::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(detailsRequeteList).isNotNull();
        assertThat(detailsRequeteList).hasSize(1);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(0);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(DEFAULT_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(DEFAULT_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(DEFAULT_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(DEFAULT_ITEM_OBS);
    }

    @Test
    void getAllDetailsRequetes() {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        // Get all the detailsRequeteList
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
            .value(hasItem(detailsRequete.getId().intValue()))
            .jsonPath("$.[*].quantiteDemandee")
            .value(hasItem(DEFAULT_QUANTITE_DEMANDEE.doubleValue()))
            .jsonPath("$.[*].quantiteApprouvee")
            .value(hasItem(DEFAULT_QUANTITE_APPROUVEE.doubleValue()))
            .jsonPath("$.[*].quantiteRecue")
            .value(hasItem(DEFAULT_QUANTITE_RECUE.doubleValue()))
            .jsonPath("$.[*].itemObs")
            .value(hasItem(DEFAULT_ITEM_OBS.toString()));
    }

    @Test
    void getDetailsRequete() {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        // Get the detailsRequete
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, detailsRequete.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(detailsRequete.getId().intValue()))
            .jsonPath("$.quantiteDemandee")
            .value(is(DEFAULT_QUANTITE_DEMANDEE.doubleValue()))
            .jsonPath("$.quantiteApprouvee")
            .value(is(DEFAULT_QUANTITE_APPROUVEE.doubleValue()))
            .jsonPath("$.quantiteRecue")
            .value(is(DEFAULT_QUANTITE_RECUE.doubleValue()))
            .jsonPath("$.itemObs")
            .value(is(DEFAULT_ITEM_OBS.toString()));
    }

    @Test
    void getNonExistingDetailsRequete() {
        // Get the detailsRequete
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDetailsRequete() throws Exception {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();

        // Update the detailsRequete
        DetailsRequete updatedDetailsRequete = detailsRequeteRepository.findById(detailsRequete.getId()).block();
        updatedDetailsRequete
            .quantiteDemandee(UPDATED_QUANTITE_DEMANDEE)
            .quantiteApprouvee(UPDATED_QUANTITE_APPROUVEE)
            .quantiteRecue(UPDATED_QUANTITE_RECUE)
            .itemObs(UPDATED_ITEM_OBS);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDetailsRequete.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedDetailsRequete))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(UPDATED_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(UPDATED_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(UPDATED_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(UPDATED_ITEM_OBS);
    }

    @Test
    void putNonExistingDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();
        detailsRequete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, detailsRequete.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDetailsRequeteWithPatch() throws Exception {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();

        // Update the detailsRequete using partial update
        DetailsRequete partialUpdatedDetailsRequete = new DetailsRequete();
        partialUpdatedDetailsRequete.setId(detailsRequete.getId());

        partialUpdatedDetailsRequete.itemObs(UPDATED_ITEM_OBS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDetailsRequete.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsRequete))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(DEFAULT_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(DEFAULT_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(DEFAULT_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(UPDATED_ITEM_OBS);
    }

    @Test
    void fullUpdateDetailsRequeteWithPatch() throws Exception {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();

        // Update the detailsRequete using partial update
        DetailsRequete partialUpdatedDetailsRequete = new DetailsRequete();
        partialUpdatedDetailsRequete.setId(detailsRequete.getId());

        partialUpdatedDetailsRequete
            .quantiteDemandee(UPDATED_QUANTITE_DEMANDEE)
            .quantiteApprouvee(UPDATED_QUANTITE_APPROUVEE)
            .quantiteRecue(UPDATED_QUANTITE_RECUE)
            .itemObs(UPDATED_ITEM_OBS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDetailsRequete.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsRequete))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
        DetailsRequete testDetailsRequete = detailsRequeteList.get(detailsRequeteList.size() - 1);
        assertThat(testDetailsRequete.getQuantiteDemandee()).isEqualTo(UPDATED_QUANTITE_DEMANDEE);
        assertThat(testDetailsRequete.getQuantiteApprouvee()).isEqualTo(UPDATED_QUANTITE_APPROUVEE);
        assertThat(testDetailsRequete.getQuantiteRecue()).isEqualTo(UPDATED_QUANTITE_RECUE);
        assertThat(testDetailsRequete.getItemObs()).isEqualTo(UPDATED_ITEM_OBS);
    }

    @Test
    void patchNonExistingDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();
        detailsRequete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, detailsRequete.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDetailsRequete() throws Exception {
        int databaseSizeBeforeUpdate = detailsRequeteRepository.findAll().collectList().block().size();
        detailsRequete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(detailsRequete))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the DetailsRequete in the database
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDetailsRequete() {
        // Initialize the database
        detailsRequeteRepository.save(detailsRequete).block();

        int databaseSizeBeforeDelete = detailsRequeteRepository.findAll().collectList().block().size();

        // Delete the detailsRequete
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, detailsRequete.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<DetailsRequete> detailsRequeteList = detailsRequeteRepository.findAll().collectList().block();
        assertThat(detailsRequeteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
