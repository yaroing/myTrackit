package com.yarolab.mytrackit.partenaire.web.rest;

import com.yarolab.mytrackit.partenaire.domain.DetailsRequete;
import com.yarolab.mytrackit.partenaire.repository.DetailsRequeteRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.partenaire.domain.DetailsRequete}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailsRequeteResource {

    private final Logger log = LoggerFactory.getLogger(DetailsRequeteResource.class);

    private static final String ENTITY_NAME = "myPartenaireDetailsRequete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailsRequeteRepository detailsRequeteRepository;

    public DetailsRequeteResource(DetailsRequeteRepository detailsRequeteRepository) {
        this.detailsRequeteRepository = detailsRequeteRepository;
    }

    /**
     * {@code POST  /details-requetes} : Create a new detailsRequete.
     *
     * @param detailsRequete the detailsRequete to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailsRequete, or with status {@code 400 (Bad Request)} if the detailsRequete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/details-requetes")
    public ResponseEntity<DetailsRequete> createDetailsRequete(@RequestBody DetailsRequete detailsRequete) throws URISyntaxException {
        log.debug("REST request to save DetailsRequete : {}", detailsRequete);
        if (detailsRequete.getId() != null) {
            throw new BadRequestAlertException("A new detailsRequete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailsRequete result = detailsRequeteRepository.save(detailsRequete);
        return ResponseEntity
            .created(new URI("/api/details-requetes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /details-requetes/:id} : Updates an existing detailsRequete.
     *
     * @param id the id of the detailsRequete to save.
     * @param detailsRequete the detailsRequete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsRequete,
     * or with status {@code 400 (Bad Request)} if the detailsRequete is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailsRequete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/details-requetes/{id}")
    public ResponseEntity<DetailsRequete> updateDetailsRequete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailsRequete detailsRequete
    ) throws URISyntaxException {
        log.debug("REST request to update DetailsRequete : {}, {}", id, detailsRequete);
        if (detailsRequete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsRequete.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsRequeteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailsRequete result = detailsRequeteRepository.save(detailsRequete);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailsRequete.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /details-requetes/:id} : Partial updates given fields of an existing detailsRequete, field will ignore if it is null
     *
     * @param id the id of the detailsRequete to save.
     * @param detailsRequete the detailsRequete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsRequete,
     * or with status {@code 400 (Bad Request)} if the detailsRequete is not valid,
     * or with status {@code 404 (Not Found)} if the detailsRequete is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailsRequete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/details-requetes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailsRequete> partialUpdateDetailsRequete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetailsRequete detailsRequete
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailsRequete partially : {}, {}", id, detailsRequete);
        if (detailsRequete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsRequete.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsRequeteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailsRequete> result = detailsRequeteRepository
            .findById(detailsRequete.getId())
            .map(existingDetailsRequete -> {
                if (detailsRequete.getQuantiteDemandee() != null) {
                    existingDetailsRequete.setQuantiteDemandee(detailsRequete.getQuantiteDemandee());
                }
                if (detailsRequete.getQuantiteApprouvee() != null) {
                    existingDetailsRequete.setQuantiteApprouvee(detailsRequete.getQuantiteApprouvee());
                }
                if (detailsRequete.getQuantiteRecue() != null) {
                    existingDetailsRequete.setQuantiteRecue(detailsRequete.getQuantiteRecue());
                }
                if (detailsRequete.getItemObs() != null) {
                    existingDetailsRequete.setItemObs(detailsRequete.getItemObs());
                }

                return existingDetailsRequete;
            })
            .map(detailsRequeteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailsRequete.getId().toString())
        );
    }

    /**
     * {@code GET  /details-requetes} : get all the detailsRequetes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailsRequetes in body.
     */
    @GetMapping("/details-requetes")
    public List<DetailsRequete> getAllDetailsRequetes() {
        log.debug("REST request to get all DetailsRequetes");
        return detailsRequeteRepository.findAll();
    }

    /**
     * {@code GET  /details-requetes/:id} : get the "id" detailsRequete.
     *
     * @param id the id of the detailsRequete to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailsRequete, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/details-requetes/{id}")
    public ResponseEntity<DetailsRequete> getDetailsRequete(@PathVariable Long id) {
        log.debug("REST request to get DetailsRequete : {}", id);
        Optional<DetailsRequete> detailsRequete = detailsRequeteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailsRequete);
    }

    /**
     * {@code DELETE  /details-requetes/:id} : delete the "id" detailsRequete.
     *
     * @param id the id of the detailsRequete to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/details-requetes/{id}")
    public ResponseEntity<Void> deleteDetailsRequete(@PathVariable Long id) {
        log.debug("REST request to delete DetailsRequete : {}", id);
        detailsRequeteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
