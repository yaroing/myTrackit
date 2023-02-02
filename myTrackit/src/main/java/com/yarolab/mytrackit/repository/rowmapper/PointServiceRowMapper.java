package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.PointService;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link PointService}, with proper type conversions.
 */
@Service
public class PointServiceRowMapper implements BiFunction<Row, String, PointService> {

    private final ColumnConverter converter;

    public PointServiceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PointService} stored in the database.
     */
    @Override
    public PointService apply(Row row, String prefix) {
        PointService entity = new PointService();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNomPos(converter.fromRow(row, prefix + "_nom_pos", String.class));
        entity.setPosLon(converter.fromRow(row, prefix + "_pos_lon", Double.class));
        entity.setPosLat(converter.fromRow(row, prefix + "_pos_lat", Double.class));
        entity.setPosContact(converter.fromRow(row, prefix + "_pos_contact", String.class));
        entity.setPosGsm(converter.fromRow(row, prefix + "_pos_gsm", String.class));
        return entity;
    }
}
