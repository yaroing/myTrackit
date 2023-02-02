package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.Mission;
import com.yarolab.mytrackit.transfert.repository.MissionRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.Mission}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MissionResource {

    private final Logger log = LoggerFactory.getLogger(MissionResource.class);

    private static final String ENTITY_NAME = "myTransfertMission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MissionRepository missionRepository;

    public MissionResource(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    /**
     * {@code POST  /missions} : Create a new mission.
     *
     * @param mission the mission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mission, or with status {@code 400 (Bad Request)} if the mission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/missions")
    public ResponseEntity<Mission> createMission(@RequestBody Mission mission) throws URISyntaxException {
        log.debug("REST request to save Mission : {}", mission);
        if (mission.getId() != null) {
            throw new BadRequestAlertException("A new mission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mission result = missionRepository.save(mission);
        return ResponseEntity
            .created(new URI("/api/missions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /missions/:id} : Updates an existing mission.
     *
     * @param id the id of the mission to save.
     * @param mission the mission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mission,
     * or with status {@code 400 (Bad Request)} if the mission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/missions/{id}")
    public ResponseEntity<Mission> updateMission(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mission mission)
        throws URISyntaxException {
        log.debug("REST request to update Mission : {}, {}", id, mission);
        if (mission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!missionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mission result = missionRepository.save(mission);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mission.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /missions/:id} : Partial updates given fields of an existing mission, field will ignore if it is null
     *
     * @param id the id of the mission to save.
     * @param mission the mission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mission,
     * or with status {@code 400 (Bad Request)} if the mission is not valid,
     * or with status {@code 404 (Not Found)} if the mission is not found,
     * or with status {@code 500 (Internal Server Error)} if the mission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/missions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mission> partialUpdateMission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mission mission
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mission partially : {}, {}", id, mission);
        if (mission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!missionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mission> result = missionRepository
            .findById(mission.getId())
            .map(existingMission -> {
                if (mission.getDateMission() != null) {
                    existingMission.setDateMission(mission.getDateMission());
                }
                if (mission.getSectionId() != null) {
                    existingMission.setSectionId(mission.getSectionId());
                }
                if (mission.getStaffId() != null) {
                    existingMission.setStaffId(mission.getStaffId());
                }
                if (mission.getRegionId() != null) {
                    existingMission.setRegionId(mission.getRegionId());
                }
                if (mission.getPartenaireId() != null) {
                    existingMission.setPartenaireId(mission.getPartenaireId());
                }
                if (mission.getDateDebut() != null) {
                    existingMission.setDateDebut(mission.getDateDebut());
                }
                if (mission.getDateFin() != null) {
                    existingMission.setDateFin(mission.getDateFin());
                }
                if (mission.getRapportMission() != null) {
                    existingMission.setRapportMission(mission.getRapportMission());
                }
                if (mission.getRapportMissionContentType() != null) {
                    existingMission.setRapportMissionContentType(mission.getRapportMissionContentType());
                }
                if (mission.getDebutMission() != null) {
                    existingMission.setDebutMission(mission.getDebutMission());
                }
                if (mission.getFinMission() != null) {
                    existingMission.setFinMission(mission.getFinMission());
                }
                if (mission.getField10() != null) {
                    existingMission.setField10(mission.getField10());
                }
                if (mission.getFin() != null) {
                    existingMission.setFin(mission.getFin());
                }

                return existingMission;
            })
            .map(missionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mission.getId().toString())
        );
    }

    /**
     * {@code GET  /missions} : get all the missions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of missions in body.
     */
    @GetMapping("/missions")
    public List<Mission> getAllMissions() {
        log.debug("REST request to get all Missions");
        return missionRepository.findAll();
    }

    /**
     * {@code GET  /missions/:id} : get the "id" mission.
     *
     * @param id the id of the mission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/missions/{id}")
    public ResponseEntity<Mission> getMission(@PathVariable Long id) {
        log.debug("REST request to get Mission : {}", id);
        Optional<Mission> mission = missionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mission);
    }

    /**
     * {@code DELETE  /missions/:id} : delete the "id" mission.
     *
     * @param id the id of the mission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/missions/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        log.debug("REST request to delete Mission : {}", id);
        missionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
