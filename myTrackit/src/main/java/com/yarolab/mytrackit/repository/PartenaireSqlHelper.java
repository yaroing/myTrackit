package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class PartenaireSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom_partenaire", table, columnPrefix + "_nom_partenaire"));
        columns.add(Column.aliased("autre_nom", table, columnPrefix + "_autre_nom"));
        columns.add(Column.aliased("log_phone", table, columnPrefix + "_log_phone"));
        columns.add(Column.aliased("email_partenaire", table, columnPrefix + "_email_partenaire"));
        columns.add(Column.aliased("loc_partenaire", table, columnPrefix + "_loc_partenaire"));

        return columns;
    }
}
