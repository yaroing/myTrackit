package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.TypeAction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the TypeAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeActionRepository extends ReactiveCrudRepository<TypeAction, Long>, TypeActionRepositoryInternal {
    @Override
    <S extends TypeAction> Mono<S> save(S entity);

    @Override
    Flux<TypeAction> findAll();

    @Override
    Mono<TypeAction> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TypeActionRepositoryInternal {
    <S extends TypeAction> Mono<S> save(S entity);

    Flux<TypeAction> findAllBy(Pageable pageable);

    Flux<TypeAction> findAll();

    Mono<TypeAction> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<TypeAction> findAllBy(Pageable pageable, Criteria criteria);

}
