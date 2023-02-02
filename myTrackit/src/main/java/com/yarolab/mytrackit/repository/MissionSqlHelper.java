package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class MissionSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("date_mission", table, columnPrefix + "_date_mission"));
        columns.add(Column.aliased("date_debut", table, columnPrefix + "_date_debut"));
        columns.add(Column.aliased("date_fin", table, columnPrefix + "_date_fin"));
        columns.add(Column.aliased("rapport_mission", table, columnPrefix + "_rapport_mission"));
        columns.add(Column.aliased("rapport_mission_content_type", table, columnPrefix + "_rapport_mission_content_type"));
        columns.add(Column.aliased("debut_mission", table, columnPrefix + "_debut_mission"));
        columns.add(Column.aliased("fin_mission", table, columnPrefix + "_fin_mission"));
        columns.add(Column.aliased("field_10", table, columnPrefix + "_field_10"));
        columns.add(Column.aliased("fin", table, columnPrefix + "_fin"));

        return columns;
    }
}
