package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.Transfert;
import com.yarolab.mytrackit.transfert.repository.TransfertRepository;
import com.yarolab.mytrackit.transfert.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.Transfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransfertResource {

    private final Logger log = LoggerFactory.getLogger(TransfertResource.class);

    private static final String ENTITY_NAME = "myTransfertTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransfertRepository transfertRepository;

    public TransfertResource(TransfertRepository transfertRepository) {
        this.transfertRepository = transfertRepository;
    }

    /**
     * {@code POST  /transferts} : Create a new transfert.
     *
     * @param transfert the transfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transfert, or with status {@code 400 (Bad Request)} if the transfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferts")
    public ResponseEntity<Transfert> createTransfert(@RequestBody Transfert transfert) throws URISyntaxException {
        log.debug("REST request to save Transfert : {}", transfert);
        if (transfert.getId() != null) {
            throw new BadRequestAlertException("A new transfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transfert result = transfertRepository.save(transfert);
        return ResponseEntity
            .created(new URI("/api/transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transferts/:id} : Updates an existing transfert.
     *
     * @param id the id of the transfert to save.
     * @param transfert the transfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfert,
     * or with status {@code 400 (Bad Request)} if the transfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferts/{id}")
    public ResponseEntity<Transfert> updateTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transfert transfert
    ) throws URISyntaxException {
        log.debug("REST request to update Transfert : {}, {}", id, transfert);
        if (transfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transfertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Transfert result = transfertRepository.save(transfert);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transferts/:id} : Partial updates given fields of an existing transfert, field will ignore if it is null
     *
     * @param id the id of the transfert to save.
     * @param transfert the transfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfert,
     * or with status {@code 400 (Bad Request)} if the transfert is not valid,
     * or with status {@code 404 (Not Found)} if the transfert is not found,
     * or with status {@code 500 (Internal Server Error)} if the transfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transferts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Transfert> partialUpdateTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transfert transfert
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transfert partially : {}, {}", id, transfert);
        if (transfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transfertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Transfert> result = transfertRepository
            .findById(transfert.getId())
            .map(existingTransfert -> {
                if (transfert.getDateExp() != null) {
                    existingTransfert.setDateExp(transfert.getDateExp());
                }
                if (transfert.getSectionId() != null) {
                    existingTransfert.setSectionId(transfert.getSectionId());
                }
                if (transfert.getStaffId() != null) {
                    existingTransfert.setStaffId(transfert.getStaffId());
                }
                if (transfert.getPartenaireId() != null) {
                    existingTransfert.setPartenaireId(transfert.getPartenaireId());
                }
                if (transfert.getTransporteurId() != null) {
                    existingTransfert.setTransporteurId(transfert.getTransporteurId());
                }
                if (transfert.getNomChauffeur() != null) {
                    existingTransfert.setNomChauffeur(transfert.getNomChauffeur());
                }
                if (transfert.getDateRec() != null) {
                    existingTransfert.setDateRec(transfert.getDateRec());
                }
                if (transfert.getStatutRecId() != null) {
                    existingTransfert.setStatutRecId(transfert.getStatutRecId());
                }
                if (transfert.getCphone() != null) {
                    existingTransfert.setCphone(transfert.getCphone());
                }

                return existingTransfert;
            })
            .map(transfertRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfert.getId().toString())
        );
    }

    /**
     * {@code GET  /transferts} : get all the transferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferts in body.
     */
    @GetMapping("/transferts")
    public List<Transfert> getAllTransferts() {
        log.debug("REST request to get all Transferts");
        return transfertRepository.findAll();
    }

    /**
     * {@code GET  /transferts/:id} : get the "id" transfert.
     *
     * @param id the id of the transfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferts/{id}")
    public ResponseEntity<Transfert> getTransfert(@PathVariable Long id) {
        log.debug("REST request to get Transfert : {}", id);
        Optional<Transfert> transfert = transfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transfert);
    }

    /**
     * {@code DELETE  /transferts/:id} : delete the "id" transfert.
     *
     * @param id the id of the transfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferts/{id}")
    public ResponseEntity<Void> deleteTransfert(@PathVariable Long id) {
        log.debug("REST request to delete Transfert : {}", id);
        transfertRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
