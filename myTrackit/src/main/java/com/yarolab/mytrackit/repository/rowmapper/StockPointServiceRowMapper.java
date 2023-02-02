package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.StockPointService;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link StockPointService}, with proper type conversions.
 */
@Service
public class StockPointServiceRowMapper implements BiFunction<Row, String, StockPointService> {

    private final ColumnConverter converter;

    public StockPointServiceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link StockPointService} stored in the database.
     */
    @Override
    public StockPointService apply(Row row, String prefix) {
        StockPointService entity = new StockPointService();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStockAnnee(converter.fromRow(row, prefix + "_stock_annee", String.class));
        entity.setStockMois(converter.fromRow(row, prefix + "_stock_mois", String.class));
        entity.setEntreeMois(converter.fromRow(row, prefix + "_entree_mois", Double.class));
        entity.setSortieMois(converter.fromRow(row, prefix + "_sortie_mois", Double.class));
        entity.setStockFinmois(converter.fromRow(row, prefix + "_stock_finmois", Double.class));
        entity.setStockDebut(converter.fromRow(row, prefix + "_stock_debut", Double.class));
        entity.setPointServiceId(converter.fromRow(row, prefix + "_point_service_id", Long.class));
        return entity;
    }
}
