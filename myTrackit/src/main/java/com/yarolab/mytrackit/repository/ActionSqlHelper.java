package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ActionSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("date_action", table, columnPrefix + "_date_action"));
        columns.add(Column.aliased("rapport_action", table, columnPrefix + "_rapport_action"));

        columns.add(Column.aliased("transfert_id", table, columnPrefix + "_transfert_id"));
        return columns;
    }
}