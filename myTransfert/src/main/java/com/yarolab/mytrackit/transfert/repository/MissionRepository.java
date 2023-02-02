package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.Mission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {}
