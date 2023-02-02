package com.yarolab.mytrackit.partenaire.repository;

import com.yarolab.mytrackit.partenaire.domain.PointFocalPartenaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointFocalPartenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointFocalPartenaireRepository extends JpaRepository<PointFocalPartenaire, Long> {}
