package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.Transfert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransfertRepository extends JpaRepository<Transfert, Long> {}
