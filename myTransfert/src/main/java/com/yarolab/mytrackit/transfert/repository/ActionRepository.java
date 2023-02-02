package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.Action;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Action entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {}
