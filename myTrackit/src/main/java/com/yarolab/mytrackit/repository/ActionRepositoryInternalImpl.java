package com.yarolab.mytrackit.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.yarolab.mytrackit.domain.Action;
import com.yarolab.mytrackit.repository.rowmapper.ActionRowMapper;
import com.yarolab.mytrackit.repository.rowmapper.TransfertRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.Instant;
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
 * Spring Data R2DBC custom repository implementation for the Action entity.
 */
@SuppressWarnings("unused")
class ActionRepositoryInternalImpl extends SimpleR2dbcRepository<Action, Long> implements ActionRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TransfertRowMapper transfertMapper;
    private final ActionRowMapper actionMapper;

    private static final Table entityTable = Table.aliased("action", EntityManager.ENTITY_ALIAS);
    private static final Table transfertTable = Table.aliased("transfert", "transfert");

    public ActionRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TransfertRowMapper transfertMapper,
        ActionRowMapper actionMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Action.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.transfertMapper = transfertMapper;
        this.actionMapper = actionMapper;
    }

    @Override
    public Flux<Action> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Action> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ActionSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TransfertSqlHelper.getColumns(transfertTable, "transfert"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(transfertTable)
            .on(Column.create("transfert_id", entityTable))
            .equals(Column.create("id", transfertTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Action.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Action> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Action> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Action process(Row row, RowMetadata metadata) {
        Action entity = actionMapper.apply(row, "e");
        entity.setTransfert(transfertMapper.apply(row, "transfert"));
        return entity;
    }

    @Override
    public <S extends Action> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
