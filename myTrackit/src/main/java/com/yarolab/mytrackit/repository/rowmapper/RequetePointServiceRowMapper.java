package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.RequetePointService;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RequetePointService}, with proper type conversions.
 */
@Service
public class RequetePointServiceRowMapper implements BiFunction<Row, String, RequetePointService> {

    private final ColumnConverter converter;

    public RequetePointServiceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RequetePointService} stored in the database.
     */
    @Override
    public RequetePointService apply(Row row, String prefix) {
        RequetePointService entity = new RequetePointService();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStockDisponible(converter.fromRow(row, prefix + "_stock_disponible", Double.class));
        entity.setQuantDem(converter.fromRow(row, prefix + "_quant_dem", Double.class));
        entity.setQuantTrs(converter.fromRow(row, prefix + "_quant_trs", Double.class));
        entity.setQuantRec(converter.fromRow(row, prefix + "_quant_rec", Double.class));
        entity.setReqTraitee(converter.fromRow(row, prefix + "_req_traitee", Integer.class));
        entity.setDateReq(converter.fromRow(row, prefix + "_date_req", Instant.class));
        entity.setDateRec(converter.fromRow(row, prefix + "_date_rec", Instant.class));
        entity.setDateTransfert(converter.fromRow(row, prefix + "_date_transfert", Instant.class));
        entity.setPointServiceId(converter.fromRow(row, prefix + "_point_service_id", Long.class));
        return entity;
    }
}
