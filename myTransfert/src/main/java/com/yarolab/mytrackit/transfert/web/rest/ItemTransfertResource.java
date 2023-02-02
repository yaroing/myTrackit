package com.yarolab.mytrackit.transfert.web.rest;

import com.yarolab.mytrackit.transfert.domain.ItemTransfert;
import com.yarolab.mytrackit.transfert.repository.ItemTransfertRepository;
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
 * REST controller for managing {@link com.yarolab.mytrackit.transfert.domain.ItemTransfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItemTransfertResource {

    private final Logger log = LoggerFactory.getLogger(ItemTransfertResource.class);

    private static final String ENTITY_NAME = "myTransfertItemTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemTransfertRepository itemTransfertRepository;

    public ItemTransfertResource(ItemTransfertRepository itemTransfertRepository) {
        this.itemTransfertRepository = itemTransfertRepository;
    }

    /**
     * {@code POST  /item-transferts} : Create a new itemTransfert.
     *
     * @param itemTransfert the itemTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemTransfert, or with status {@code 400 (Bad Request)} if the itemTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-transferts")
    public ResponseEntity<ItemTransfert> createItemTransfert(@RequestBody ItemTransfert itemTransfert) throws URISyntaxException {
        log.debug("REST request to save ItemTransfert : {}", itemTransfert);
        if (itemTransfert.getId() != null) {
            throw new BadRequestAlertException("A new itemTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemTransfert result = itemTransfertRepository.save(itemTransfert);
        return ResponseEntity
            .created(new URI("/api/item-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-transferts/:id} : Updates an existing itemTransfert.
     *
     * @param id the id of the itemTransfert to save.
     * @param itemTransfert the itemTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemTransfert,
     * or with status {@code 400 (Bad Request)} if the itemTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-transferts/{id}")
    public ResponseEntity<ItemTransfert> updateItemTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemTransfert itemTransfert
    ) throws URISyntaxException {
        log.debug("REST request to update ItemTransfert : {}, {}", id, itemTransfert);
        if (itemTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemTransfert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemTransfertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemTransfert result = itemTransfertRepository.save(itemTransfert);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-transferts/:id} : Partial updates given fields of an existing itemTransfert, field will ignore if it is null
     *
     * @param id the id of the itemTransfert to save.
     * @param itemTransfert the itemTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemTransfert,
     * or with status {@code 400 (Bad Request)} if the itemTransfert is not valid,
     * or with status {@code 404 (Not Found)} if the itemTransfert is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-transferts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemTransfert> partialUpdateItemTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemTransfert itemTransfert
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemTransfert partially : {}, {}", id, itemTransfert);
        if (itemTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemTransfert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemTransfertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemTransfert> result = itemTransfertRepository
            .findById(itemTransfert.getId())
            .map(existingItemTransfert -> {
                if (itemTransfert.getWaybId() != null) {
                    existingItemTransfert.setWaybId(itemTransfert.getWaybId());
                }
                if (itemTransfert.getRoId() != null) {
                    existingItemTransfert.setRoId(itemTransfert.getRoId());
                }
                if (itemTransfert.getRoDate() != null) {
                    existingItemTransfert.setRoDate(itemTransfert.getRoDate());
                }
                if (itemTransfert.getMaterialId() != null) {
                    existingItemTransfert.setMaterialId(itemTransfert.getMaterialId());
                }
                if (itemTransfert.getMatDesc() != null) {
                    existingItemTransfert.setMatDesc(itemTransfert.getMatDesc());
                }
                if (itemTransfert.getUnit() != null) {
                    existingItemTransfert.setUnit(itemTransfert.getUnit());
                }
                if (itemTransfert.getDelQty() != null) {
                    existingItemTransfert.setDelQty(itemTransfert.getDelQty());
                }
                if (itemTransfert.getValue() != null) {
                    existingItemTransfert.setValue(itemTransfert.getValue());
                }
                if (itemTransfert.getBatch() != null) {
                    existingItemTransfert.setBatch(itemTransfert.getBatch());
                }
                if (itemTransfert.getBbDate() != null) {
                    existingItemTransfert.setBbDate(itemTransfert.getBbDate());
                }
                if (itemTransfert.getWeight() != null) {
                    existingItemTransfert.setWeight(itemTransfert.getWeight());
                }
                if (itemTransfert.getVolume() != null) {
                    existingItemTransfert.setVolume(itemTransfert.getVolume());
                }
                if (itemTransfert.getRecQty() != null) {
                    existingItemTransfert.setRecQty(itemTransfert.getRecQty());
                }

                return existingItemTransfert;
            })
            .map(itemTransfertRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemTransfert.getId().toString())
        );
    }

    /**
     * {@code GET  /item-transferts} : get all the itemTransferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemTransferts in body.
     */
    @GetMapping("/item-transferts")
    public List<ItemTransfert> getAllItemTransferts() {
        log.debug("REST request to get all ItemTransferts");
        return itemTransfertRepository.findAll();
    }

    /**
     * {@code GET  /item-transferts/:id} : get the "id" itemTransfert.
     *
     * @param id the id of the itemTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-transferts/{id}")
    public ResponseEntity<ItemTransfert> getItemTransfert(@PathVariable Long id) {
        log.debug("REST request to get ItemTransfert : {}", id);
        Optional<ItemTransfert> itemTransfert = itemTransfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemTransfert);
    }

    /**
     * {@code DELETE  /item-transferts/:id} : delete the "id" itemTransfert.
     *
     * @param id the id of the itemTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-transferts/{id}")
    public ResponseEntity<Void> deleteItemTransfert(@PathVariable Long id) {
        log.debug("REST request to delete ItemTransfert : {}", id);
        itemTransfertRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
