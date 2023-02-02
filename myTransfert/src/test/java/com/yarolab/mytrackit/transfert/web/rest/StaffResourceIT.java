package com.yarolab.mytrackit.transfert.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.transfert.IntegrationTest;
import com.yarolab.mytrackit.transfert.domain.Staff;
import com.yarolab.mytrackit.transfert.repository.StaffRepository;
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
 * Integration tests for the {@link StaffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restStaffMockMvc;

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

    @BeforeEach
    public void initTest() {
        staff = createEntity(em);
    }

    @Test
    @Transactional
    void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();
        // Create the Staff
        restStaffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
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
    @Transactional
    void createStaffWithExistingId() throws Exception {
        // Create the Staff with an existing ID
        staff.setId(1L);

        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get all the staffList
        restStaffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
            .andExpect(jsonPath("$.[*].staffFname").value(hasItem(DEFAULT_STAFF_FNAME)))
            .andExpect(jsonPath("$.[*].staffLname").value(hasItem(DEFAULT_STAFF_LNAME)))
            .andExpect(jsonPath("$.[*].staffTitle").value(hasItem(DEFAULT_STAFF_TITLE)))
            .andExpect(jsonPath("$.[*].staffName").value(hasItem(DEFAULT_STAFF_NAME)))
            .andExpect(jsonPath("$.[*].staffEmail").value(hasItem(DEFAULT_STAFF_EMAIL)))
            .andExpect(jsonPath("$.[*].staffPhone").value(hasItem(DEFAULT_STAFF_PHONE)));
    }

    @Test
    @Transactional
    void getStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get the staff
        restStaffMockMvc
            .perform(get(ENTITY_API_URL_ID, staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staff.getId().intValue()))
            .andExpect(jsonPath("$.staffFname").value(DEFAULT_STAFF_FNAME))
            .andExpect(jsonPath("$.staffLname").value(DEFAULT_STAFF_LNAME))
            .andExpect(jsonPath("$.staffTitle").value(DEFAULT_STAFF_TITLE))
            .andExpect(jsonPath("$.staffName").value(DEFAULT_STAFF_NAME))
            .andExpect(jsonPath("$.staffEmail").value(DEFAULT_STAFF_EMAIL))
            .andExpect(jsonPath("$.staffPhone").value(DEFAULT_STAFF_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).get();
        // Disconnect from session so that the updates on updatedStaff are not directly saved in db
        em.detach(updatedStaff);
        updatedStaff
            .staffFname(UPDATED_STAFF_FNAME)
            .staffLname(UPDATED_STAFF_LNAME)
            .staffTitle(UPDATED_STAFF_TITLE)
            .staffName(UPDATED_STAFF_NAME)
            .staffEmail(UPDATED_STAFF_EMAIL)
            .staffPhone(UPDATED_STAFF_PHONE);

        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStaff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
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
    @Transactional
    void putNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff using partial update
        Staff partialUpdatedStaff = new Staff();
        partialUpdatedStaff.setId(staff.getId());

        partialUpdatedStaff.staffLname(UPDATED_STAFF_LNAME).staffTitle(UPDATED_STAFF_TITLE).staffEmail(UPDATED_STAFF_EMAIL);

        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
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
    @Transactional
    void fullUpdateStaffWithPatch() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

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

        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaff))
            )
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
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
    @Transactional
    void patchNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, staff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();
        staff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Delete the staff
        restStaffMockMvc
            .perform(delete(ENTITY_API_URL_ID, staff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
