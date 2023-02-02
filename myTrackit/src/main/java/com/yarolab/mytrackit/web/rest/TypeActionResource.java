package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.TypeAction;
import com.yarolab.mytrackit.repository.TypeActionRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.TypeAction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeActionResource {

    private final Logger log = LoggerFactory.getLogger(TypeActionResource.class);

    private static final String ENTITY_NAME = "typeAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeActionRepository typeActionRepository;

    public TypeActionResource(TypeActionRepository typeActionRepository) {
        this.typeActionRepository = typeActionRepository;
    }

    /**
     * {@code POST  /type-actions} : Create a new typeAction.
     *
     * @param typeAction the typeAction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAction, or with status {@code 400 (Bad Request)} if the typeAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-actions")
    public Mono<ResponseEntity<TypeAction>> createTypeAction(@RequestBody TypeAction typeAction) throws URISyntaxException {
        log.debug("REST request to save TypeAction : {}", typeAction);
        if (typeAction.getId() != null) {
            throw new BadRequestAlertException("A new typeAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return typeActionRepository
            .save(typeAction)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/type-actions/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /type-actions/:id} : Updates an existing typeAction.
     *
     * @param id the id of the typeAction to save.
     * @param typeAction the typeAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAction,
     * or with status {@code 400 (Bad Request)} if the typeAction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-actions/{id}")
    public Mono<ResponseEntity<TypeAction>> updateTypeAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAction typeAction
    ) throws URISyntaxException {
        log.debug("REST request to update TypeAction : {}, {}", id, typeAction);
        if (typeAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return typeActionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return typeActionRepository
                    .save(typeAction)
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
     * {@code PATCH  /type-actions/:id} : Partial updates given fields of an existing typeAction, field will ignore if it is null
     *
     * @param id the id of the typeAction to save.
     * @param typeAction the typeAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAction,
     * or with status {@code 400 (Bad Request)} if the typeAction is not valid,
     * or with status {@code 404 (Not Found)} if the typeAction is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-actions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TypeAction>> partialUpdateTypeAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAction typeAction
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeAction partially : {}, {}", id, typeAction);
        if (typeAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return typeActionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TypeAction> result = typeActionRepository
                    .findById(typeAction.getId())
                    .map(existingTypeAction -> {
                        if (typeAction.getType() != null) {
                            existingTypeAction.setType(typeAction.getType());
                        }

                        return existingTypeAction;
                    })
                    .flatMap(typeActionRepository::save);

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
     * {@code GET  /type-actions} : get all the typeActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeActions in body.
     */
    @GetMapping("/type-actions")
    public Mono<List<TypeAction>> getAllTypeActions() {
        log.debug("REST request to get all TypeActions");
        return typeActionRepository.findAll().collectList();
    }

    /**
     * {@code GET  /type-actions} : get all the typeActions as a stream.
     * @return the {@link Flux} of typeActions.
     */
    @GetMapping(value = "/type-actions", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<TypeAction> getAllTypeActionsAsStream() {
        log.debug("REST request to get all TypeActions as a stream");
        return typeActionRepository.findAll();
    }

    /**
     * {@code GET  /type-actions/:id} : get the "id" typeAction.
     *
     * @param id the id of the typeAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-actions/{id}")
    public Mono<ResponseEntity<TypeAction>> getTypeAction(@PathVariable Long id) {
        log.debug("REST request to get TypeAction : {}", id);
        Mono<TypeAction> typeAction = typeActionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeAction);
    }

    /**
     * {@code DELETE  /type-actions/:id} : delete the "id" typeAction.
     *
     * @param id the id of the typeAction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-actions/{id}")
    public Mono<ResponseEntity<Void>> deleteTypeAction(@PathVariable Long id) {
        log.debug("REST request to delete TypeAction : {}", id);
        return typeActionRepository
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
