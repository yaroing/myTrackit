package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.Section;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Section entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionRepository extends ReactiveCrudRepository<Section, Long>, SectionRepositoryInternal {
    @Override
    <S extends Section> Mono<S> save(S entity);

    @Override
    Flux<Section> findAll();

    @Override
    Mono<Section> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SectionRepositoryInternal {
    <S extends Section> Mono<S> save(S entity);

    Flux<Section> findAllBy(Pageable pageable);

    Flux<Section> findAll();

    Mono<Section> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Section> findAllBy(Pageable pageable, Criteria criteria);

}
