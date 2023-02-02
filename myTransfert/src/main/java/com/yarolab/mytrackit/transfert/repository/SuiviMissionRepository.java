package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.SuiviMission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SuiviMission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuiviMissionRepository extends JpaRepository<SuiviMission, Long> {}
