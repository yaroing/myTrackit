package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.PointFocalPointService;
import com.yarolab.mytrackit.repository.PointFocalPointServiceRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.PointFocalPointService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PointFocalPointServiceResource {

    private final Logger log = LoggerFactory.getLogger(PointFocalPointServiceResource.class);

    private static final String ENTITY_NAME = "pointFocalPointService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointFocalPointServiceRepository pointFocalPointServiceRepository;

    public PointFocalPointServiceResource(PointFocalPointServiceRepository pointFocalPointServiceRepository) {
        this.pointFocalPointServiceRepository = pointFocalPointServiceRepository;
    }

    /**
     * {@code POST  /point-focal-point-services} : Create a new pointFocalPointService.
     *
     * @param pointFocalPointService the pointFocalPointService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointFocalPointService, or with status {@code 400 (Bad Request)} if the pointFocalPointService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-focal-point-services")
    public Mono<ResponseEntity<PointFocalPointService>> createPointFocalPointService(
        @RequestBody PointFocalPointService pointFocalPointService
    ) throws URISyntaxException {
        log.debug("REST request to save PointFocalPointService : {}", pointFocalPointService);
        if (pointFocalPointService.getId() != null) {
            throw new BadRequestAlertException("A new pointFocalPointService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return pointFocalPointServiceRepository
            .save(pointFocalPointService)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/point-focal-point-services/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /point-focal-point-services/:id} : Updates an existing pointFocalPointService.
     *
     * @param id the id of the pointFocalPointService to save.
     * @param pointFocalPointService the pointFocalPointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointFocalPointService,
     * or with status {@code 400 (Bad Request)} if the pointFocalPointService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointFocalPointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-focal-point-services/{id}")
    public Mono<ResponseEntity<PointFocalPointService>> updatePointFocalPointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointFocalPointService pointFocalPointService
    ) throws URISyntaxException {
        log.debug("REST request to update PointFocalPointService : {}, {}", id, pointFocalPointService);
        if (pointFocalPointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointFocalPointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return pointFocalPointServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return pointFocalPointServiceRepository
                    .save(pointFocalPointService)
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
     * {@code PATCH  /point-focal-point-services/:id} : Partial updates given fields of an existing pointFocalPointService, field will ignore if it is null
     *
     * @param id the id of the pointFocalPointService to save.
     * @param pointFocalPointService the pointFocalPointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointFocalPointService,
     * or with status {@code 400 (Bad Request)} if the pointFocalPointService is not valid,
     * or with status {@code 404 (Not Found)} if the pointFocalPointService is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointFocalPointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-focal-point-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<PointFocalPointService>> partialUpdatePointFocalPointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointFocalPointService pointFocalPointService
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointFocalPointService partially : {}, {}", id, pointFocalPointService);
        if (pointFocalPointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointFocalPointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return pointFocalPointServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<PointFocalPointService> result = pointFocalPointServiceRepository
                    .findById(pointFocalPointService.getId())
                    .map(existingPointFocalPointService -> {
                        if (pointFocalPointService.getNomPf() != null) {
                            existingPointFocalPointService.setNomPf(pointFocalPointService.getNomPf());
                        }
                        if (pointFocalPointService.getFonctionPf() != null) {
                            existingPointFocalPointService.setFonctionPf(pointFocalPointService.getFonctionPf());
                        }
                        if (pointFocalPointService.getGsmPf() != null) {
                            existingPointFocalPointService.setGsmPf(pointFocalPointService.getGsmPf());
                        }
                        if (pointFocalPointService.getEmailPf() != null) {
                            existingPointFocalPointService.setEmailPf(pointFocalPointService.getEmailPf());
                        }

                        return existingPointFocalPointService;
                    })
                    .flatMap(pointFocalPointServiceRepository::save);

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
     * {@code GET  /point-focal-point-services} : get all the pointFocalPointServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointFocalPointServices in body.
     */
    @GetMapping("/point-focal-point-services")
    public Mono<List<PointFocalPointService>> getAllPointFocalPointServices() {
        log.debug("REST request to get all PointFocalPointServices");
        return pointFocalPointServiceRepository.findAll().collectList();
    }

    /**
     * {@code GET  /point-focal-point-services} : get all the pointFocalPointServices as a stream.
     * @return the {@link Flux} of pointFocalPointServices.
     */
    @GetMapping(value = "/point-focal-point-services", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PointFocalPointService> getAllPointFocalPointServicesAsStream() {
        log.debug("REST request to get all PointFocalPointServices as a stream");
        return pointFocalPointServiceRepository.findAll();
    }

    /**
     * {@code GET  /point-focal-point-services/:id} : get the "id" pointFocalPointService.
     *
     * @param id the id of the pointFocalPointService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointFocalPointService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-focal-point-services/{id}")
    public Mono<ResponseEntity<PointFocalPointService>> getPointFocalPointService(@PathVariable Long id) {
        log.debug("REST request to get PointFocalPointService : {}", id);
        Mono<PointFocalPointService> pointFocalPointService = pointFocalPointServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointFocalPointService);
    }

    /**
     * {@code DELETE  /point-focal-point-services/:id} : delete the "id" pointFocalPointService.
     *
     * @param id the id of the pointFocalPointService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-focal-point-services/{id}")
    public Mono<ResponseEntity<Void>> deletePointFocalPointService(@PathVariable Long id) {
        log.debug("REST request to delete PointFocalPointService : {}", id);
        return pointFocalPointServiceRepository
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
