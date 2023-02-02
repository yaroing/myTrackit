package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class RequetePartenaireSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("requete_date", table, columnPrefix + "_requete_date"));
        columns.add(Column.aliased("fichier_atache", table, columnPrefix + "_fichier_atache"));
        columns.add(Column.aliased("fichier_atache_content_type", table, columnPrefix + "_fichier_atache_content_type"));
        columns.add(Column.aliased("requete_obs", table, columnPrefix + "_requete_obs"));
        columns.add(Column.aliased("req_traitee", table, columnPrefix + "_req_traitee"));

        return columns;
    }
}
