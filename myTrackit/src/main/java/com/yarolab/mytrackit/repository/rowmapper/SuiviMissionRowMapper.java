package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.SuiviMission;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link SuiviMission}, with proper type conversions.
 */
@Service
public class SuiviMissionRowMapper implements BiFunction<Row, String, SuiviMission> {

    private final ColumnConverter converter;

    public SuiviMissionRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SuiviMission} stored in the database.
     */
    @Override
    public SuiviMission apply(Row row, String prefix) {
        SuiviMission entity = new SuiviMission();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setProblemeConstate(converter.fromRow(row, prefix + "_probleme_constate", String.class));
        entity.setActionRecommandee(converter.fromRow(row, prefix + "_action_recommandee", String.class));
        entity.setDateEcheance(converter.fromRow(row, prefix + "_date_echeance", String.class));
        return entity;
    }
}
