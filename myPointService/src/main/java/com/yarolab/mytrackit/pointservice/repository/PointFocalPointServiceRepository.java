package com.yarolab.mytrackit.pointservice.repository;

import com.yarolab.mytrackit.pointservice.domain.PointFocalPointService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointFocalPointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointFocalPointServiceRepository extends JpaRepository<PointFocalPointService, Long> {}
