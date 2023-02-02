package com.yarolab.mytrackit.coreservice.repository;

import com.yarolab.mytrackit.coreservice.domain.Catalogue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Catalogue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {}
