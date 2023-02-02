package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Catalogue;
import com.yarolab.mytrackit.repository.CatalogueRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Catalogue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CatalogueResource {

    private final Logger log = LoggerFactory.getLogger(CatalogueResource.class);

    private static final String ENTITY_NAME = "catalogue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatalogueRepository catalogueRepository;

    public CatalogueResource(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * {@code POST  /catalogues} : Create a new catalogue.
     *
     * @param catalogue the catalogue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catalogue, or with status {@code 400 (Bad Request)} if the catalogue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/catalogues")
    public Mono<ResponseEntity<Catalogue>> createCatalogue(@RequestBody Catalogue catalogue) throws URISyntaxException {
        log.debug("REST request to save Catalogue : {}", catalogue);
        if (catalogue.getId() != null) {
            throw new BadRequestAlertException("A new catalogue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return catalogueRepository
            .save(catalogue)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/catalogues/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /catalogues/:id} : Updates an existing catalogue.
     *
     * @param id the id of the catalogue to save.
     * @param catalogue the catalogue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogue,
     * or with status {@code 400 (Bad Request)} if the catalogue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catalogue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/catalogues/{id}")
    public Mono<ResponseEntity<Catalogue>> updateCatalogue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Catalogue catalogue
    ) throws URISyntaxException {
        log.debug("REST request to update Catalogue : {}, {}", id, catalogue);
        if (catalogue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalogue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return catalogueRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return catalogueRepository
                    .save(catalogue)
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
     * {@code PATCH  /catalogues/:id} : Partial updates given fields of an existing catalogue, field will ignore if it is null
     *
     * @param id the id of the catalogue to save.
     * @param catalogue the catalogue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogue,
     * or with status {@code 400 (Bad Request)} if the catalogue is not valid,
     * or with status {@code 404 (Not Found)} if the catalogue is not found,
     * or with status {@code 500 (Internal Server Error)} if the catalogue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/catalogues/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Catalogue>> partialUpdateCatalogue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Catalogue catalogue
    ) throws URISyntaxException {
        log.debug("REST request to partial update Catalogue partially : {}, {}", id, catalogue);
        if (catalogue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalogue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return catalogueRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Catalogue> result = catalogueRepository
                    .findById(catalogue.getId())
                    .map(existingCatalogue -> {
                        if (catalogue.getMaterialCode() != null) {
                            existingCatalogue.setMaterialCode(catalogue.getMaterialCode());
                        }
                        if (catalogue.getMaterialDesc() != null) {
                            existingCatalogue.setMaterialDesc(catalogue.getMaterialDesc());
                        }
                        if (catalogue.getMaterialGroup() != null) {
                            existingCatalogue.setMaterialGroup(catalogue.getMaterialGroup());
                        }

                        return existingCatalogue;
                    })
                    .flatMap(catalogueRepository::save);

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
     * {@code GET  /catalogues} : get all the catalogues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catalogues in body.
     */
    @GetMapping("/catalogues")
    public Mono<List<Catalogue>> getAllCatalogues() {
        log.debug("REST request to get all Catalogues");
        return catalogueRepository.findAll().collectList();
    }

    /**
     * {@code GET  /catalogues} : get all the catalogues as a stream.
     * @return the {@link Flux} of catalogues.
     */
    @GetMapping(value = "/catalogues", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Catalogue> getAllCataloguesAsStream() {
        log.debug("REST request to get all Catalogues as a stream");
        return catalogueRepository.findAll();
    }

    /**
     * {@code GET  /catalogues/:id} : get the "id" catalogue.
     *
     * @param id the id of the catalogue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catalogue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/catalogues/{id}")
    public Mono<ResponseEntity<Catalogue>> getCatalogue(@PathVariable Long id) {
        log.debug("REST request to get Catalogue : {}", id);
        Mono<Catalogue> catalogue = catalogueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(catalogue);
    }

    /**
     * {@code DELETE  /catalogues/:id} : delete the "id" catalogue.
     *
     * @param id the id of the catalogue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/catalogues/{id}")
    public Mono<ResponseEntity<Void>> deleteCatalogue(@PathVariable Long id) {
        log.debug("REST request to delete Catalogue : {}", id);
        return catalogueRepository
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
