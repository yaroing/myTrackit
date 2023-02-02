package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Monitoring;
import com.yarolab.mytrackit.repository.MonitoringRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Monitoring}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MonitoringResource {

    private final Logger log = LoggerFactory.getLogger(MonitoringResource.class);

    private static final String ENTITY_NAME = "monitoring";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonitoringRepository monitoringRepository;

    public MonitoringResource(MonitoringRepository monitoringRepository) {
        this.monitoringRepository = monitoringRepository;
    }

    /**
     * {@code POST  /monitorings} : Create a new monitoring.
     *
     * @param monitoring the monitoring to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monitoring, or with status {@code 400 (Bad Request)} if the monitoring has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monitorings")
    public Mono<ResponseEntity<Monitoring>> createMonitoring(@RequestBody Monitoring monitoring) throws URISyntaxException {
        log.debug("REST request to save Monitoring : {}", monitoring);
        if (monitoring.getId() != null) {
            throw new BadRequestAlertException("A new monitoring cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return monitoringRepository
            .save(monitoring)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/monitorings/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /monitorings/:id} : Updates an existing monitoring.
     *
     * @param id the id of the monitoring to save.
     * @param monitoring the monitoring to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monitoring,
     * or with status {@code 400 (Bad Request)} if the monitoring is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monitoring couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monitorings/{id}")
    public Mono<ResponseEntity<Monitoring>> updateMonitoring(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Monitoring monitoring
    ) throws URISyntaxException {
        log.debug("REST request to update Monitoring : {}, {}", id, monitoring);
        if (monitoring.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monitoring.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return monitoringRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return monitoringRepository
                    .save(monitoring)
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
     * {@code PATCH  /monitorings/:id} : Partial updates given fields of an existing monitoring, field will ignore if it is null
     *
     * @param id the id of the monitoring to save.
     * @param monitoring the monitoring to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monitoring,
     * or with status {@code 400 (Bad Request)} if the monitoring is not valid,
     * or with status {@code 404 (Not Found)} if the monitoring is not found,
     * or with status {@code 500 (Internal Server Error)} if the monitoring couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monitorings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Monitoring>> partialUpdateMonitoring(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Monitoring monitoring
    ) throws URISyntaxException {
        log.debug("REST request to partial update Monitoring partially : {}, {}", id, monitoring);
        if (monitoring.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monitoring.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return monitoringRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Monitoring> result = monitoringRepository
                    .findById(monitoring.getId())
                    .map(existingMonitoring -> {
                        if (monitoring.getAtpeAnnee() != null) {
                            existingMonitoring.setAtpeAnnee(monitoring.getAtpeAnnee());
                        }
                        if (monitoring.getAtpeMois() != null) {
                            existingMonitoring.setAtpeMois(monitoring.getAtpeMois());
                        }
                        if (monitoring.getAtpeStock() != null) {
                            existingMonitoring.setAtpeStock(monitoring.getAtpeStock());
                        }
                        if (monitoring.getAtpeDispo() != null) {
                            existingMonitoring.setAtpeDispo(monitoring.getAtpeDispo());
                        }
                        if (monitoring.getAtpeEndom() != null) {
                            existingMonitoring.setAtpeEndom(monitoring.getAtpeEndom());
                        }
                        if (monitoring.getAtpePerime() != null) {
                            existingMonitoring.setAtpePerime(monitoring.getAtpePerime());
                        }
                        if (monitoring.getAtpeRupture() != null) {
                            existingMonitoring.setAtpeRupture(monitoring.getAtpeRupture());
                        }
                        if (monitoring.getAtpeNjour() != null) {
                            existingMonitoring.setAtpeNjour(monitoring.getAtpeNjour());
                        }
                        if (monitoring.getAtpeMagasin() != null) {
                            existingMonitoring.setAtpeMagasin(monitoring.getAtpeMagasin());
                        }
                        if (monitoring.getAtpePalette() != null) {
                            existingMonitoring.setAtpePalette(monitoring.getAtpePalette());
                        }
                        if (monitoring.getAtpePosition() != null) {
                            existingMonitoring.setAtpePosition(monitoring.getAtpePosition());
                        }
                        if (monitoring.getAtpeHauteur() != null) {
                            existingMonitoring.setAtpeHauteur(monitoring.getAtpeHauteur());
                        }
                        if (monitoring.getAtpePersonnel() != null) {
                            existingMonitoring.setAtpePersonnel(monitoring.getAtpePersonnel());
                        }
                        if (monitoring.getAtpeAdmission() != null) {
                            existingMonitoring.setAtpeAdmission(monitoring.getAtpeAdmission());
                        }
                        if (monitoring.getAtpeSortie() != null) {
                            existingMonitoring.setAtpeSortie(monitoring.getAtpeSortie());
                        }
                        if (monitoring.getAtpeGueris() != null) {
                            existingMonitoring.setAtpeGueris(monitoring.getAtpeGueris());
                        }
                        if (monitoring.getAtpeAbandon() != null) {
                            existingMonitoring.setAtpeAbandon(monitoring.getAtpeAbandon());
                        }
                        if (monitoring.getAtpePoids() != null) {
                            existingMonitoring.setAtpePoids(monitoring.getAtpePoids());
                        }
                        if (monitoring.getAtpeTrasnsfert() != null) {
                            existingMonitoring.setAtpeTrasnsfert(monitoring.getAtpeTrasnsfert());
                        }
                        if (monitoring.getAtpeParent() != null) {
                            existingMonitoring.setAtpeParent(monitoring.getAtpeParent());
                        }

                        return existingMonitoring;
                    })
                    .flatMap(monitoringRepository::save);

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
     * {@code GET  /monitorings} : get all the monitorings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monitorings in body.
     */
    @GetMapping("/monitorings")
    public Mono<List<Monitoring>> getAllMonitorings() {
        log.debug("REST request to get all Monitorings");
        return monitoringRepository.findAll().collectList();
    }

    /**
     * {@code GET  /monitorings} : get all the monitorings as a stream.
     * @return the {@link Flux} of monitorings.
     */
    @GetMapping(value = "/monitorings", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Monitoring> getAllMonitoringsAsStream() {
        log.debug("REST request to get all Monitorings as a stream");
        return monitoringRepository.findAll();
    }

    /**
     * {@code GET  /monitorings/:id} : get the "id" monitoring.
     *
     * @param id the id of the monitoring to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monitoring, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monitorings/{id}")
    public Mono<ResponseEntity<Monitoring>> getMonitoring(@PathVariable Long id) {
        log.debug("REST request to get Monitoring : {}", id);
        Mono<Monitoring> monitoring = monitoringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(monitoring);
    }

    /**
     * {@code DELETE  /monitorings/:id} : delete the "id" monitoring.
     *
     * @param id the id of the monitoring to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monitorings/{id}")
    public Mono<ResponseEntity<Void>> deleteMonitoring(@PathVariable Long id) {
        log.debug("REST request to delete Monitoring : {}", id);
        return monitoringRepository
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
