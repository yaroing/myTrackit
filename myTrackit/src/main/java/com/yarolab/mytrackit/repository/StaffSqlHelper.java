package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class StaffSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("staff_fname", table, columnPrefix + "_staff_fname"));
        columns.add(Column.aliased("staff_lname", table, columnPrefix + "_staff_lname"));
        columns.add(Column.aliased("staff_title", table, columnPrefix + "_staff_title"));
        columns.add(Column.aliased("staff_name", table, columnPrefix + "_staff_name"));
        columns.add(Column.aliased("staff_email", table, columnPrefix + "_staff_email"));
        columns.add(Column.aliased("staff_phone", table, columnPrefix + "_staff_phone"));

        return columns;
    }
}
