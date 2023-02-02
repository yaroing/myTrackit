package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.PointFocalPartenaire;
import com.yarolab.mytrackit.repository.PointFocalPartenaireRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.PointFocalPartenaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PointFocalPartenaireResource {

    private final Logger log = LoggerFactory.getLogger(PointFocalPartenaireResource.class);

    private static final String ENTITY_NAME = "pointFocalPartenaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointFocalPartenaireRepository pointFocalPartenaireRepository;

    public PointFocalPartenaireResource(PointFocalPartenaireRepository pointFocalPartenaireRepository) {
        this.pointFocalPartenaireRepository = pointFocalPartenaireRepository;
    }

    /**
     * {@code POST  /point-focal-partenaires} : Create a new pointFocalPartenaire.
     *
     * @param pointFocalPartenaire the pointFocalPartenaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointFocalPartenaire, or with status {@code 400 (Bad Request)} if the pointFocalPartenaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-focal-partenaires")
    public Mono<ResponseEntity<PointFocalPartenaire>> createPointFocalPartenaire(@RequestBody PointFocalPartenaire pointFocalPartenaire)
        throws URISyntaxException {
        log.debug("REST request to save PointFocalPartenaire : {}", pointFocalPartenaire);
        if (pointFocalPartenaire.getId() != null) {
            throw new BadRequestAlertException("A new pointFocalPartenaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return pointFocalPartenaireRepository
            .save(pointFocalPartenaire)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/point-focal-partenaires/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /point-focal-partenaires/:id} : Updates an existing pointFocalPartenaire.
     *
     * @param id the id of the pointFocalPartenaire to save.
     * @param pointFocalPartenaire the pointFocalPartenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointFocalPartenaire,
     * or with status {@code 400 (Bad Request)} if the pointFocalPartenaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointFocalPartenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-focal-partenaires/{id}")
    public Mono<ResponseEntity<PointFocalPartenaire>> updatePointFocalPartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointFocalPartenaire pointFocalPartenaire
    ) throws URISyntaxException {
        log.debug("REST request to update PointFocalPartenaire : {}, {}", id, pointFocalPartenaire);
        if (pointFocalPartenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointFocalPartenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return pointFocalPartenaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return pointFocalPartenaireRepository
                    .save(pointFocalPartenaire)
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
     * {@code PATCH  /point-focal-partenaires/:id} : Partial updates given fields of an existing pointFocalPartenaire, field will ignore if it is null
     *
     * @param id the id of the pointFocalPartenaire to save.
     * @param pointFocalPartenaire the pointFocalPartenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointFocalPartenaire,
     * or with status {@code 400 (Bad Request)} if the pointFocalPartenaire is not valid,
     * or with status {@code 404 (Not Found)} if the pointFocalPartenaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointFocalPartenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-focal-partenaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<PointFocalPartenaire>> partialUpdatePointFocalPartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointFocalPartenaire pointFocalPartenaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointFocalPartenaire partially : {}, {}", id, pointFocalPartenaire);
        if (pointFocalPartenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointFocalPartenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return pointFocalPartenaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<PointFocalPartenaire> result = pointFocalPartenaireRepository
                    .findById(pointFocalPartenaire.getId())
                    .map(existingPointFocalPartenaire -> {
                        if (pointFocalPartenaire.getNomPf() != null) {
                            existingPointFocalPartenaire.setNomPf(pointFocalPartenaire.getNomPf());
                        }
                        if (pointFocalPartenaire.getFonctionPf() != null) {
                            existingPointFocalPartenaire.setFonctionPf(pointFocalPartenaire.getFonctionPf());
                        }
                        if (pointFocalPartenaire.getGsmPf() != null) {
                            existingPointFocalPartenaire.setGsmPf(pointFocalPartenaire.getGsmPf());
                        }
                        if (pointFocalPartenaire.getEmailPf() != null) {
                            existingPointFocalPartenaire.setEmailPf(pointFocalPartenaire.getEmailPf());
                        }

                        return existingPointFocalPartenaire;
                    })
                    .flatMap(pointFocalPartenaireRepository::save);

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
     * {@code GET  /point-focal-partenaires} : get all the pointFocalPartenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointFocalPartenaires in body.
     */
    @GetMapping("/point-focal-partenaires")
    public Mono<List<PointFocalPartenaire>> getAllPointFocalPartenaires() {
        log.debug("REST request to get all PointFocalPartenaires");
        return pointFocalPartenaireRepository.findAll().collectList();
    }

    /**
     * {@code GET  /point-focal-partenaires} : get all the pointFocalPartenaires as a stream.
     * @return the {@link Flux} of pointFocalPartenaires.
     */
    @GetMapping(value = "/point-focal-partenaires", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PointFocalPartenaire> getAllPointFocalPartenairesAsStream() {
        log.debug("REST request to get all PointFocalPartenaires as a stream");
        return pointFocalPartenaireRepository.findAll();
    }

    /**
     * {@code GET  /point-focal-partenaires/:id} : get the "id" pointFocalPartenaire.
     *
     * @param id the id of the pointFocalPartenaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointFocalPartenaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-focal-partenaires/{id}")
    public Mono<ResponseEntity<PointFocalPartenaire>> getPointFocalPartenaire(@PathVariable Long id) {
        log.debug("REST request to get PointFocalPartenaire : {}", id);
        Mono<PointFocalPartenaire> pointFocalPartenaire = pointFocalPartenaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointFocalPartenaire);
    }

    /**
     * {@code DELETE  /point-focal-partenaires/:id} : delete the "id" pointFocalPartenaire.
     *
     * @param id the id of the pointFocalPartenaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-focal-partenaires/{id}")
    public Mono<ResponseEntity<Void>> deletePointFocalPartenaire(@PathVariable Long id) {
        log.debug("REST request to delete PointFocalPartenaire : {}", id);
        return pointFocalPartenaireRepository
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
