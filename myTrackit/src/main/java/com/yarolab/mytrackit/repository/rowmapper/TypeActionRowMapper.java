package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.TypeAction;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link TypeAction}, with proper type conversions.
 */
@Service
public class TypeActionRowMapper implements BiFunction<Row, String, TypeAction> {

    private final ColumnConverter converter;

    public TypeActionRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link TypeAction} stored in the database.
     */
    @Override
    public TypeAction apply(Row row, String prefix) {
        TypeAction entity = new TypeAction();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}
