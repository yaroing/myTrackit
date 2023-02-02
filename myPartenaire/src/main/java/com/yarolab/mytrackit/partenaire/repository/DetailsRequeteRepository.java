package com.yarolab.mytrackit.partenaire.repository;

import com.yarolab.mytrackit.partenaire.domain.DetailsRequete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetailsRequete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsRequeteRepository extends JpaRepository<DetailsRequete, Long> {}
