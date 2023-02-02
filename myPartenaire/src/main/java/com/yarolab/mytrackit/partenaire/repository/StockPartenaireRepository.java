package com.yarolab.mytrackit.partenaire.repository;

import com.yarolab.mytrackit.partenaire.domain.StockPartenaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StockPartenaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockPartenaireRepository extends JpaRepository<StockPartenaire, Long> {}
