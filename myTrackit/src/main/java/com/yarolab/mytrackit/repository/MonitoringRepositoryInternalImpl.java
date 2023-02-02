package com.yarolab.mytrackit.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.yarolab.mytrackit.domain.Monitoring;
import com.yarolab.mytrackit.repository.rowmapper.MonitoringRowMapper;
import com.yarolab.mytrackit.repository.rowmapper.PointServiceRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Monitoring entity.
 */
@SuppressWarnings("unused")
class MonitoringRepositoryInternalImpl extends SimpleR2dbcRepository<Monitoring, Long> implements MonitoringRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PointServiceRowMapper pointserviceMapper;
    private final MonitoringRowMapper monitoringMapper;

    private static final Table entityTable = Table.aliased("monitoring", EntityManager.ENTITY_ALIAS);
    private static final Table pointServiceTable = Table.aliased("point_service", "pointService");

    public MonitoringRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PointServiceRowMapper pointserviceMapper,
        MonitoringRowMapper monitoringMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Monitoring.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.pointserviceMapper = pointserviceMapper;
        this.monitoringMapper = monitoringMapper;
    }

    @Override
    public Flux<Monitoring> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Monitoring> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = MonitoringSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PointServiceSqlHelper.getColumns(pointServiceTable, "pointService"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(pointServiceTable)
            .on(Column.create("point_service_id", entityTable))
            .equals(Column.create("id", pointServiceTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Monitoring.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Monitoring> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Monitoring> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Monitoring process(Row row, RowMetadata metadata) {
        Monitoring entity = monitoringMapper.apply(row, "e");
        entity.setPointService(pointserviceMapper.apply(row, "pointService"));
        return entity;
    }

    @Override
    public <S extends Monitoring> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
