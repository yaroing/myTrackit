package com.yarolab.mytrackit.pointservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.pointservice.IntegrationTest;
import com.yarolab.mytrackit.pointservice.domain.Section;
import com.yarolab.mytrackit.pointservice.repository.SectionRepository;
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
 * Integration tests for the {@link SectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SectionResourceIT {

    private static final String DEFAULT_SECTION_NOM = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CHEF_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_CHEF_SECTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CHEF = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CHEF = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_CHEF = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_CHEF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSectionMockMvc;

    private Section section;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Section createEntity(EntityManager em) {
        Section section = new Section()
            .sectionNom(DEFAULT_SECTION_NOM)
            .chefSection(DEFAULT_CHEF_SECTION)
            .emailChef(DEFAULT_EMAIL_CHEF)
            .phoneChef(DEFAULT_PHONE_CHEF);
        return section;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Section createUpdatedEntity(EntityManager em) {
        Section section = new Section()
            .sectionNom(UPDATED_SECTION_NOM)
            .chefSection(UPDATED_CHEF_SECTION)
            .emailChef(UPDATED_EMAIL_CHEF)
            .phoneChef(UPDATED_PHONE_CHEF);
        return section;
    }

    @BeforeEach
    public void initTest() {
        section = createEntity(em);
    }

    @Test
    @Transactional
    void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();
        // Create the Section
        restSectionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(DEFAULT_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(DEFAULT_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(DEFAULT_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(DEFAULT_PHONE_CHEF);
    }

    @Test
    @Transactional
    void createSectionWithExistingId() throws Exception {
        // Create the Section with an existing ID
        section.setId(1L);

        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSections() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList
        restSectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionNom").value(hasItem(DEFAULT_SECTION_NOM)))
            .andExpect(jsonPath("$.[*].chefSection").value(hasItem(DEFAULT_CHEF_SECTION)))
            .andExpect(jsonPath("$.[*].emailChef").value(hasItem(DEFAULT_EMAIL_CHEF)))
            .andExpect(jsonPath("$.[*].phoneChef").value(hasItem(DEFAULT_PHONE_CHEF)));
    }

    @Test
    @Transactional
    void getSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get the section
        restSectionMockMvc
            .perform(get(ENTITY_API_URL_ID, section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(section.getId().intValue()))
            .andExpect(jsonPath("$.sectionNom").value(DEFAULT_SECTION_NOM))
            .andExpect(jsonPath("$.chefSection").value(DEFAULT_CHEF_SECTION))
            .andExpect(jsonPath("$.emailChef").value(DEFAULT_EMAIL_CHEF))
            .andExpect(jsonPath("$.phoneChef").value(DEFAULT_PHONE_CHEF));
    }

    @Test
    @Transactional
    void getNonExistingSection() throws Exception {
        // Get the section
        restSectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section
        Section updatedSection = sectionRepository.findById(section.getId()).get();
        // Disconnect from session so that the updates on updatedSection are not directly saved in db
        em.detach(updatedSection);
        updatedSection
            .sectionNom(UPDATED_SECTION_NOM)
            .chefSection(UPDATED_CHEF_SECTION)
            .emailChef(UPDATED_EMAIL_CHEF)
            .phoneChef(UPDATED_PHONE_CHEF);

        restSectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSection.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSection))
            )
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(UPDATED_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(UPDATED_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(UPDATED_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(UPDATED_PHONE_CHEF);
    }

    @Test
    @Transactional
    void putNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();
        section.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, section.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(section))
            )
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(section))
            )
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSectionWithPatch() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section using partial update
        Section partialUpdatedSection = new Section();
        partialUpdatedSection.setId(section.getId());

        partialUpdatedSection.chefSection(UPDATED_CHEF_SECTION);

        restSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSection))
            )
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(DEFAULT_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(UPDATED_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(DEFAULT_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(DEFAULT_PHONE_CHEF);
    }

    @Test
    @Transactional
    void fullUpdateSectionWithPatch() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section using partial update
        Section partialUpdatedSection = new Section();
        partialUpdatedSection.setId(section.getId());

        partialUpdatedSection
            .sectionNom(UPDATED_SECTION_NOM)
            .chefSection(UPDATED_CHEF_SECTION)
            .emailChef(UPDATED_EMAIL_CHEF)
            .phoneChef(UPDATED_PHONE_CHEF);

        restSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSection))
            )
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(UPDATED_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(UPDATED_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(UPDATED_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(UPDATED_PHONE_CHEF);
    }

    @Test
    @Transactional
    void patchNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();
        section.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, section.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(section))
            )
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(section))
            )
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeDelete = sectionRepository.findAll().size();

        // Delete the section
        restSectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, section.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
