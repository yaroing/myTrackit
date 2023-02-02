package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.SuiviMission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the SuiviMission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuiviMissionRepository extends ReactiveCrudRepository<SuiviMission, Long>, SuiviMissionRepositoryInternal {
    @Override
    <S extends SuiviMission> Mono<S> save(S entity);

    @Override
    Flux<SuiviMission> findAll();

    @Override
    Mono<SuiviMission> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SuiviMissionRepositoryInternal {
    <S extends SuiviMission> Mono<S> save(S entity);

    Flux<SuiviMission> findAllBy(Pageable pageable);

    Flux<SuiviMission> findAll();

    Mono<SuiviMission> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<SuiviMission> findAllBy(Pageable pageable, Criteria criteria);

}
