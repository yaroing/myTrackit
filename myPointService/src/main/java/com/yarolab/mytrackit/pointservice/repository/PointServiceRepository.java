package com.yarolab.mytrackit.pointservice.repository;

import com.yarolab.mytrackit.pointservice.domain.PointService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointServiceRepository extends JpaRepository<PointService, Long> {}
