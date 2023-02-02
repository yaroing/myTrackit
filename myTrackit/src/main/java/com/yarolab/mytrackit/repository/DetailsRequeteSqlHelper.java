package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class DetailsRequeteSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("quantite_demandee", table, columnPrefix + "_quantite_demandee"));
        columns.add(Column.aliased("quantite_approuvee", table, columnPrefix + "_quantite_approuvee"));
        columns.add(Column.aliased("quantite_recue", table, columnPrefix + "_quantite_recue"));
        columns.add(Column.aliased("item_obs", table, columnPrefix + "_item_obs"));

        columns.add(Column.aliased("requete_partenaire_id", table, columnPrefix + "_requete_partenaire_id"));
        return columns;
    }
}
