package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Action;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Action}, with proper type conversions.
 */
@Service
public class ActionRowMapper implements BiFunction<Row, String, Action> {

    private final ColumnConverter converter;

    public ActionRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Action} stored in the database.
     */
    @Override
    public Action apply(Row row, String prefix) {
        Action entity = new Action();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDateAction(converter.fromRow(row, prefix + "_date_action", Instant.class));
        entity.setRapportAction(converter.fromRow(row, prefix + "_rapport_action", String.class));
        entity.setTransfertId(converter.fromRow(row, prefix + "_transfert_id", Long.class));
        return entity;
    }
}
