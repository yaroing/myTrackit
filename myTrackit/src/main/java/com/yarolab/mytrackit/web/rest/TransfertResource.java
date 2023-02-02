package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Transfert;
import com.yarolab.mytrackit.repository.TransfertRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Transfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransfertResource {

    private final Logger log = LoggerFactory.getLogger(TransfertResource.class);

    private static final String ENTITY_NAME = "transfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransfertRepository transfertRepository;

    public TransfertResource(TransfertRepository transfertRepository) {
        this.transfertRepository = transfertRepository;
    }

    /**
     * {@code POST  /transferts} : Create a new transfert.
     *
     * @param transfert the transfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transfert, or with status {@code 400 (Bad Request)} if the transfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferts")
    public Mono<ResponseEntity<Transfert>> createTransfert(@RequestBody Transfert transfert) throws URISyntaxException {
        log.debug("REST request to save Transfert : {}", transfert);
        if (transfert.getId() != null) {
            throw new BadRequestAlertException("A new transfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return transfertRepository
            .save(transfert)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/transferts/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /transferts/:id} : Updates an existing transfert.
     *
     * @param id the id of the transfert to save.
     * @param transfert the transfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfert,
     * or with status {@code 400 (Bad Request)} if the transfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferts/{id}")
    public Mono<ResponseEntity<Transfert>> updateTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transfert transfert
    ) throws URISyntaxException {
        log.debug("REST request to update Transfert : {}, {}", id, transfert);
        if (transfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return transfertRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return transfertRepository
                    .save(transfert)
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
     * {@code PATCH  /transferts/:id} : Partial updates given fields of an existing transfert, field will ignore if it is null
     *
     * @param id the id of the transfert to save.
     * @param transfert the transfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfert,
     * or with status {@code 400 (Bad Request)} if the transfert is not valid,
     * or with status {@code 404 (Not Found)} if the transfert is not found,
     * or with status {@code 500 (Internal Server Error)} if the transfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transferts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Transfert>> partialUpdateTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transfert transfert
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transfert partially : {}, {}", id, transfert);
        if (transfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return transfertRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Transfert> result = transfertRepository
                    .findById(transfert.getId())
                    .map(existingTransfert -> {
                        if (transfert.getDateExp() != null) {
                            existingTransfert.setDateExp(transfert.getDateExp());
                        }
                        if (transfert.getNomChauffeur() != null) {
                            existingTransfert.setNomChauffeur(transfert.getNomChauffeur());
                        }
                        if (transfert.getDateRec() != null) {
                            existingTransfert.setDateRec(transfert.getDateRec());
                        }
                        if (transfert.getCphone() != null) {
                            existingTransfert.setCphone(transfert.getCphone());
                        }

                        return existingTransfert;
                    })
                    .flatMap(transfertRepository::save);

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
     * {@code GET  /transferts} : get all the transferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferts in body.
     */
    @GetMapping("/transferts")
    public Mono<List<Transfert>> getAllTransferts() {
        log.debug("REST request to get all Transferts");
        return transfertRepository.findAll().collectList();
    }

    /**
     * {@code GET  /transferts} : get all the transferts as a stream.
     * @return the {@link Flux} of transferts.
     */
    @GetMapping(value = "/transferts", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Transfert> getAllTransfertsAsStream() {
        log.debug("REST request to get all Transferts as a stream");
        return transfertRepository.findAll();
    }

    /**
     * {@code GET  /transferts/:id} : get the "id" transfert.
     *
     * @param id the id of the transfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferts/{id}")
    public Mono<ResponseEntity<Transfert>> getTransfert(@PathVariable Long id) {
        log.debug("REST request to get Transfert : {}", id);
        Mono<Transfert> transfert = transfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transfert);
    }

    /**
     * {@code DELETE  /transferts/:id} : delete the "id" transfert.
     *
     * @param id the id of the transfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferts/{id}")
    public Mono<ResponseEntity<Void>> deleteTransfert(@PathVariable Long id) {
        log.debug("REST request to delete Transfert : {}", id);
        return transfertRepository
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
