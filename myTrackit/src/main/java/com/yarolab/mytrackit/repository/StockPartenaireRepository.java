package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.StockPartenaire;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the StockPartenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockPartenaireRepository extends ReactiveCrudRepository<StockPartenaire, Long>, StockPartenaireRepositoryInternal {
    @Override
    <S extends StockPartenaire> Mono<S> save(S entity);

    @Override
    Flux<StockPartenaire> findAll();

    @Override
    Mono<StockPartenaire> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface StockPartenaireRepositoryInternal {
    <S extends StockPartenaire> Mono<S> save(S entity);

    Flux<StockPartenaire> findAllBy(Pageable pageable);

    Flux<StockPartenaire> findAll();

    Mono<StockPartenaire> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<StockPartenaire> findAllBy(Pageable pageable, Criteria criteria);

}
