package com.yarolab.mytrackit.pointservice.web.rest;

import com.yarolab.mytrackit.pointservice.domain.RequetePointService;
import com.yarolab.mytrackit.pointservice.repository.RequetePointServiceRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.pointservice.domain.RequetePointService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequetePointServiceResource {

    private final Logger log = LoggerFactory.getLogger(RequetePointServiceResource.class);

    private static final String ENTITY_NAME = "myPointServiceRequetePointService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequetePointServiceRepository requetePointServiceRepository;

    public RequetePointServiceResource(RequetePointServiceRepository requetePointServiceRepository) {
        this.requetePointServiceRepository = requetePointServiceRepository;
    }

    /**
     * {@code POST  /requete-point-services} : Create a new requetePointService.
     *
     * @param requetePointService the requetePointService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requetePointService, or with status {@code 400 (Bad Request)} if the requetePointService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requete-point-services")
    public ResponseEntity<RequetePointService> createRequetePointService(@RequestBody RequetePointService requetePointService)
        throws URISyntaxException {
        log.debug("REST request to save RequetePointService : {}", requetePointService);
        if (requetePointService.getId() != null) {
            throw new BadRequestAlertException("A new requetePointService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequetePointService result = requetePointServiceRepository.save(requetePointService);
        return ResponseEntity
            .created(new URI("/api/requete-point-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requete-point-services/:id} : Updates an existing requetePointService.
     *
     * @param id the id of the requetePointService to save.
     * @param requetePointService the requetePointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requetePointService,
     * or with status {@code 400 (Bad Request)} if the requetePointService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requetePointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requete-point-services/{id}")
    public ResponseEntity<RequetePointService> updateRequetePointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequetePointService requetePointService
    ) throws URISyntaxException {
        log.debug("REST request to update RequetePointService : {}, {}", id, requetePointService);
        if (requetePointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requetePointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requetePointServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequetePointService result = requetePointServiceRepository.save(requetePointService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requetePointService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requete-point-services/:id} : Partial updates given fields of an existing requetePointService, field will ignore if it is null
     *
     * @param id the id of the requetePointService to save.
     * @param requetePointService the requetePointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requetePointService,
     * or with status {@code 400 (Bad Request)} if the requetePointService is not valid,
     * or with status {@code 404 (Not Found)} if the requetePointService is not found,
     * or with status {@code 500 (Internal Server Error)} if the requetePointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requete-point-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequetePointService> partialUpdateRequetePointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequetePointService requetePointService
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequetePointService partially : {}, {}", id, requetePointService);
        if (requetePointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requetePointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requetePointServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequetePointService> result = requetePointServiceRepository
            .findById(requetePointService.getId())
            .map(existingRequetePointService -> {
                if (requetePointService.getStockDisponible() != null) {
                    existingRequetePointService.setStockDisponible(requetePointService.getStockDisponible());
                }
                if (requetePointService.getQuantDem() != null) {
                    existingRequetePointService.setQuantDem(requetePointService.getQuantDem());
                }
                if (requetePointService.getQuantTrs() != null) {
                    existingRequetePointService.setQuantTrs(requetePointService.getQuantTrs());
                }
                if (requetePointService.getQuantRec() != null) {
                    existingRequetePointService.setQuantRec(requetePointService.getQuantRec());
                }
                if (requetePointService.getReqTraitee() != null) {
                    existingRequetePointService.setReqTraitee(requetePointService.getReqTraitee());
                }
                if (requetePointService.getDateReq() != null) {
                    existingRequetePointService.setDateReq(requetePointService.getDateReq());
                }
                if (requetePointService.getDateRec() != null) {
                    existingRequetePointService.setDateRec(requetePointService.getDateRec());
                }
                if (requetePointService.getDateTransfert() != null) {
                    existingRequetePointService.setDateTransfert(requetePointService.getDateTransfert());
                }

                return existingRequetePointService;
            })
            .map(requetePointServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requetePointService.getId().toString())
        );
    }

    /**
     * {@code GET  /requete-point-services} : get all the requetePointServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requetePointServices in body.
     */
    @GetMapping("/requete-point-services")
    public List<RequetePointService> getAllRequetePointServices() {
        log.debug("REST request to get all RequetePointServices");
        return requetePointServiceRepository.findAll();
    }

    /**
     * {@code GET  /requete-point-services/:id} : get the "id" requetePointService.
     *
     * @param id the id of the requetePointService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requetePointService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requete-point-services/{id}")
    public ResponseEntity<RequetePointService> getRequetePointService(@PathVariable Long id) {
        log.debug("REST request to get RequetePointService : {}", id);
        Optional<RequetePointService> requetePointService = requetePointServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requetePointService);
    }

    /**
     * {@code DELETE  /requete-point-services/:id} : delete the "id" requetePointService.
     *
     * @param id the id of the requetePointService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requete-point-services/{id}")
    public ResponseEntity<Void> deleteRequetePointService(@PathVariable Long id) {
        log.debug("REST request to delete RequetePointService : {}", id);
        requetePointServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
