package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.PointService;
import com.yarolab.mytrackit.repository.PointServiceRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.PointService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PointServiceResource {

    private final Logger log = LoggerFactory.getLogger(PointServiceResource.class);

    private static final String ENTITY_NAME = "pointService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointServiceRepository pointServiceRepository;

    public PointServiceResource(PointServiceRepository pointServiceRepository) {
        this.pointServiceRepository = pointServiceRepository;
    }

    /**
     * {@code POST  /point-services} : Create a new pointService.
     *
     * @param pointService the pointService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointService, or with status {@code 400 (Bad Request)} if the pointService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-services")
    public Mono<ResponseEntity<PointService>> createPointService(@RequestBody PointService pointService) throws URISyntaxException {
        log.debug("REST request to save PointService : {}", pointService);
        if (pointService.getId() != null) {
            throw new BadRequestAlertException("A new pointService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return pointServiceRepository
            .save(pointService)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/point-services/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /point-services/:id} : Updates an existing pointService.
     *
     * @param id the id of the pointService to save.
     * @param pointService the pointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointService,
     * or with status {@code 400 (Bad Request)} if the pointService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-services/{id}")
    public Mono<ResponseEntity<PointService>> updatePointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointService pointService
    ) throws URISyntaxException {
        log.debug("REST request to update PointService : {}, {}", id, pointService);
        if (pointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return pointServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return pointServiceRepository
                    .save(pointService)
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
     * {@code PATCH  /point-services/:id} : Partial updates given fields of an existing pointService, field will ignore if it is null
     *
     * @param id the id of the pointService to save.
     * @param pointService the pointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointService,
     * or with status {@code 400 (Bad Request)} if the pointService is not valid,
     * or with status {@code 404 (Not Found)} if the pointService is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<PointService>> partialUpdatePointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointService pointService
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointService partially : {}, {}", id, pointService);
        if (pointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return pointServiceRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<PointService> result = pointServiceRepository
                    .findById(pointService.getId())
                    .map(existingPointService -> {
                        if (pointService.getNomPos() != null) {
                            existingPointService.setNomPos(pointService.getNomPos());
                        }
                        if (pointService.getPosLon() != null) {
                            existingPointService.setPosLon(pointService.getPosLon());
                        }
                        if (pointService.getPosLat() != null) {
                            existingPointService.setPosLat(pointService.getPosLat());
                        }
                        if (pointService.getPosContact() != null) {
                            existingPointService.setPosContact(pointService.getPosContact());
                        }
                        if (pointService.getPosGsm() != null) {
                            existingPointService.setPosGsm(pointService.getPosGsm());
                        }

                        return existingPointService;
                    })
                    .flatMap(pointServiceRepository::save);

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
     * {@code GET  /point-services} : get all the pointServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointServices in body.
     */
    @GetMapping("/point-services")
    public Mono<List<PointService>> getAllPointServices() {
        log.debug("REST request to get all PointServices");
        return pointServiceRepository.findAll().collectList();
    }

    /**
     * {@code GET  /point-services} : get all the pointServices as a stream.
     * @return the {@link Flux} of pointServices.
     */
    @GetMapping(value = "/point-services", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PointService> getAllPointServicesAsStream() {
        log.debug("REST request to get all PointServices as a stream");
        return pointServiceRepository.findAll();
    }

    /**
     * {@code GET  /point-services/:id} : get the "id" pointService.
     *
     * @param id the id of the pointService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-services/{id}")
    public Mono<ResponseEntity<PointService>> getPointService(@PathVariable Long id) {
        log.debug("REST request to get PointService : {}", id);
        Mono<PointService> pointService = pointServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointService);
    }

    /**
     * {@code DELETE  /point-services/:id} : delete the "id" pointService.
     *
     * @param id the id of the pointService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-services/{id}")
    public Mono<ResponseEntity<Void>> deletePointService(@PathVariable Long id) {
        log.debug("REST request to delete PointService : {}", id);
        return pointServiceRepository
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
