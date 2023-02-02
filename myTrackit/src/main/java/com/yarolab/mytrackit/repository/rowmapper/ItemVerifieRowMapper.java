package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.ItemVerifie;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ItemVerifie}, with proper type conversions.
 */
@Service
public class ItemVerifieRowMapper implements BiFunction<Row, String, ItemVerifie> {

    private final ColumnConverter converter;

    public ItemVerifieRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ItemVerifie} stored in the database.
     */
    @Override
    public ItemVerifie apply(Row row, String prefix) {
        ItemVerifie entity = new ItemVerifie();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setQuantiteTransfert(converter.fromRow(row, prefix + "_quantite_transfert", Double.class));
        entity.setQuantiteRecu(converter.fromRow(row, prefix + "_quantite_recu", Double.class));
        entity.setQuantiteUtilisee(converter.fromRow(row, prefix + "_quantite_utilisee", Double.class));
        entity.setQuantiteDisponible(converter.fromRow(row, prefix + "_quantite_disponible", Double.class));
        entity.setQuantiteEcart(converter.fromRow(row, prefix + "_quantite_ecart", Double.class));
        entity.setMissionId(converter.fromRow(row, prefix + "_mission_id", Long.class));
        return entity;
    }
}
