package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.Transporteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transporteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporteurRepository extends JpaRepository<Transporteur, Long> {}
