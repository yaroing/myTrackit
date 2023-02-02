package com.yarolab.mytrackit.partenaire.repository;

import com.yarolab.mytrackit.partenaire.domain.RequetePartenaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequetePartenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequetePartenaireRepository extends JpaRepository<RequetePartenaire, Long> {}
