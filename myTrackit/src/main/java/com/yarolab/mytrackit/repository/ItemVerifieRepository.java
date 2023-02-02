package com.yarolab.mytrackit.repository;

import com.yarolab.mytrackit.domain.ItemVerifie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ItemVerifie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemVerifieRepository extends ReactiveCrudRepository<ItemVerifie, Long>, ItemVerifieRepositoryInternal {
    @Query("SELECT * FROM item_verifie entity WHERE entity.mission_id = :id")
    Flux<ItemVerifie> findByMission(Long id);

    @Query("SELECT * FROM item_verifie entity WHERE entity.mission_id IS NULL")
    Flux<ItemVerifie> findAllWhereMissionIsNull();

    @Override
    <S extends ItemVerifie> Mono<S> save(S entity);

    @Override
    Flux<ItemVerifie> findAll();

    @Override
    Mono<ItemVerifie> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ItemVerifieRepositoryInternal {
    <S extends ItemVerifie> Mono<S> save(S entity);

    Flux<ItemVerifie> findAllBy(Pageable pageable);

    Flux<ItemVerifie> findAll();

    Mono<ItemVerifie> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ItemVerifie> findAllBy(Pageable pageable, Criteria criteria);

}
