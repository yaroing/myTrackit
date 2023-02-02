package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.SuiviMission;
import com.yarolab.mytrackit.transfert.repository.SuiviMissionRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.SuiviMission}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SuiviMissionResource {

    private final Logger log = LoggerFactory.getLogger(SuiviMissionResource.class);

    private static final String ENTITY_NAME = "myTransfertSuiviMission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuiviMissionRepository suiviMissionRepository;

    public SuiviMissionResource(SuiviMissionRepository suiviMissionRepository) {
        this.suiviMissionRepository = suiviMissionRepository;
    }

    /**
     * {@code POST  /suivi-missions} : Create a new suiviMission.
     *
     * @param suiviMission the suiviMission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suiviMission, or with status {@code 400 (Bad Request)} if the suiviMission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suivi-missions")
    public ResponseEntity<SuiviMission> createSuiviMission(@RequestBody SuiviMission suiviMission) throws URISyntaxException {
        log.debug("REST request to save SuiviMission : {}", suiviMission);
        if (suiviMission.getId() != null) {
            throw new BadRequestAlertException("A new suiviMission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuiviMission result = suiviMissionRepository.save(suiviMission);
        return ResponseEntity
            .created(new URI("/api/suivi-missions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suivi-missions/:id} : Updates an existing suiviMission.
     *
     * @param id the id of the suiviMission to save.
     * @param suiviMission the suiviMission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suiviMission,
     * or with status {@code 400 (Bad Request)} if the suiviMission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suiviMission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suivi-missions/{id}")
    public ResponseEntity<SuiviMission> updateSuiviMission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SuiviMission suiviMission
    ) throws URISyntaxException {
        log.debug("REST request to update SuiviMission : {}, {}", id, suiviMission);
        if (suiviMission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suiviMission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!suiviMissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SuiviMission result = suiviMissionRepository.save(suiviMission);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suiviMission.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /suivi-missions/:id} : Partial updates given fields of an existing suiviMission, field will ignore if it is null
     *
     * @param id the id of the suiviMission to save.
     * @param suiviMission the suiviMission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suiviMission,
     * or with status {@code 400 (Bad Request)} if the suiviMission is not valid,
     * or with status {@code 404 (Not Found)} if the suiviMission is not found,
     * or with status {@code 500 (Internal Server Error)} if the suiviMission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/suivi-missions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SuiviMission> partialUpdateSuiviMission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SuiviMission suiviMission
    ) throws URISyntaxException {
        log.debug("REST request to partial update SuiviMission partially : {}, {}", id, suiviMission);
        if (suiviMission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suiviMission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!suiviMissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SuiviMission> result = suiviMissionRepository
            .findById(suiviMission.getId())
            .map(existingSuiviMission -> {
                if (suiviMission.getProblemeConstate() != null) {
                    existingSuiviMission.setProblemeConstate(suiviMission.getProblemeConstate());
                }
                if (suiviMission.getActionRecommandee() != null) {
                    existingSuiviMission.setActionRecommandee(suiviMission.getActionRecommandee());
                }
                if (suiviMission.getDateEcheance() != null) {
                    existingSuiviMission.setDateEcheance(suiviMission.getDateEcheance());
                }

                return existingSuiviMission;
            })
            .map(suiviMissionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suiviMission.getId().toString())
        );
    }

    /**
     * {@code GET  /suivi-missions} : get all the suiviMissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suiviMissions in body.
     */
    @GetMapping("/suivi-missions")
    public List<SuiviMission> getAllSuiviMissions() {
        log.debug("REST request to get all SuiviMissions");
        return suiviMissionRepository.findAll();
    }

    /**
     * {@code GET  /suivi-missions/:id} : get the "id" suiviMission.
     *
     * @param id the id of the suiviMission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suiviMission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suivi-missions/{id}")
    public ResponseEntity<SuiviMission> getSuiviMission(@PathVariable Long id) {
        log.debug("REST request to get SuiviMission : {}", id);
        Optional<SuiviMission> suiviMission = suiviMissionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(suiviMission);
    }

    /**
     * {@code DELETE  /suivi-missions/:id} : delete the "id" suiviMission.
     *
     * @param id the id of the suiviMission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suivi-missions/{id}")
    public ResponseEntity<Void> deleteSuiviMission(@PathVariable Long id) {
        log.debug("REST request to delete SuiviMission : {}", id);
        suiviMissionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
