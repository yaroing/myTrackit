package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.RequetePartenaire;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RequetePartenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequetePartenaireRepository extends ReactiveCrudRepository<RequetePartenaire, Long>, RequetePartenaireRepositoryInternal {
    @Override
    <S extends RequetePartenaire> Mono<S> save(S entity);

    @Override
    Flux<RequetePartenaire> findAll();

    @Override
    Mono<RequetePartenaire> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RequetePartenaireRepositoryInternal {
    <S extends RequetePartenaire> Mono<S> save(S entity);

    Flux<RequetePartenaire> findAllBy(Pageable pageable);

    Flux<RequetePartenaire> findAll();

    Mono<RequetePartenaire> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RequetePartenaire> findAllBy(Pageable pageable, Criteria criteria);

}
