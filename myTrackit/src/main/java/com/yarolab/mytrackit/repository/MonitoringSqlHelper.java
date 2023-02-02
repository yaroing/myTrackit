package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class MonitoringSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("atpe_annee", table, columnPrefix + "_atpe_annee"));
        columns.add(Column.aliased("atpe_mois", table, columnPrefix + "_atpe_mois"));
        columns.add(Column.aliased("atpe_stock", table, columnPrefix + "_atpe_stock"));
        columns.add(Column.aliased("atpe_dispo", table, columnPrefix + "_atpe_dispo"));
        columns.add(Column.aliased("atpe_endom", table, columnPrefix + "_atpe_endom"));
        columns.add(Column.aliased("atpe_perime", table, columnPrefix + "_atpe_perime"));
        columns.add(Column.aliased("atpe_rupture", table, columnPrefix + "_atpe_rupture"));
        columns.add(Column.aliased("atpe_njour", table, columnPrefix + "_atpe_njour"));
        columns.add(Column.aliased("atpe_magasin", table, columnPrefix + "_atpe_magasin"));
        columns.add(Column.aliased("atpe_palette", table, columnPrefix + "_atpe_palette"));
        columns.add(Column.aliased("atpe_position", table, columnPrefix + "_atpe_position"));
        columns.add(Column.aliased("atpe_hauteur", table, columnPrefix + "_atpe_hauteur"));
        columns.add(Column.aliased("atpe_personnel", table, columnPrefix + "_atpe_personnel"));
        columns.add(Column.aliased("atpe_admission", table, columnPrefix + "_atpe_admission"));
        columns.add(Column.aliased("atpe_sortie", table, columnPrefix + "_atpe_sortie"));
        columns.add(Column.aliased("atpe_gueris", table, columnPrefix + "_atpe_gueris"));
        columns.add(Column.aliased("atpe_abandon", table, columnPrefix + "_atpe_abandon"));
        columns.add(Column.aliased("atpe_poids", table, columnPrefix + "_atpe_poids"));
        columns.add(Column.aliased("atpe_trasnsfert", table, columnPrefix + "_atpe_trasnsfert"));
        columns.add(Column.aliased("atpe_parent", table, columnPrefix + "_atpe_parent"));

        columns.add(Column.aliased("point_service_id", table, columnPrefix + "_point_service_id"));
        return columns;
    }
}
