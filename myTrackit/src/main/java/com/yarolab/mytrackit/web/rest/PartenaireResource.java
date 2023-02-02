package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Partenaire;
import com.yarolab.mytrackit.repository.PartenaireRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Partenaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartenaireResource {

    private final Logger log = LoggerFactory.getLogger(PartenaireResource.class);

    private static final String ENTITY_NAME = "partenaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartenaireRepository partenaireRepository;

    public PartenaireResource(PartenaireRepository partenaireRepository) {
        this.partenaireRepository = partenaireRepository;
    }

    /**
     * {@code POST  /partenaires} : Create a new partenaire.
     *
     * @param partenaire the partenaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partenaire, or with status {@code 400 (Bad Request)} if the partenaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partenaires")
    public Mono<ResponseEntity<Partenaire>> createPartenaire(@RequestBody Partenaire partenaire) throws URISyntaxException {
        log.debug("REST request to save Partenaire : {}", partenaire);
        if (partenaire.getId() != null) {
            throw new BadRequestAlertException("A new partenaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return partenaireRepository
            .save(partenaire)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/partenaires/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /partenaires/:id} : Updates an existing partenaire.
     *
     * @param id the id of the partenaire to save.
     * @param partenaire the partenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaire,
     * or with status {@code 400 (Bad Request)} if the partenaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partenaires/{id}")
    public Mono<ResponseEntity<Partenaire>> updatePartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Partenaire partenaire
    ) throws URISyntaxException {
        log.debug("REST request to update Partenaire : {}, {}", id, partenaire);
        if (partenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return partenaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return partenaireRepository
                    .save(partenaire)
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
     * {@code PATCH  /partenaires/:id} : Partial updates given fields of an existing partenaire, field will ignore if it is null
     *
     * @param id the id of the partenaire to save.
     * @param partenaire the partenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partenaire,
     * or with status {@code 400 (Bad Request)} if the partenaire is not valid,
     * or with status {@code 404 (Not Found)} if the partenaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the partenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/partenaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Partenaire>> partialUpdatePartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Partenaire partenaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update Partenaire partially : {}, {}", id, partenaire);
        if (partenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return partenaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Partenaire> result = partenaireRepository
                    .findById(partenaire.getId())
                    .map(existingPartenaire -> {
                        if (partenaire.getNomPartenaire() != null) {
                            existingPartenaire.setNomPartenaire(partenaire.getNomPartenaire());
                        }
                        if (partenaire.getAutreNom() != null) {
                            existingPartenaire.setAutreNom(partenaire.getAutreNom());
                        }
                        if (partenaire.getLogPhone() != null) {
                            existingPartenaire.setLogPhone(partenaire.getLogPhone());
                        }
                        if (partenaire.getEmailPartenaire() != null) {
                            existingPartenaire.setEmailPartenaire(partenaire.getEmailPartenaire());
                        }
                        if (partenaire.getLocPartenaire() != null) {
                            existingPartenaire.setLocPartenaire(partenaire.getLocPartenaire());
                        }

                        return existingPartenaire;
                    })
                    .flatMap(partenaireRepository::save);

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
     * {@code GET  /partenaires} : get all the partenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partenaires in body.
     */
    @GetMapping("/partenaires")
    public Mono<List<Partenaire>> getAllPartenaires() {
        log.debug("REST request to get all Partenaires");
        return partenaireRepository.findAll().collectList();
    }

    /**
     * {@code GET  /partenaires} : get all the partenaires as a stream.
     * @return the {@link Flux} of partenaires.
     */
    @GetMapping(value = "/partenaires", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Partenaire> getAllPartenairesAsStream() {
        log.debug("REST request to get all Partenaires as a stream");
        return partenaireRepository.findAll();
    }

    /**
     * {@code GET  /partenaires/:id} : get the "id" partenaire.
     *
     * @param id the id of the partenaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partenaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partenaires/{id}")
    public Mono<ResponseEntity<Partenaire>> getPartenaire(@PathVariable Long id) {
        log.debug("REST request to get Partenaire : {}", id);
        Mono<Partenaire> partenaire = partenaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partenaire);
    }

    /**
     * {@code DELETE  /partenaires/:id} : delete the "id" partenaire.
     *
     * @param id the id of the partenaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partenaires/{id}")
    public Mono<ResponseEntity<Void>> deletePartenaire(@PathVariable Long id) {
        log.debug("REST request to delete Partenaire : {}", id);
        return partenaireRepository
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
