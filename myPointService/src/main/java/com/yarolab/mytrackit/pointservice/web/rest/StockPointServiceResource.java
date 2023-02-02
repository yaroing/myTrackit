package com.yarolab.mytrackit.pointservice.web.rest;

import com.yarolab.mytrackit.pointservice.domain.StockPointService;
import com.yarolab.mytrackit.pointservice.repository.StockPointServiceRepository;
import com.yarolab.mytrackit.pointservice.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.yarolab.mytrackit.pointservice.domain.StockPointService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StockPointServiceResource {

    private final Logger log = LoggerFactory.getLogger(StockPointServiceResource.class);

    private static final String ENTITY_NAME = "myPointServiceStockPointService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockPointServiceRepository stockPointServiceRepository;

    public StockPointServiceResource(StockPointServiceRepository stockPointServiceRepository) {
        this.stockPointServiceRepository = stockPointServiceRepository;
    }

    /**
     * {@code POST  /stock-point-services} : Create a new stockPointService.
     *
     * @param stockPointService the stockPointService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockPointService, or with status {@code 400 (Bad Request)} if the stockPointService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-point-services")
    public ResponseEntity<StockPointService> createStockPointService(@RequestBody StockPointService stockPointService)
        throws URISyntaxException {
        log.debug("REST request to save StockPointService : {}", stockPointService);
        if (stockPointService.getId() != null) {
            throw new BadRequestAlertException("A new stockPointService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockPointService result = stockPointServiceRepository.save(stockPointService);
        return ResponseEntity
            .created(new URI("/api/stock-point-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-point-services/:id} : Updates an existing stockPointService.
     *
     * @param id the id of the stockPointService to save.
     * @param stockPointService the stockPointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockPointService,
     * or with status {@code 400 (Bad Request)} if the stockPointService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockPointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-point-services/{id}")
    public ResponseEntity<StockPointService> updateStockPointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StockPointService stockPointService
    ) throws URISyntaxException {
        log.debug("REST request to update StockPointService : {}, {}", id, stockPointService);
        if (stockPointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockPointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockPointServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StockPointService result = stockPointServiceRepository.save(stockPointService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockPointService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stock-point-services/:id} : Partial updates given fields of an existing stockPointService, field will ignore if it is null
     *
     * @param id the id of the stockPointService to save.
     * @param stockPointService the stockPointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockPointService,
     * or with status {@code 400 (Bad Request)} if the stockPointService is not valid,
     * or with status {@code 404 (Not Found)} if the stockPointService is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockPointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stock-point-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StockPointService> partialUpdateStockPointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StockPointService stockPointService
    ) throws URISyntaxException {
        log.debug("REST request to partial update StockPointService partially : {}, {}", id, stockPointService);
        if (stockPointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockPointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockPointServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockPointService> result = stockPointServiceRepository
            .findById(stockPointService.getId())
            .map(existingStockPointService -> {
                if (stockPointService.getPosId() != null) {
                    existingStockPointService.setPosId(stockPointService.getPosId());
                }
                if (stockPointService.getStockAnnee() != null) {
                    existingStockPointService.setStockAnnee(stockPointService.getStockAnnee());
                }
                if (stockPointService.getStockMois() != null) {
                    existingStockPointService.setStockMois(stockPointService.getStockMois());
                }
                if (stockPointService.getCatalogueId() != null) {
                    existingStockPointService.setCatalogueId(stockPointService.getCatalogueId());
                }
                if (stockPointService.getEntreeMois() != null) {
                    existingStockPointService.setEntreeMois(stockPointService.getEntreeMois());
                }
                if (stockPointService.getSortieMois() != null) {
                    existingStockPointService.setSortieMois(stockPointService.getSortieMois());
                }
                if (stockPointService.getStockFinmois() != null) {
                    existingStockPointService.setStockFinmois(stockPointService.getStockFinmois());
                }
                if (stockPointService.getStockDebut() != null) {
                    existingStockPointService.setStockDebut(stockPointService.getStockDebut());
                }

                return existingStockPointService;
            })
            .map(stockPointServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockPointService.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-point-services} : get all the stockPointServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockPointServices in body.
     */
    @GetMapping("/stock-point-services")
    public List<StockPointService> getAllStockPointServices() {
        log.debug("REST request to get all StockPointServices");
        return stockPointServiceRepository.findAll();
    }

    /**
     * {@code GET  /stock-point-services/:id} : get the "id" stockPointService.
     *
     * @param id the id of the stockPointService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockPointService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-point-services/{id}")
    public ResponseEntity<StockPointService> getStockPointService(@PathVariable Long id) {
        log.debug("REST request to get StockPointService : {}", id);
        Optional<StockPointService> stockPointService = stockPointServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stockPointService);
    }

    /**
     * {@code DELETE  /stock-point-services/:id} : delete the "id" stockPointService.
     *
     * @param id the id of the stockPointService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-point-services/{id}")
    public ResponseEntity<Void> deleteStockPointService(@PathVariable Long id) {
        log.debug("REST request to delete StockPointService : {}", id);
        stockPointServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
