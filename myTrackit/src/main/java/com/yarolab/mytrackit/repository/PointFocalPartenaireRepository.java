package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.PointFocalPartenaire;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the PointFocalPartenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointFocalPartenaireRepository
    extends ReactiveCrudRepository<PointFocalPartenaire, Long>, PointFocalPartenaireRepositoryInternal {
    @Override
    <S extends PointFocalPartenaire> Mono<S> save(S entity);

    @Override
    Flux<PointFocalPartenaire> findAll();

    @Override
    Mono<PointFocalPartenaire> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PointFocalPartenaireRepositoryInternal {
    <S extends PointFocalPartenaire> Mono<S> save(S entity);

    Flux<PointFocalPartenaire> findAllBy(Pageable pageable);

    Flux<PointFocalPartenaire> findAll();

    Mono<PointFocalPartenaire> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<PointFocalPartenaire> findAllBy(Pageable pageable, Criteria criteria);

}
