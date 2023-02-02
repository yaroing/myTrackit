package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ZrostsSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("ro_id", table, columnPrefix + "_ro_id"));
        columns.add(Column.aliased("ro_item", table, columnPrefix + "_ro_item"));
        columns.add(Column.aliased("ro_date", table, columnPrefix + "_ro_date"));
        columns.add(Column.aliased("ro_tdd", table, columnPrefix + "_ro_tdd"));
        columns.add(Column.aliased("material_id", table, columnPrefix + "_material_id"));
        columns.add(Column.aliased("mat_desc", table, columnPrefix + "_mat_desc"));
        columns.add(Column.aliased("del_qty", table, columnPrefix + "_del_qty"));
        columns.add(Column.aliased("value", table, columnPrefix + "_value"));
        columns.add(Column.aliased("storage_loc", table, columnPrefix + "_storage_loc"));
        columns.add(Column.aliased("wh_id", table, columnPrefix + "_wh_id"));
        columns.add(Column.aliased("wh_desc", table, columnPrefix + "_wh_desc"));
        columns.add(Column.aliased("cons_id", table, columnPrefix + "_cons_id"));
        columns.add(Column.aliased("cons_name", table, columnPrefix + "_cons_name"));
        columns.add(Column.aliased("auth_person", table, columnPrefix + "_auth_person"));
        columns.add(Column.aliased("so_id", table, columnPrefix + "_so_id"));
        columns.add(Column.aliased("po_id", table, columnPrefix + "_po_id"));
        columns.add(Column.aliased("delivery", table, columnPrefix + "_delivery"));
        columns.add(Column.aliased("jhi_grant", table, columnPrefix + "_jhi_grant"));
        columns.add(Column.aliased("wbs", table, columnPrefix + "_wbs"));
        columns.add(Column.aliased("pick_status", table, columnPrefix + "_pick_status"));
        columns.add(Column.aliased("to_number", table, columnPrefix + "_to_number"));
        columns.add(Column.aliased("trspt_status", table, columnPrefix + "_trspt_status"));
        columns.add(Column.aliased("wayb_id", table, columnPrefix + "_wayb_id"));
        columns.add(Column.aliased("trsptr_name", table, columnPrefix + "_trsptr_name"));
        columns.add(Column.aliased("shipmt_ed", table, columnPrefix + "_shipmt_ed"));
        columns.add(Column.aliased("gds_status", table, columnPrefix + "_gds_status"));
        columns.add(Column.aliased("gds_date", table, columnPrefix + "_gds_date"));
        columns.add(Column.aliased("ro_subitem", table, columnPrefix + "_ro_subitem"));
        columns.add(Column.aliased("ro_type", table, columnPrefix + "_ro_type"));
        columns.add(Column.aliased("unit", table, columnPrefix + "_unit"));
        columns.add(Column.aliased("moving_price", table, columnPrefix + "_moving_price"));
        columns.add(Column.aliased("plant_id", table, columnPrefix + "_plant_id"));
        columns.add(Column.aliased("plant_name", table, columnPrefix + "_plant_name"));
        columns.add(Column.aliased("storage_locp", table, columnPrefix + "_storage_locp"));
        columns.add(Column.aliased("dwh_id", table, columnPrefix + "_dwh_id"));
        columns.add(Column.aliased("dwh_desc", table, columnPrefix + "_dwh_desc"));
        columns.add(Column.aliased("ship_party", table, columnPrefix + "_ship_party"));
        columns.add(Column.aliased("trspt_means", table, columnPrefix + "_trspt_means"));
        columns.add(Column.aliased("prog_officer", table, columnPrefix + "_prog_officer"));
        columns.add(Column.aliased("so_items", table, columnPrefix + "_so_items"));
        columns.add(Column.aliased("po_items", table, columnPrefix + "_po_items"));
        columns.add(Column.aliased("trsptr_id", table, columnPrefix + "_trsptr_id"));
        columns.add(Column.aliased("gds_id", table, columnPrefix + "_gds_id"));
        columns.add(Column.aliased("gds_item", table, columnPrefix + "_gds_item"));
        columns.add(Column.aliased("batch", table, columnPrefix + "_batch"));
        columns.add(Column.aliased("bb_date", table, columnPrefix + "_bb_date"));
        columns.add(Column.aliased("planning_date", table, columnPrefix + "_planning_date"));
        columns.add(Column.aliased("checkin_date", table, columnPrefix + "_checkin_date"));
        columns.add(Column.aliased("shipment_sdate", table, columnPrefix + "_shipment_sdate"));
        columns.add(Column.aliased("loading_sdate", table, columnPrefix + "_loading_sdate"));
        columns.add(Column.aliased("loading_edate", table, columnPrefix + "_loading_edate"));
        columns.add(Column.aliased("ashipment_sdate", table, columnPrefix + "_ashipment_sdate"));
        columns.add(Column.aliased("shipment_cdate", table, columnPrefix + "_shipment_cdate"));
        columns.add(Column.aliased("weight", table, columnPrefix + "_weight"));
        columns.add(Column.aliased("volume", table, columnPrefix + "_volume"));
        columns.add(Column.aliased("section", table, columnPrefix + "_section"));
        columns.add(Column.aliased("commodity_group", table, columnPrefix + "_commodity_group"));
        columns.add(Column.aliased("region", table, columnPrefix + "_region"));

        return columns;
    }
}
