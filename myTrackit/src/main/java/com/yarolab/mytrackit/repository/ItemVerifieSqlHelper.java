package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ItemVerifieSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("quantite_transfert", table, columnPrefix + "_quantite_transfert"));
        columns.add(Column.aliased("quantite_recu", table, columnPrefix + "_quantite_recu"));
        columns.add(Column.aliased("quantite_utilisee", table, columnPrefix + "_quantite_utilisee"));
        columns.add(Column.aliased("quantite_disponible", table, columnPrefix + "_quantite_disponible"));
        columns.add(Column.aliased("quantite_ecart", table, columnPrefix + "_quantite_ecart"));

        columns.add(Column.aliased("mission_id", table, columnPrefix + "_mission_id"));
        return columns;
    }
}
