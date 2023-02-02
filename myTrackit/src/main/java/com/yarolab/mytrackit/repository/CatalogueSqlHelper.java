package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class CatalogueSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("material_code", table, columnPrefix + "_material_code"));
        columns.add(Column.aliased("material_desc", table, columnPrefix + "_material_desc"));
        columns.add(Column.aliased("material_group", table, columnPrefix + "_material_group"));

        return columns;
    }
}
