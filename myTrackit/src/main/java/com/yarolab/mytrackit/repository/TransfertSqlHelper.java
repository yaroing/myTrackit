package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TransfertSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("date_exp", table, columnPrefix + "_date_exp"));
        columns.add(Column.aliased("nom_chauffeur", table, columnPrefix + "_nom_chauffeur"));
        columns.add(Column.aliased("date_rec", table, columnPrefix + "_date_rec"));
        columns.add(Column.aliased("cphone", table, columnPrefix + "_cphone"));

        return columns;
    }
}
