package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ItemTransfertSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("ro_date", table, columnPrefix + "_ro_date"));
        columns.add(Column.aliased("mat_desc", table, columnPrefix + "_mat_desc"));
        columns.add(Column.aliased("unit", table, columnPrefix + "_unit"));
        columns.add(Column.aliased("del_qty", table, columnPrefix + "_del_qty"));
        columns.add(Column.aliased("value", table, columnPrefix + "_value"));
        columns.add(Column.aliased("batch", table, columnPrefix + "_batch"));
        columns.add(Column.aliased("bb_date", table, columnPrefix + "_bb_date"));
        columns.add(Column.aliased("weight", table, columnPrefix + "_weight"));
        columns.add(Column.aliased("volume", table, columnPrefix + "_volume"));
        columns.add(Column.aliased("rec_qty", table, columnPrefix + "_rec_qty"));

        columns.add(Column.aliased("transfert_id", table, columnPrefix + "_transfert_id"));
        return columns;
    }
}
