package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class RequetePointServiceSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("stock_disponible", table, columnPrefix + "_stock_disponible"));
        columns.add(Column.aliased("quant_dem", table, columnPrefix + "_quant_dem"));
        columns.add(Column.aliased("quant_trs", table, columnPrefix + "_quant_trs"));
        columns.add(Column.aliased("quant_rec", table, columnPrefix + "_quant_rec"));
        columns.add(Column.aliased("req_traitee", table, columnPrefix + "_req_traitee"));
        columns.add(Column.aliased("date_req", table, columnPrefix + "_date_req"));
        columns.add(Column.aliased("date_rec", table, columnPrefix + "_date_rec"));
        columns.add(Column.aliased("date_transfert", table, columnPrefix + "_date_transfert"));

        columns.add(Column.aliased("point_service_id", table, columnPrefix + "_point_service_id"));
        return columns;
    }
}
