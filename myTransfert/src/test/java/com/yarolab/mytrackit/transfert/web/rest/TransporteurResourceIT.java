package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.Transporteur;
import com.yarolab.mytrackit.transfert.repository.TransporteurRepository;
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

/**
 * Integration tests for the {@link TransporteurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restTransporteurMockMvc;

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

    @BeforeEach
    public void initTest() {
        transporteur = createEntity(em);
    }

    @Test
    @Transactional
    void createTransporteur() throws Exception {
        int databaseSizeBeforeCreate = transporteurRepository.findAll().size();
        // Create the Transporteur
        restTransporteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isCreated());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeCreate + 1);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(DEFAULT_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(DEFAULT_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(DEFAULT_EMAIL_TRANSPORTEUR);
    }

    @Test
    @Transactional
    void createTransporteurWithExistingId() throws Exception {
        // Create the Transporteur with an existing ID
        transporteur.setId(1L);

        int databaseSizeBeforeCreate = transporteurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransporteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransporteurs() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList
        restTransporteurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTransporteur").value(hasItem(DEFAULT_NOM_TRANSPORTEUR)))
            .andExpect(jsonPath("$.[*].nomDirecteur").value(hasItem(DEFAULT_NOM_DIRECTEUR)))
            .andExpect(jsonPath("$.[*].phoneTransporteur").value(hasItem(DEFAULT_PHONE_TRANSPORTEUR)))
            .andExpect(jsonPath("$.[*].emailTransporteur").value(hasItem(DEFAULT_EMAIL_TRANSPORTEUR)));
    }

    @Test
    @Transactional
    void getTransporteur() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get the transporteur
        restTransporteurMockMvc
            .perform(get(ENTITY_API_URL_ID, transporteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transporteur.getId().intValue()))
            .andExpect(jsonPath("$.nomTransporteur").value(DEFAULT_NOM_TRANSPORTEUR))
            .andExpect(jsonPath("$.nomDirecteur").value(DEFAULT_NOM_DIRECTEUR))
            .andExpect(jsonPath("$.phoneTransporteur").value(DEFAULT_PHONE_TRANSPORTEUR))
            .andExpect(jsonPath("$.emailTransporteur").value(DEFAULT_EMAIL_TRANSPORTEUR));
    }

    @Test
    @Transactional
    void getNonExistingTransporteur() throws Exception {
        // Get the transporteur
        restTransporteurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransporteur() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();

        // Update the transporteur
        Transporteur updatedTransporteur = transporteurRepository.findById(transporteur.getId()).get();
        // Disconnect from session so that the updates on updatedTransporteur are not directly saved in db
        em.detach(updatedTransporteur);
        updatedTransporteur
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);

        restTransporteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTransporteur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTransporteur))
            )
            .andExpect(status().isOk());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(UPDATED_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(UPDATED_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(UPDATED_EMAIL_TRANSPORTEUR);
    }

    @Test
    @Transactional
    void putNonExistingTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();
        transporteur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transporteur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transporteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transporteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporteurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransporteurWithPatch() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();

        // Update the transporteur using partial update
        Transporteur partialUpdatedTransporteur = new Transporteur();
        partialUpdatedTransporteur.setId(transporteur.getId());

        partialUpdatedTransporteur
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);

        restTransporteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransporteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransporteur))
            )
            .andExpect(status().isOk());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(UPDATED_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(UPDATED_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(UPDATED_EMAIL_TRANSPORTEUR);
    }

    @Test
    @Transactional
    void fullUpdateTransporteurWithPatch() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();

        // Update the transporteur using partial update
        Transporteur partialUpdatedTransporteur = new Transporteur();
        partialUpdatedTransporteur.setId(transporteur.getId());

        partialUpdatedTransporteur
            .nomTransporteur(UPDATED_NOM_TRANSPORTEUR)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .phoneTransporteur(UPDATED_PHONE_TRANSPORTEUR)
            .emailTransporteur(UPDATED_EMAIL_TRANSPORTEUR);

        restTransporteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransporteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransporteur))
            )
            .andExpect(status().isOk());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNomTransporteur()).isEqualTo(UPDATED_NOM_TRANSPORTEUR);
        assertThat(testTransporteur.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testTransporteur.getPhoneTransporteur()).isEqualTo(UPDATED_PHONE_TRANSPORTEUR);
        assertThat(testTransporteur.getEmailTransporteur()).isEqualTo(UPDATED_EMAIL_TRANSPORTEUR);
    }

    @Test
    @Transactional
    void patchNonExistingTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();
        transporteur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transporteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transporteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transporteur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();
        transporteur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporteurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transporteur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransporteur() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        int databaseSizeBeforeDelete = transporteurRepository.findAll().size();

        // Delete the transporteur
        restTransporteurMockMvc
            .perform(delete(ENTITY_API_URL_ID, transporteur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
