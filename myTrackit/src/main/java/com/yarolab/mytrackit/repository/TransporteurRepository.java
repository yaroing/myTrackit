package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Transporteur;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Transporteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporteurRepository extends ReactiveCrudRepository<Transporteur, Long>, TransporteurRepositoryInternal {
    @Override
    <S extends Transporteur> Mono<S> save(S entity);

    @Override
    Flux<Transporteur> findAll();

    @Override
    Mono<Transporteur> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TransporteurRepositoryInternal {
    <S extends Transporteur> Mono<S> save(S entity);

    Flux<Transporteur> findAllBy(Pageable pageable);

    Flux<Transporteur> findAll();

    Mono<Transporteur> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Transporteur> findAllBy(Pageable pageable, Criteria criteria);

}
