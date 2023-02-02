package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.StockPointService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the StockPointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockPointServiceRepository extends ReactiveCrudRepository<StockPointService, Long>, StockPointServiceRepositoryInternal {
    @Query("SELECT * FROM stock_point_service entity WHERE entity.point_service_id = :id")
    Flux<StockPointService> findByPointService(Long id);

    @Query("SELECT * FROM stock_point_service entity WHERE entity.point_service_id IS NULL")
    Flux<StockPointService> findAllWherePointServiceIsNull();

    @Override
    <S extends StockPointService> Mono<S> save(S entity);

    @Override
    Flux<StockPointService> findAll();

    @Override
    Mono<StockPointService> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface StockPointServiceRepositoryInternal {
    <S extends StockPointService> Mono<S> save(S entity);

    Flux<StockPointService> findAllBy(Pageable pageable);

    Flux<StockPointService> findAll();

    Mono<StockPointService> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<StockPointService> findAllBy(Pageable pageable, Criteria criteria);

}
