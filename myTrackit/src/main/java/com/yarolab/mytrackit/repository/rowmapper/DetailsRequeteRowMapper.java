package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.DetailsRequete;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DetailsRequete}, with proper type conversions.
 */
@Service
public class DetailsRequeteRowMapper implements BiFunction<Row, String, DetailsRequete> {

    private final ColumnConverter converter;

    public DetailsRequeteRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DetailsRequete} stored in the database.
     */
    @Override
    public DetailsRequete apply(Row row, String prefix) {
        DetailsRequete entity = new DetailsRequete();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setQuantiteDemandee(converter.fromRow(row, prefix + "_quantite_demandee", Double.class));
        entity.setQuantiteApprouvee(converter.fromRow(row, prefix + "_quantite_approuvee", Double.class));
        entity.setQuantiteRecue(converter.fromRow(row, prefix + "_quantite_recue", Double.class));
        entity.setItemObs(converter.fromRow(row, prefix + "_item_obs", String.class));
        entity.setRequetePartenaireId(converter.fromRow(row, prefix + "_requete_partenaire_id", Long.class));
        return entity;
    }
}
