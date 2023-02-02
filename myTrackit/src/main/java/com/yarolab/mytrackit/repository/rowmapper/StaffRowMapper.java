package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Staff;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Staff}, with proper type conversions.
 */
@Service
public class StaffRowMapper implements BiFunction<Row, String, Staff> {

    private final ColumnConverter converter;

    public StaffRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Staff} stored in the database.
     */
    @Override
    public Staff apply(Row row, String prefix) {
        Staff entity = new Staff();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStaffFname(converter.fromRow(row, prefix + "_staff_fname", String.class));
        entity.setStaffLname(converter.fromRow(row, prefix + "_staff_lname", String.class));
        entity.setStaffTitle(converter.fromRow(row, prefix + "_staff_title", String.class));
        entity.setStaffName(converter.fromRow(row, prefix + "_staff_name", String.class));
        entity.setStaffEmail(converter.fromRow(row, prefix + "_staff_email", String.class));
        entity.setStaffPhone(converter.fromRow(row, prefix + "_staff_phone", String.class));
        return entity;
    }
}
