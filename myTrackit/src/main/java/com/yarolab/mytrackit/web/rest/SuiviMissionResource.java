package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.SuiviMission;
import com.yarolab.mytrackit.repository.SuiviMissionRepository;
import com.yarolab.mytrackit.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.yarolab.mytrackit.domain.SuiviMission}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SuiviMissionResource {

    private final Logger log = LoggerFactory.getLogger(SuiviMissionResource.class);

    private static final String ENTITY_NAME = "suiviMission";

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
    public Mono<ResponseEntity<SuiviMission>> createSuiviMission(@RequestBody SuiviMission suiviMission) throws URISyntaxException {
        log.debug("REST request to save SuiviMission : {}", suiviMission);
        if (suiviMission.getId() != null) {
            throw new BadRequestAlertException("A new suiviMission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return suiviMissionRepository
            .save(suiviMission)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/suivi-missions/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
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
    public Mono<ResponseEntity<SuiviMission>> updateSuiviMission(
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

        return suiviMissionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return suiviMissionRepository
                    .save(suiviMission)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
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
    public Mono<ResponseEntity<SuiviMission>> partialUpdateSuiviMission(
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

        return suiviMissionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SuiviMission> result = suiviMissionRepository
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
                    .flatMap(suiviMissionRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /suivi-missions} : get all the suiviMissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suiviMissions in body.
     */
    @GetMapping("/suivi-missions")
    public Mono<List<SuiviMission>> getAllSuiviMissions() {
        log.debug("REST request to get all SuiviMissions");
        return suiviMissionRepository.findAll().collectList();
    }

    /**
     * {@code GET  /suivi-missions} : get all the suiviMissions as a stream.
     * @return the {@link Flux} of suiviMissions.
     */
    @GetMapping(value = "/suivi-missions", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<SuiviMission> getAllSuiviMissionsAsStream() {
        log.debug("REST request to get all SuiviMissions as a stream");
        return suiviMissionRepository.findAll();
    }

    /**
     * {@code GET  /suivi-missions/:id} : get the "id" suiviMission.
     *
     * @param id the id of the suiviMission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suiviMission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suivi-missions/{id}")
    public Mono<ResponseEntity<SuiviMission>> getSuiviMission(@PathVariable Long id) {
        log.debug("REST request to get SuiviMission : {}", id);
        Mono<SuiviMission> suiviMission = suiviMissionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(suiviMission);
    }

    /**
     * {@code DELETE  /suivi-missions/:id} : delete the "id" suiviMission.
     *
     * @param id the id of the suiviMission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suivi-missions/{id}")
    public Mono<ResponseEntity<Void>> deleteSuiviMission(@PathVariable Long id) {
        log.debug("REST request to delete SuiviMission : {}", id);
        return suiviMissionRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
