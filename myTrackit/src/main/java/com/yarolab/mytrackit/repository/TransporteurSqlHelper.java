package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TransporteurSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom_transporteur", table, columnPrefix + "_nom_transporteur"));
        columns.add(Column.aliased("nom_directeur", table, columnPrefix + "_nom_directeur"));
        columns.add(Column.aliased("phone_transporteur", table, columnPrefix + "_phone_transporteur"));
        columns.add(Column.aliased("email_transporteur", table, columnPrefix + "_email_transporteur"));

        return columns;
    }
}
