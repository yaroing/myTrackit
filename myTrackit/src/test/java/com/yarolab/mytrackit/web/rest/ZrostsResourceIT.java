package com.yarolab.mytrackit.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.yarolab.mytrackit.IntegrationTest;
import com.yarolab.mytrackit.domain.Zrosts;
import com.yarolab.mytrackit.repository.EntityManager;
import com.yarolab.mytrackit.repository.ZrostsRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ZrostsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ZrostsResourceIT {

    private static final Integer DEFAULT_RO_ID = 1;
    private static final Integer UPDATED_RO_ID = 2;

    private static final Double DEFAULT_RO_ITEM = 1D;
    private static final Double UPDATED_RO_ITEM = 2D;

    private static final Instant DEFAULT_RO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RO_TDD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RO_TDD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MATERIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MAT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_MAT_DESC = "BBBBBBBBBB";

    private static final Double DEFAULT_DEL_QTY = 1D;
    private static final Double UPDATED_DEL_QTY = 2D;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Double DEFAULT_STORAGE_LOC = 1D;
    private static final Double UPDATED_STORAGE_LOC = 2D;

    private static final Double DEFAULT_WH_ID = 1D;
    private static final Double UPDATED_WH_ID = 2D;

    private static final String DEFAULT_WH_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WH_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_CONS_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_SO_ID = "AAAAAAAAAA";
    private static final String UPDATED_SO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PO_ID = "AAAAAAAAAA";
    private static final String UPDATED_PO_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_DELIVERY = 1D;
    private static final Double UPDATED_DELIVERY = 2D;

    private static final String DEFAULT_GRANT = "AAAAAAAAAA";
    private static final String UPDATED_GRANT = "BBBBBBBBBB";

    private static final String DEFAULT_WBS = "AAAAAAAAAA";
    private static final String UPDATED_WBS = "BBBBBBBBBB";

    private static final String DEFAULT_PICK_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PICK_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TO_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TRSPT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TRSPT_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_WAYB_ID = 1;
    private static final Integer UPDATED_WAYB_ID = 2;

    private static final String DEFAULT_TRSPTR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRSPTR_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_SHIPMT_ED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHIPMT_ED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GDS_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_GDS_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_GDS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GDS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_RO_SUBITEM = 1D;
    private static final Double UPDATED_RO_SUBITEM = 2D;

    private static final String DEFAULT_RO_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RO_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Double DEFAULT_MOVING_PRICE = 1D;
    private static final Double UPDATED_MOVING_PRICE = 2D;

    private static final Double DEFAULT_PLANT_ID = 1D;
    private static final Double UPDATED_PLANT_ID = 2D;

    private static final String DEFAULT_PLANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STORAGE_LOCP = "AAAAAAAAAA";
    private static final String UPDATED_STORAGE_LOCP = "BBBBBBBBBB";

    private static final String DEFAULT_DWH_ID = "AAAAAAAAAA";
    private static final String UPDATED_DWH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DWH_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DWH_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_SHIP_PARTY = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_PARTY = "BBBBBBBBBB";

    private static final String DEFAULT_TRSPT_MEANS = "AAAAAAAAAA";
    private static final String UPDATED_TRSPT_MEANS = "BBBBBBBBBB";

    private static final String DEFAULT_PROG_OFFICER = "AAAAAAAAAA";
    private static final String UPDATED_PROG_OFFICER = "BBBBBBBBBB";

    private static final Double DEFAULT_SO_ITEMS = 1D;
    private static final Double UPDATED_SO_ITEMS = 2D;

    private static final Double DEFAULT_PO_ITEMS = 1D;
    private static final Double UPDATED_PO_ITEMS = 2D;

    private static final String DEFAULT_TRSPTR_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRSPTR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GDS_ID = "AAAAAAAAAA";
    private static final String UPDATED_GDS_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_GDS_ITEM = 1D;
    private static final Double UPDATED_GDS_ITEM = 2D;

    private static final String DEFAULT_BATCH = "AAAAAAAAAA";
    private static final String UPDATED_BATCH = "BBBBBBBBBB";

    private static final Instant DEFAULT_BB_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BB_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PLANNING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLANNING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHECKIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECKIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SHIPMENT_SDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHIPMENT_SDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOADING_SDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOADING_SDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOADING_EDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOADING_EDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ASHIPMENT_SDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASHIPMENT_SDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SHIPMENT_CDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHIPMENT_CDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    private static final String DEFAULT_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_SECTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMODITY_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_COMMODITY_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zrosts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ZrostsRepository zrostsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Zrosts zrosts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zrosts createEntity(EntityManager em) {
        Zrosts zrosts = new Zrosts()
            .roId(DEFAULT_RO_ID)
            .roItem(DEFAULT_RO_ITEM)
            .roDate(DEFAULT_RO_DATE)
            .roTdd(DEFAULT_RO_TDD)
            .materialId(DEFAULT_MATERIAL_ID)
            .matDesc(DEFAULT_MAT_DESC)
            .delQty(DEFAULT_DEL_QTY)
            .value(DEFAULT_VALUE)
            .storageLoc(DEFAULT_STORAGE_LOC)
            .whId(DEFAULT_WH_ID)
            .whDesc(DEFAULT_WH_DESC)
            .consId(DEFAULT_CONS_ID)
            .consName(DEFAULT_CONS_NAME)
            .authPerson(DEFAULT_AUTH_PERSON)
            .soId(DEFAULT_SO_ID)
            .poId(DEFAULT_PO_ID)
            .delivery(DEFAULT_DELIVERY)
            .grant(DEFAULT_GRANT)
            .wbs(DEFAULT_WBS)
            .pickStatus(DEFAULT_PICK_STATUS)
            .toNumber(DEFAULT_TO_NUMBER)
            .trsptStatus(DEFAULT_TRSPT_STATUS)
            .waybId(DEFAULT_WAYB_ID)
            .trsptrName(DEFAULT_TRSPTR_NAME)
            .shipmtEd(DEFAULT_SHIPMT_ED)
            .gdsStatus(DEFAULT_GDS_STATUS)
            .gdsDate(DEFAULT_GDS_DATE)
            .roSubitem(DEFAULT_RO_SUBITEM)
            .roType(DEFAULT_RO_TYPE)
            .unit(DEFAULT_UNIT)
            .movingPrice(DEFAULT_MOVING_PRICE)
            .plantId(DEFAULT_PLANT_ID)
            .plantName(DEFAULT_PLANT_NAME)
            .storageLocp(DEFAULT_STORAGE_LOCP)
            .dwhId(DEFAULT_DWH_ID)
            .dwhDesc(DEFAULT_DWH_DESC)
            .shipParty(DEFAULT_SHIP_PARTY)
            .trsptMeans(DEFAULT_TRSPT_MEANS)
            .progOfficer(DEFAULT_PROG_OFFICER)
            .soItems(DEFAULT_SO_ITEMS)
            .poItems(DEFAULT_PO_ITEMS)
            .trsptrId(DEFAULT_TRSPTR_ID)
            .gdsId(DEFAULT_GDS_ID)
            .gdsItem(DEFAULT_GDS_ITEM)
            .batch(DEFAULT_BATCH)
            .bbDate(DEFAULT_BB_DATE)
            .planningDate(DEFAULT_PLANNING_DATE)
            .checkinDate(DEFAULT_CHECKIN_DATE)
            .shipmentSdate(DEFAULT_SHIPMENT_SDATE)
            .loadingSdate(DEFAULT_LOADING_SDATE)
            .loadingEdate(DEFAULT_LOADING_EDATE)
            .ashipmentSdate(DEFAULT_ASHIPMENT_SDATE)
            .shipmentCdate(DEFAULT_SHIPMENT_CDATE)
            .weight(DEFAULT_WEIGHT)
            .volume(DEFAULT_VOLUME)
            .section(DEFAULT_SECTION)
            .commodityGroup(DEFAULT_COMMODITY_GROUP)
            .region(DEFAULT_REGION);
        return zrosts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zrosts createUpdatedEntity(EntityManager em) {
        Zrosts zrosts = new Zrosts()
            .roId(UPDATED_RO_ID)
            .roItem(UPDATED_RO_ITEM)
            .roDate(UPDATED_RO_DATE)
            .roTdd(UPDATED_RO_TDD)
            .materialId(UPDATED_MATERIAL_ID)
            .matDesc(UPDATED_MAT_DESC)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .storageLoc(UPDATED_STORAGE_LOC)
            .whId(UPDATED_WH_ID)
            .whDesc(UPDATED_WH_DESC)
            .consId(UPDATED_CONS_ID)
            .consName(UPDATED_CONS_NAME)
            .authPerson(UPDATED_AUTH_PERSON)
            .soId(UPDATED_SO_ID)
            .poId(UPDATED_PO_ID)
            .delivery(UPDATED_DELIVERY)
            .grant(UPDATED_GRANT)
            .wbs(UPDATED_WBS)
            .pickStatus(UPDATED_PICK_STATUS)
            .toNumber(UPDATED_TO_NUMBER)
            .trsptStatus(UPDATED_TRSPT_STATUS)
            .waybId(UPDATED_WAYB_ID)
            .trsptrName(UPDATED_TRSPTR_NAME)
            .shipmtEd(UPDATED_SHIPMT_ED)
            .gdsStatus(UPDATED_GDS_STATUS)
            .gdsDate(UPDATED_GDS_DATE)
            .roSubitem(UPDATED_RO_SUBITEM)
            .roType(UPDATED_RO_TYPE)
            .unit(UPDATED_UNIT)
            .movingPrice(UPDATED_MOVING_PRICE)
            .plantId(UPDATED_PLANT_ID)
            .plantName(UPDATED_PLANT_NAME)
            .storageLocp(UPDATED_STORAGE_LOCP)
            .dwhId(UPDATED_DWH_ID)
            .dwhDesc(UPDATED_DWH_DESC)
            .shipParty(UPDATED_SHIP_PARTY)
            .trsptMeans(UPDATED_TRSPT_MEANS)
            .progOfficer(UPDATED_PROG_OFFICER)
            .soItems(UPDATED_SO_ITEMS)
            .poItems(UPDATED_PO_ITEMS)
            .trsptrId(UPDATED_TRSPTR_ID)
            .gdsId(UPDATED_GDS_ID)
            .gdsItem(UPDATED_GDS_ITEM)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .planningDate(UPDATED_PLANNING_DATE)
            .checkinDate(UPDATED_CHECKIN_DATE)
            .shipmentSdate(UPDATED_SHIPMENT_SDATE)
            .loadingSdate(UPDATED_LOADING_SDATE)
            .loadingEdate(UPDATED_LOADING_EDATE)
            .ashipmentSdate(UPDATED_ASHIPMENT_SDATE)
            .shipmentCdate(UPDATED_SHIPMENT_CDATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .section(UPDATED_SECTION)
            .commodityGroup(UPDATED_COMMODITY_GROUP)
            .region(UPDATED_REGION);
        return zrosts;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Zrosts.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        zrosts = createEntity(em);
    }

    @Test
    void createZrosts() throws Exception {
        int databaseSizeBeforeCreate = zrostsRepository.findAll().collectList().block().size();
        // Create the Zrosts
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeCreate + 1);
        Zrosts testZrosts = zrostsList.get(zrostsList.size() - 1);
        assertThat(testZrosts.getRoId()).isEqualTo(DEFAULT_RO_ID);
        assertThat(testZrosts.getRoItem()).isEqualTo(DEFAULT_RO_ITEM);
        assertThat(testZrosts.getRoDate()).isEqualTo(DEFAULT_RO_DATE);
        assertThat(testZrosts.getRoTdd()).isEqualTo(DEFAULT_RO_TDD);
        assertThat(testZrosts.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testZrosts.getMatDesc()).isEqualTo(DEFAULT_MAT_DESC);
        assertThat(testZrosts.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testZrosts.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testZrosts.getStorageLoc()).isEqualTo(DEFAULT_STORAGE_LOC);
        assertThat(testZrosts.getWhId()).isEqualTo(DEFAULT_WH_ID);
        assertThat(testZrosts.getWhDesc()).isEqualTo(DEFAULT_WH_DESC);
        assertThat(testZrosts.getConsId()).isEqualTo(DEFAULT_CONS_ID);
        assertThat(testZrosts.getConsName()).isEqualTo(DEFAULT_CONS_NAME);
        assertThat(testZrosts.getAuthPerson()).isEqualTo(DEFAULT_AUTH_PERSON);
        assertThat(testZrosts.getSoId()).isEqualTo(DEFAULT_SO_ID);
        assertThat(testZrosts.getPoId()).isEqualTo(DEFAULT_PO_ID);
        assertThat(testZrosts.getDelivery()).isEqualTo(DEFAULT_DELIVERY);
        assertThat(testZrosts.getGrant()).isEqualTo(DEFAULT_GRANT);
        assertThat(testZrosts.getWbs()).isEqualTo(DEFAULT_WBS);
        assertThat(testZrosts.getPickStatus()).isEqualTo(DEFAULT_PICK_STATUS);
        assertThat(testZrosts.getToNumber()).isEqualTo(DEFAULT_TO_NUMBER);
        assertThat(testZrosts.getTrsptStatus()).isEqualTo(DEFAULT_TRSPT_STATUS);
        assertThat(testZrosts.getWaybId()).isEqualTo(DEFAULT_WAYB_ID);
        assertThat(testZrosts.getTrsptrName()).isEqualTo(DEFAULT_TRSPTR_NAME);
        assertThat(testZrosts.getShipmtEd()).isEqualTo(DEFAULT_SHIPMT_ED);
        assertThat(testZrosts.getGdsStatus()).isEqualTo(DEFAULT_GDS_STATUS);
        assertThat(testZrosts.getGdsDate()).isEqualTo(DEFAULT_GDS_DATE);
        assertThat(testZrosts.getRoSubitem()).isEqualTo(DEFAULT_RO_SUBITEM);
        assertThat(testZrosts.getRoType()).isEqualTo(DEFAULT_RO_TYPE);
        assertThat(testZrosts.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testZrosts.getMovingPrice()).isEqualTo(DEFAULT_MOVING_PRICE);
        assertThat(testZrosts.getPlantId()).isEqualTo(DEFAULT_PLANT_ID);
        assertThat(testZrosts.getPlantName()).isEqualTo(DEFAULT_PLANT_NAME);
        assertThat(testZrosts.getStorageLocp()).isEqualTo(DEFAULT_STORAGE_LOCP);
        assertThat(testZrosts.getDwhId()).isEqualTo(DEFAULT_DWH_ID);
        assertThat(testZrosts.getDwhDesc()).isEqualTo(DEFAULT_DWH_DESC);
        assertThat(testZrosts.getShipParty()).isEqualTo(DEFAULT_SHIP_PARTY);
        assertThat(testZrosts.getTrsptMeans()).isEqualTo(DEFAULT_TRSPT_MEANS);
        assertThat(testZrosts.getProgOfficer()).isEqualTo(DEFAULT_PROG_OFFICER);
        assertThat(testZrosts.getSoItems()).isEqualTo(DEFAULT_SO_ITEMS);
        assertThat(testZrosts.getPoItems()).isEqualTo(DEFAULT_PO_ITEMS);
        assertThat(testZrosts.getTrsptrId()).isEqualTo(DEFAULT_TRSPTR_ID);
        assertThat(testZrosts.getGdsId()).isEqualTo(DEFAULT_GDS_ID);
        assertThat(testZrosts.getGdsItem()).isEqualTo(DEFAULT_GDS_ITEM);
        assertThat(testZrosts.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testZrosts.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testZrosts.getPlanningDate()).isEqualTo(DEFAULT_PLANNING_DATE);
        assertThat(testZrosts.getCheckinDate()).isEqualTo(DEFAULT_CHECKIN_DATE);
        assertThat(testZrosts.getShipmentSdate()).isEqualTo(DEFAULT_SHIPMENT_SDATE);
        assertThat(testZrosts.getLoadingSdate()).isEqualTo(DEFAULT_LOADING_SDATE);
        assertThat(testZrosts.getLoadingEdate()).isEqualTo(DEFAULT_LOADING_EDATE);
        assertThat(testZrosts.getAshipmentSdate()).isEqualTo(DEFAULT_ASHIPMENT_SDATE);
        assertThat(testZrosts.getShipmentCdate()).isEqualTo(DEFAULT_SHIPMENT_CDATE);
        assertThat(testZrosts.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testZrosts.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testZrosts.getSection()).isEqualTo(DEFAULT_SECTION);
        assertThat(testZrosts.getCommodityGroup()).isEqualTo(DEFAULT_COMMODITY_GROUP);
        assertThat(testZrosts.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    void createZrostsWithExistingId() throws Exception {
        // Create the Zrosts with an existing ID
        zrosts.setId(1L);

        int databaseSizeBeforeCreate = zrostsRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllZrostsAsStream() {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        List<Zrosts> zrostsList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Zrosts.class)
            .getResponseBody()
            .filter(zrosts::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(zrostsList).isNotNull();
        assertThat(zrostsList).hasSize(1);
        Zrosts testZrosts = zrostsList.get(0);
        assertThat(testZrosts.getRoId()).isEqualTo(DEFAULT_RO_ID);
        assertThat(testZrosts.getRoItem()).isEqualTo(DEFAULT_RO_ITEM);
        assertThat(testZrosts.getRoDate()).isEqualTo(DEFAULT_RO_DATE);
        assertThat(testZrosts.getRoTdd()).isEqualTo(DEFAULT_RO_TDD);
        assertThat(testZrosts.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testZrosts.getMatDesc()).isEqualTo(DEFAULT_MAT_DESC);
        assertThat(testZrosts.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testZrosts.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testZrosts.getStorageLoc()).isEqualTo(DEFAULT_STORAGE_LOC);
        assertThat(testZrosts.getWhId()).isEqualTo(DEFAULT_WH_ID);
        assertThat(testZrosts.getWhDesc()).isEqualTo(DEFAULT_WH_DESC);
        assertThat(testZrosts.getConsId()).isEqualTo(DEFAULT_CONS_ID);
        assertThat(testZrosts.getConsName()).isEqualTo(DEFAULT_CONS_NAME);
        assertThat(testZrosts.getAuthPerson()).isEqualTo(DEFAULT_AUTH_PERSON);
        assertThat(testZrosts.getSoId()).isEqualTo(DEFAULT_SO_ID);
        assertThat(testZrosts.getPoId()).isEqualTo(DEFAULT_PO_ID);
        assertThat(testZrosts.getDelivery()).isEqualTo(DEFAULT_DELIVERY);
        assertThat(testZrosts.getGrant()).isEqualTo(DEFAULT_GRANT);
        assertThat(testZrosts.getWbs()).isEqualTo(DEFAULT_WBS);
        assertThat(testZrosts.getPickStatus()).isEqualTo(DEFAULT_PICK_STATUS);
        assertThat(testZrosts.getToNumber()).isEqualTo(DEFAULT_TO_NUMBER);
        assertThat(testZrosts.getTrsptStatus()).isEqualTo(DEFAULT_TRSPT_STATUS);
        assertThat(testZrosts.getWaybId()).isEqualTo(DEFAULT_WAYB_ID);
        assertThat(testZrosts.getTrsptrName()).isEqualTo(DEFAULT_TRSPTR_NAME);
        assertThat(testZrosts.getShipmtEd()).isEqualTo(DEFAULT_SHIPMT_ED);
        assertThat(testZrosts.getGdsStatus()).isEqualTo(DEFAULT_GDS_STATUS);
        assertThat(testZrosts.getGdsDate()).isEqualTo(DEFAULT_GDS_DATE);
        assertThat(testZrosts.getRoSubitem()).isEqualTo(DEFAULT_RO_SUBITEM);
        assertThat(testZrosts.getRoType()).isEqualTo(DEFAULT_RO_TYPE);
        assertThat(testZrosts.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testZrosts.getMovingPrice()).isEqualTo(DEFAULT_MOVING_PRICE);
        assertThat(testZrosts.getPlantId()).isEqualTo(DEFAULT_PLANT_ID);
        assertThat(testZrosts.getPlantName()).isEqualTo(DEFAULT_PLANT_NAME);
        assertThat(testZrosts.getStorageLocp()).isEqualTo(DEFAULT_STORAGE_LOCP);
        assertThat(testZrosts.getDwhId()).isEqualTo(DEFAULT_DWH_ID);
        assertThat(testZrosts.getDwhDesc()).isEqualTo(DEFAULT_DWH_DESC);
        assertThat(testZrosts.getShipParty()).isEqualTo(DEFAULT_SHIP_PARTY);
        assertThat(testZrosts.getTrsptMeans()).isEqualTo(DEFAULT_TRSPT_MEANS);
        assertThat(testZrosts.getProgOfficer()).isEqualTo(DEFAULT_PROG_OFFICER);
        assertThat(testZrosts.getSoItems()).isEqualTo(DEFAULT_SO_ITEMS);
        assertThat(testZrosts.getPoItems()).isEqualTo(DEFAULT_PO_ITEMS);
        assertThat(testZrosts.getTrsptrId()).isEqualTo(DEFAULT_TRSPTR_ID);
        assertThat(testZrosts.getGdsId()).isEqualTo(DEFAULT_GDS_ID);
        assertThat(testZrosts.getGdsItem()).isEqualTo(DEFAULT_GDS_ITEM);
        assertThat(testZrosts.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testZrosts.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testZrosts.getPlanningDate()).isEqualTo(DEFAULT_PLANNING_DATE);
        assertThat(testZrosts.getCheckinDate()).isEqualTo(DEFAULT_CHECKIN_DATE);
        assertThat(testZrosts.getShipmentSdate()).isEqualTo(DEFAULT_SHIPMENT_SDATE);
        assertThat(testZrosts.getLoadingSdate()).isEqualTo(DEFAULT_LOADING_SDATE);
        assertThat(testZrosts.getLoadingEdate()).isEqualTo(DEFAULT_LOADING_EDATE);
        assertThat(testZrosts.getAshipmentSdate()).isEqualTo(DEFAULT_ASHIPMENT_SDATE);
        assertThat(testZrosts.getShipmentCdate()).isEqualTo(DEFAULT_SHIPMENT_CDATE);
        assertThat(testZrosts.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testZrosts.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testZrosts.getSection()).isEqualTo(DEFAULT_SECTION);
        assertThat(testZrosts.getCommodityGroup()).isEqualTo(DEFAULT_COMMODITY_GROUP);
        assertThat(testZrosts.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    void getAllZrosts() {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        // Get all the zrostsList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(zrosts.getId().intValue()))
            .jsonPath("$.[*].roId")
            .value(hasItem(DEFAULT_RO_ID))
            .jsonPath("$.[*].roItem")
            .value(hasItem(DEFAULT_RO_ITEM.doubleValue()))
            .jsonPath("$.[*].roDate")
            .value(hasItem(DEFAULT_RO_DATE.toString()))
            .jsonPath("$.[*].roTdd")
            .value(hasItem(DEFAULT_RO_TDD.toString()))
            .jsonPath("$.[*].materialId")
            .value(hasItem(DEFAULT_MATERIAL_ID))
            .jsonPath("$.[*].matDesc")
            .value(hasItem(DEFAULT_MAT_DESC))
            .jsonPath("$.[*].delQty")
            .value(hasItem(DEFAULT_DEL_QTY.doubleValue()))
            .jsonPath("$.[*].value")
            .value(hasItem(DEFAULT_VALUE.doubleValue()))
            .jsonPath("$.[*].storageLoc")
            .value(hasItem(DEFAULT_STORAGE_LOC.doubleValue()))
            .jsonPath("$.[*].whId")
            .value(hasItem(DEFAULT_WH_ID.doubleValue()))
            .jsonPath("$.[*].whDesc")
            .value(hasItem(DEFAULT_WH_DESC))
            .jsonPath("$.[*].consId")
            .value(hasItem(DEFAULT_CONS_ID))
            .jsonPath("$.[*].consName")
            .value(hasItem(DEFAULT_CONS_NAME))
            .jsonPath("$.[*].authPerson")
            .value(hasItem(DEFAULT_AUTH_PERSON))
            .jsonPath("$.[*].soId")
            .value(hasItem(DEFAULT_SO_ID))
            .jsonPath("$.[*].poId")
            .value(hasItem(DEFAULT_PO_ID))
            .jsonPath("$.[*].delivery")
            .value(hasItem(DEFAULT_DELIVERY.doubleValue()))
            .jsonPath("$.[*].grant")
            .value(hasItem(DEFAULT_GRANT))
            .jsonPath("$.[*].wbs")
            .value(hasItem(DEFAULT_WBS))
            .jsonPath("$.[*].pickStatus")
            .value(hasItem(DEFAULT_PICK_STATUS))
            .jsonPath("$.[*].toNumber")
            .value(hasItem(DEFAULT_TO_NUMBER))
            .jsonPath("$.[*].trsptStatus")
            .value(hasItem(DEFAULT_TRSPT_STATUS))
            .jsonPath("$.[*].waybId")
            .value(hasItem(DEFAULT_WAYB_ID))
            .jsonPath("$.[*].trsptrName")
            .value(hasItem(DEFAULT_TRSPTR_NAME))
            .jsonPath("$.[*].shipmtEd")
            .value(hasItem(DEFAULT_SHIPMT_ED.toString()))
            .jsonPath("$.[*].gdsStatus")
            .value(hasItem(DEFAULT_GDS_STATUS))
            .jsonPath("$.[*].gdsDate")
            .value(hasItem(DEFAULT_GDS_DATE.toString()))
            .jsonPath("$.[*].roSubitem")
            .value(hasItem(DEFAULT_RO_SUBITEM.doubleValue()))
            .jsonPath("$.[*].roType")
            .value(hasItem(DEFAULT_RO_TYPE))
            .jsonPath("$.[*].unit")
            .value(hasItem(DEFAULT_UNIT))
            .jsonPath("$.[*].movingPrice")
            .value(hasItem(DEFAULT_MOVING_PRICE.doubleValue()))
            .jsonPath("$.[*].plantId")
            .value(hasItem(DEFAULT_PLANT_ID.doubleValue()))
            .jsonPath("$.[*].plantName")
            .value(hasItem(DEFAULT_PLANT_NAME))
            .jsonPath("$.[*].storageLocp")
            .value(hasItem(DEFAULT_STORAGE_LOCP))
            .jsonPath("$.[*].dwhId")
            .value(hasItem(DEFAULT_DWH_ID))
            .jsonPath("$.[*].dwhDesc")
            .value(hasItem(DEFAULT_DWH_DESC))
            .jsonPath("$.[*].shipParty")
            .value(hasItem(DEFAULT_SHIP_PARTY))
            .jsonPath("$.[*].trsptMeans")
            .value(hasItem(DEFAULT_TRSPT_MEANS))
            .jsonPath("$.[*].progOfficer")
            .value(hasItem(DEFAULT_PROG_OFFICER))
            .jsonPath("$.[*].soItems")
            .value(hasItem(DEFAULT_SO_ITEMS.doubleValue()))
            .jsonPath("$.[*].poItems")
            .value(hasItem(DEFAULT_PO_ITEMS.doubleValue()))
            .jsonPath("$.[*].trsptrId")
            .value(hasItem(DEFAULT_TRSPTR_ID))
            .jsonPath("$.[*].gdsId")
            .value(hasItem(DEFAULT_GDS_ID))
            .jsonPath("$.[*].gdsItem")
            .value(hasItem(DEFAULT_GDS_ITEM.doubleValue()))
            .jsonPath("$.[*].batch")
            .value(hasItem(DEFAULT_BATCH))
            .jsonPath("$.[*].bbDate")
            .value(hasItem(DEFAULT_BB_DATE.toString()))
            .jsonPath("$.[*].planningDate")
            .value(hasItem(DEFAULT_PLANNING_DATE.toString()))
            .jsonPath("$.[*].checkinDate")
            .value(hasItem(DEFAULT_CHECKIN_DATE.toString()))
            .jsonPath("$.[*].shipmentSdate")
            .value(hasItem(DEFAULT_SHIPMENT_SDATE.toString()))
            .jsonPath("$.[*].loadingSdate")
            .value(hasItem(DEFAULT_LOADING_SDATE.toString()))
            .jsonPath("$.[*].loadingEdate")
            .value(hasItem(DEFAULT_LOADING_EDATE.toString()))
            .jsonPath("$.[*].ashipmentSdate")
            .value(hasItem(DEFAULT_ASHIPMENT_SDATE.toString()))
            .jsonPath("$.[*].shipmentCdate")
            .value(hasItem(DEFAULT_SHIPMENT_CDATE.toString()))
            .jsonPath("$.[*].weight")
            .value(hasItem(DEFAULT_WEIGHT.doubleValue()))
            .jsonPath("$.[*].volume")
            .value(hasItem(DEFAULT_VOLUME.doubleValue()))
            .jsonPath("$.[*].section")
            .value(hasItem(DEFAULT_SECTION))
            .jsonPath("$.[*].commodityGroup")
            .value(hasItem(DEFAULT_COMMODITY_GROUP))
            .jsonPath("$.[*].region")
            .value(hasItem(DEFAULT_REGION));
    }

    @Test
    void getZrosts() {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        // Get the zrosts
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, zrosts.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(zrosts.getId().intValue()))
            .jsonPath("$.roId")
            .value(is(DEFAULT_RO_ID))
            .jsonPath("$.roItem")
            .value(is(DEFAULT_RO_ITEM.doubleValue()))
            .jsonPath("$.roDate")
            .value(is(DEFAULT_RO_DATE.toString()))
            .jsonPath("$.roTdd")
            .value(is(DEFAULT_RO_TDD.toString()))
            .jsonPath("$.materialId")
            .value(is(DEFAULT_MATERIAL_ID))
            .jsonPath("$.matDesc")
            .value(is(DEFAULT_MAT_DESC))
            .jsonPath("$.delQty")
            .value(is(DEFAULT_DEL_QTY.doubleValue()))
            .jsonPath("$.value")
            .value(is(DEFAULT_VALUE.doubleValue()))
            .jsonPath("$.storageLoc")
            .value(is(DEFAULT_STORAGE_LOC.doubleValue()))
            .jsonPath("$.whId")
            .value(is(DEFAULT_WH_ID.doubleValue()))
            .jsonPath("$.whDesc")
            .value(is(DEFAULT_WH_DESC))
            .jsonPath("$.consId")
            .value(is(DEFAULT_CONS_ID))
            .jsonPath("$.consName")
            .value(is(DEFAULT_CONS_NAME))
            .jsonPath("$.authPerson")
            .value(is(DEFAULT_AUTH_PERSON))
            .jsonPath("$.soId")
            .value(is(DEFAULT_SO_ID))
            .jsonPath("$.poId")
            .value(is(DEFAULT_PO_ID))
            .jsonPath("$.delivery")
            .value(is(DEFAULT_DELIVERY.doubleValue()))
            .jsonPath("$.grant")
            .value(is(DEFAULT_GRANT))
            .jsonPath("$.wbs")
            .value(is(DEFAULT_WBS))
            .jsonPath("$.pickStatus")
            .value(is(DEFAULT_PICK_STATUS))
            .jsonPath("$.toNumber")
            .value(is(DEFAULT_TO_NUMBER))
            .jsonPath("$.trsptStatus")
            .value(is(DEFAULT_TRSPT_STATUS))
            .jsonPath("$.waybId")
            .value(is(DEFAULT_WAYB_ID))
            .jsonPath("$.trsptrName")
            .value(is(DEFAULT_TRSPTR_NAME))
            .jsonPath("$.shipmtEd")
            .value(is(DEFAULT_SHIPMT_ED.toString()))
            .jsonPath("$.gdsStatus")
            .value(is(DEFAULT_GDS_STATUS))
            .jsonPath("$.gdsDate")
            .value(is(DEFAULT_GDS_DATE.toString()))
            .jsonPath("$.roSubitem")
            .value(is(DEFAULT_RO_SUBITEM.doubleValue()))
            .jsonPath("$.roType")
            .value(is(DEFAULT_RO_TYPE))
            .jsonPath("$.unit")
            .value(is(DEFAULT_UNIT))
            .jsonPath("$.movingPrice")
            .value(is(DEFAULT_MOVING_PRICE.doubleValue()))
            .jsonPath("$.plantId")
            .value(is(DEFAULT_PLANT_ID.doubleValue()))
            .jsonPath("$.plantName")
            .value(is(DEFAULT_PLANT_NAME))
            .jsonPath("$.storageLocp")
            .value(is(DEFAULT_STORAGE_LOCP))
            .jsonPath("$.dwhId")
            .value(is(DEFAULT_DWH_ID))
            .jsonPath("$.dwhDesc")
            .value(is(DEFAULT_DWH_DESC))
            .jsonPath("$.shipParty")
            .value(is(DEFAULT_SHIP_PARTY))
            .jsonPath("$.trsptMeans")
            .value(is(DEFAULT_TRSPT_MEANS))
            .jsonPath("$.progOfficer")
            .value(is(DEFAULT_PROG_OFFICER))
            .jsonPath("$.soItems")
            .value(is(DEFAULT_SO_ITEMS.doubleValue()))
            .jsonPath("$.poItems")
            .value(is(DEFAULT_PO_ITEMS.doubleValue()))
            .jsonPath("$.trsptrId")
            .value(is(DEFAULT_TRSPTR_ID))
            .jsonPath("$.gdsId")
            .value(is(DEFAULT_GDS_ID))
            .jsonPath("$.gdsItem")
            .value(is(DEFAULT_GDS_ITEM.doubleValue()))
            .jsonPath("$.batch")
            .value(is(DEFAULT_BATCH))
            .jsonPath("$.bbDate")
            .value(is(DEFAULT_BB_DATE.toString()))
            .jsonPath("$.planningDate")
            .value(is(DEFAULT_PLANNING_DATE.toString()))
            .jsonPath("$.checkinDate")
            .value(is(DEFAULT_CHECKIN_DATE.toString()))
            .jsonPath("$.shipmentSdate")
            .value(is(DEFAULT_SHIPMENT_SDATE.toString()))
            .jsonPath("$.loadingSdate")
            .value(is(DEFAULT_LOADING_SDATE.toString()))
            .jsonPath("$.loadingEdate")
            .value(is(DEFAULT_LOADING_EDATE.toString()))
            .jsonPath("$.ashipmentSdate")
            .value(is(DEFAULT_ASHIPMENT_SDATE.toString()))
            .jsonPath("$.shipmentCdate")
            .value(is(DEFAULT_SHIPMENT_CDATE.toString()))
            .jsonPath("$.weight")
            .value(is(DEFAULT_WEIGHT.doubleValue()))
            .jsonPath("$.volume")
            .value(is(DEFAULT_VOLUME.doubleValue()))
            .jsonPath("$.section")
            .value(is(DEFAULT_SECTION))
            .jsonPath("$.commodityGroup")
            .value(is(DEFAULT_COMMODITY_GROUP))
            .jsonPath("$.region")
            .value(is(DEFAULT_REGION));
    }

    @Test
    void getNonExistingZrosts() {
        // Get the zrosts
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingZrosts() throws Exception {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();

        // Update the zrosts
        Zrosts updatedZrosts = zrostsRepository.findById(zrosts.getId()).block();
        updatedZrosts
            .roId(UPDATED_RO_ID)
            .roItem(UPDATED_RO_ITEM)
            .roDate(UPDATED_RO_DATE)
            .roTdd(UPDATED_RO_TDD)
            .materialId(UPDATED_MATERIAL_ID)
            .matDesc(UPDATED_MAT_DESC)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .storageLoc(UPDATED_STORAGE_LOC)
            .whId(UPDATED_WH_ID)
            .whDesc(UPDATED_WH_DESC)
            .consId(UPDATED_CONS_ID)
            .consName(UPDATED_CONS_NAME)
            .authPerson(UPDATED_AUTH_PERSON)
            .soId(UPDATED_SO_ID)
            .poId(UPDATED_PO_ID)
            .delivery(UPDATED_DELIVERY)
            .grant(UPDATED_GRANT)
            .wbs(UPDATED_WBS)
            .pickStatus(UPDATED_PICK_STATUS)
            .toNumber(UPDATED_TO_NUMBER)
            .trsptStatus(UPDATED_TRSPT_STATUS)
            .waybId(UPDATED_WAYB_ID)
            .trsptrName(UPDATED_TRSPTR_NAME)
            .shipmtEd(UPDATED_SHIPMT_ED)
            .gdsStatus(UPDATED_GDS_STATUS)
            .gdsDate(UPDATED_GDS_DATE)
            .roSubitem(UPDATED_RO_SUBITEM)
            .roType(UPDATED_RO_TYPE)
            .unit(UPDATED_UNIT)
            .movingPrice(UPDATED_MOVING_PRICE)
            .plantId(UPDATED_PLANT_ID)
            .plantName(UPDATED_PLANT_NAME)
            .storageLocp(UPDATED_STORAGE_LOCP)
            .dwhId(UPDATED_DWH_ID)
            .dwhDesc(UPDATED_DWH_DESC)
            .shipParty(UPDATED_SHIP_PARTY)
            .trsptMeans(UPDATED_TRSPT_MEANS)
            .progOfficer(UPDATED_PROG_OFFICER)
            .soItems(UPDATED_SO_ITEMS)
            .poItems(UPDATED_PO_ITEMS)
            .trsptrId(UPDATED_TRSPTR_ID)
            .gdsId(UPDATED_GDS_ID)
            .gdsItem(UPDATED_GDS_ITEM)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .planningDate(UPDATED_PLANNING_DATE)
            .checkinDate(UPDATED_CHECKIN_DATE)
            .shipmentSdate(UPDATED_SHIPMENT_SDATE)
            .loadingSdate(UPDATED_LOADING_SDATE)
            .loadingEdate(UPDATED_LOADING_EDATE)
            .ashipmentSdate(UPDATED_ASHIPMENT_SDATE)
            .shipmentCdate(UPDATED_SHIPMENT_CDATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .section(UPDATED_SECTION)
            .commodityGroup(UPDATED_COMMODITY_GROUP)
            .region(UPDATED_REGION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedZrosts.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedZrosts))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
        Zrosts testZrosts = zrostsList.get(zrostsList.size() - 1);
        assertThat(testZrosts.getRoId()).isEqualTo(UPDATED_RO_ID);
        assertThat(testZrosts.getRoItem()).isEqualTo(UPDATED_RO_ITEM);
        assertThat(testZrosts.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testZrosts.getRoTdd()).isEqualTo(UPDATED_RO_TDD);
        assertThat(testZrosts.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testZrosts.getMatDesc()).isEqualTo(UPDATED_MAT_DESC);
        assertThat(testZrosts.getDelQty()).isEqualTo(UPDATED_DEL_QTY);
        assertThat(testZrosts.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testZrosts.getStorageLoc()).isEqualTo(UPDATED_STORAGE_LOC);
        assertThat(testZrosts.getWhId()).isEqualTo(UPDATED_WH_ID);
        assertThat(testZrosts.getWhDesc()).isEqualTo(UPDATED_WH_DESC);
        assertThat(testZrosts.getConsId()).isEqualTo(UPDATED_CONS_ID);
        assertThat(testZrosts.getConsName()).isEqualTo(UPDATED_CONS_NAME);
        assertThat(testZrosts.getAuthPerson()).isEqualTo(UPDATED_AUTH_PERSON);
        assertThat(testZrosts.getSoId()).isEqualTo(UPDATED_SO_ID);
        assertThat(testZrosts.getPoId()).isEqualTo(UPDATED_PO_ID);
        assertThat(testZrosts.getDelivery()).isEqualTo(UPDATED_DELIVERY);
        assertThat(testZrosts.getGrant()).isEqualTo(UPDATED_GRANT);
        assertThat(testZrosts.getWbs()).isEqualTo(UPDATED_WBS);
        assertThat(testZrosts.getPickStatus()).isEqualTo(UPDATED_PICK_STATUS);
        assertThat(testZrosts.getToNumber()).isEqualTo(UPDATED_TO_NUMBER);
        assertThat(testZrosts.getTrsptStatus()).isEqualTo(UPDATED_TRSPT_STATUS);
        assertThat(testZrosts.getWaybId()).isEqualTo(UPDATED_WAYB_ID);
        assertThat(testZrosts.getTrsptrName()).isEqualTo(UPDATED_TRSPTR_NAME);
        assertThat(testZrosts.getShipmtEd()).isEqualTo(UPDATED_SHIPMT_ED);
        assertThat(testZrosts.getGdsStatus()).isEqualTo(UPDATED_GDS_STATUS);
        assertThat(testZrosts.getGdsDate()).isEqualTo(UPDATED_GDS_DATE);
        assertThat(testZrosts.getRoSubitem()).isEqualTo(UPDATED_RO_SUBITEM);
        assertThat(testZrosts.getRoType()).isEqualTo(UPDATED_RO_TYPE);
        assertThat(testZrosts.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testZrosts.getMovingPrice()).isEqualTo(UPDATED_MOVING_PRICE);
        assertThat(testZrosts.getPlantId()).isEqualTo(UPDATED_PLANT_ID);
        assertThat(testZrosts.getPlantName()).isEqualTo(UPDATED_PLANT_NAME);
        assertThat(testZrosts.getStorageLocp()).isEqualTo(UPDATED_STORAGE_LOCP);
        assertThat(testZrosts.getDwhId()).isEqualTo(UPDATED_DWH_ID);
        assertThat(testZrosts.getDwhDesc()).isEqualTo(UPDATED_DWH_DESC);
        assertThat(testZrosts.getShipParty()).isEqualTo(UPDATED_SHIP_PARTY);
        assertThat(testZrosts.getTrsptMeans()).isEqualTo(UPDATED_TRSPT_MEANS);
        assertThat(testZrosts.getProgOfficer()).isEqualTo(UPDATED_PROG_OFFICER);
        assertThat(testZrosts.getSoItems()).isEqualTo(UPDATED_SO_ITEMS);
        assertThat(testZrosts.getPoItems()).isEqualTo(UPDATED_PO_ITEMS);
        assertThat(testZrosts.getTrsptrId()).isEqualTo(UPDATED_TRSPTR_ID);
        assertThat(testZrosts.getGdsId()).isEqualTo(UPDATED_GDS_ID);
        assertThat(testZrosts.getGdsItem()).isEqualTo(UPDATED_GDS_ITEM);
        assertThat(testZrosts.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testZrosts.getBbDate()).isEqualTo(UPDATED_BB_DATE);
        assertThat(testZrosts.getPlanningDate()).isEqualTo(UPDATED_PLANNING_DATE);
        assertThat(testZrosts.getCheckinDate()).isEqualTo(UPDATED_CHECKIN_DATE);
        assertThat(testZrosts.getShipmentSdate()).isEqualTo(UPDATED_SHIPMENT_SDATE);
        assertThat(testZrosts.getLoadingSdate()).isEqualTo(UPDATED_LOADING_SDATE);
        assertThat(testZrosts.getLoadingEdate()).isEqualTo(UPDATED_LOADING_EDATE);
        assertThat(testZrosts.getAshipmentSdate()).isEqualTo(UPDATED_ASHIPMENT_SDATE);
        assertThat(testZrosts.getShipmentCdate()).isEqualTo(UPDATED_SHIPMENT_CDATE);
        assertThat(testZrosts.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testZrosts.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testZrosts.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testZrosts.getCommodityGroup()).isEqualTo(UPDATED_COMMODITY_GROUP);
        assertThat(testZrosts.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    void putNonExistingZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();
        zrosts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, zrosts.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateZrostsWithPatch() throws Exception {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();

        // Update the zrosts using partial update
        Zrosts partialUpdatedZrosts = new Zrosts();
        partialUpdatedZrosts.setId(zrosts.getId());

        partialUpdatedZrosts
            .roItem(UPDATED_RO_ITEM)
            .roDate(UPDATED_RO_DATE)
            .materialId(UPDATED_MATERIAL_ID)
            .storageLoc(UPDATED_STORAGE_LOC)
            .whId(UPDATED_WH_ID)
            .whDesc(UPDATED_WH_DESC)
            .poId(UPDATED_PO_ID)
            .wbs(UPDATED_WBS)
            .pickStatus(UPDATED_PICK_STATUS)
            .toNumber(UPDATED_TO_NUMBER)
            .trsptStatus(UPDATED_TRSPT_STATUS)
            .trsptrName(UPDATED_TRSPTR_NAME)
            .shipmtEd(UPDATED_SHIPMT_ED)
            .gdsDate(UPDATED_GDS_DATE)
            .roSubitem(UPDATED_RO_SUBITEM)
            .unit(UPDATED_UNIT)
            .movingPrice(UPDATED_MOVING_PRICE)
            .plantId(UPDATED_PLANT_ID)
            .plantName(UPDATED_PLANT_NAME)
            .dwhId(UPDATED_DWH_ID)
            .dwhDesc(UPDATED_DWH_DESC)
            .shipParty(UPDATED_SHIP_PARTY)
            .trsptMeans(UPDATED_TRSPT_MEANS)
            .progOfficer(UPDATED_PROG_OFFICER)
            .soItems(UPDATED_SO_ITEMS)
            .poItems(UPDATED_PO_ITEMS)
            .trsptrId(UPDATED_TRSPTR_ID)
            .gdsId(UPDATED_GDS_ID)
            .batch(UPDATED_BATCH)
            .planningDate(UPDATED_PLANNING_DATE)
            .checkinDate(UPDATED_CHECKIN_DATE)
            .loadingEdate(UPDATED_LOADING_EDATE)
            .ashipmentSdate(UPDATED_ASHIPMENT_SDATE)
            .volume(UPDATED_VOLUME)
            .section(UPDATED_SECTION)
            .commodityGroup(UPDATED_COMMODITY_GROUP)
            .region(UPDATED_REGION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedZrosts.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedZrosts))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
        Zrosts testZrosts = zrostsList.get(zrostsList.size() - 1);
        assertThat(testZrosts.getRoId()).isEqualTo(DEFAULT_RO_ID);
        assertThat(testZrosts.getRoItem()).isEqualTo(UPDATED_RO_ITEM);
        assertThat(testZrosts.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testZrosts.getRoTdd()).isEqualTo(DEFAULT_RO_TDD);
        assertThat(testZrosts.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testZrosts.getMatDesc()).isEqualTo(DEFAULT_MAT_DESC);
        assertThat(testZrosts.getDelQty()).isEqualTo(DEFAULT_DEL_QTY);
        assertThat(testZrosts.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testZrosts.getStorageLoc()).isEqualTo(UPDATED_STORAGE_LOC);
        assertThat(testZrosts.getWhId()).isEqualTo(UPDATED_WH_ID);
        assertThat(testZrosts.getWhDesc()).isEqualTo(UPDATED_WH_DESC);
        assertThat(testZrosts.getConsId()).isEqualTo(DEFAULT_CONS_ID);
        assertThat(testZrosts.getConsName()).isEqualTo(DEFAULT_CONS_NAME);
        assertThat(testZrosts.getAuthPerson()).isEqualTo(DEFAULT_AUTH_PERSON);
        assertThat(testZrosts.getSoId()).isEqualTo(DEFAULT_SO_ID);
        assertThat(testZrosts.getPoId()).isEqualTo(UPDATED_PO_ID);
        assertThat(testZrosts.getDelivery()).isEqualTo(DEFAULT_DELIVERY);
        assertThat(testZrosts.getGrant()).isEqualTo(DEFAULT_GRANT);
        assertThat(testZrosts.getWbs()).isEqualTo(UPDATED_WBS);
        assertThat(testZrosts.getPickStatus()).isEqualTo(UPDATED_PICK_STATUS);
        assertThat(testZrosts.getToNumber()).isEqualTo(UPDATED_TO_NUMBER);
        assertThat(testZrosts.getTrsptStatus()).isEqualTo(UPDATED_TRSPT_STATUS);
        assertThat(testZrosts.getWaybId()).isEqualTo(DEFAULT_WAYB_ID);
        assertThat(testZrosts.getTrsptrName()).isEqualTo(UPDATED_TRSPTR_NAME);
        assertThat(testZrosts.getShipmtEd()).isEqualTo(UPDATED_SHIPMT_ED);
        assertThat(testZrosts.getGdsStatus()).isEqualTo(DEFAULT_GDS_STATUS);
        assertThat(testZrosts.getGdsDate()).isEqualTo(UPDATED_GDS_DATE);
        assertThat(testZrosts.getRoSubitem()).isEqualTo(UPDATED_RO_SUBITEM);
        assertThat(testZrosts.getRoType()).isEqualTo(DEFAULT_RO_TYPE);
        assertThat(testZrosts.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testZrosts.getMovingPrice()).isEqualTo(UPDATED_MOVING_PRICE);
        assertThat(testZrosts.getPlantId()).isEqualTo(UPDATED_PLANT_ID);
        assertThat(testZrosts.getPlantName()).isEqualTo(UPDATED_PLANT_NAME);
        assertThat(testZrosts.getStorageLocp()).isEqualTo(DEFAULT_STORAGE_LOCP);
        assertThat(testZrosts.getDwhId()).isEqualTo(UPDATED_DWH_ID);
        assertThat(testZrosts.getDwhDesc()).isEqualTo(UPDATED_DWH_DESC);
        assertThat(testZrosts.getShipParty()).isEqualTo(UPDATED_SHIP_PARTY);
        assertThat(testZrosts.getTrsptMeans()).isEqualTo(UPDATED_TRSPT_MEANS);
        assertThat(testZrosts.getProgOfficer()).isEqualTo(UPDATED_PROG_OFFICER);
        assertThat(testZrosts.getSoItems()).isEqualTo(UPDATED_SO_ITEMS);
        assertThat(testZrosts.getPoItems()).isEqualTo(UPDATED_PO_ITEMS);
        assertThat(testZrosts.getTrsptrId()).isEqualTo(UPDATED_TRSPTR_ID);
        assertThat(testZrosts.getGdsId()).isEqualTo(UPDATED_GDS_ID);
        assertThat(testZrosts.getGdsItem()).isEqualTo(DEFAULT_GDS_ITEM);
        assertThat(testZrosts.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testZrosts.getBbDate()).isEqualTo(DEFAULT_BB_DATE);
        assertThat(testZrosts.getPlanningDate()).isEqualTo(UPDATED_PLANNING_DATE);
        assertThat(testZrosts.getCheckinDate()).isEqualTo(UPDATED_CHECKIN_DATE);
        assertThat(testZrosts.getShipmentSdate()).isEqualTo(DEFAULT_SHIPMENT_SDATE);
        assertThat(testZrosts.getLoadingSdate()).isEqualTo(DEFAULT_LOADING_SDATE);
        assertThat(testZrosts.getLoadingEdate()).isEqualTo(UPDATED_LOADING_EDATE);
        assertThat(testZrosts.getAshipmentSdate()).isEqualTo(UPDATED_ASHIPMENT_SDATE);
        assertThat(testZrosts.getShipmentCdate()).isEqualTo(DEFAULT_SHIPMENT_CDATE);
        assertThat(testZrosts.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testZrosts.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testZrosts.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testZrosts.getCommodityGroup()).isEqualTo(UPDATED_COMMODITY_GROUP);
        assertThat(testZrosts.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    void fullUpdateZrostsWithPatch() throws Exception {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();

        // Update the zrosts using partial update
        Zrosts partialUpdatedZrosts = new Zrosts();
        partialUpdatedZrosts.setId(zrosts.getId());

        partialUpdatedZrosts
            .roId(UPDATED_RO_ID)
            .roItem(UPDATED_RO_ITEM)
            .roDate(UPDATED_RO_DATE)
            .roTdd(UPDATED_RO_TDD)
            .materialId(UPDATED_MATERIAL_ID)
            .matDesc(UPDATED_MAT_DESC)
            .delQty(UPDATED_DEL_QTY)
            .value(UPDATED_VALUE)
            .storageLoc(UPDATED_STORAGE_LOC)
            .whId(UPDATED_WH_ID)
            .whDesc(UPDATED_WH_DESC)
            .consId(UPDATED_CONS_ID)
            .consName(UPDATED_CONS_NAME)
            .authPerson(UPDATED_AUTH_PERSON)
            .soId(UPDATED_SO_ID)
            .poId(UPDATED_PO_ID)
            .delivery(UPDATED_DELIVERY)
            .grant(UPDATED_GRANT)
            .wbs(UPDATED_WBS)
            .pickStatus(UPDATED_PICK_STATUS)
            .toNumber(UPDATED_TO_NUMBER)
            .trsptStatus(UPDATED_TRSPT_STATUS)
            .waybId(UPDATED_WAYB_ID)
            .trsptrName(UPDATED_TRSPTR_NAME)
            .shipmtEd(UPDATED_SHIPMT_ED)
            .gdsStatus(UPDATED_GDS_STATUS)
            .gdsDate(UPDATED_GDS_DATE)
            .roSubitem(UPDATED_RO_SUBITEM)
            .roType(UPDATED_RO_TYPE)
            .unit(UPDATED_UNIT)
            .movingPrice(UPDATED_MOVING_PRICE)
            .plantId(UPDATED_PLANT_ID)
            .plantName(UPDATED_PLANT_NAME)
            .storageLocp(UPDATED_STORAGE_LOCP)
            .dwhId(UPDATED_DWH_ID)
            .dwhDesc(UPDATED_DWH_DESC)
            .shipParty(UPDATED_SHIP_PARTY)
            .trsptMeans(UPDATED_TRSPT_MEANS)
            .progOfficer(UPDATED_PROG_OFFICER)
            .soItems(UPDATED_SO_ITEMS)
            .poItems(UPDATED_PO_ITEMS)
            .trsptrId(UPDATED_TRSPTR_ID)
            .gdsId(UPDATED_GDS_ID)
            .gdsItem(UPDATED_GDS_ITEM)
            .batch(UPDATED_BATCH)
            .bbDate(UPDATED_BB_DATE)
            .planningDate(UPDATED_PLANNING_DATE)
            .checkinDate(UPDATED_CHECKIN_DATE)
            .shipmentSdate(UPDATED_SHIPMENT_SDATE)
            .loadingSdate(UPDATED_LOADING_SDATE)
            .loadingEdate(UPDATED_LOADING_EDATE)
            .ashipmentSdate(UPDATED_ASHIPMENT_SDATE)
            .shipmentCdate(UPDATED_SHIPMENT_CDATE)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .section(UPDATED_SECTION)
            .commodityGroup(UPDATED_COMMODITY_GROUP)
            .region(UPDATED_REGION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedZrosts.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedZrosts))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
        Zrosts testZrosts = zrostsList.get(zrostsList.size() - 1);
        assertThat(testZrosts.getRoId()).isEqualTo(UPDATED_RO_ID);
        assertThat(testZrosts.getRoItem()).isEqualTo(UPDATED_RO_ITEM);
        assertThat(testZrosts.getRoDate()).isEqualTo(UPDATED_RO_DATE);
        assertThat(testZrosts.getRoTdd()).isEqualTo(UPDATED_RO_TDD);
        assertThat(testZrosts.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testZrosts.getMatDesc()).isEqualTo(UPDATED_MAT_DESC);
        assertThat(testZrosts.getDelQty()).isEqualTo(UPDATED_DEL_QTY);
        assertThat(testZrosts.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testZrosts.getStorageLoc()).isEqualTo(UPDATED_STORAGE_LOC);
        assertThat(testZrosts.getWhId()).isEqualTo(UPDATED_WH_ID);
        assertThat(testZrosts.getWhDesc()).isEqualTo(UPDATED_WH_DESC);
        assertThat(testZrosts.getConsId()).isEqualTo(UPDATED_CONS_ID);
        assertThat(testZrosts.getConsName()).isEqualTo(UPDATED_CONS_NAME);
        assertThat(testZrosts.getAuthPerson()).isEqualTo(UPDATED_AUTH_PERSON);
        assertThat(testZrosts.getSoId()).isEqualTo(UPDATED_SO_ID);
        assertThat(testZrosts.getPoId()).isEqualTo(UPDATED_PO_ID);
        assertThat(testZrosts.getDelivery()).isEqualTo(UPDATED_DELIVERY);
        assertThat(testZrosts.getGrant()).isEqualTo(UPDATED_GRANT);
        assertThat(testZrosts.getWbs()).isEqualTo(UPDATED_WBS);
        assertThat(testZrosts.getPickStatus()).isEqualTo(UPDATED_PICK_STATUS);
        assertThat(testZrosts.getToNumber()).isEqualTo(UPDATED_TO_NUMBER);
        assertThat(testZrosts.getTrsptStatus()).isEqualTo(UPDATED_TRSPT_STATUS);
        assertThat(testZrosts.getWaybId()).isEqualTo(UPDATED_WAYB_ID);
        assertThat(testZrosts.getTrsptrName()).isEqualTo(UPDATED_TRSPTR_NAME);
        assertThat(testZrosts.getShipmtEd()).isEqualTo(UPDATED_SHIPMT_ED);
        assertThat(testZrosts.getGdsStatus()).isEqualTo(UPDATED_GDS_STATUS);
        assertThat(testZrosts.getGdsDate()).isEqualTo(UPDATED_GDS_DATE);
        assertThat(testZrosts.getRoSubitem()).isEqualTo(UPDATED_RO_SUBITEM);
        assertThat(testZrosts.getRoType()).isEqualTo(UPDATED_RO_TYPE);
        assertThat(testZrosts.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testZrosts.getMovingPrice()).isEqualTo(UPDATED_MOVING_PRICE);
        assertThat(testZrosts.getPlantId()).isEqualTo(UPDATED_PLANT_ID);
        assertThat(testZrosts.getPlantName()).isEqualTo(UPDATED_PLANT_NAME);
        assertThat(testZrosts.getStorageLocp()).isEqualTo(UPDATED_STORAGE_LOCP);
        assertThat(testZrosts.getDwhId()).isEqualTo(UPDATED_DWH_ID);
        assertThat(testZrosts.getDwhDesc()).isEqualTo(UPDATED_DWH_DESC);
        assertThat(testZrosts.getShipParty()).isEqualTo(UPDATED_SHIP_PARTY);
        assertThat(testZrosts.getTrsptMeans()).isEqualTo(UPDATED_TRSPT_MEANS);
        assertThat(testZrosts.getProgOfficer()).isEqualTo(UPDATED_PROG_OFFICER);
        assertThat(testZrosts.getSoItems()).isEqualTo(UPDATED_SO_ITEMS);
        assertThat(testZrosts.getPoItems()).isEqualTo(UPDATED_PO_ITEMS);
        assertThat(testZrosts.getTrsptrId()).isEqualTo(UPDATED_TRSPTR_ID);
        assertThat(testZrosts.getGdsId()).isEqualTo(UPDATED_GDS_ID);
        assertThat(testZrosts.getGdsItem()).isEqualTo(UPDATED_GDS_ITEM);
        assertThat(testZrosts.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testZrosts.getBbDate()).isEqualTo(UPDATED_BB_DATE);
        assertThat(testZrosts.getPlanningDate()).isEqualTo(UPDATED_PLANNING_DATE);
        assertThat(testZrosts.getCheckinDate()).isEqualTo(UPDATED_CHECKIN_DATE);
        assertThat(testZrosts.getShipmentSdate()).isEqualTo(UPDATED_SHIPMENT_SDATE);
        assertThat(testZrosts.getLoadingSdate()).isEqualTo(UPDATED_LOADING_SDATE);
        assertThat(testZrosts.getLoadingEdate()).isEqualTo(UPDATED_LOADING_EDATE);
        assertThat(testZrosts.getAshipmentSdate()).isEqualTo(UPDATED_ASHIPMENT_SDATE);
        assertThat(testZrosts.getShipmentCdate()).isEqualTo(UPDATED_SHIPMENT_CDATE);
        assertThat(testZrosts.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testZrosts.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testZrosts.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testZrosts.getCommodityGroup()).isEqualTo(UPDATED_COMMODITY_GROUP);
        assertThat(testZrosts.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    void patchNonExistingZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();
        zrosts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, zrosts.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().collectList().block().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(zrosts))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteZrosts() {
        // Initialize the database
        zrostsRepository.save(zrosts).block();

        int databaseSizeBeforeDelete = zrostsRepository.findAll().collectList().block().size();

        // Delete the zrosts
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, zrosts.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Zrosts> zrostsList = zrostsRepository.findAll().collectList().block();
        assertThat(zrostsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
