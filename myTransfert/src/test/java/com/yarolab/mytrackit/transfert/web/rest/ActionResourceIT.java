package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.Action;
import com.yarolab.mytrackit.transfert.repository.ActionRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActionResourceIT {

    private static final Integer DEFAULT_TRANSFERT_ID = 1;
    private static final Integer UPDATED_TRANSFERT_ID = 2;

    private static final Instant DEFAULT_DATE_ACTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ACTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TYPE_ACTION_ID = 1;
    private static final Integer UPDATED_TYPE_ACTION_ID = 2;

    private static final String DEFAULT_RAPPORT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_RAPPORT_ACTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_STAFF_ID = 1;
    private static final Integer UPDATED_STAFF_ID = 2;

    private static final String ENTITY_API_URL = "/api/actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionMockMvc;

    private Action action;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .transfertId(DEFAULT_TRANSFERT_ID)
            .dateAction(DEFAULT_DATE_ACTION)
            .typeActionId(DEFAULT_TYPE_ACTION_ID)
            .rapportAction(DEFAULT_RAPPORT_ACTION)
            .staffId(DEFAULT_STAFF_ID);
        return action;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createUpdatedEntity(EntityManager em) {
        Action action = new Action()
            .transfertId(UPDATED_TRANSFERT_ID)
            .dateAction(UPDATED_DATE_ACTION)
            .typeActionId(UPDATED_TYPE_ACTION_ID)
            .rapportAction(UPDATED_RAPPORT_ACTION)
            .staffId(UPDATED_STAFF_ID);
        return action;
    }

    @BeforeEach
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();
        // Create the Action
        restActionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getTransfertId()).isEqualTo(DEFAULT_TRANSFERT_ID);
        assertThat(testAction.getDateAction()).isEqualTo(DEFAULT_DATE_ACTION);
        assertThat(testAction.getTypeActionId()).isEqualTo(DEFAULT_TYPE_ACTION_ID);
        assertThat(testAction.getRapportAction()).isEqualTo(DEFAULT_RAPPORT_ACTION);
        assertThat(testAction.getStaffId()).isEqualTo(DEFAULT_STAFF_ID);
    }

    @Test
    @Transactional
    void createActionWithExistingId() throws Exception {
        // Create the Action with an existing ID
        action.setId(1L);

        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].transfertId").value(hasItem(DEFAULT_TRANSFERT_ID)))
            .andExpect(jsonPath("$.[*].dateAction").value(hasItem(DEFAULT_DATE_ACTION.toString())))
            .andExpect(jsonPath("$.[*].typeActionId").value(hasItem(DEFAULT_TYPE_ACTION_ID)))
            .andExpect(jsonPath("$.[*].rapportAction").value(hasItem(DEFAULT_RAPPORT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].staffId").value(hasItem(DEFAULT_STAFF_ID)));
    }

    @Test
    @Transactional
    void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc
            .perform(get(ENTITY_API_URL_ID, action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.transfertId").value(DEFAULT_TRANSFERT_ID))
            .andExpect(jsonPath("$.dateAction").value(DEFAULT_DATE_ACTION.toString()))
            .andExpect(jsonPath("$.typeActionId").value(DEFAULT_TYPE_ACTION_ID))
            .andExpect(jsonPath("$.rapportAction").value(DEFAULT_RAPPORT_ACTION.toString()))
            .andExpect(jsonPath("$.staffId").value(DEFAULT_STAFF_ID));
    }

    @Test
    @Transactional
    void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .transfertId(UPDATED_TRANSFERT_ID)
            .dateAction(UPDATED_DATE_ACTION)
            .typeActionId(UPDATED_TYPE_ACTION_ID)
            .rapportAction(UPDATED_RAPPORT_ACTION)
            .staffId(UPDATED_STAFF_ID);

        restActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAction))
            )
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getTransfertId()).isEqualTo(UPDATED_TRANSFERT_ID);
        assertThat(testAction.getDateAction()).isEqualTo(UPDATED_DATE_ACTION);
        assertThat(testAction.getTypeActionId()).isEqualTo(UPDATED_TYPE_ACTION_ID);
        assertThat(testAction.getRapportAction()).isEqualTo(UPDATED_RAPPORT_ACTION);
        assertThat(testAction.getStaffId()).isEqualTo(UPDATED_STAFF_ID);
    }

    @Test
    @Transactional
    void putNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();
        action.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, action.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(action))
            )
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(action))
            )
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActionWithPatch() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action using partial update
        Action partialUpdatedAction = new Action();
        partialUpdatedAction.setId(action.getId());

        partialUpdatedAction.transfertId(UPDATED_TRANSFERT_ID).typeActionId(UPDATED_TYPE_ACTION_ID);

        restActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAction))
            )
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getTransfertId()).isEqualTo(UPDATED_TRANSFERT_ID);
        assertThat(testAction.getDateAction()).isEqualTo(DEFAULT_DATE_ACTION);
        assertThat(testAction.getTypeActionId()).isEqualTo(UPDATED_TYPE_ACTION_ID);
        assertThat(testAction.getRapportAction()).isEqualTo(DEFAULT_RAPPORT_ACTION);
        assertThat(testAction.getStaffId()).isEqualTo(DEFAULT_STAFF_ID);
    }

    @Test
    @Transactional
    void fullUpdateActionWithPatch() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action using partial update
        Action partialUpdatedAction = new Action();
        partialUpdatedAction.setId(action.getId());

        partialUpdatedAction
            .transfertId(UPDATED_TRANSFERT_ID)
            .dateAction(UPDATED_DATE_ACTION)
            .typeActionId(UPDATED_TYPE_ACTION_ID)
            .rapportAction(UPDATED_RAPPORT_ACTION)
            .staffId(UPDATED_STAFF_ID);

        restActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAction))
            )
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getTransfertId()).isEqualTo(UPDATED_TRANSFERT_ID);
        assertThat(testAction.getDateAction()).isEqualTo(UPDATED_DATE_ACTION);
        assertThat(testAction.getTypeActionId()).isEqualTo(UPDATED_TYPE_ACTION_ID);
        assertThat(testAction.getRapportAction()).isEqualTo(UPDATED_RAPPORT_ACTION);
        assertThat(testAction.getStaffId()).isEqualTo(UPDATED_STAFF_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();
        action.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, action.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(action))
            )
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(action))
            )
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();
        action.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Delete the action
        restActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, action.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
