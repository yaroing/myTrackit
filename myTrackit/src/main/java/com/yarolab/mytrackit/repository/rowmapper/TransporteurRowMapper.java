package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Transporteur;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Transporteur}, with proper type conversions.
 */
@Service
public class TransporteurRowMapper implements BiFunction<Row, String, Transporteur> {

    private final ColumnConverter converter;

    public TransporteurRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Transporteur} stored in the database.
     */
    @Override
    public Transporteur apply(Row row, String prefix) {
        Transporteur entity = new Transporteur();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNomTransporteur(converter.fromRow(row, prefix + "_nom_transporteur", String.class));
        entity.setNomDirecteur(converter.fromRow(row, prefix + "_nom_directeur", String.class));
        entity.setPhoneTransporteur(converter.fromRow(row, prefix + "_phone_transporteur", String.class));
        entity.setEmailTransporteur(converter.fromRow(row, prefix + "_email_transporteur", String.class));
        return entity;
    }
}
