package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class PointFocalPartenaireSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom_pf", table, columnPrefix + "_nom_pf"));
        columns.add(Column.aliased("fonction_pf", table, columnPrefix + "_fonction_pf"));
        columns.add(Column.aliased("gsm_pf", table, columnPrefix + "_gsm_pf"));
        columns.add(Column.aliased("email_pf", table, columnPrefix + "_email_pf"));

        return columns;
    }
}
