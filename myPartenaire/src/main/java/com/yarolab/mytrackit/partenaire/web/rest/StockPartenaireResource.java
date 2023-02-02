package com.yarolab.mytrackit.partenaire.web.rest;

import com.yarolab.mytrackit.partenaire.domain.StockPartenaire;
import com.yarolab.mytrackit.partenaire.repository.StockPartenaireRepository;
import com.yarolab.mytrackit.partenaire.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yarolab.mytrackit.partenaire.domain.StockPartenaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StockPartenaireResource {

    private final Logger log = LoggerFactory.getLogger(StockPartenaireResource.class);

    private static final String ENTITY_NAME = "myPartenaireStockPartenaire";

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
    public ResponseEntity<StockPartenaire> createStockPartenaire(@RequestBody StockPartenaire stockPartenaire) throws URISyntaxException {
        log.debug("REST request to save StockPartenaire : {}", stockPartenaire);
        if (stockPartenaire.getId() != null) {
            throw new BadRequestAlertException("A new stockPartenaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockPartenaire result = stockPartenaireRepository.save(stockPartenaire);
        return ResponseEntity
            .created(new URI("/api/stock-partenaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<StockPartenaire> updateStockPartenaire(
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

        if (!stockPartenaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StockPartenaire result = stockPartenaireRepository.save(stockPartenaire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockPartenaire.getId().toString()))
            .body(result);
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
    public ResponseEntity<StockPartenaire> partialUpdateStockPartenaire(
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

        if (!stockPartenaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockPartenaire> result = stockPartenaireRepository
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
            .map(stockPartenaireRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockPartenaire.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-partenaires} : get all the stockPartenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockPartenaires in body.
     */
    @GetMapping("/stock-partenaires")
    public List<StockPartenaire> getAllStockPartenaires() {
        log.debug("REST request to get all StockPartenaires");
        return stockPartenaireRepository.findAll();
    }

    /**
     * {@code GET  /stock-partenaires/:id} : get the "id" stockPartenaire.
     *
     * @param id the id of the stockPartenaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockPartenaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-partenaires/{id}")
    public ResponseEntity<StockPartenaire> getStockPartenaire(@PathVariable Long id) {
        log.debug("REST request to get StockPartenaire : {}", id);
        Optional<StockPartenaire> stockPartenaire = stockPartenaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stockPartenaire);
    }

    /**
     * {@code DELETE  /stock-partenaires/:id} : delete the "id" stockPartenaire.
     *
     * @param id the id of the stockPartenaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-partenaires/{id}")
    public ResponseEntity<Void> deleteStockPartenaire(@PathVariable Long id) {
        log.debug("REST request to delete StockPartenaire : {}", id);
        stockPartenaireRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
