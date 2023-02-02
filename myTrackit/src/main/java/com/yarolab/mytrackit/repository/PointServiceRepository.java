package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.PointService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the PointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointServiceRepository extends ReactiveCrudRepository<PointService, Long>, PointServiceRepositoryInternal {
    @Override
    <S extends PointService> Mono<S> save(S entity);

    @Override
    Flux<PointService> findAll();

    @Override
    Mono<PointService> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PointServiceRepositoryInternal {
    <S extends PointService> Mono<S> save(S entity);

    Flux<PointService> findAllBy(Pageable pageable);

    Flux<PointService> findAll();

    Mono<PointService> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<PointService> findAllBy(Pageable pageable, Criteria criteria);

}
