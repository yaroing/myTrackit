package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Action;
import com.yarolab.mytrackit.repository.ActionRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Action}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActionResource {

    private final Logger log = LoggerFactory.getLogger(ActionResource.class);

    private static final String ENTITY_NAME = "action";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionRepository actionRepository;

    public ActionResource(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    /**
     * {@code POST  /actions} : Create a new action.
     *
     * @param action the action to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new action, or with status {@code 400 (Bad Request)} if the action has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions")
    public Mono<ResponseEntity<Action>> createAction(@RequestBody Action action) throws URISyntaxException {
        log.debug("REST request to save Action : {}", action);
        if (action.getId() != null) {
            throw new BadRequestAlertException("A new action cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return actionRepository
            .save(action)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/actions/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /actions/:id} : Updates an existing action.
     *
     * @param id the id of the action to save.
     * @param action the action to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated action,
     * or with status {@code 400 (Bad Request)} if the action is not valid,
     * or with status {@code 500 (Internal Server Error)} if the action couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actions/{id}")
    public Mono<ResponseEntity<Action>> updateAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Action action
    ) throws URISyntaxException {
        log.debug("REST request to update Action : {}, {}", id, action);
        if (action.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, action.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return actionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return actionRepository
                    .save(action)
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
     * {@code PATCH  /actions/:id} : Partial updates given fields of an existing action, field will ignore if it is null
     *
     * @param id the id of the action to save.
     * @param action the action to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated action,
     * or with status {@code 400 (Bad Request)} if the action is not valid,
     * or with status {@code 404 (Not Found)} if the action is not found,
     * or with status {@code 500 (Internal Server Error)} if the action couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/actions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Action>> partialUpdateAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Action action
    ) throws URISyntaxException {
        log.debug("REST request to partial update Action partially : {}, {}", id, action);
        if (action.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, action.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return actionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Action> result = actionRepository
                    .findById(action.getId())
                    .map(existingAction -> {
                        if (action.getDateAction() != null) {
                            existingAction.setDateAction(action.getDateAction());
                        }
                        if (action.getRapportAction() != null) {
                            existingAction.setRapportAction(action.getRapportAction());
                        }

                        return existingAction;
                    })
                    .flatMap(actionRepository::save);

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
     * {@code GET  /actions} : get all the actions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actions in body.
     */
    @GetMapping("/actions")
    public Mono<List<Action>> getAllActions() {
        log.debug("REST request to get all Actions");
        return actionRepository.findAll().collectList();
    }

    /**
     * {@code GET  /actions} : get all the actions as a stream.
     * @return the {@link Flux} of actions.
     */
    @GetMapping(value = "/actions", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Action> getAllActionsAsStream() {
        log.debug("REST request to get all Actions as a stream");
        return actionRepository.findAll();
    }

    /**
     * {@code GET  /actions/:id} : get the "id" action.
     *
     * @param id the id of the action to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the action, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actions/{id}")
    public Mono<ResponseEntity<Action>> getAction(@PathVariable Long id) {
        log.debug("REST request to get Action : {}", id);
        Mono<Action> action = actionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(action);
    }

    /**
     * {@code DELETE  /actions/:id} : delete the "id" action.
     *
     * @param id the id of the action to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actions/{id}")
    public Mono<ResponseEntity<Void>> deleteAction(@PathVariable Long id) {
        log.debug("REST request to delete Action : {}", id);
        return actionRepository
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
