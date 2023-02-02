package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Catalogue;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Catalogue}, with proper type conversions.
 */
@Service
public class CatalogueRowMapper implements BiFunction<Row, String, Catalogue> {

    private final ColumnConverter converter;

    public CatalogueRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Catalogue} stored in the database.
     */
    @Override
    public Catalogue apply(Row row, String prefix) {
        Catalogue entity = new Catalogue();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setMaterialCode(converter.fromRow(row, prefix + "_material_code", String.class));
        entity.setMaterialDesc(converter.fromRow(row, prefix + "_material_desc", String.class));
        entity.setMaterialGroup(converter.fromRow(row, prefix + "_material_group", String.class));
        return entity;
    }
}
