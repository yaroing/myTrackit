package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Transfert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Transfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransfertRepository extends ReactiveCrudRepository<Transfert, Long>, TransfertRepositoryInternal {
    @Override
    <S extends Transfert> Mono<S> save(S entity);

    @Override
    Flux<Transfert> findAll();

    @Override
    Mono<Transfert> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TransfertRepositoryInternal {
    <S extends Transfert> Mono<S> save(S entity);

    Flux<Transfert> findAllBy(Pageable pageable);

    Flux<Transfert> findAll();

    Mono<Transfert> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Transfert> findAllBy(Pageable pageable, Criteria criteria);

}
