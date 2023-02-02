package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Monitoring;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Monitoring}, with proper type conversions.
 */
@Service
public class MonitoringRowMapper implements BiFunction<Row, String, Monitoring> {

    private final ColumnConverter converter;

    public MonitoringRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Monitoring} stored in the database.
     */
    @Override
    public Monitoring apply(Row row, String prefix) {
        Monitoring entity = new Monitoring();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setAtpeAnnee(converter.fromRow(row, prefix + "_atpe_annee", Integer.class));
        entity.setAtpeMois(converter.fromRow(row, prefix + "_atpe_mois", Integer.class));
        entity.setAtpeStock(converter.fromRow(row, prefix + "_atpe_stock", String.class));
        entity.setAtpeDispo(converter.fromRow(row, prefix + "_atpe_dispo", Double.class));
        entity.setAtpeEndom(converter.fromRow(row, prefix + "_atpe_endom", Double.class));
        entity.setAtpePerime(converter.fromRow(row, prefix + "_atpe_perime", Double.class));
        entity.setAtpeRupture(converter.fromRow(row, prefix + "_atpe_rupture", String.class));
        entity.setAtpeNjour(converter.fromRow(row, prefix + "_atpe_njour", Integer.class));
        entity.setAtpeMagasin(converter.fromRow(row, prefix + "_atpe_magasin", String.class));
        entity.setAtpePalette(converter.fromRow(row, prefix + "_atpe_palette", String.class));
        entity.setAtpePosition(converter.fromRow(row, prefix + "_atpe_position", String.class));
        entity.setAtpeHauteur(converter.fromRow(row, prefix + "_atpe_hauteur", Double.class));
        entity.setAtpePersonnel(converter.fromRow(row, prefix + "_atpe_personnel", String.class));
        entity.setAtpeAdmission(converter.fromRow(row, prefix + "_atpe_admission", Integer.class));
        entity.setAtpeSortie(converter.fromRow(row, prefix + "_atpe_sortie", Integer.class));
        entity.setAtpeGueris(converter.fromRow(row, prefix + "_atpe_gueris", Integer.class));
        entity.setAtpeAbandon(converter.fromRow(row, prefix + "_atpe_abandon", Integer.class));
        entity.setAtpePoids(converter.fromRow(row, prefix + "_atpe_poids", Integer.class));
        entity.setAtpeTrasnsfert(converter.fromRow(row, prefix + "_atpe_trasnsfert", Integer.class));
        entity.setAtpeParent(converter.fromRow(row, prefix + "_atpe_parent", Integer.class));
        entity.setPointServiceId(converter.fromRow(row, prefix + "_point_service_id", Long.class));
        return entity;
    }
}
