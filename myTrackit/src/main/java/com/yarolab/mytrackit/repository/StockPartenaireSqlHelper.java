package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class StockPartenaireSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("stock_annee", table, columnPrefix + "_stock_annee"));
        columns.add(Column.aliased("stock_mois", table, columnPrefix + "_stock_mois"));
        columns.add(Column.aliased("entree_mois", table, columnPrefix + "_entree_mois"));
        columns.add(Column.aliased("sortie_mois", table, columnPrefix + "_sortie_mois"));
        columns.add(Column.aliased("stock_finmois", table, columnPrefix + "_stock_finmois"));
        columns.add(Column.aliased("stock_debut", table, columnPrefix + "_stock_debut"));

        return columns;
    }
}
