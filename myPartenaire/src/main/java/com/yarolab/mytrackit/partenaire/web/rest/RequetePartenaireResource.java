package com.yarolab.mytrackit.partenaire.web.rest;

import com.yarolab.mytrackit.partenaire.domain.RequetePartenaire;
import com.yarolab.mytrackit.partenaire.repository.RequetePartenaireRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.partenaire.domain.RequetePartenaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequetePartenaireResource {

    private final Logger log = LoggerFactory.getLogger(RequetePartenaireResource.class);

    private static final String ENTITY_NAME = "myPartenaireRequetePartenaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequetePartenaireRepository requetePartenaireRepository;

    public RequetePartenaireResource(RequetePartenaireRepository requetePartenaireRepository) {
        this.requetePartenaireRepository = requetePartenaireRepository;
    }

    /**
     * {@code POST  /requete-partenaires} : Create a new requetePartenaire.
     *
     * @param requetePartenaire the requetePartenaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requetePartenaire, or with status {@code 400 (Bad Request)} if the requetePartenaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requete-partenaires")
    public ResponseEntity<RequetePartenaire> createRequetePartenaire(@RequestBody RequetePartenaire requetePartenaire)
        throws URISyntaxException {
        log.debug("REST request to save RequetePartenaire : {}", requetePartenaire);
        if (requetePartenaire.getId() != null) {
            throw new BadRequestAlertException("A new requetePartenaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequetePartenaire result = requetePartenaireRepository.save(requetePartenaire);
        return ResponseEntity
            .created(new URI("/api/requete-partenaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requete-partenaires/:id} : Updates an existing requetePartenaire.
     *
     * @param id the id of the requetePartenaire to save.
     * @param requetePartenaire the requetePartenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requetePartenaire,
     * or with status {@code 400 (Bad Request)} if the requetePartenaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requetePartenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requete-partenaires/{id}")
    public ResponseEntity<RequetePartenaire> updateRequetePartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequetePartenaire requetePartenaire
    ) throws URISyntaxException {
        log.debug("REST request to update RequetePartenaire : {}, {}", id, requetePartenaire);
        if (requetePartenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requetePartenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requetePartenaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequetePartenaire result = requetePartenaireRepository.save(requetePartenaire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requetePartenaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requete-partenaires/:id} : Partial updates given fields of an existing requetePartenaire, field will ignore if it is null
     *
     * @param id the id of the requetePartenaire to save.
     * @param requetePartenaire the requetePartenaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requetePartenaire,
     * or with status {@code 400 (Bad Request)} if the requetePartenaire is not valid,
     * or with status {@code 404 (Not Found)} if the requetePartenaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the requetePartenaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requete-partenaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequetePartenaire> partialUpdateRequetePartenaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequetePartenaire requetePartenaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequetePartenaire partially : {}, {}", id, requetePartenaire);
        if (requetePartenaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requetePartenaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requetePartenaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequetePartenaire> result = requetePartenaireRepository
            .findById(requetePartenaire.getId())
            .map(existingRequetePartenaire -> {
                if (requetePartenaire.getRequeteDate() != null) {
                    existingRequetePartenaire.setRequeteDate(requetePartenaire.getRequeteDate());
                }
                if (requetePartenaire.getFichierAtache() != null) {
                    existingRequetePartenaire.setFichierAtache(requetePartenaire.getFichierAtache());
                }
                if (requetePartenaire.getFichierAtacheContentType() != null) {
                    existingRequetePartenaire.setFichierAtacheContentType(requetePartenaire.getFichierAtacheContentType());
                }
                if (requetePartenaire.getRequeteObs() != null) {
                    existingRequetePartenaire.setRequeteObs(requetePartenaire.getRequeteObs());
                }
                if (requetePartenaire.getReqTraitee() != null) {
                    existingRequetePartenaire.setReqTraitee(requetePartenaire.getReqTraitee());
                }

                return existingRequetePartenaire;
            })
            .map(requetePartenaireRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requetePartenaire.getId().toString())
        );
    }

    /**
     * {@code GET  /requete-partenaires} : get all the requetePartenaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requetePartenaires in body.
     */
    @GetMapping("/requete-partenaires")
    public List<RequetePartenaire> getAllRequetePartenaires() {
        log.debug("REST request to get all RequetePartenaires");
        return requetePartenaireRepository.findAll();
    }

    /**
     * {@code GET  /requete-partenaires/:id} : get the "id" requetePartenaire.
     *
     * @param id the id of the requetePartenaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requetePartenaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requete-partenaires/{id}")
    public ResponseEntity<RequetePartenaire> getRequetePartenaire(@PathVariable Long id) {
        log.debug("REST request to get RequetePartenaire : {}", id);
        Optional<RequetePartenaire> requetePartenaire = requetePartenaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requetePartenaire);
    }

    /**
     * {@code DELETE  /requete-partenaires/:id} : delete the "id" requetePartenaire.
     *
     * @param id the id of the requetePartenaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requete-partenaires/{id}")
    public ResponseEntity<Void> deleteRequetePartenaire(@PathVariable Long id) {
        log.debug("REST request to delete RequetePartenaire : {}", id);
        requetePartenaireRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
