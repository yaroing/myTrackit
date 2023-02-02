package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.StockPartenaire;
import com.yarolab.mytrackit.repository.StockPartenaireRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.domain.StockPartenaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StockPartenaireResource {

    private final Logger log = LoggerFactory.getLogger(StockPartenaireResource.class);

    private static final String ENTITY_NAME = "stockPartenaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockPartenaireRepository stockPartenaireRepository;

    public StockPartenaireResource(StockPartenaireRepository stockPartenaireRepository) {
        this.stockPartenaireRepository = stockPartenaireRepository;
    }

    /**
     * {@code POST  /stock-partenaires} : Create a new stockPartenaire.
     *
     * @param stockPartenaire the stockPartenaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockPartenaire, or with status {@code 400 (Bad Request)} if the stockPartenaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-partenaires")
    public Mono<ResponseEntity<StockPartenaire>> createStockPartenaire(@RequestBody StockPartenaire stockPartenaire)
        throws URISyntaxException {
        log.debug("REST request to save StockPartenaire : {}", stockPartenaire);
        if (stockPartenaire.getId() != null) {
            throw new BadRequestAlertException("A new stockPartenaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return stockPartenaireRepository
            .save(stockPartenaire)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/stock-partenaires/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /stock-partenaires/:id} : Updates an existing stockPartenaire.
     *
     * @param id the id of the stockPartenaire to save.
     * @param stockPartenaire the stockPartenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockPartenaire,
     * or with status {@code 400 (Bad Request)} if the stockPartenaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockPartenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-partenaires/{id}")
    public Mono<ResponseEntity<StockPartenaire>> updateStockPartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StockPartenaire stockPartenaire
    ) throws URISyntaxException {
        log.debug("REST request to update StockPartenaire : {}, {}", id, stockPartenaire);
        if (stockPartenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockPartenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return stockPartenaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return stockPartenaireRepository
                    .save(stockPartenaire)
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
     * {@code PATCH  /stock-partenaires/:id} : Partial updates given fields of an existing stockPartenaire, field will ignore if it is null
     *
     * @param id the id of the stockPartenaire to save.
     * @param stockPartenaire the stockPartenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockPartenaire,
     * or with status {@code 400 (Bad Request)} if the stockPartenaire is not valid,
     * or with status {@code 404 (Not Found)} if the stockPartenaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockPartenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stock-partenaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<StockPartenaire>> partialUpdateStockPartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StockPartenaire stockPartenaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update StockPartenaire partially : {}, {}", id, stockPartenaire);
        if (stockPartenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockPartenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return stockPartenaireRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<StockPartenaire> result = stockPartenaireRepository
                    .findById(stockPartenaire.getId())
                    .map(existingStockPartenaire -> {
                        if (stockPartenaire.getStockAnnee() != null) {
                            existingStockPartenaire.setStockAnnee(stockPartenaire.getStockAnnee());
                        }
                        if (stockPartenaire.getStockMois() != null) {
                            existingStockPartenaire.setStockMois(stockPartenaire.getStockMois());
                        }
                        if (stockPartenaire.getEntreeMois() != null) {
                            existingStockPartenaire.setEntreeMois(stockPartenaire.getEntreeMois());
                        }
                        if (stockPartenaire.getSortieMois() != null) {
                            existingStockPartenaire.setSortieMois(stockPartenaire.getSortieMois());
                        }
                        if (stockPartenaire.getStockFinmois() != null) {
                            existingStockPartenaire.setStockFinmois(stockPartenaire.getStockFinmois());
                        }
                        if (stockPartenaire.getStockDebut() != null) {
                            existingStockPartenaire.setStockDebut(stockPartenaire.getStockDebut());
                        }

                        return existingStockPartenaire;
                    })
                    .flatMap(stockPartenaireRepository::save);

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
     * {@code GET  /stock-partenaires} : get all the stockPartenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockPartenaires in body.
     */
    @GetMapping("/stock-partenaires")
    public Mono<List<StockPartenaire>> getAllStockPartenaires() {
        log.debug("REST request to get all StockPartenaires");
        return stockPartenaireRepository.findAll().collectList();
    }

    /**
     * {@code GET  /stock-partenaires} : get all the stockPartenaires as a stream.
     * @return the {@link Flux} of stockPartenaires.
     */
    @GetMapping(value = "/stock-partenaires", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StockPartenaire> getAllStockPartenairesAsStream() {
        log.debug("REST request to get all StockPartenaires as a stream");
        return stockPartenaireRepository.findAll();
    }

    /**
     * {@code GET  /stock-partenaires/:id} : get the "id" stockPartenaire.
     *
     * @param id the id of the stockPartenaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockPartenaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-partenaires/{id}")
    public Mono<ResponseEntity<StockPartenaire>> getStockPartenaire(@PathVariable Long id) {
        log.debug("REST request to get StockPartenaire : {}", id);
        Mono<StockPartenaire> stockPartenaire = stockPartenaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stockPartenaire);
    }

    /**
     * {@code DELETE  /stock-partenaires/:id} : delete the "id" stockPartenaire.
     *
     * @param id the id of the stockPartenaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-partenaires/{id}")
    public Mono<ResponseEntity<Void>> deleteStockPartenaire(@PathVariable Long id) {
        log.debug("REST request to delete StockPartenaire : {}", id);
        return stockPartenaireRepository
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
