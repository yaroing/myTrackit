package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Mission;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Mission}, with proper type conversions.
 */
@Service
public class MissionRowMapper implements BiFunction<Row, String, Mission> {

    private final ColumnConverter converter;

    public MissionRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Mission} stored in the database.
     */
    @Override
    public Mission apply(Row row, String prefix) {
        Mission entity = new Mission();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDateMission(converter.fromRow(row, prefix + "_date_mission", Instant.class));
        entity.setDateDebut(converter.fromRow(row, prefix + "_date_debut", Instant.class));
        entity.setDateFin(converter.fromRow(row, prefix + "_date_fin", Instant.class));
        entity.setRapportMissionContentType(converter.fromRow(row, prefix + "_rapport_mission_content_type", String.class));
        entity.setRapportMission(converter.fromRow(row, prefix + "_rapport_mission", byte[].class));
        entity.setDebutMission(converter.fromRow(row, prefix + "_debut_mission", Instant.class));
        entity.setFinMission(converter.fromRow(row, prefix + "_fin_mission", Instant.class));
        entity.setField10(converter.fromRow(row, prefix + "_field_10", String.class));
        entity.setFin(converter.fromRow(row, prefix + "_fin", String.class));
        return entity;
    }
}
