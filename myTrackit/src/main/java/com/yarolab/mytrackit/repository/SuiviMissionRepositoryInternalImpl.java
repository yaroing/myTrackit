package com.yarolab.mytrackit.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.yarolab.mytrackit.domain.SuiviMission;
import com.yarolab.mytrackit.repository.rowmapper.SuiviMissionRowMapper;
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
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the SuiviMission entity.
 */
@SuppressWarnings("unused")
class SuiviMissionRepositoryInternalImpl extends SimpleR2dbcRepository<SuiviMission, Long> implements SuiviMissionRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final SuiviMissionRowMapper suivimissionMapper;

    private static final Table entityTable = Table.aliased("suivi_mission", EntityManager.ENTITY_ALIAS);

    public SuiviMissionRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        SuiviMissionRowMapper suivimissionMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(SuiviMission.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.suivimissionMapper = suivimissionMapper;
    }

    @Override
    public Flux<SuiviMission> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<SuiviMission> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = SuiviMissionSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, SuiviMission.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<SuiviMission> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<SuiviMission> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private SuiviMission process(Row row, RowMetadata metadata) {
        SuiviMission entity = suivimissionMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends SuiviMission> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
