package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.PointFocalPartenaire;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link PointFocalPartenaire}, with proper type conversions.
 */
@Service
public class PointFocalPartenaireRowMapper implements BiFunction<Row, String, PointFocalPartenaire> {

    private final ColumnConverter converter;

    public PointFocalPartenaireRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PointFocalPartenaire} stored in the database.
     */
    @Override
    public PointFocalPartenaire apply(Row row, String prefix) {
        PointFocalPartenaire entity = new PointFocalPartenaire();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNomPf(converter.fromRow(row, prefix + "_nom_pf", String.class));
        entity.setFonctionPf(converter.fromRow(row, prefix + "_fonction_pf", String.class));
        entity.setGsmPf(converter.fromRow(row, prefix + "_gsm_pf", String.class));
        entity.setEmailPf(converter.fromRow(row, prefix + "_email_pf", String.class));
        return entity;
    }
}
