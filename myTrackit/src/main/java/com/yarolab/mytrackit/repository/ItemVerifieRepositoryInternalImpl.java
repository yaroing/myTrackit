package com.yarolab.mytrackit.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.yarolab.mytrackit.domain.ItemVerifie;
import com.yarolab.mytrackit.repository.rowmapper.ItemVerifieRowMapper;
import com.yarolab.mytrackit.repository.rowmapper.MissionRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the ItemVerifie entity.
 */
@SuppressWarnings("unused")
class ItemVerifieRepositoryInternalImpl extends SimpleR2dbcRepository<ItemVerifie, Long> implements ItemVerifieRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final MissionRowMapper missionMapper;
    private final ItemVerifieRowMapper itemverifieMapper;

    private static final Table entityTable = Table.aliased("item_verifie", EntityManager.ENTITY_ALIAS);
    private static final Table missionTable = Table.aliased("mission", "mission");

    public ItemVerifieRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        MissionRowMapper missionMapper,
        ItemVerifieRowMapper itemverifieMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ItemVerifie.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.missionMapper = missionMapper;
        this.itemverifieMapper = itemverifieMapper;
    }

    @Override
    public Flux<ItemVerifie> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ItemVerifie> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ItemVerifieSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(MissionSqlHelper.getColumns(missionTable, "mission"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(missionTable)
            .on(Column.create("mission_id", entityTable))
            .equals(Column.create("id", missionTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ItemVerifie.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ItemVerifie> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ItemVerifie> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private ItemVerifie process(Row row, RowMetadata metadata) {
        ItemVerifie entity = itemverifieMapper.apply(row, "e");
        entity.setMission(missionMapper.apply(row, "mission"));
        return entity;
    }

    @Override
    public <S extends ItemVerifie> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
