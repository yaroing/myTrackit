package com.yarolab.mytrackit.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.yarolab.mytrackit.domain.ItemTransfert;
import com.yarolab.mytrackit.repository.rowmapper.ItemTransfertRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the ItemTransfert entity.
 */
@SuppressWarnings("unused")
class ItemTransfertRepositoryInternalImpl extends SimpleR2dbcRepository<ItemTransfert, Long> implements ItemTransfertRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TransfertRowMapper transfertMapper;
    private final ItemTransfertRowMapper itemtransfertMapper;

    private static final Table entityTable = Table.aliased("item_transfert", EntityManager.ENTITY_ALIAS);
    private static final Table transfertTable = Table.aliased("transfert", "transfert");

    public ItemTransfertRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TransfertRowMapper transfertMapper,
        ItemTransfertRowMapper itemtransfertMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ItemTransfert.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.transfertMapper = transfertMapper;
        this.itemtransfertMapper = itemtransfertMapper;
    }

    @Override
    public Flux<ItemTransfert> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ItemTransfert> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ItemTransfertSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TransfertSqlHelper.getColumns(transfertTable, "transfert"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(transfertTable)
            .on(Column.create("transfert_id", entityTable))
            .equals(Column.create("id", transfertTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ItemTransfert.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ItemTransfert> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ItemTransfert> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private ItemTransfert process(Row row, RowMetadata metadata) {
        ItemTransfert entity = itemtransfertMapper.apply(row, "e");
        entity.setTransfert(transfertMapper.apply(row, "transfert"));
        return entity;
    }

    @Override
    public <S extends ItemTransfert> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
