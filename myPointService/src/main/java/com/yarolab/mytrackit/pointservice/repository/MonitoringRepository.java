package com.yarolab.mytrackit.pointservice.repository;

import com.yarolab.mytrackit.pointservice.domain.Monitoring;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Monitoring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonitoringRepository extends JpaRepository<Monitoring, Long> {}
