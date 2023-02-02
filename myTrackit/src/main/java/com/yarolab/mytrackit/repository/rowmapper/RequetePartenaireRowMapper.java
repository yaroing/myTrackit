package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.RequetePartenaire;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RequetePartenaire}, with proper type conversions.
 */
@Service
public class RequetePartenaireRowMapper implements BiFunction<Row, String, RequetePartenaire> {

    private final ColumnConverter converter;

    public RequetePartenaireRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RequetePartenaire} stored in the database.
     */
    @Override
    public RequetePartenaire apply(Row row, String prefix) {
        RequetePartenaire entity = new RequetePartenaire();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setRequeteDate(converter.fromRow(row, prefix + "_requete_date", Instant.class));
        entity.setFichierAtacheContentType(converter.fromRow(row, prefix + "_fichier_atache_content_type", String.class));
        entity.setFichierAtache(converter.fromRow(row, prefix + "_fichier_atache", byte[].class));
        entity.setRequeteObs(converter.fromRow(row, prefix + "_requete_obs", String.class));
        entity.setReqTraitee(converter.fromRow(row, prefix + "_req_traitee", Integer.class));
        return entity;
    }
}
