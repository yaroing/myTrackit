package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.ItemVerifie;
import com.yarolab.mytrackit.transfert.repository.ItemVerifieRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.ItemVerifie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItemVerifieResource {

    private final Logger log = LoggerFactory.getLogger(ItemVerifieResource.class);

    private static final String ENTITY_NAME = "myTransfertItemVerifie";

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
    public ResponseEntity<ItemVerifie> createItemVerifie(@RequestBody ItemVerifie itemVerifie) throws URISyntaxException {
        log.debug("REST request to save ItemVerifie : {}", itemVerifie);
        if (itemVerifie.getId() != null) {
            throw new BadRequestAlertException("A new itemVerifie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemVerifie result = itemVerifieRepository.save(itemVerifie);
        return ResponseEntity
            .created(new URI("/api/item-verifies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<ItemVerifie> updateItemVerifie(
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

        if (!itemVerifieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemVerifie result = itemVerifieRepository.save(itemVerifie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemVerifie.getId().toString()))
            .body(result);
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
    public ResponseEntity<ItemVerifie> partialUpdateItemVerifie(
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

        if (!itemVerifieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemVerifie> result = itemVerifieRepository
            .findById(itemVerifie.getId())
            .map(existingItemVerifie -> {
                if (itemVerifie.getMissionId() != null) {
                    existingItemVerifie.setMissionId(itemVerifie.getMissionId());
                }
                if (itemVerifie.getCatalogueId() != null) {
                    existingItemVerifie.setCatalogueId(itemVerifie.getCatalogueId());
                }
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
            .map(itemVerifieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemVerifie.getId().toString())
        );
    }

    /**
     * {@code GET  /item-verifies} : get all the itemVerifies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemVerifies in body.
     */
    @GetMapping("/item-verifies")
    public List<ItemVerifie> getAllItemVerifies() {
        log.debug("REST request to get all ItemVerifies");
        return itemVerifieRepository.findAll();
    }

    /**
     * {@code GET  /item-verifies/:id} : get the "id" itemVerifie.
     *
     * @param id the id of the itemVerifie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemVerifie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-verifies/{id}")
    public ResponseEntity<ItemVerifie> getItemVerifie(@PathVariable Long id) {
        log.debug("REST request to get ItemVerifie : {}", id);
        Optional<ItemVerifie> itemVerifie = itemVerifieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemVerifie);
    }

    /**
     * {@code DELETE  /item-verifies/:id} : delete the "id" itemVerifie.
     *
     * @param id the id of the itemVerifie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-verifies/{id}")
    public ResponseEntity<Void> deleteItemVerifie(@PathVariable Long id) {
        log.debug("REST request to delete ItemVerifie : {}", id);
        itemVerifieRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
