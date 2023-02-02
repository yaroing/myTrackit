package com.yarolab.mytrackit.repository.rowmapper;

import com.yarolab.mytrackit.domain.Section;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Section}, with proper type conversions.
 */
@Service
public class SectionRowMapper implements BiFunction<Row, String, Section> {

    private final ColumnConverter converter;

    public SectionRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Section} stored in the database.
     */
    @Override
    public Section apply(Row row, String prefix) {
        Section entity = new Section();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setSectionNom(converter.fromRow(row, prefix + "_section_nom", String.class));
        entity.setChefSection(converter.fromRow(row, prefix + "_chef_section", String.class));
        entity.setEmailChef(converter.fromRow(row, prefix + "_email_chef", String.class));
        entity.setPhoneChef(converter.fromRow(row, prefix + "_phone_chef", String.class));
        return entity;
    }
}
