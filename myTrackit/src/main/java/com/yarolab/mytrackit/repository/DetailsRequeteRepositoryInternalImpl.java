package com.yarolab.mytrackit.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.yarolab.mytrackit.domain.DetailsRequete;
import com.yarolab.mytrackit.repository.rowmapper.DetailsRequeteRowMapper;
import com.yarolab.mytrackit.repository.rowmapper.RequetePartenaireRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the DetailsRequete entity.
 */
@SuppressWarnings("unused")
class DetailsRequeteRepositoryInternalImpl extends SimpleR2dbcRepository<DetailsRequete, Long> implements DetailsRequeteRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RequetePartenaireRowMapper requetepartenaireMapper;
    private final DetailsRequeteRowMapper detailsrequeteMapper;

    private static final Table entityTable = Table.aliased("details_requete", EntityManager.ENTITY_ALIAS);
    private static final Table requetePartenaireTable = Table.aliased("requete_partenaire", "requetePartenaire");

    public DetailsRequeteRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RequetePartenaireRowMapper requetepartenaireMapper,
        DetailsRequeteRowMapper detailsrequeteMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(DetailsRequete.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.requetepartenaireMapper = requetepartenaireMapper;
        this.detailsrequeteMapper = detailsrequeteMapper;
    }

    @Override
    public Flux<DetailsRequete> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<DetailsRequete> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = DetailsRequeteSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(RequetePartenaireSqlHelper.getColumns(requetePartenaireTable, "requetePartenaire"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(requetePartenaireTable)
            .on(Column.create("requete_partenaire_id", entityTable))
            .equals(Column.create("id", requetePartenaireTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, DetailsRequete.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<DetailsRequete> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<DetailsRequete> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private DetailsRequete process(Row row, RowMetadata metadata) {
        DetailsRequete entity = detailsrequeteMapper.apply(row, "e");
        entity.setRequetePartenaire(requetepartenaireMapper.apply(row, "requetePartenaire"));
        return entity;
    }

    @Override
    public <S extends DetailsRequete> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
