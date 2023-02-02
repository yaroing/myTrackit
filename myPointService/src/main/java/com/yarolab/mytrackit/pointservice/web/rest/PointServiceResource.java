package com.yarolab.mytrackit.pointservice.web.rest;

import com.yarolab.mytrackit.pointservice.domain.PointService;
import com.yarolab.mytrackit.pointservice.repository.PointServiceRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.pointservice.domain.PointService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PointServiceResource {

    private final Logger log = LoggerFactory.getLogger(PointServiceResource.class);

    private static final String ENTITY_NAME = "myPointServicePointService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointServiceRepository pointServiceRepository;

    public PointServiceResource(PointServiceRepository pointServiceRepository) {
        this.pointServiceRepository = pointServiceRepository;
    }

    /**
     * {@code POST  /point-services} : Create a new pointService.
     *
     * @param pointService the pointService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointService, or with status {@code 400 (Bad Request)} if the pointService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-services")
    public ResponseEntity<PointService> createPointService(@RequestBody PointService pointService) throws URISyntaxException {
        log.debug("REST request to save PointService : {}", pointService);
        if (pointService.getId() != null) {
            throw new BadRequestAlertException("A new pointService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointService result = pointServiceRepository.save(pointService);
        return ResponseEntity
            .created(new URI("/api/point-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /point-services/:id} : Updates an existing pointService.
     *
     * @param id the id of the pointService to save.
     * @param pointService the pointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointService,
     * or with status {@code 400 (Bad Request)} if the pointService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-services/{id}")
    public ResponseEntity<PointService> updatePointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointService pointService
    ) throws URISyntaxException {
        log.debug("REST request to update PointService : {}, {}", id, pointService);
        if (pointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PointService result = pointServiceRepository.save(pointService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /point-services/:id} : Partial updates given fields of an existing pointService, field will ignore if it is null
     *
     * @param id the id of the pointService to save.
     * @param pointService the pointService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointService,
     * or with status {@code 400 (Bad Request)} if the pointService is not valid,
     * or with status {@code 404 (Not Found)} if the pointService is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointService> partialUpdatePointService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointService pointService
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointService partially : {}, {}", id, pointService);
        if (pointService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointService> result = pointServiceRepository
            .findById(pointService.getId())
            .map(existingPointService -> {
                if (pointService.getPartenaireId() != null) {
                    existingPointService.setPartenaireId(pointService.getPartenaireId());
                }
                if (pointService.getNomPos() != null) {
                    existingPointService.setNomPos(pointService.getNomPos());
                }
                if (pointService.getPosLon() != null) {
                    existingPointService.setPosLon(pointService.getPosLon());
                }
                if (pointService.getPosLat() != null) {
                    existingPointService.setPosLat(pointService.getPosLat());
                }
                if (pointService.getPosContact() != null) {
                    existingPointService.setPosContact(pointService.getPosContact());
                }
                if (pointService.getPosGsm() != null) {
                    existingPointService.setPosGsm(pointService.getPosGsm());
                }

                return existingPointService;
            })
            .map(pointServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointService.getId().toString())
        );
    }

    /**
     * {@code GET  /point-services} : get all the pointServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointServices in body.
     */
    @GetMapping("/point-services")
    public List<PointService> getAllPointServices() {
        log.debug("REST request to get all PointServices");
        return pointServiceRepository.findAll();
    }

    /**
     * {@code GET  /point-services/:id} : get the "id" pointService.
     *
     * @param id the id of the pointService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-services/{id}")
    public ResponseEntity<PointService> getPointService(@PathVariable Long id) {
        log.debug("REST request to get PointService : {}", id);
        Optional<PointService> pointService = pointServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pointService);
    }

    /**
     * {@code DELETE  /point-services/:id} : delete the "id" pointService.
     *
     * @param id the id of the pointService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-services/{id}")
    public ResponseEntity<Void> deletePointService(@PathVariable Long id) {
        log.debug("REST request to delete PointService : {}", id);
        pointServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
