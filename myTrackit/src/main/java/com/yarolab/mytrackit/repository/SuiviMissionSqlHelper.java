package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class SuiviMissionSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("probleme_constate", table, columnPrefix + "_probleme_constate"));
        columns.add(Column.aliased("action_recommandee", table, columnPrefix + "_action_recommandee"));
        columns.add(Column.aliased("date_echeance", table, columnPrefix + "_date_echeance"));

        return columns;
    }
}
