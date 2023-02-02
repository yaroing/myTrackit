package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.Staff;
import com.yarolab.mytrackit.transfert.repository.StaffRepository;
import com.yarolab.mytrackit.transfert.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.Staff}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StaffResource {

    private final Logger log = LoggerFactory.getLogger(StaffResource.class);

    private static final String ENTITY_NAME = "myTransfertStaff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaffRepository staffRepository;

    public StaffResource(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * {@code POST  /staff} : Create a new staff.
     *
     * @param staff the staff to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staff, or with status {@code 400 (Bad Request)} if the staff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/staff")
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) throws URISyntaxException {
        log.debug("REST request to save Staff : {}", staff);
        if (staff.getId() != null) {
            throw new BadRequestAlertException("A new staff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Staff result = staffRepository.save(staff);
        return ResponseEntity
            .created(new URI("/api/staff/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /staff/:id} : Updates an existing staff.
     *
     * @param id the id of the staff to save.
     * @param staff the staff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staff,
     * or with status {@code 400 (Bad Request)} if the staff is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/staff/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable(value = "id", required = false) final Long id, @RequestBody Staff staff)
        throws URISyntaxException {
        log.debug("REST request to update Staff : {}, {}", id, staff);
        if (staff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, staff.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!staffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Staff result = staffRepository.save(staff);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staff.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /staff/:id} : Partial updates given fields of an existing staff, field will ignore if it is null
     *
     * @param id the id of the staff to save.
     * @param staff the staff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staff,
     * or with status {@code 400 (Bad Request)} if the staff is not valid,
     * or with status {@code 404 (Not Found)} if the staff is not found,
     * or with status {@code 500 (Internal Server Error)} if the staff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/staff/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Staff> partialUpdateStaff(@PathVariable(value = "id", required = false) final Long id, @RequestBody Staff staff)
        throws URISyntaxException {
        log.debug("REST request to partial update Staff partially : {}, {}", id, staff);
        if (staff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, staff.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!staffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Staff> result = staffRepository
            .findById(staff.getId())
            .map(existingStaff -> {
                if (staff.getStaffFname() != null) {
                    existingStaff.setStaffFname(staff.getStaffFname());
                }
                if (staff.getStaffLname() != null) {
                    existingStaff.setStaffLname(staff.getStaffLname());
                }
                if (staff.getStaffTitle() != null) {
                    existingStaff.setStaffTitle(staff.getStaffTitle());
                }
                if (staff.getStaffName() != null) {
                    existingStaff.setStaffName(staff.getStaffName());
                }
                if (staff.getStaffEmail() != null) {
                    existingStaff.setStaffEmail(staff.getStaffEmail());
                }
                if (staff.getStaffPhone() != null) {
                    existingStaff.setStaffPhone(staff.getStaffPhone());
                }

                return existingStaff;
            })
            .map(staffRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staff.getId().toString())
        );
    }

    /**
     * {@code GET  /staff} : get all the staff.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staff in body.
     */
    @GetMapping("/staff")
    public List<Staff> getAllStaff() {
        log.debug("REST request to get all Staff");
        return staffRepository.findAll();
    }

    /**
     * {@code GET  /staff/:id} : get the "id" staff.
     *
     * @param id the id of the staff to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staff, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/staff/{id}")
    public ResponseEntity<Staff> getStaff(@PathVariable Long id) {
        log.debug("REST request to get Staff : {}", id);
        Optional<Staff> staff = staffRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staff);
    }

    /**
     * {@code DELETE  /staff/:id} : delete the "id" staff.
     *
     * @param id the id of the staff to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/staff/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        log.debug("REST request to delete Staff : {}", id);
        staffRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
