package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Section;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.SectionRepository;
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
 * Integration tests for the {@link SectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Section.class).block();
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
        section = createEntity(em);
    }

    @Test
    void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().collectList().block().size();
        // Create the Section
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(DEFAULT_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(DEFAULT_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(DEFAULT_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(DEFAULT_PHONE_CHEF);
    }

    @Test
    void createSectionWithExistingId() throws Exception {
        // Create the Section with an existing ID
        section.setId(1L);

        int databaseSizeBeforeCreate = sectionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSectionsAsStream() {
        // Initialize the database
        sectionRepository.save(section).block();

        List<Section> sectionList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Section.class)
            .getResponseBody()
            .filter(section::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(sectionList).isNotNull();
        assertThat(sectionList).hasSize(1);
        Section testSection = sectionList.get(0);
        assertThat(testSection.getSectionNom()).isEqualTo(DEFAULT_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(DEFAULT_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(DEFAULT_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(DEFAULT_PHONE_CHEF);
    }

    @Test
    void getAllSections() {
        // Initialize the database
        sectionRepository.save(section).block();

        // Get all the sectionList
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
            .value(hasItem(section.getId().intValue()))
            .jsonPath("$.[*].sectionNom")
            .value(hasItem(DEFAULT_SECTION_NOM))
            .jsonPath("$.[*].chefSection")
            .value(hasItem(DEFAULT_CHEF_SECTION))
            .jsonPath("$.[*].emailChef")
            .value(hasItem(DEFAULT_EMAIL_CHEF))
            .jsonPath("$.[*].phoneChef")
            .value(hasItem(DEFAULT_PHONE_CHEF));
    }

    @Test
    void getSection() {
        // Initialize the database
        sectionRepository.save(section).block();

        // Get the section
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, section.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(section.getId().intValue()))
            .jsonPath("$.sectionNom")
            .value(is(DEFAULT_SECTION_NOM))
            .jsonPath("$.chefSection")
            .value(is(DEFAULT_CHEF_SECTION))
            .jsonPath("$.emailChef")
            .value(is(DEFAULT_EMAIL_CHEF))
            .jsonPath("$.phoneChef")
            .value(is(DEFAULT_PHONE_CHEF));
    }

    @Test
    void getNonExistingSection() {
        // Get the section
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSection() throws Exception {
        // Initialize the database
        sectionRepository.save(section).block();

        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();

        // Update the section
        Section updatedSection = sectionRepository.findById(section.getId()).block();
        updatedSection
            .sectionNom(UPDATED_SECTION_NOM)
            .chefSection(UPDATED_CHEF_SECTION)
            .emailChef(UPDATED_EMAIL_CHEF)
            .phoneChef(UPDATED_PHONE_CHEF);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedSection.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedSection))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(UPDATED_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(UPDATED_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(UPDATED_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(UPDATED_PHONE_CHEF);
    }

    @Test
    void putNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();
        section.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, section.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSectionWithPatch() throws Exception {
        // Initialize the database
        sectionRepository.save(section).block();

        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();

        // Update the section using partial update
        Section partialUpdatedSection = new Section();
        partialUpdatedSection.setId(section.getId());

        partialUpdatedSection.chefSection(UPDATED_CHEF_SECTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSection.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSection))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(DEFAULT_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(UPDATED_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(DEFAULT_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(DEFAULT_PHONE_CHEF);
    }

    @Test
    void fullUpdateSectionWithPatch() throws Exception {
        // Initialize the database
        sectionRepository.save(section).block();

        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();

        // Update the section using partial update
        Section partialUpdatedSection = new Section();
        partialUpdatedSection.setId(section.getId());

        partialUpdatedSection
            .sectionNom(UPDATED_SECTION_NOM)
            .chefSection(UPDATED_CHEF_SECTION)
            .emailChef(UPDATED_EMAIL_CHEF)
            .phoneChef(UPDATED_PHONE_CHEF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSection.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSection))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionNom()).isEqualTo(UPDATED_SECTION_NOM);
        assertThat(testSection.getChefSection()).isEqualTo(UPDATED_CHEF_SECTION);
        assertThat(testSection.getEmailChef()).isEqualTo(UPDATED_EMAIL_CHEF);
        assertThat(testSection.getPhoneChef()).isEqualTo(UPDATED_PHONE_CHEF);
    }

    @Test
    void patchNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();
        section.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, section.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().collectList().block().size();
        section.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(section))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSection() {
        // Initialize the database
        sectionRepository.save(section).block();

        int databaseSizeBeforeDelete = sectionRepository.findAll().collectList().block().size();

        // Delete the section
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, section.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Section> sectionList = sectionRepository.findAll().collectList().block();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
