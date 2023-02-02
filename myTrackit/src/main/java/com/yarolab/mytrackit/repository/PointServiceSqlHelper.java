package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class PointServiceSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom_pos", table, columnPrefix + "_nom_pos"));
        columns.add(Column.aliased("pos_lon", table, columnPrefix + "_pos_lon"));
        columns.add(Column.aliased("pos_lat", table, columnPrefix + "_pos_lat"));
        columns.add(Column.aliased("pos_contact", table, columnPrefix + "_pos_contact"));
        columns.add(Column.aliased("pos_gsm", table, columnPrefix + "_pos_gsm"));

        return columns;
    }
}
