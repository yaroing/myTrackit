package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Monitoring;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Monitoring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonitoringRepository extends ReactiveCrudRepository<Monitoring, Long>, MonitoringRepositoryInternal {
    @Query("SELECT * FROM monitoring entity WHERE entity.point_service_id = :id")
    Flux<Monitoring> findByPointService(Long id);

    @Query("SELECT * FROM monitoring entity WHERE entity.point_service_id IS NULL")
    Flux<Monitoring> findAllWherePointServiceIsNull();

    @Override
    <S extends Monitoring> Mono<S> save(S entity);

    @Override
    Flux<Monitoring> findAll();

    @Override
    Mono<Monitoring> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface MonitoringRepositoryInternal {
    <S extends Monitoring> Mono<S> save(S entity);

    Flux<Monitoring> findAllBy(Pageable pageable);

    Flux<Monitoring> findAll();

    Mono<Monitoring> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Monitoring> findAllBy(Pageable pageable, Criteria criteria);

}
