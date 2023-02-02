package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.RequetePointService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RequetePointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequetePointServiceRepository
    extends ReactiveCrudRepository<RequetePointService, Long>, RequetePointServiceRepositoryInternal {
    @Query("SELECT * FROM requete_point_service entity WHERE entity.point_service_id = :id")
    Flux<RequetePointService> findByPointService(Long id);

    @Query("SELECT * FROM requete_point_service entity WHERE entity.point_service_id IS NULL")
    Flux<RequetePointService> findAllWherePointServiceIsNull();

    @Override
    <S extends RequetePointService> Mono<S> save(S entity);

    @Override
    Flux<RequetePointService> findAll();

    @Override
    Mono<RequetePointService> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RequetePointServiceRepositoryInternal {
    <S extends RequetePointService> Mono<S> save(S entity);

    Flux<RequetePointService> findAllBy(Pageable pageable);

    Flux<RequetePointService> findAll();

    Mono<RequetePointService> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RequetePointService> findAllBy(Pageable pageable, Criteria criteria);

}
