package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Zrosts;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Zrosts}, with proper type conversions.
 */
@Service
public class ZrostsRowMapper implements BiFunction<Row, String, Zrosts> {

    private final ColumnConverter converter;

    public ZrostsRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Zrosts} stored in the database.
     */
    @Override
    public Zrosts apply(Row row, String prefix) {
        Zrosts entity = new Zrosts();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setRoId(converter.fromRow(row, prefix + "_ro_id", Integer.class));
        entity.setRoItem(converter.fromRow(row, prefix + "_ro_item", Double.class));
        entity.setRoDate(converter.fromRow(row, prefix + "_ro_date", Instant.class));
        entity.setRoTdd(converter.fromRow(row, prefix + "_ro_tdd", Instant.class));
        entity.setMaterialId(converter.fromRow(row, prefix + "_material_id", String.class));
        entity.setMatDesc(converter.fromRow(row, prefix + "_mat_desc", String.class));
        entity.setDelQty(converter.fromRow(row, prefix + "_del_qty", Double.class));
        entity.setValue(converter.fromRow(row, prefix + "_value", Double.class));
        entity.setStorageLoc(converter.fromRow(row, prefix + "_storage_loc", Double.class));
        entity.setWhId(converter.fromRow(row, prefix + "_wh_id", Double.class));
        entity.setWhDesc(converter.fromRow(row, prefix + "_wh_desc", String.class));
        entity.setConsId(converter.fromRow(row, prefix + "_cons_id", String.class));
        entity.setConsName(converter.fromRow(row, prefix + "_cons_name", String.class));
        entity.setAuthPerson(converter.fromRow(row, prefix + "_auth_person", String.class));
        entity.setSoId(converter.fromRow(row, prefix + "_so_id", String.class));
        entity.setPoId(converter.fromRow(row, prefix + "_po_id", String.class));
        entity.setDelivery(converter.fromRow(row, prefix + "_delivery", Double.class));
        entity.setGrant(converter.fromRow(row, prefix + "_jhi_grant", String.class));
        entity.setWbs(converter.fromRow(row, prefix + "_wbs", String.class));
        entity.setPickStatus(converter.fromRow(row, prefix + "_pick_status", String.class));
        entity.setToNumber(converter.fromRow(row, prefix + "_to_number", String.class));
        entity.setTrsptStatus(converter.fromRow(row, prefix + "_trspt_status", String.class));
        entity.setWaybId(converter.fromRow(row, prefix + "_wayb_id", Integer.class));
        entity.setTrsptrName(converter.fromRow(row, prefix + "_trsptr_name", String.class));
        entity.setShipmtEd(converter.fromRow(row, prefix + "_shipmt_ed", Instant.class));
        entity.setGdsStatus(converter.fromRow(row, prefix + "_gds_status", String.class));
        entity.setGdsDate(converter.fromRow(row, prefix + "_gds_date", Instant.class));
        entity.setRoSubitem(converter.fromRow(row, prefix + "_ro_subitem", Double.class));
        entity.setRoType(converter.fromRow(row, prefix + "_ro_type", String.class));
        entity.setUnit(converter.fromRow(row, prefix + "_unit", String.class));
        entity.setMovingPrice(converter.fromRow(row, prefix + "_moving_price", Double.class));
        entity.setPlantId(converter.fromRow(row, prefix + "_plant_id", Double.class));
        entity.setPlantName(converter.fromRow(row, prefix + "_plant_name", String.class));
        entity.setStorageLocp(converter.fromRow(row, prefix + "_storage_locp", String.class));
        entity.setDwhId(converter.fromRow(row, prefix + "_dwh_id", String.class));
        entity.setDwhDesc(converter.fromRow(row, prefix + "_dwh_desc", String.class));
        entity.setShipParty(converter.fromRow(row, prefix + "_ship_party", String.class));
        entity.setTrsptMeans(converter.fromRow(row, prefix + "_trspt_means", String.class));
        entity.setProgOfficer(converter.fromRow(row, prefix + "_prog_officer", String.class));
        entity.setSoItems(converter.fromRow(row, prefix + "_so_items", Double.class));
        entity.setPoItems(converter.fromRow(row, prefix + "_po_items", Double.class));
        entity.setTrsptrId(converter.fromRow(row, prefix + "_trsptr_id", String.class));
        entity.setGdsId(converter.fromRow(row, prefix + "_gds_id", String.class));
        entity.setGdsItem(converter.fromRow(row, prefix + "_gds_item", Double.class));
        entity.setBatch(converter.fromRow(row, prefix + "_batch", String.class));
        entity.setBbDate(converter.fromRow(row, prefix + "_bb_date", Instant.class));
        entity.setPlanningDate(converter.fromRow(row, prefix + "_planning_date", Instant.class));
        entity.setCheckinDate(converter.fromRow(row, prefix + "_checkin_date", Instant.class));
        entity.setShipmentSdate(converter.fromRow(row, prefix + "_shipment_sdate", Instant.class));
        entity.setLoadingSdate(converter.fromRow(row, prefix + "_loading_sdate", Instant.class));
        entity.setLoadingEdate(converter.fromRow(row, prefix + "_loading_edate", Instant.class));
        entity.setAshipmentSdate(converter.fromRow(row, prefix + "_ashipment_sdate", Instant.class));
        entity.setShipmentCdate(converter.fromRow(row, prefix + "_shipment_cdate", Instant.class));
        entity.setWeight(converter.fromRow(row, prefix + "_weight", Double.class));
        entity.setVolume(converter.fromRow(row, prefix + "_volume", Double.class));
        entity.setSection(converter.fromRow(row, prefix + "_section", String.class));
        entity.setCommodityGroup(converter.fromRow(row, prefix + "_commodity_group", String.class));
        entity.setRegion(converter.fromRow(row, prefix + "_region", String.class));
        return entity;
    }
}
