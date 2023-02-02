package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.ItemVerifie;
import com.yarolab.mytrackit.repository.ItemVerifieRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.ItemVerifie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItemVerifieResource {

    private final Logger log = LoggerFactory.getLogger(ItemVerifieResource.class);

    private static final String ENTITY_NAME = "itemVerifie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemVerifieRepository itemVerifieRepository;

    public ItemVerifieResource(ItemVerifieRepository itemVerifieRepository) {
        this.itemVerifieRepository = itemVerifieRepository;
    }

    /**
     * {@code POST  /item-verifies} : Create a new itemVerifie.
     *
     * @param itemVerifie the itemVerifie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemVerifie, or with status {@code 400 (Bad Request)} if the itemVerifie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-verifies")
    public Mono<ResponseEntity<ItemVerifie>> createItemVerifie(@RequestBody ItemVerifie itemVerifie) throws URISyntaxException {
        log.debug("REST request to save ItemVerifie : {}", itemVerifie);
        if (itemVerifie.getId() != null) {
            throw new BadRequestAlertException("A new itemVerifie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return itemVerifieRepository
            .save(itemVerifie)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/item-verifies/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /item-verifies/:id} : Updates an existing itemVerifie.
     *
     * @param id the id of the itemVerifie to save.
     * @param itemVerifie the itemVerifie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemVerifie,
     * or with status {@code 400 (Bad Request)} if the itemVerifie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemVerifie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-verifies/{id}")
    public Mono<ResponseEntity<ItemVerifie>> updateItemVerifie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemVerifie itemVerifie
    ) throws URISyntaxException {
        log.debug("REST request to update ItemVerifie : {}, {}", id, itemVerifie);
        if (itemVerifie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemVerifie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return itemVerifieRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return itemVerifieRepository
                    .save(itemVerifie)
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
     * {@code PATCH  /item-verifies/:id} : Partial updates given fields of an existing itemVerifie, field will ignore if it is null
     *
     * @param id the id of the itemVerifie to save.
     * @param itemVerifie the itemVerifie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemVerifie,
     * or with status {@code 400 (Bad Request)} if the itemVerifie is not valid,
     * or with status {@code 404 (Not Found)} if the itemVerifie is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemVerifie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-verifies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ItemVerifie>> partialUpdateItemVerifie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemVerifie itemVerifie
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemVerifie partially : {}, {}", id, itemVerifie);
        if (itemVerifie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemVerifie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return itemVerifieRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ItemVerifie> result = itemVerifieRepository
                    .findById(itemVerifie.getId())
                    .map(existingItemVerifie -> {
                        if (itemVerifie.getQuantiteTransfert() != null) {
                            existingItemVerifie.setQuantiteTransfert(itemVerifie.getQuantiteTransfert());
                        }
                        if (itemVerifie.getQuantiteRecu() != null) {
                            existingItemVerifie.setQuantiteRecu(itemVerifie.getQuantiteRecu());
                        }
                        if (itemVerifie.getQuantiteUtilisee() != null) {
                            existingItemVerifie.setQuantiteUtilisee(itemVerifie.getQuantiteUtilisee());
                        }
                        if (itemVerifie.getQuantiteDisponible() != null) {
                            existingItemVerifie.setQuantiteDisponible(itemVerifie.getQuantiteDisponible());
                        }
                        if (itemVerifie.getQuantiteEcart() != null) {
                            existingItemVerifie.setQuantiteEcart(itemVerifie.getQuantiteEcart());
                        }

                        return existingItemVerifie;
                    })
                    .flatMap(itemVerifieRepository::save);

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
     * {@code GET  /item-verifies} : get all the itemVerifies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemVerifies in body.
     */
    @GetMapping("/item-verifies")
    public Mono<List<ItemVerifie>> getAllItemVerifies() {
        log.debug("REST request to get all ItemVerifies");
        return itemVerifieRepository.findAll().collectList();
    }

    /**
     * {@code GET  /item-verifies} : get all the itemVerifies as a stream.
     * @return the {@link Flux} of itemVerifies.
     */
    @GetMapping(value = "/item-verifies", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ItemVerifie> getAllItemVerifiesAsStream() {
        log.debug("REST request to get all ItemVerifies as a stream");
        return itemVerifieRepository.findAll();
    }

    /**
     * {@code GET  /item-verifies/:id} : get the "id" itemVerifie.
     *
     * @param id the id of the itemVerifie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemVerifie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-verifies/{id}")
    public Mono<ResponseEntity<ItemVerifie>> getItemVerifie(@PathVariable Long id) {
        log.debug("REST request to get ItemVerifie : {}", id);
        Mono<ItemVerifie> itemVerifie = itemVerifieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemVerifie);
    }

    /**
     * {@code DELETE  /item-verifies/:id} : delete the "id" itemVerifie.
     *
     * @param id the id of the itemVerifie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-verifies/{id}")
    public Mono<ResponseEntity<Void>> deleteItemVerifie(@PathVariable Long id) {
        log.debug("REST request to delete ItemVerifie : {}", id);
        return itemVerifieRepository
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
