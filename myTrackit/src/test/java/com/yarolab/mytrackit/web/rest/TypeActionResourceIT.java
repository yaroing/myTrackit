package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.TypeAction;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.TypeActionRepository;
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
 * Integration tests for the {@link TypeActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TypeActionResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeActionRepository typeActionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private TypeAction typeAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAction createEntity(EntityManager em) {
        TypeAction typeAction = new TypeAction().type(DEFAULT_TYPE);
        return typeAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAction createUpdatedEntity(EntityManager em) {
        TypeAction typeAction = new TypeAction().type(UPDATED_TYPE);
        return typeAction;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(TypeAction.class).block();
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
        typeAction = createEntity(em);
    }

    @Test
    void createTypeAction() throws Exception {
        int databaseSizeBeforeCreate = typeActionRepository.findAll().collectList().block().size();
        // Create the TypeAction
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createTypeActionWithExistingId() throws Exception {
        // Create the TypeAction with an existing ID
        typeAction.setId(1L);

        int databaseSizeBeforeCreate = typeActionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTypeActionsAsStream() {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        List<TypeAction> typeActionList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(TypeAction.class)
            .getResponseBody()
            .filter(typeAction::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(typeActionList).isNotNull();
        assertThat(typeActionList).hasSize(1);
        TypeAction testTypeAction = typeActionList.get(0);
        assertThat(testTypeAction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void getAllTypeActions() {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        // Get all the typeActionList
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
            .value(hasItem(typeAction.getId().intValue()))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getTypeAction() {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        // Get the typeAction
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, typeAction.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(typeAction.getId().intValue()))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingTypeAction() {
        // Get the typeAction
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTypeAction() throws Exception {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();

        // Update the typeAction
        TypeAction updatedTypeAction = typeActionRepository.findById(typeAction.getId()).block();
        updatedTypeAction.type(UPDATED_TYPE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTypeAction.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTypeAction))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();
        typeAction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, typeAction.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();
        typeAction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();
        typeAction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTypeActionWithPatch() throws Exception {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();

        // Update the typeAction using partial update
        TypeAction partialUpdatedTypeAction = new TypeAction();
        partialUpdatedTypeAction.setId(typeAction.getId());

        partialUpdatedTypeAction.type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTypeAction.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAction))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void fullUpdateTypeActionWithPatch() throws Exception {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();

        // Update the typeAction using partial update
        TypeAction partialUpdatedTypeAction = new TypeAction();
        partialUpdatedTypeAction.setId(typeAction.getId());

        partialUpdatedTypeAction.type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTypeAction.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAction))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();
        typeAction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, typeAction.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();
        typeAction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().collectList().block().size();
        typeAction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(typeAction))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTypeAction() {
        // Initialize the database
        typeActionRepository.save(typeAction).block();

        int databaseSizeBeforeDelete = typeActionRepository.findAll().collectList().block().size();

        // Delete the typeAction
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, typeAction.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<TypeAction> typeActionList = typeActionRepository.findAll().collectList().block();
        assertThat(typeActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
