package com.yarolab.mytrackit.coreservice.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Zrosts.
 */
@Entity
@Table(name = "zrosts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zrosts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ro_id")
    private Integer roId;

    @Column(name = "ro_item")
    private Double roItem;

    @Column(name = "ro_date")
    private Instant roDate;

    @Column(name = "ro_tdd")
    private Instant roTdd;

    @Column(name = "material_id")
    private String materialId;

    @Column(name = "mat_desc")
    private String matDesc;

    @Column(name = "del_qty")
    private Double delQty;

    @Column(name = "value")
    private Double value;

    @Column(name = "storage_loc")
    private Double storageLoc;

    @Column(name = "wh_id")
    private Double whId;

    @Column(name = "wh_desc")
    private String whDesc;

    @Column(name = "cons_id")
    private String consId;

    @Column(name = "cons_name")
    private String consName;

    @Column(name = "auth_person")
    private String authPerson;

    @Column(name = "so_id")
    private String soId;

    @Column(name = "po_id")
    private String poId;

    @Column(name = "delivery")
    private Double delivery;

    @Column(name = "jhi_grant")
    private String grant;

    @Column(name = "wbs")
    private String wbs;

    @Column(name = "pick_status")
    private String pickStatus;

    @Column(name = "to_number")
    private String toNumber;

    @Column(name = "trspt_status")
    private String trsptStatus;

    @Column(name = "wayb_id")
    private Integer waybId;

    @Column(name = "trsptr_name")
    private String trsptrName;

    @Column(name = "shipmt_ed")
    private Instant shipmtEd;

    @Column(name = "gds_status")
    private String gdsStatus;

    @Column(name = "gds_date")
    private Instant gdsDate;

    @Column(name = "ro_subitem")
    private Double roSubitem;

    @Column(name = "ro_type")
    private String roType;

    @Column(name = "unit")
    private String unit;

    @Column(name = "moving_price")
    private Double movingPrice;

    @Column(name = "plant_id")
    private Double plantId;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "storage_locp")
    private String storageLocp;

    @Column(name = "dwh_id")
    private String dwhId;

    @Column(name = "dwh_desc")
    private String dwhDesc;

    @Column(name = "ship_party")
    private String shipParty;

    @Column(name = "trspt_means")
    private String trsptMeans;

    @Column(name = "prog_officer")
    private String progOfficer;

    @Column(name = "so_items")
    private Double soItems;

    @Column(name = "po_items")
    private Double poItems;

    @Column(name = "trsptr_id")
    private String trsptrId;

    @Column(name = "gds_id")
    private String gdsId;

    @Column(name = "gds_item")
    private Double gdsItem;

    @Column(name = "batch")
    private String batch;

    @Column(name = "bb_date")
    private Instant bbDate;

    @Column(name = "planning_date")
    private Instant planningDate;

    @Column(name = "checkin_date")
    private Instant checkinDate;

    @Column(name = "shipment_sdate")
    private Instant shipmentSdate;

    @Column(name = "loading_sdate")
    private Instant loadingSdate;

    @Column(name = "loading_edate")
    private Instant loadingEdate;

    @Column(name = "ashipment_sdate")
    private Instant ashipmentSdate;

    @Column(name = "shipment_cdate")
    private Instant shipmentCdate;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "section")
    private String section;

    @Column(name = "commodity_group")
    private String commodityGroup;

    @Column(name = "region")
    private String region;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zrosts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoId() {
        return this.roId;
    }

    public Zrosts roId(Integer roId) {
        this.setRoId(roId);
        return this;
    }

    public void setRoId(Integer roId) {
        this.roId = roId;
    }

    public Double getRoItem() {
        return this.roItem;
    }

    public Zrosts roItem(Double roItem) {
        this.setRoItem(roItem);
        return this;
    }

    public void setRoItem(Double roItem) {
        this.roItem = roItem;
    }

    public Instant getRoDate() {
        return this.roDate;
    }

    public Zrosts roDate(Instant roDate) {
        this.setRoDate(roDate);
        return this;
    }

    public void setRoDate(Instant roDate) {
        this.roDate = roDate;
    }

    public Instant getRoTdd() {
        return this.roTdd;
    }

    public Zrosts roTdd(Instant roTdd) {
        this.setRoTdd(roTdd);
        return this;
    }

    public void setRoTdd(Instant roTdd) {
        this.roTdd = roTdd;
    }

    public String getMaterialId() {
        return this.materialId;
    }

    public Zrosts materialId(String materialId) {
        this.setMaterialId(materialId);
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMatDesc() {
        return this.matDesc;
    }

    public Zrosts matDesc(String matDesc) {
        this.setMatDesc(matDesc);
        return this;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc;
    }

    public Double getDelQty() {
        return this.delQty;
    }

    public Zrosts delQty(Double delQty) {
        this.setDelQty(delQty);
        return this;
    }

    public void setDelQty(Double delQty) {
        this.delQty = delQty;
    }

    public Double getValue() {
        return this.value;
    }

    public Zrosts value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getStorageLoc() {
        return this.storageLoc;
    }

    public Zrosts storageLoc(Double storageLoc) {
        this.setStorageLoc(storageLoc);
        return this;
    }

    public void setStorageLoc(Double storageLoc) {
        this.storageLoc = storageLoc;
    }

    public Double getWhId() {
        return this.whId;
    }

    public Zrosts whId(Double whId) {
        this.setWhId(whId);
        return this;
    }

    public void setWhId(Double whId) {
        this.whId = whId;
    }

    public String getWhDesc() {
        return this.whDesc;
    }

    public Zrosts whDesc(String whDesc) {
        this.setWhDesc(whDesc);
        return this;
    }

    public void setWhDesc(String whDesc) {
        this.whDesc = whDesc;
    }

    public String getConsId() {
        return this.consId;
    }

    public Zrosts consId(String consId) {
        this.setConsId(consId);
        return this;
    }

    public void setConsId(String consId) {
        this.consId = consId;
    }

    public String getConsName() {
        return this.consName;
    }

    public Zrosts consName(String consName) {
        this.setConsName(consName);
        return this;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public String getAuthPerson() {
        return this.authPerson;
    }

    public Zrosts authPerson(String authPerson) {
        this.setAuthPerson(authPerson);
        return this;
    }

    public void setAuthPerson(String authPerson) {
        this.authPerson = authPerson;
    }

    public String getSoId() {
        return this.soId;
    }

    public Zrosts soId(String soId) {
        this.setSoId(soId);
        return this;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getPoId() {
        return this.poId;
    }

    public Zrosts poId(String poId) {
        this.setPoId(poId);
        return this;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public Double getDelivery() {
        return this.delivery;
    }

    public Zrosts delivery(Double delivery) {
        this.setDelivery(delivery);
        return this;
    }

    public void setDelivery(Double delivery) {
        this.delivery = delivery;
    }

    public String getGrant() {
        return this.grant;
    }

    public Zrosts grant(String grant) {
        this.setGrant(grant);
        return this;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }

    public String getWbs() {
        return this.wbs;
    }

    public Zrosts wbs(String wbs) {
        this.setWbs(wbs);
        return this;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getPickStatus() {
        return this.pickStatus;
    }

    public Zrosts pickStatus(String pickStatus) {
        this.setPickStatus(pickStatus);
        return this;
    }

    public void setPickStatus(String pickStatus) {
        this.pickStatus = pickStatus;
    }

    public String getToNumber() {
        return this.toNumber;
    }

    public Zrosts toNumber(String toNumber) {
        this.setToNumber(toNumber);
        return this;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getTrsptStatus() {
        return this.trsptStatus;
    }

    public Zrosts trsptStatus(String trsptStatus) {
        this.setTrsptStatus(trsptStatus);
        return this;
    }

    public void setTrsptStatus(String trsptStatus) {
        this.trsptStatus = trsptStatus;
    }

    public Integer getWaybId() {
        return this.waybId;
    }

    public Zrosts waybId(Integer waybId) {
        this.setWaybId(waybId);
        return this;
    }

    public void setWaybId(Integer waybId) {
        this.waybId = waybId;
    }

    public String getTrsptrName() {
        return this.trsptrName;
    }

    public Zrosts trsptrName(String trsptrName) {
        this.setTrsptrName(trsptrName);
        return this;
    }

    public void setTrsptrName(String trsptrName) {
        this.trsptrName = trsptrName;
    }

    public Instant getShipmtEd() {
        return this.shipmtEd;
    }

    public Zrosts shipmtEd(Instant shipmtEd) {
        this.setShipmtEd(shipmtEd);
        return this;
    }

    public void setShipmtEd(Instant shipmtEd) {
        this.shipmtEd = shipmtEd;
    }

    public String getGdsStatus() {
        return this.gdsStatus;
    }

    public Zrosts gdsStatus(String gdsStatus) {
        this.setGdsStatus(gdsStatus);
        return this;
    }

    public void setGdsStatus(String gdsStatus) {
        this.gdsStatus = gdsStatus;
    }

    public Instant getGdsDate() {
        return this.gdsDate;
    }

    public Zrosts gdsDate(Instant gdsDate) {
        this.setGdsDate(gdsDate);
        return this;
    }

    public void setGdsDate(Instant gdsDate) {
        this.gdsDate = gdsDate;
    }

    public Double getRoSubitem() {
        return this.roSubitem;
    }

    public Zrosts roSubitem(Double roSubitem) {
        this.setRoSubitem(roSubitem);
        return this;
    }

    public void setRoSubitem(Double roSubitem) {
        this.roSubitem = roSubitem;
    }

    public String getRoType() {
        return this.roType;
    }

    public Zrosts roType(String roType) {
        this.setRoType(roType);
        return this;
    }

    public void setRoType(String roType) {
        this.roType = roType;
    }

    public String getUnit() {
        return this.unit;
    }

    public Zrosts unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getMovingPrice() {
        return this.movingPrice;
    }

    public Zrosts movingPrice(Double movingPrice) {
        this.setMovingPrice(movingPrice);
        return this;
    }

    public void setMovingPrice(Double movingPrice) {
        this.movingPrice = movingPrice;
    }

    public Double getPlantId() {
        return this.plantId;
    }

    public Zrosts plantId(Double plantId) {
        this.setPlantId(plantId);
        return this;
    }

    public void setPlantId(Double plantId) {
        this.plantId = plantId;
    }

    public String getPlantName() {
        return this.plantName;
    }

    public Zrosts plantName(String plantName) {
        this.setPlantName(plantName);
        return this;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getStorageLocp() {
        return this.storageLocp;
    }

    public Zrosts storageLocp(String storageLocp) {
        this.setStorageLocp(storageLocp);
        return this;
    }

    public void setStorageLocp(String storageLocp) {
        this.storageLocp = storageLocp;
    }

    public String getDwhId() {
        return this.dwhId;
    }

    public Zrosts dwhId(String dwhId) {
        this.setDwhId(dwhId);
        return this;
    }

    public void setDwhId(String dwhId) {
        this.dwhId = dwhId;
    }

    public String getDwhDesc() {
        return this.dwhDesc;
    }

    public Zrosts dwhDesc(String dwhDesc) {
        this.setDwhDesc(dwhDesc);
        return this;
    }

    public void setDwhDesc(String dwhDesc) {
        this.dwhDesc = dwhDesc;
    }

    public String getShipParty() {
        return this.shipParty;
    }

    public Zrosts shipParty(String shipParty) {
        this.setShipParty(shipParty);
        return this;
    }

    public void setShipParty(String shipParty) {
        this.shipParty = shipParty;
    }

    public String getTrsptMeans() {
        return this.trsptMeans;
    }

    public Zrosts trsptMeans(String trsptMeans) {
        this.setTrsptMeans(trsptMeans);
        return this;
    }

    public void setTrsptMeans(String trsptMeans) {
        this.trsptMeans = trsptMeans;
    }

    public String getProgOfficer() {
        return this.progOfficer;
    }

    public Zrosts progOfficer(String progOfficer) {
        this.setProgOfficer(progOfficer);
        return this;
    }

    public void setProgOfficer(String progOfficer) {
        this.progOfficer = progOfficer;
    }

    public Double getSoItems() {
        return this.soItems;
    }

    public Zrosts soItems(Double soItems) {
        this.setSoItems(soItems);
        return this;
    }

    public void setSoItems(Double soItems) {
        this.soItems = soItems;
    }

    public Double getPoItems() {
        return this.poItems;
    }

    public Zrosts poItems(Double poItems) {
        this.setPoItems(poItems);
        return this;
    }

    public void setPoItems(Double poItems) {
        this.poItems = poItems;
    }

    public String getTrsptrId() {
        return this.trsptrId;
    }

    public Zrosts trsptrId(String trsptrId) {
        this.setTrsptrId(trsptrId);
        return this;
    }

    public void setTrsptrId(String trsptrId) {
        this.trsptrId = trsptrId;
    }

    public String getGdsId() {
        return this.gdsId;
    }

    public Zrosts gdsId(String gdsId) {
        this.setGdsId(gdsId);
        return this;
    }

    public void setGdsId(String gdsId) {
        this.gdsId = gdsId;
    }

    public Double getGdsItem() {
        return this.gdsItem;
    }

    public Zrosts gdsItem(Double gdsItem) {
        this.setGdsItem(gdsItem);
        return this;
    }

    public void setGdsItem(Double gdsItem) {
        this.gdsItem = gdsItem;
    }

    public String getBatch() {
        return this.batch;
    }

    public Zrosts batch(String batch) {
        this.setBatch(batch);
        return this;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Instant getBbDate() {
        return this.bbDate;
    }

    public Zrosts bbDate(Instant bbDate) {
        this.setBbDate(bbDate);
        return this;
    }

    public void setBbDate(Instant bbDate) {
        this.bbDate = bbDate;
    }

    public Instant getPlanningDate() {
        return this.planningDate;
    }

    public Zrosts planningDate(Instant planningDate) {
        this.setPlanningDate(planningDate);
        return this;
    }

    public void setPlanningDate(Instant planningDate) {
        this.planningDate = planningDate;
    }

    public Instant getCheckinDate() {
        return this.checkinDate;
    }

    public Zrosts checkinDate(Instant checkinDate) {
        this.setCheckinDate(checkinDate);
        return this;
    }

    public void setCheckinDate(Instant checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Instant getShipmentSdate() {
        return this.shipmentSdate;
    }

    public Zrosts shipmentSdate(Instant shipmentSdate) {
        this.setShipmentSdate(shipmentSdate);
        return this;
    }

    public void setShipmentSdate(Instant shipmentSdate) {
        this.shipmentSdate = shipmentSdate;
    }

    public Instant getLoadingSdate() {
        return this.loadingSdate;
    }

    public Zrosts loadingSdate(Instant loadingSdate) {
        this.setLoadingSdate(loadingSdate);
        return this;
    }

    public void setLoadingSdate(Instant loadingSdate) {
        this.loadingSdate = loadingSdate;
    }

    public Instant getLoadingEdate() {
        return this.loadingEdate;
    }

    public Zrosts loadingEdate(Instant loadingEdate) {
        this.setLoadingEdate(loadingEdate);
        return this;
    }

    public void setLoadingEdate(Instant loadingEdate) {
        this.loadingEdate = loadingEdate;
    }

    public Instant getAshipmentSdate() {
        return this.ashipmentSdate;
    }

    public Zrosts ashipmentSdate(Instant ashipmentSdate) {
        this.setAshipmentSdate(ashipmentSdate);
        return this;
    }

    public void setAshipmentSdate(Instant ashipmentSdate) {
        this.ashipmentSdate = ashipmentSdate;
    }

    public Instant getShipmentCdate() {
        return this.shipmentCdate;
    }

    public Zrosts shipmentCdate(Instant shipmentCdate) {
        this.setShipmentCdate(shipmentCdate);
        return this;
    }

    public void setShipmentCdate(Instant shipmentCdate) {
        this.shipmentCdate = shipmentCdate;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Zrosts weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return this.volume;
    }

    public Zrosts volume(Double volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getSection() {
        return this.section;
    }

    public Zrosts section(String section) {
        this.setSection(section);
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCommodityGroup() {
        return this.commodityGroup;
    }

    public Zrosts commodityGroup(String commodityGroup) {
        this.setCommodityGroup(commodityGroup);
        return this;
    }

    public void setCommodityGroup(String commodityGroup) {
        this.commodityGroup = commodityGroup;
    }

    public String getRegion() {
        return this.region;
    }

    public Zrosts region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zrosts)) {
            return false;
        }
        return id != null && id.equals(((Zrosts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zrosts{" +
            "id=" + getId() +
            ", roId=" + getRoId() +
            ", roItem=" + getRoItem() +
            ", roDate='" + getRoDate() + "'" +
            ", roTdd='" + getRoTdd() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", matDesc='" + getMatDesc() + "'" +
            ", delQty=" + getDelQty() +
            ", value=" + getValue() +
            ", storageLoc=" + getStorageLoc() +
            ", whId=" + getWhId() +
            ", whDesc='" + getWhDesc() + "'" +
            ", consId='" + getConsId() + "'" +
            ", consName='" + getConsName() + "'" +
            ", authPerson='" + getAuthPerson() + "'" +
            ", soId='" + getSoId() + "'" +
            ", poId='" + getPoId() + "'" +
            ", delivery=" + getDelivery() +
            ", grant='" + getGrant() + "'" +
            ", wbs='" + getWbs() + "'" +
            ", pickStatus='" + getPickStatus() + "'" +
            ", toNumber='" + getToNumber() + "'" +
            ", trsptStatus='" + getTrsptStatus() + "'" +
            ", waybId=" + getWaybId() +
            ", trsptrName='" + getTrsptrName() + "'" +
            ", shipmtEd='" + getShipmtEd() + "'" +
            ", gdsStatus='" + getGdsStatus() + "'" +
            ", gdsDate='" + getGdsDate() + "'" +
            ", roSubitem=" + getRoSubitem() +
            ", roType='" + getRoType() + "'" +
            ", unit='" + getUnit() + "'" +
            ", movingPrice=" + getMovingPrice() +
            ", plantId=" + getPlantId() +
            ", plantName='" + getPlantName() + "'" +
            ", storageLocp='" + getStorageLocp() + "'" +
            ", dwhId='" + getDwhId() + "'" +
            ", dwhDesc='" + getDwhDesc() + "'" +
            ", shipParty='" + getShipParty() + "'" +
            ", trsptMeans='" + getTrsptMeans() + "'" +
            ", progOfficer='" + getProgOfficer() + "'" +
            ", soItems=" + getSoItems() +
            ", poItems=" + getPoItems() +
            ", trsptrId='" + getTrsptrId() + "'" +
            ", gdsId='" + getGdsId() + "'" +
            ", gdsItem=" + getGdsItem() +
            ", batch='" + getBatch() + "'" +
            ", bbDate='" + getBbDate() + "'" +
            ", planningDate='" + getPlanningDate() + "'" +
            ", checkinDate='" + getCheckinDate() + "'" +
            ", shipmentSdate='" + getShipmentSdate() + "'" +
            ", loadingSdate='" + getLoadingSdate() + "'" +
            ", loadingEdate='" + getLoadingEdate() + "'" +
            ", ashipmentSdate='" + getAshipmentSdate() + "'" +
            ", shipmentCdate='" + getShipmentCdate() + "'" +
            ", weight=" + getWeight() +
            ", volume=" + getVolume() +
            ", section='" + getSection() + "'" +
            ", commodityGroup='" + getCommodityGroup() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
