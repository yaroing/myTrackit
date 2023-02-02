package com.yarolab.mytrackit.coreservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yarolab.mytrackit.coreservice.IntegrationTest;
import com.yarolab.mytrackit.coreservice.domain.Zrosts;
import com.yarolab.mytrackit.coreservice.repository.ZrostsRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZrostsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restZrostsMockMvc;

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

    @BeforeEach
    public void initTest() {
        zrosts = createEntity(em);
    }

    @Test
    @Transactional
    void createZrosts() throws Exception {
        int databaseSizeBeforeCreate = zrostsRepository.findAll().size();
        // Create the Zrosts
        restZrostsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zrosts)))
            .andExpect(status().isCreated());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
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
    @Transactional
    void createZrostsWithExistingId() throws Exception {
        // Create the Zrosts with an existing ID
        zrosts.setId(1L);

        int databaseSizeBeforeCreate = zrostsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZrostsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zrosts)))
            .andExpect(status().isBadRequest());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZrosts() throws Exception {
        // Initialize the database
        zrostsRepository.saveAndFlush(zrosts);

        // Get all the zrostsList
        restZrostsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zrosts.getId().intValue())))
            .andExpect(jsonPath("$.[*].roId").value(hasItem(DEFAULT_RO_ID)))
            .andExpect(jsonPath("$.[*].roItem").value(hasItem(DEFAULT_RO_ITEM.doubleValue())))
            .andExpect(jsonPath("$.[*].roDate").value(hasItem(DEFAULT_RO_DATE.toString())))
            .andExpect(jsonPath("$.[*].roTdd").value(hasItem(DEFAULT_RO_TDD.toString())))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].matDesc").value(hasItem(DEFAULT_MAT_DESC)))
            .andExpect(jsonPath("$.[*].delQty").value(hasItem(DEFAULT_DEL_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].storageLoc").value(hasItem(DEFAULT_STORAGE_LOC.doubleValue())))
            .andExpect(jsonPath("$.[*].whId").value(hasItem(DEFAULT_WH_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].whDesc").value(hasItem(DEFAULT_WH_DESC)))
            .andExpect(jsonPath("$.[*].consId").value(hasItem(DEFAULT_CONS_ID)))
            .andExpect(jsonPath("$.[*].consName").value(hasItem(DEFAULT_CONS_NAME)))
            .andExpect(jsonPath("$.[*].authPerson").value(hasItem(DEFAULT_AUTH_PERSON)))
            .andExpect(jsonPath("$.[*].soId").value(hasItem(DEFAULT_SO_ID)))
            .andExpect(jsonPath("$.[*].poId").value(hasItem(DEFAULT_PO_ID)))
            .andExpect(jsonPath("$.[*].delivery").value(hasItem(DEFAULT_DELIVERY.doubleValue())))
            .andExpect(jsonPath("$.[*].grant").value(hasItem(DEFAULT_GRANT)))
            .andExpect(jsonPath("$.[*].wbs").value(hasItem(DEFAULT_WBS)))
            .andExpect(jsonPath("$.[*].pickStatus").value(hasItem(DEFAULT_PICK_STATUS)))
            .andExpect(jsonPath("$.[*].toNumber").value(hasItem(DEFAULT_TO_NUMBER)))
            .andExpect(jsonPath("$.[*].trsptStatus").value(hasItem(DEFAULT_TRSPT_STATUS)))
            .andExpect(jsonPath("$.[*].waybId").value(hasItem(DEFAULT_WAYB_ID)))
            .andExpect(jsonPath("$.[*].trsptrName").value(hasItem(DEFAULT_TRSPTR_NAME)))
            .andExpect(jsonPath("$.[*].shipmtEd").value(hasItem(DEFAULT_SHIPMT_ED.toString())))
            .andExpect(jsonPath("$.[*].gdsStatus").value(hasItem(DEFAULT_GDS_STATUS)))
            .andExpect(jsonPath("$.[*].gdsDate").value(hasItem(DEFAULT_GDS_DATE.toString())))
            .andExpect(jsonPath("$.[*].roSubitem").value(hasItem(DEFAULT_RO_SUBITEM.doubleValue())))
            .andExpect(jsonPath("$.[*].roType").value(hasItem(DEFAULT_RO_TYPE)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].movingPrice").value(hasItem(DEFAULT_MOVING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].plantId").value(hasItem(DEFAULT_PLANT_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].plantName").value(hasItem(DEFAULT_PLANT_NAME)))
            .andExpect(jsonPath("$.[*].storageLocp").value(hasItem(DEFAULT_STORAGE_LOCP)))
            .andExpect(jsonPath("$.[*].dwhId").value(hasItem(DEFAULT_DWH_ID)))
            .andExpect(jsonPath("$.[*].dwhDesc").value(hasItem(DEFAULT_DWH_DESC)))
            .andExpect(jsonPath("$.[*].shipParty").value(hasItem(DEFAULT_SHIP_PARTY)))
            .andExpect(jsonPath("$.[*].trsptMeans").value(hasItem(DEFAULT_TRSPT_MEANS)))
            .andExpect(jsonPath("$.[*].progOfficer").value(hasItem(DEFAULT_PROG_OFFICER)))
            .andExpect(jsonPath("$.[*].soItems").value(hasItem(DEFAULT_SO_ITEMS.doubleValue())))
            .andExpect(jsonPath("$.[*].poItems").value(hasItem(DEFAULT_PO_ITEMS.doubleValue())))
            .andExpect(jsonPath("$.[*].trsptrId").value(hasItem(DEFAULT_TRSPTR_ID)))
            .andExpect(jsonPath("$.[*].gdsId").value(hasItem(DEFAULT_GDS_ID)))
            .andExpect(jsonPath("$.[*].gdsItem").value(hasItem(DEFAULT_GDS_ITEM.doubleValue())))
            .andExpect(jsonPath("$.[*].batch").value(hasItem(DEFAULT_BATCH)))
            .andExpect(jsonPath("$.[*].bbDate").value(hasItem(DEFAULT_BB_DATE.toString())))
            .andExpect(jsonPath("$.[*].planningDate").value(hasItem(DEFAULT_PLANNING_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkinDate").value(hasItem(DEFAULT_CHECKIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].shipmentSdate").value(hasItem(DEFAULT_SHIPMENT_SDATE.toString())))
            .andExpect(jsonPath("$.[*].loadingSdate").value(hasItem(DEFAULT_LOADING_SDATE.toString())))
            .andExpect(jsonPath("$.[*].loadingEdate").value(hasItem(DEFAULT_LOADING_EDATE.toString())))
            .andExpect(jsonPath("$.[*].ashipmentSdate").value(hasItem(DEFAULT_ASHIPMENT_SDATE.toString())))
            .andExpect(jsonPath("$.[*].shipmentCdate").value(hasItem(DEFAULT_SHIPMENT_CDATE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].section").value(hasItem(DEFAULT_SECTION)))
            .andExpect(jsonPath("$.[*].commodityGroup").value(hasItem(DEFAULT_COMMODITY_GROUP)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)));
    }

    @Test
    @Transactional
    void getZrosts() throws Exception {
        // Initialize the database
        zrostsRepository.saveAndFlush(zrosts);

        // Get the zrosts
        restZrostsMockMvc
            .perform(get(ENTITY_API_URL_ID, zrosts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zrosts.getId().intValue()))
            .andExpect(jsonPath("$.roId").value(DEFAULT_RO_ID))
            .andExpect(jsonPath("$.roItem").value(DEFAULT_RO_ITEM.doubleValue()))
            .andExpect(jsonPath("$.roDate").value(DEFAULT_RO_DATE.toString()))
            .andExpect(jsonPath("$.roTdd").value(DEFAULT_RO_TDD.toString()))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID))
            .andExpect(jsonPath("$.matDesc").value(DEFAULT_MAT_DESC))
            .andExpect(jsonPath("$.delQty").value(DEFAULT_DEL_QTY.doubleValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.storageLoc").value(DEFAULT_STORAGE_LOC.doubleValue()))
            .andExpect(jsonPath("$.whId").value(DEFAULT_WH_ID.doubleValue()))
            .andExpect(jsonPath("$.whDesc").value(DEFAULT_WH_DESC))
            .andExpect(jsonPath("$.consId").value(DEFAULT_CONS_ID))
            .andExpect(jsonPath("$.consName").value(DEFAULT_CONS_NAME))
            .andExpect(jsonPath("$.authPerson").value(DEFAULT_AUTH_PERSON))
            .andExpect(jsonPath("$.soId").value(DEFAULT_SO_ID))
            .andExpect(jsonPath("$.poId").value(DEFAULT_PO_ID))
            .andExpect(jsonPath("$.delivery").value(DEFAULT_DELIVERY.doubleValue()))
            .andExpect(jsonPath("$.grant").value(DEFAULT_GRANT))
            .andExpect(jsonPath("$.wbs").value(DEFAULT_WBS))
            .andExpect(jsonPath("$.pickStatus").value(DEFAULT_PICK_STATUS))
            .andExpect(jsonPath("$.toNumber").value(DEFAULT_TO_NUMBER))
            .andExpect(jsonPath("$.trsptStatus").value(DEFAULT_TRSPT_STATUS))
            .andExpect(jsonPath("$.waybId").value(DEFAULT_WAYB_ID))
            .andExpect(jsonPath("$.trsptrName").value(DEFAULT_TRSPTR_NAME))
            .andExpect(jsonPath("$.shipmtEd").value(DEFAULT_SHIPMT_ED.toString()))
            .andExpect(jsonPath("$.gdsStatus").value(DEFAULT_GDS_STATUS))
            .andExpect(jsonPath("$.gdsDate").value(DEFAULT_GDS_DATE.toString()))
            .andExpect(jsonPath("$.roSubitem").value(DEFAULT_RO_SUBITEM.doubleValue()))
            .andExpect(jsonPath("$.roType").value(DEFAULT_RO_TYPE))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.movingPrice").value(DEFAULT_MOVING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.plantId").value(DEFAULT_PLANT_ID.doubleValue()))
            .andExpect(jsonPath("$.plantName").value(DEFAULT_PLANT_NAME))
            .andExpect(jsonPath("$.storageLocp").value(DEFAULT_STORAGE_LOCP))
            .andExpect(jsonPath("$.dwhId").value(DEFAULT_DWH_ID))
            .andExpect(jsonPath("$.dwhDesc").value(DEFAULT_DWH_DESC))
            .andExpect(jsonPath("$.shipParty").value(DEFAULT_SHIP_PARTY))
            .andExpect(jsonPath("$.trsptMeans").value(DEFAULT_TRSPT_MEANS))
            .andExpect(jsonPath("$.progOfficer").value(DEFAULT_PROG_OFFICER))
            .andExpect(jsonPath("$.soItems").value(DEFAULT_SO_ITEMS.doubleValue()))
            .andExpect(jsonPath("$.poItems").value(DEFAULT_PO_ITEMS.doubleValue()))
            .andExpect(jsonPath("$.trsptrId").value(DEFAULT_TRSPTR_ID))
            .andExpect(jsonPath("$.gdsId").value(DEFAULT_GDS_ID))
            .andExpect(jsonPath("$.gdsItem").value(DEFAULT_GDS_ITEM.doubleValue()))
            .andExpect(jsonPath("$.batch").value(DEFAULT_BATCH))
            .andExpect(jsonPath("$.bbDate").value(DEFAULT_BB_DATE.toString()))
            .andExpect(jsonPath("$.planningDate").value(DEFAULT_PLANNING_DATE.toString()))
            .andExpect(jsonPath("$.checkinDate").value(DEFAULT_CHECKIN_DATE.toString()))
            .andExpect(jsonPath("$.shipmentSdate").value(DEFAULT_SHIPMENT_SDATE.toString()))
            .andExpect(jsonPath("$.loadingSdate").value(DEFAULT_LOADING_SDATE.toString()))
            .andExpect(jsonPath("$.loadingEdate").value(DEFAULT_LOADING_EDATE.toString()))
            .andExpect(jsonPath("$.ashipmentSdate").value(DEFAULT_ASHIPMENT_SDATE.toString()))
            .andExpect(jsonPath("$.shipmentCdate").value(DEFAULT_SHIPMENT_CDATE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.section").value(DEFAULT_SECTION))
            .andExpect(jsonPath("$.commodityGroup").value(DEFAULT_COMMODITY_GROUP))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION));
    }

    @Test
    @Transactional
    void getNonExistingZrosts() throws Exception {
        // Get the zrosts
        restZrostsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZrosts() throws Exception {
        // Initialize the database
        zrostsRepository.saveAndFlush(zrosts);

        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();

        // Update the zrosts
        Zrosts updatedZrosts = zrostsRepository.findById(zrosts.getId()).get();
        // Disconnect from session so that the updates on updatedZrosts are not directly saved in db
        em.detach(updatedZrosts);
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

        restZrostsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZrosts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedZrosts))
            )
            .andExpect(status().isOk());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
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
    @Transactional
    void putNonExistingZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();
        zrosts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZrostsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zrosts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zrosts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZrostsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zrosts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZrostsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zrosts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZrostsWithPatch() throws Exception {
        // Initialize the database
        zrostsRepository.saveAndFlush(zrosts);

        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();

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

        restZrostsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZrosts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZrosts))
            )
            .andExpect(status().isOk());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
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
    @Transactional
    void fullUpdateZrostsWithPatch() throws Exception {
        // Initialize the database
        zrostsRepository.saveAndFlush(zrosts);

        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();

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

        restZrostsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZrosts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZrosts))
            )
            .andExpect(status().isOk());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
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
    @Transactional
    void patchNonExistingZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();
        zrosts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZrostsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zrosts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zrosts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZrostsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zrosts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZrosts() throws Exception {
        int databaseSizeBeforeUpdate = zrostsRepository.findAll().size();
        zrosts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZrostsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(zrosts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zrosts in the database
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZrosts() throws Exception {
        // Initialize the database
        zrostsRepository.saveAndFlush(zrosts);

        int databaseSizeBeforeDelete = zrostsRepository.findAll().size();

        // Delete the zrosts
        restZrostsMockMvc
            .perform(delete(ENTITY_API_URL_ID, zrosts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zrosts> zrostsList = zrostsRepository.findAll();
        assertThat(zrostsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
