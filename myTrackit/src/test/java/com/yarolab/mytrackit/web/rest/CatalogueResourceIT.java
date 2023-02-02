package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Catalogue;
import com.yarolab.mytrackit.repository.CatalogueRepository;
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

/**
 * Integration tests for the {@link CatalogueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CatalogueResourceIT {

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_DESC = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_GROUP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/catalogues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Catalogue catalogue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .materialCode(DEFAULT_MATERIAL_CODE)
            .materialDesc(DEFAULT_MATERIAL_DESC)
            .materialGroup(DEFAULT_MATERIAL_GROUP);
        return catalogue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createUpdatedEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialDesc(UPDATED_MATERIAL_DESC)
            .materialGroup(UPDATED_MATERIAL_GROUP);
        return catalogue;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Catalogue.class).block();
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
        catalogue = createEntity(em);
    }

    @Test
    void createCatalogue() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().collectList().block().size();
        // Create the Catalogue
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate + 1);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testCatalogue.getMaterialDesc()).isEqualTo(DEFAULT_MATERIAL_DESC);
        assertThat(testCatalogue.getMaterialGroup()).isEqualTo(DEFAULT_MATERIAL_GROUP);
    }

    @Test
    void createCatalogueWithExistingId() throws Exception {
        // Create the Catalogue with an existing ID
        catalogue.setId(1L);

        int databaseSizeBeforeCreate = catalogueRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCataloguesAsStream() {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        List<Catalogue> catalogueList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Catalogue.class)
            .getResponseBody()
            .filter(catalogue::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(catalogueList).isNotNull();
        assertThat(catalogueList).hasSize(1);
        Catalogue testCatalogue = catalogueList.get(0);
        assertThat(testCatalogue.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testCatalogue.getMaterialDesc()).isEqualTo(DEFAULT_MATERIAL_DESC);
        assertThat(testCatalogue.getMaterialGroup()).isEqualTo(DEFAULT_MATERIAL_GROUP);
    }

    @Test
    void getAllCatalogues() {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        // Get all the catalogueList
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
            .value(hasItem(catalogue.getId().intValue()))
            .jsonPath("$.[*].materialCode")
            .value(hasItem(DEFAULT_MATERIAL_CODE))
            .jsonPath("$.[*].materialDesc")
            .value(hasItem(DEFAULT_MATERIAL_DESC))
            .jsonPath("$.[*].materialGroup")
            .value(hasItem(DEFAULT_MATERIAL_GROUP));
    }

    @Test
    void getCatalogue() {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        // Get the catalogue
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, catalogue.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(catalogue.getId().intValue()))
            .jsonPath("$.materialCode")
            .value(is(DEFAULT_MATERIAL_CODE))
            .jsonPath("$.materialDesc")
            .value(is(DEFAULT_MATERIAL_DESC))
            .jsonPath("$.materialGroup")
            .value(is(DEFAULT_MATERIAL_GROUP));
    }

    @Test
    void getNonExistingCatalogue() {
        // Get the catalogue
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();

        // Update the catalogue
        Catalogue updatedCatalogue = catalogueRepository.findById(catalogue.getId()).block();
        updatedCatalogue.materialCode(UPDATED_MATERIAL_CODE).materialDesc(UPDATED_MATERIAL_DESC).materialGroup(UPDATED_MATERIAL_GROUP);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedCatalogue.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedCatalogue))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testCatalogue.getMaterialDesc()).isEqualTo(UPDATED_MATERIAL_DESC);
        assertThat(testCatalogue.getMaterialGroup()).isEqualTo(UPDATED_MATERIAL_GROUP);
    }

    @Test
    void putNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();
        catalogue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, catalogue.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCatalogueWithPatch() throws Exception {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();

        // Update the catalogue using partial update
        Catalogue partialUpdatedCatalogue = new Catalogue();
        partialUpdatedCatalogue.setId(catalogue.getId());

        partialUpdatedCatalogue.materialCode(UPDATED_MATERIAL_CODE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCatalogue.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogue))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testCatalogue.getMaterialDesc()).isEqualTo(DEFAULT_MATERIAL_DESC);
        assertThat(testCatalogue.getMaterialGroup()).isEqualTo(DEFAULT_MATERIAL_GROUP);
    }

    @Test
    void fullUpdateCatalogueWithPatch() throws Exception {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();

        // Update the catalogue using partial update
        Catalogue partialUpdatedCatalogue = new Catalogue();
        partialUpdatedCatalogue.setId(catalogue.getId());

        partialUpdatedCatalogue
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialDesc(UPDATED_MATERIAL_DESC)
            .materialGroup(UPDATED_MATERIAL_GROUP);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCatalogue.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogue))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testCatalogue.getMaterialDesc()).isEqualTo(UPDATED_MATERIAL_DESC);
        assertThat(testCatalogue.getMaterialGroup()).isEqualTo(UPDATED_MATERIAL_GROUP);
    }

    @Test
    void patchNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();
        catalogue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, catalogue.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().collectList().block().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(catalogue))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCatalogue() {
        // Initialize the database
        catalogueRepository.save(catalogue).block();

        int databaseSizeBeforeDelete = catalogueRepository.findAll().collectList().block().size();

        // Delete the catalogue
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, catalogue.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Catalogue> catalogueList = catalogueRepository.findAll().collectList().block();
        assertThat(catalogueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
