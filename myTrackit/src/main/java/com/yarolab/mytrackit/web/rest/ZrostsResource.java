package com.yarolab.mytrackit.web.rest;

import com.yarolab.mytrackit.domain.Zrosts;
import com.yarolab.mytrackit.repository.ZrostsRepository;
import com.yarolab.mytrackit.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.yarolab.mytrackit.domain.Zrosts}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ZrostsResource {

    private final Logger log = LoggerFactory.getLogger(ZrostsResource.class);

    private static final String ENTITY_NAME = "zrosts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZrostsRepository zrostsRepository;

    public ZrostsResource(ZrostsRepository zrostsRepository) {
        this.zrostsRepository = zrostsRepository;
    }

    /**
     * {@code POST  /zrosts} : Create a new zrosts.
     *
     * @param zrosts the zrosts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zrosts, or with status {@code 400 (Bad Request)} if the zrosts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zrosts")
    public Mono<ResponseEntity<Zrosts>> createZrosts(@RequestBody Zrosts zrosts) throws URISyntaxException {
        log.debug("REST request to save Zrosts : {}", zrosts);
        if (zrosts.getId() != null) {
            throw new BadRequestAlertException("A new zrosts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return zrostsRepository
            .save(zrosts)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/zrosts/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /zrosts/:id} : Updates an existing zrosts.
     *
     * @param id the id of the zrosts to save.
     * @param zrosts the zrosts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zrosts,
     * or with status {@code 400 (Bad Request)} if the zrosts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zrosts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zrosts/{id}")
    public Mono<ResponseEntity<Zrosts>> updateZrosts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zrosts zrosts
    ) throws URISyntaxException {
        log.debug("REST request to update Zrosts : {}, {}", id, zrosts);
        if (zrosts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zrosts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return zrostsRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return zrostsRepository
                    .save(zrosts)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /zrosts/:id} : Partial updates given fields of an existing zrosts, field will ignore if it is null
     *
     * @param id the id of the zrosts to save.
     * @param zrosts the zrosts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zrosts,
     * or with status {@code 400 (Bad Request)} if the zrosts is not valid,
     * or with status {@code 404 (Not Found)} if the zrosts is not found,
     * or with status {@code 500 (Internal Server Error)} if the zrosts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/zrosts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Zrosts>> partialUpdateZrosts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zrosts zrosts
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zrosts partially : {}, {}", id, zrosts);
        if (zrosts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zrosts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return zrostsRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Zrosts> result = zrostsRepository
                    .findById(zrosts.getId())
                    .map(existingZrosts -> {
                        if (zrosts.getRoId() != null) {
                            existingZrosts.setRoId(zrosts.getRoId());
                        }
                        if (zrosts.getRoItem() != null) {
                            existingZrosts.setRoItem(zrosts.getRoItem());
                        }
                        if (zrosts.getRoDate() != null) {
                            existingZrosts.setRoDate(zrosts.getRoDate());
                        }
                        if (zrosts.getRoTdd() != null) {
                            existingZrosts.setRoTdd(zrosts.getRoTdd());
                        }
                        if (zrosts.getMaterialId() != null) {
                            existingZrosts.setMaterialId(zrosts.getMaterialId());
                        }
                        if (zrosts.getMatDesc() != null) {
                            existingZrosts.setMatDesc(zrosts.getMatDesc());
                        }
                        if (zrosts.getDelQty() != null) {
                            existingZrosts.setDelQty(zrosts.getDelQty());
                        }
                        if (zrosts.getValue() != null) {
                            existingZrosts.setValue(zrosts.getValue());
                        }
                        if (zrosts.getStorageLoc() != null) {
                            existingZrosts.setStorageLoc(zrosts.getStorageLoc());
                        }
                        if (zrosts.getWhId() != null) {
                            existingZrosts.setWhId(zrosts.getWhId());
                        }
                        if (zrosts.getWhDesc() != null) {
                            existingZrosts.setWhDesc(zrosts.getWhDesc());
                        }
                        if (zrosts.getConsId() != null) {
                            existingZrosts.setConsId(zrosts.getConsId());
                        }
                        if (zrosts.getConsName() != null) {
                            existingZrosts.setConsName(zrosts.getConsName());
                        }
                        if (zrosts.getAuthPerson() != null) {
                            existingZrosts.setAuthPerson(zrosts.getAuthPerson());
                        }
                        if (zrosts.getSoId() != null) {
                            existingZrosts.setSoId(zrosts.getSoId());
                        }
                        if (zrosts.getPoId() != null) {
                            existingZrosts.setPoId(zrosts.getPoId());
                        }
                        if (zrosts.getDelivery() != null) {
                            existingZrosts.setDelivery(zrosts.getDelivery());
                        }
                        if (zrosts.getGrant() != null) {
                            existingZrosts.setGrant(zrosts.getGrant());
                        }
                        if (zrosts.getWbs() != null) {
                            existingZrosts.setWbs(zrosts.getWbs());
                        }
                        if (zrosts.getPickStatus() != null) {
                            existingZrosts.setPickStatus(zrosts.getPickStatus());
                        }
                        if (zrosts.getToNumber() != null) {
                            existingZrosts.setToNumber(zrosts.getToNumber());
                        }
                        if (zrosts.getTrsptStatus() != null) {
                            existingZrosts.setTrsptStatus(zrosts.getTrsptStatus());
                        }
                        if (zrosts.getWaybId() != null) {
                            existingZrosts.setWaybId(zrosts.getWaybId());
                        }
                        if (zrosts.getTrsptrName() != null) {
                            existingZrosts.setTrsptrName(zrosts.getTrsptrName());
                        }
                        if (zrosts.getShipmtEd() != null) {
                            existingZrosts.setShipmtEd(zrosts.getShipmtEd());
                        }
                        if (zrosts.getGdsStatus() != null) {
                            existingZrosts.setGdsStatus(zrosts.getGdsStatus());
                        }
                        if (zrosts.getGdsDate() != null) {
                            existingZrosts.setGdsDate(zrosts.getGdsDate());
                        }
                        if (zrosts.getRoSubitem() != null) {
                            existingZrosts.setRoSubitem(zrosts.getRoSubitem());
                        }
                        if (zrosts.getRoType() != null) {
                            existingZrosts.setRoType(zrosts.getRoType());
                        }
                        if (zrosts.getUnit() != null) {
                            existingZrosts.setUnit(zrosts.getUnit());
                        }
                        if (zrosts.getMovingPrice() != null) {
                            existingZrosts.setMovingPrice(zrosts.getMovingPrice());
                        }
                        if (zrosts.getPlantId() != null) {
                            existingZrosts.setPlantId(zrosts.getPlantId());
                        }
                        if (zrosts.getPlantName() != null) {
                            existingZrosts.setPlantName(zrosts.getPlantName());
                        }
                        if (zrosts.getStorageLocp() != null) {
                            existingZrosts.setStorageLocp(zrosts.getStorageLocp());
                        }
                        if (zrosts.getDwhId() != null) {
                            existingZrosts.setDwhId(zrosts.getDwhId());
                        }
                        if (zrosts.getDwhDesc() != null) {
                            existingZrosts.setDwhDesc(zrosts.getDwhDesc());
                        }
                        if (zrosts.getShipParty() != null) {
                            existingZrosts.setShipParty(zrosts.getShipParty());
                        }
                        if (zrosts.getTrsptMeans() != null) {
                            existingZrosts.setTrsptMeans(zrosts.getTrsptMeans());
                        }
                        if (zrosts.getProgOfficer() != null) {
                            existingZrosts.setProgOfficer(zrosts.getProgOfficer());
                        }
                        if (zrosts.getSoItems() != null) {
                            existingZrosts.setSoItems(zrosts.getSoItems());
                        }
                        if (zrosts.getPoItems() != null) {
                            existingZrosts.setPoItems(zrosts.getPoItems());
                        }
                        if (zrosts.getTrsptrId() != null) {
                            existingZrosts.setTrsptrId(zrosts.getTrsptrId());
                        }
                        if (zrosts.getGdsId() != null) {
                            existingZrosts.setGdsId(zrosts.getGdsId());
                        }
                        if (zrosts.getGdsItem() != null) {
                            existingZrosts.setGdsItem(zrosts.getGdsItem());
                        }
                        if (zrosts.getBatch() != null) {
                            existingZrosts.setBatch(zrosts.getBatch());
                        }
                        if (zrosts.getBbDate() != null) {
                            existingZrosts.setBbDate(zrosts.getBbDate());
                        }
                        if (zrosts.getPlanningDate() != null) {
                            existingZrosts.setPlanningDate(zrosts.getPlanningDate());
                        }
                        if (zrosts.getCheckinDate() != null) {
                            existingZrosts.setCheckinDate(zrosts.getCheckinDate());
                        }
                        if (zrosts.getShipmentSdate() != null) {
                            existingZrosts.setShipmentSdate(zrosts.getShipmentSdate());
                        }
                        if (zrosts.getLoadingSdate() != null) {
                            existingZrosts.setLoadingSdate(zrosts.getLoadingSdate());
                        }
                        if (zrosts.getLoadingEdate() != null) {
                            existingZrosts.setLoadingEdate(zrosts.getLoadingEdate());
                        }
                        if (zrosts.getAshipmentSdate() != null) {
                            existingZrosts.setAshipmentSdate(zrosts.getAshipmentSdate());
                        }
                        if (zrosts.getShipmentCdate() != null) {
                            existingZrosts.setShipmentCdate(zrosts.getShipmentCdate());
                        }
                        if (zrosts.getWeight() != null) {
                            existingZrosts.setWeight(zrosts.getWeight());
                        }
                        if (zrosts.getVolume() != null) {
                            existingZrosts.setVolume(zrosts.getVolume());
                        }
                        if (zrosts.getSection() != null) {
                            existingZrosts.setSection(zrosts.getSection());
                        }
                        if (zrosts.getCommodityGroup() != null) {
                            existingZrosts.setCommodityGroup(zrosts.getCommodityGroup());
                        }
                        if (zrosts.getRegion() != null) {
                            existingZrosts.setRegion(zrosts.getRegion());
                        }

                        return existingZrosts;
                    })
                    .flatMap(zrostsRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /zrosts} : get all the zrosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zrosts in body.
     */
    @GetMapping("/zrosts")
    public Mono<List<Zrosts>> getAllZrosts() {
        log.debug("REST request to get all Zrosts");
        return zrostsRepository.findAll().collectList();
    }

    /**
     * {@code GET  /zrosts} : get all the zrosts as a stream.
     * @return the {@link Flux} of zrosts.
     */
    @GetMapping(value = "/zrosts", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Zrosts> getAllZrostsAsStream() {
        log.debug("REST request to get all Zrosts as a stream");
        return zrostsRepository.findAll();
    }

    /**
     * {@code GET  /zrosts/:id} : get the "id" zrosts.
     *
     * @param id the id of the zrosts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zrosts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zrosts/{id}")
    public Mono<ResponseEntity<Zrosts>> getZrosts(@PathVariable Long id) {
        log.debug("REST request to get Zrosts : {}", id);
        Mono<Zrosts> zrosts = zrostsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zrosts);
    }

    /**
     * {@code DELETE  /zrosts/:id} : delete the "id" zrosts.
     *
     * @param id the id of the zrosts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zrosts/{id}")
    public Mono<ResponseEntity<Void>> deleteZrosts(@PathVariable Long id) {
        log.debug("REST request to delete Zrosts : {}", id);
        return zrostsRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
