package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Mission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissionRepository extends ReactiveCrudRepository<Mission, Long>, MissionRepositoryInternal {
    @Override
    <S extends Mission> Mono<S> save(S entity);

    @Override
    Flux<Mission> findAll();

    @Override
    Mono<Mission> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface MissionRepositoryInternal {
    <S extends Mission> Mono<S> save(S entity);

    Flux<Mission> findAllBy(Pageable pageable);

    Flux<Mission> findAll();

    Mono<Mission> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Mission> findAllBy(Pageable pageable, Criteria criteria);

}
