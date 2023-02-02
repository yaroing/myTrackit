package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.DetailsRequete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DetailsRequete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsRequeteRepository extends ReactiveCrudRepository<DetailsRequete, Long>, DetailsRequeteRepositoryInternal {
    @Query("SELECT * FROM details_requete entity WHERE entity.requete_partenaire_id = :id")
    Flux<DetailsRequete> findByRequetePartenaire(Long id);

    @Query("SELECT * FROM details_requete entity WHERE entity.requete_partenaire_id IS NULL")
    Flux<DetailsRequete> findAllWhereRequetePartenaireIsNull();

    @Override
    <S extends DetailsRequete> Mono<S> save(S entity);

    @Override
    Flux<DetailsRequete> findAll();

    @Override
    Mono<DetailsRequete> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DetailsRequeteRepositoryInternal {
    <S extends DetailsRequete> Mono<S> save(S entity);

    Flux<DetailsRequete> findAllBy(Pageable pageable);

    Flux<DetailsRequete> findAll();

    Mono<DetailsRequete> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DetailsRequete> findAllBy(Pageable pageable, Criteria criteria);

}
