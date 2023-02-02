package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.PointFocalPointService;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link PointFocalPointService}, with proper type conversions.
 */
@Service
public class PointFocalPointServiceRowMapper implements BiFunction<Row, String, PointFocalPointService> {

    private final ColumnConverter converter;

    public PointFocalPointServiceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PointFocalPointService} stored in the database.
     */
    @Override
    public PointFocalPointService apply(Row row, String prefix) {
        PointFocalPointService entity = new PointFocalPointService();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNomPf(converter.fromRow(row, prefix + "_nom_pf", String.class));
        entity.setFonctionPf(converter.fromRow(row, prefix + "_fonction_pf", String.class));
        entity.setGsmPf(converter.fromRow(row, prefix + "_gsm_pf", String.class));
        entity.setEmailPf(converter.fromRow(row, prefix + "_email_pf", String.class));
        return entity;
    }
}
