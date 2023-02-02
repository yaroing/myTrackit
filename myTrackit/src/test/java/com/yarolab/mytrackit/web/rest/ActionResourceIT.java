package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Action;
import com.yarolab.mytrackit.repository.ActionRepository;
import com.yarolab.mytrackit.repository.EntityManager;
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
 * Integration tests for the {@link ActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ActionResourceIT {

    private static final Instant DEFAULT_DATE_ACTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ACTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RAPPORT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_RAPPORT_ACTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Action action;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createEntity(EntityManager em) {
        Action action = new Action().dateAction(DEFAULT_DATE_ACTION).rapportAction(DEFAULT_RAPPORT_ACTION);
        return action;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createUpdatedEntity(EntityManager em) {
        Action action = new Action().dateAction(UPDATED_DATE_ACTION).rapportAction(UPDATED_RAPPORT_ACTION);
        return action;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Action.class).block();
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
        action = createEntity(em);
    }

    @Test
    void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().collectList().block().size();
        // Create the Action
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDateAction()).isEqualTo(DEFAULT_DATE_ACTION);
        assertThat(testAction.getRapportAction()).isEqualTo(DEFAULT_RAPPORT_ACTION);
    }

    @Test
    void createActionWithExistingId() throws Exception {
        // Create the Action with an existing ID
        action.setId(1L);

        int databaseSizeBeforeCreate = actionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllActionsAsStream() {
        // Initialize the database
        actionRepository.save(action).block();

        List<Action> actionList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Action.class)
            .getResponseBody()
            .filter(action::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(actionList).isNotNull();
        assertThat(actionList).hasSize(1);
        Action testAction = actionList.get(0);
        assertThat(testAction.getDateAction()).isEqualTo(DEFAULT_DATE_ACTION);
        assertThat(testAction.getRapportAction()).isEqualTo(DEFAULT_RAPPORT_ACTION);
    }

    @Test
    void getAllActions() {
        // Initialize the database
        actionRepository.save(action).block();

        // Get all the actionList
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
            .value(hasItem(action.getId().intValue()))
            .jsonPath("$.[*].dateAction")
            .value(hasItem(DEFAULT_DATE_ACTION.toString()))
            .jsonPath("$.[*].rapportAction")
            .value(hasItem(DEFAULT_RAPPORT_ACTION.toString()));
    }

    @Test
    void getAction() {
        // Initialize the database
        actionRepository.save(action).block();

        // Get the action
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, action.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(action.getId().intValue()))
            .jsonPath("$.dateAction")
            .value(is(DEFAULT_DATE_ACTION.toString()))
            .jsonPath("$.rapportAction")
            .value(is(DEFAULT_RAPPORT_ACTION.toString()));
    }

    @Test
    void getNonExistingAction() {
        // Get the action
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAction() throws Exception {
        // Initialize the database
        actionRepository.save(action).block();

        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).block();
        updatedAction.dateAction(UPDATED_DATE_ACTION).rapportAction(UPDATED_RAPPORT_ACTION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedAction.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedAction))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDateAction()).isEqualTo(UPDATED_DATE_ACTION);
        assertThat(testAction.getRapportAction()).isEqualTo(UPDATED_RAPPORT_ACTION);
    }

    @Test
    void putNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();
        action.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, action.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateActionWithPatch() throws Exception {
        // Initialize the database
        actionRepository.save(action).block();

        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();

        // Update the action using partial update
        Action partialUpdatedAction = new Action();
        partialUpdatedAction.setId(action.getId());

        partialUpdatedAction.dateAction(UPDATED_DATE_ACTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAction.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAction))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDateAction()).isEqualTo(UPDATED_DATE_ACTION);
        assertThat(testAction.getRapportAction()).isEqualTo(DEFAULT_RAPPORT_ACTION);
    }

    @Test
    void fullUpdateActionWithPatch() throws Exception {
        // Initialize the database
        actionRepository.save(action).block();

        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();

        // Update the action using partial update
        Action partialUpdatedAction = new Action();
        partialUpdatedAction.setId(action.getId());

        partialUpdatedAction.dateAction(UPDATED_DATE_ACTION).rapportAction(UPDATED_RAPPORT_ACTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAction.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAction))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDateAction()).isEqualTo(UPDATED_DATE_ACTION);
        assertThat(testAction.getRapportAction()).isEqualTo(UPDATED_RAPPORT_ACTION);
    }

    @Test
    void patchNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();
        action.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, action.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().collectList().block().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(action))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAction() {
        // Initialize the database
        actionRepository.save(action).block();

        int databaseSizeBeforeDelete = actionRepository.findAll().collectList().block().size();

        // Delete the action
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, action.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Action> actionList = actionRepository.findAll().collectList().block();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
