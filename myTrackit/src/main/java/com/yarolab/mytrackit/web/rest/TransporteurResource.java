package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Transporteur;
import com.yarolab.mytrackit.repository.TransporteurRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Transporteur}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransporteurResource {

    private final Logger log = LoggerFactory.getLogger(TransporteurResource.class);

    private static final String ENTITY_NAME = "transporteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransporteurRepository transporteurRepository;

    public TransporteurResource(TransporteurRepository transporteurRepository) {
        this.transporteurRepository = transporteurRepository;
    }

    /**
     * {@code POST  /transporteurs} : Create a new transporteur.
     *
     * @param transporteur the transporteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transporteur, or with status {@code 400 (Bad Request)} if the transporteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transporteurs")
    public Mono<ResponseEntity<Transporteur>> createTransporteur(@RequestBody Transporteur transporteur) throws URISyntaxException {
        log.debug("REST request to save Transporteur : {}", transporteur);
        if (transporteur.getId() != null) {
            throw new BadRequestAlertException("A new transporteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return transporteurRepository
            .save(transporteur)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/transporteurs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /transporteurs/:id} : Updates an existing transporteur.
     *
     * @param id the id of the transporteur to save.
     * @param transporteur the transporteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporteur,
     * or with status {@code 400 (Bad Request)} if the transporteur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transporteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transporteurs/{id}")
    public Mono<ResponseEntity<Transporteur>> updateTransporteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transporteur transporteur
    ) throws URISyntaxException {
        log.debug("REST request to update Transporteur : {}, {}", id, transporteur);
        if (transporteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transporteur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return transporteurRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return transporteurRepository
                    .save(transporteur)
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
     * {@code PATCH  /transporteurs/:id} : Partial updates given fields of an existing transporteur, field will ignore if it is null
     *
     * @param id the id of the transporteur to save.
     * @param transporteur the transporteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporteur,
     * or with status {@code 400 (Bad Request)} if the transporteur is not valid,
     * or with status {@code 404 (Not Found)} if the transporteur is not found,
     * or with status {@code 500 (Internal Server Error)} if the transporteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transporteurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Transporteur>> partialUpdateTransporteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transporteur transporteur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transporteur partially : {}, {}", id, transporteur);
        if (transporteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transporteur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return transporteurRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Transporteur> result = transporteurRepository
                    .findById(transporteur.getId())
                    .map(existingTransporteur -> {
                        if (transporteur.getNomTransporteur() != null) {
                            existingTransporteur.setNomTransporteur(transporteur.getNomTransporteur());
                        }
                        if (transporteur.getNomDirecteur() != null) {
                            existingTransporteur.setNomDirecteur(transporteur.getNomDirecteur());
                        }
                        if (transporteur.getPhoneTransporteur() != null) {
                            existingTransporteur.setPhoneTransporteur(transporteur.getPhoneTransporteur());
                        }
                        if (transporteur.getEmailTransporteur() != null) {
                            existingTransporteur.setEmailTransporteur(transporteur.getEmailTransporteur());
                        }

                        return existingTransporteur;
                    })
                    .flatMap(transporteurRepository::save);

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
     * {@code GET  /transporteurs} : get all the transporteurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transporteurs in body.
     */
    @GetMapping("/transporteurs")
    public Mono<List<Transporteur>> getAllTransporteurs() {
        log.debug("REST request to get all Transporteurs");
        return transporteurRepository.findAll().collectList();
    }

    /**
     * {@code GET  /transporteurs} : get all the transporteurs as a stream.
     * @return the {@link Flux} of transporteurs.
     */
    @GetMapping(value = "/transporteurs", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Transporteur> getAllTransporteursAsStream() {
        log.debug("REST request to get all Transporteurs as a stream");
        return transporteurRepository.findAll();
    }

    /**
     * {@code GET  /transporteurs/:id} : get the "id" transporteur.
     *
     * @param id the id of the transporteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transporteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transporteurs/{id}")
    public Mono<ResponseEntity<Transporteur>> getTransporteur(@PathVariable Long id) {
        log.debug("REST request to get Transporteur : {}", id);
        Mono<Transporteur> transporteur = transporteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transporteur);
    }

    /**
     * {@code DELETE  /transporteurs/:id} : delete the "id" transporteur.
     *
     * @param id the id of the transporteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transporteurs/{id}")
    public Mono<ResponseEntity<Void>> deleteTransporteur(@PathVariable Long id) {
        log.debug("REST request to delete Transporteur : {}", id);
        return transporteurRepository
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
