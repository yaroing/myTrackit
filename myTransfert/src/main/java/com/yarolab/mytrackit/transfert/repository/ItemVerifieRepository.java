package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.ItemVerifie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemVerifie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemVerifieRepository extends JpaRepository<ItemVerifie, Long> {}
