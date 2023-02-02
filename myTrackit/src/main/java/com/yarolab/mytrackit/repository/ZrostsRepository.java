package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Zrosts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Zrosts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZrostsRepository extends ReactiveCrudRepository<Zrosts, Long>, ZrostsRepositoryInternal {
    @Override
    <S extends Zrosts> Mono<S> save(S entity);

    @Override
    Flux<Zrosts> findAll();

    @Override
    Mono<Zrosts> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ZrostsRepositoryInternal {
    <S extends Zrosts> Mono<S> save(S entity);

    Flux<Zrosts> findAllBy(Pageable pageable);

    Flux<Zrosts> findAll();

    Mono<Zrosts> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Zrosts> findAllBy(Pageable pageable, Criteria criteria);

}
