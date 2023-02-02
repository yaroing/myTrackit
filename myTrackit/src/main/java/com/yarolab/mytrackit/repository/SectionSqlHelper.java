package com.yarolab.mytrackit.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class SectionSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("section_nom", table, columnPrefix + "_section_nom"));
        columns.add(Column.aliased("chef_section", table, columnPrefix + "_chef_section"));
        columns.add(Column.aliased("email_chef", table, columnPrefix + "_email_chef"));
        columns.add(Column.aliased("phone_chef", table, columnPrefix + "_phone_chef"));

        return columns;
    }
}
