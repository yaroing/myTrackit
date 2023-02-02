package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Transfert;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Transfert}, with proper type conversions.
 */
@Service
public class TransfertRowMapper implements BiFunction<Row, String, Transfert> {

    private final ColumnConverter converter;

    public TransfertRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Transfert} stored in the database.
     */
    @Override
    public Transfert apply(Row row, String prefix) {
        Transfert entity = new Transfert();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDateExp(converter.fromRow(row, prefix + "_date_exp", Instant.class));
        entity.setNomChauffeur(converter.fromRow(row, prefix + "_nom_chauffeur", String.class));
        entity.setDateRec(converter.fromRow(row, prefix + "_date_rec", Instant.class));
        entity.setCphone(converter.fromRow(row, prefix + "_cphone", String.class));
        return entity;
    }
}
