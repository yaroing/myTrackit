package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Staff;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.StaffRepository;
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
 * Integration tests for the {@link StaffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class StaffResourceIT {

    private static final String DEFAULT_STAFF_FNAME = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_FNAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_LNAME = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_LNAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/staff";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Staff staff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createEntity(EntityManager em) {
        Staff staff = new Staff()
            .staffFname(DEFAULT_STAFF_FNAME)
            .staffLname(DEFAULT_STAFF_LNAME)
            .staffTitle(DEFAULT_STAFF_TITLE)
            .staffName(DEFAULT_STAFF_NAME)
            .staffEmail(DEFAULT_STAFF_EMAIL)
            .staffPhone(DEFAULT_STAFF_PHONE);
        return staff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createUpdatedEntity(EntityManager em) {
        Staff staff = new Staff()
            .staffFname(UPDATED_STAFF_FNAME)
            .staffLname(UPDATED_STAFF_LNAME)
            .staffTitle(UPDATED_STAFF_TITLE)
            .staffName(UPDATED_STAFF_NAME)
            .staffEmail(UPDATED_STAFF_EMAIL)
            .staffPhone(UPDATED_STAFF_PHONE);
        return staff;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Staff.class).block();
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
        staff = createEntity(em);
    }

    @Test
    void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().collectList().block().size();
        // Create the Staff
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getStaffFname()).isEqualTo(DEFAULT_STAFF_FNAME);
        assertThat(testStaff.getStaffLname()).isEqualTo(DEFAULT_STAFF_LNAME);
        assertThat(testStaff.getStaffTitle()).isEqualTo(DEFAULT_STAFF_TITLE);
        assertThat(testStaff.getStaffName()).isEqualTo(DEFAULT_STAFF_NAME);
        assertThat(testStaff.getStaffEmail()).isEqualTo(DEFAULT_STAFF_EMAIL);
        assertThat(testStaff.getStaffPhone()).isEqualTo(DEFAULT_STAFF_PHONE);
    }

    @Test
    void createStaffWithExistingId() throws Exception {
        // Create the Staff with an existing ID
        staff.setId(1L);

        int databaseSizeBeforeCreate = staffRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllStaffAsStream() {
        // Initialize the database
        staffRepository.save(staff).block();

        List<Staff> staffList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Staff.class)
            .getResponseBody()
            .filter(staff::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(staffList).isNotNull();
        assertThat(staffList).hasSize(1);
        Staff testStaff = staffList.get(0);
        assertThat(testStaff.getStaffFname()).isEqualTo(DEFAULT_STAFF_FNAME);
        assertThat(testStaff.getStaffLname()).isEqualTo(DEFAULT_STAFF_LNAME);
        assertThat(testStaff.getStaffTitle()).isEqualTo(DEFAULT_STAFF_TITLE);
        assertThat(testStaff.getStaffName()).isEqualTo(DEFAULT_STAFF_NAME);
        assertThat(testStaff.getStaffEmail()).isEqualTo(DEFAULT_STAFF_EMAIL);
        assertThat(testStaff.getStaffPhone()).isEqualTo(DEFAULT_STAFF_PHONE);
    }

    @Test
    void getAllStaff() {
        // Initialize the database
        staffRepository.save(staff).block();

        // Get all the staffList
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
            .value(hasItem(staff.getId().intValue()))
            .jsonPath("$.[*].staffFname")
            .value(hasItem(DEFAULT_STAFF_FNAME))
            .jsonPath("$.[*].staffLname")
            .value(hasItem(DEFAULT_STAFF_LNAME))
            .jsonPath("$.[*].staffTitle")
            .value(hasItem(DEFAULT_STAFF_TITLE))
            .jsonPath("$.[*].staffName")
            .value(hasItem(DEFAULT_STAFF_NAME))
            .jsonPath("$.[*].staffEmail")
            .value(hasItem(DEFAULT_STAFF_EMAIL))
            .jsonPath("$.[*].staffPhone")
            .value(hasItem(DEFAULT_STAFF_PHONE));
    }

    @Test
    void getStaff() {
        // Initialize the database
        staffRepository.save(staff).block();

        // Get the staff
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, staff.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(staff.getId().intValue()))
            .jsonPath("$.staffFname")
            .value(is(DEFAULT_STAFF_FNAME))
            .jsonPath("$.staffLname")
            .value(is(DEFAULT_STAFF_LNAME))
            .jsonPath("$.staffTitle")
            .value(is(DEFAULT_STAFF_TITLE))
            .jsonPath("$.staffName")
            .value(is(DEFAULT_STAFF_NAME))
            .jsonPath("$.staffEmail")
            .value(is(DEFAULT_STAFF_EMAIL))
            .jsonPath("$.staffPhone")
            .value(is(DEFAULT_STAFF_PHONE));
    }

    @Test
    void getNonExistingStaff() {
        // Get the staff
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingStaff() throws Exception {
        // Initialize the database
        staffRepository.save(staff).block();

        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).block();
        updatedStaff
            .staffFname(UPDATED_STAFF_FNAME)
            .staffLname(UPDATED_STAFF_LNAME)
            .staffTitle(UPDATED_STAFF_TITLE)
            .staffName(UPDATED_STAFF_NAME)
            .staffEmail(UPDATED_STAFF_EMAIL)
            .staffPhone(UPDATED_STAFF_PHONE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedStaff.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedStaff))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getStaffFname()).isEqualTo(UPDATED_STAFF_FNAME);
        assertThat(testStaff.getStaffLname()).isEqualTo(UPDATED_STAFF_LNAME);
        assertThat(testStaff.getStaffTitle()).isEqualTo(UPDATED_STAFF_TITLE);
        assertThat(testStaff.getStaffName()).isEqualTo(UPDATED_STAFF_NAME);
        assertThat(testStaff.getStaffEmail()).isEqualTo(UPDATED_STAFF_EMAIL);
        assertThat(testStaff.getStaffPhone()).isEqualTo(UPDATED_STAFF_PHONE);
    }

    @Test
    void putNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();
        staff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, staff.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.save(staff).block();

        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff.staffLname(UPDATED_STAFF_LNAME).staffTitle(UPDATED_STAFF_TITLE).staffEmail(UPDATED_STAFF_EMAIL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getStaffFname()).isEqualTo(DEFAULT_STAFF_FNAME);
        assertThat(testStaff.getStaffLname()).isEqualTo(UPDATED_STAFF_LNAME);
        assertThat(testStaff.getStaffTitle()).isEqualTo(UPDATED_STAFF_TITLE);
        assertThat(testStaff.getStaffName()).isEqualTo(DEFAULT_STAFF_NAME);
        assertThat(testStaff.getStaffEmail()).isEqualTo(UPDATED_STAFF_EMAIL);
        assertThat(testStaff.getStaffPhone()).isEqualTo(DEFAULT_STAFF_PHONE);
    }

    @Test
    void fullUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.save(staff).block();

        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff
            .staffFname(UPDATED_STAFF_FNAME)
            .staffLname(UPDATED_STAFF_LNAME)
            .staffTitle(UPDATED_STAFF_TITLE)
            .staffName(UPDATED_STAFF_NAME)
            .staffEmail(UPDATED_STAFF_EMAIL)
            .staffPhone(UPDATED_STAFF_PHONE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getStaffFname()).isEqualTo(UPDATED_STAFF_FNAME);
        assertThat(testStaff.getStaffLname()).isEqualTo(UPDATED_STAFF_LNAME);
        assertThat(testStaff.getStaffTitle()).isEqualTo(UPDATED_STAFF_TITLE);
        assertThat(testStaff.getStaffName()).isEqualTo(UPDATED_STAFF_NAME);
        assertThat(testStaff.getStaffEmail()).isEqualTo(UPDATED_STAFF_EMAIL);
        assertThat(testStaff.getStaffPhone()).isEqualTo(UPDATED_STAFF_PHONE);
    }

    @Test
    void patchNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();
        staff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, staff.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().collectList().block().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(staff))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStaff() {
        // Initialize the database
        staffRepository.save(staff).block();

        int databaseSizeBeforeDelete = staffRepository.findAll().collectList().block().size();

        // Delete the staff
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, staff.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Staff> staffList = staffRepository.findAll().collectList().block();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
