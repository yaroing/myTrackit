package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.ItemTransfert;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ItemTransfert}, with proper type conversions.
 */
@Service
public class ItemTransfertRowMapper implements BiFunction<Row, String, ItemTransfert> {

    private final ColumnConverter converter;

    public ItemTransfertRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ItemTransfert} stored in the database.
     */
    @Override
    public ItemTransfert apply(Row row, String prefix) {
        ItemTransfert entity = new ItemTransfert();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setRoDate(converter.fromRow(row, prefix + "_ro_date", Instant.class));
        entity.setMatDesc(converter.fromRow(row, prefix + "_mat_desc", String.class));
        entity.setUnit(converter.fromRow(row, prefix + "_unit", String.class));
        entity.setDelQty(converter.fromRow(row, prefix + "_del_qty", Double.class));
        entity.setValue(converter.fromRow(row, prefix + "_value", Double.class));
        entity.setBatch(converter.fromRow(row, prefix + "_batch", String.class));
        entity.setBbDate(converter.fromRow(row, prefix + "_bb_date", Instant.class));
        entity.setWeight(converter.fromRow(row, prefix + "_weight", Double.class));
        entity.setVolume(converter.fromRow(row, prefix + "_volume", Double.class));
        entity.setRecQty(converter.fromRow(row, prefix + "_rec_qty", Double.class));
        entity.setTransfertId(converter.fromRow(row, prefix + "_transfert_id", Long.class));
        return entity;
    }
}
