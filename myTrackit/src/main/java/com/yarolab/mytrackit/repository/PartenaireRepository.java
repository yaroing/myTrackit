package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Partenaire;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Partenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartenaireRepository extends ReactiveCrudRepository<Partenaire, Long>, PartenaireRepositoryInternal {
    @Override
    <S extends Partenaire> Mono<S> save(S entity);

    @Override
    Flux<Partenaire> findAll();

    @Override
    Mono<Partenaire> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PartenaireRepositoryInternal {
    <S extends Partenaire> Mono<S> save(S entity);

    Flux<Partenaire> findAllBy(Pageable pageable);

    Flux<Partenaire> findAll();

    Mono<Partenaire> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Partenaire> findAllBy(Pageable pageable, Criteria criteria);

}
