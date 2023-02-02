package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.Transporteur;
import com.yarolab.mytrackit.transfert.repository.TransporteurRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.Transporteur}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransporteurResource {

    private final Logger log = LoggerFactory.getLogger(TransporteurResource.class);

    private static final String ENTITY_NAME = "myTransfertTransporteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransporteurRepository transporteurRepository;

    public TransporteurResource(TransporteurRepository transporteurRepository) {
        this.transporteurRepository = transporteurRepository;
    }

    /**
     * {@code POST  /transporteurs} : Create a new transporteur.
     *
     * @param transporteur the transporteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transporteur, or with status {@code 400 (Bad Request)} if the transporteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transporteurs")
    public ResponseEntity<Transporteur> createTransporteur(@RequestBody Transporteur transporteur) throws URISyntaxException {
        log.debug("REST request to save Transporteur : {}", transporteur);
        if (transporteur.getId() != null) {
            throw new BadRequestAlertException("A new transporteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transporteur result = transporteurRepository.save(transporteur);
        return ResponseEntity
            .created(new URI("/api/transporteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transporteurs/:id} : Updates an existing transporteur.
     *
     * @param id the id of the transporteur to save.
     * @param transporteur the transporteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporteur,
     * or with status {@code 400 (Bad Request)} if the transporteur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transporteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transporteurs/{id}")
    public ResponseEntity<Transporteur> updateTransporteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transporteur transporteur
    ) throws URISyntaxException {
        log.debug("REST request to update Transporteur : {}, {}", id, transporteur);
        if (transporteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transporteur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transporteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Transporteur result = transporteurRepository.save(transporteur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transporteur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transporteurs/:id} : Partial updates given fields of an existing transporteur, field will ignore if it is null
     *
     * @param id the id of the transporteur to save.
     * @param transporteur the transporteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporteur,
     * or with status {@code 400 (Bad Request)} if the transporteur is not valid,
     * or with status {@code 404 (Not Found)} if the transporteur is not found,
     * or with status {@code 500 (Internal Server Error)} if the transporteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transporteurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Transporteur> partialUpdateTransporteur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transporteur transporteur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transporteur partially : {}, {}", id, transporteur);
        if (transporteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transporteur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transporteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Transporteur> result = transporteurRepository
            .findById(transporteur.getId())
            .map(existingTransporteur -> {
                if (transporteur.getNomTransporteur() != null) {
                    existingTransporteur.setNomTransporteur(transporteur.getNomTransporteur());
                }
                if (transporteur.getNomDirecteur() != null) {
                    existingTransporteur.setNomDirecteur(transporteur.getNomDirecteur());
                }
                if (transporteur.getPhoneTransporteur() != null) {
                    existingTransporteur.setPhoneTransporteur(transporteur.getPhoneTransporteur());
                }
                if (transporteur.getEmailTransporteur() != null) {
                    existingTransporteur.setEmailTransporteur(transporteur.getEmailTransporteur());
                }

                return existingTransporteur;
            })
            .map(transporteurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transporteur.getId().toString())
        );
    }

    /**
     * {@code GET  /transporteurs} : get all the transporteurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transporteurs in body.
     */
    @GetMapping("/transporteurs")
    public List<Transporteur> getAllTransporteurs() {
        log.debug("REST request to get all Transporteurs");
        return transporteurRepository.findAll();
    }

    /**
     * {@code GET  /transporteurs/:id} : get the "id" transporteur.
     *
     * @param id the id of the transporteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transporteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transporteurs/{id}")
    public ResponseEntity<Transporteur> getTransporteur(@PathVariable Long id) {
        log.debug("REST request to get Transporteur : {}", id);
        Optional<Transporteur> transporteur = transporteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transporteur);
    }

    /**
     * {@code DELETE  /transporteurs/:id} : delete the "id" transporteur.
     *
     * @param id the id of the transporteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transporteurs/{id}")
    public ResponseEntity<Void> deleteTransporteur(@PathVariable Long id) {
        log.debug("REST request to delete Transporteur : {}", id);
        transporteurRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
