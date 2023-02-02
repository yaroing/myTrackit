package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Partenaire;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Partenaire}, with proper type conversions.
 */
@Service
public class PartenaireRowMapper implements BiFunction<Row, String, Partenaire> {

    private final ColumnConverter converter;

    public PartenaireRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Partenaire} stored in the database.
     */
    @Override
    public Partenaire apply(Row row, String prefix) {
        Partenaire entity = new Partenaire();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNomPartenaire(converter.fromRow(row, prefix + "_nom_partenaire", String.class));
        entity.setAutreNom(converter.fromRow(row, prefix + "_autre_nom", String.class));
        entity.setLogPhone(converter.fromRow(row, prefix + "_log_phone", String.class));
        entity.setEmailPartenaire(converter.fromRow(row, prefix + "_email_partenaire", String.class));
        entity.setLocPartenaire(converter.fromRow(row, prefix + "_loc_partenaire", String.class));
        return entity;
    }
}
