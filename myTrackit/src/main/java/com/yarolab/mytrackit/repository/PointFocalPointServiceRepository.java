package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.PointFocalPointService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the PointFocalPointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointFocalPointServiceRepository
    extends ReactiveCrudRepository<PointFocalPointService, Long>, PointFocalPointServiceRepositoryInternal {
    @Override
    <S extends PointFocalPointService> Mono<S> save(S entity);

    @Override
    Flux<PointFocalPointService> findAll();

    @Override
    Mono<PointFocalPointService> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PointFocalPointServiceRepositoryInternal {
    <S extends PointFocalPointService> Mono<S> save(S entity);

    Flux<PointFocalPointService> findAllBy(Pageable pageable);

    Flux<PointFocalPointService> findAll();

    Mono<PointFocalPointService> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<PointFocalPointService> findAllBy(Pageable pageable, Criteria criteria);

}
