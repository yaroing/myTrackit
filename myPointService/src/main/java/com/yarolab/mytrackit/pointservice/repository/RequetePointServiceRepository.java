package com.yarolab.mytrackit.pointservice.repository;

import com.yarolab.mytrackit.pointservice.domain.RequetePointService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequetePointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequetePointServiceRepository extends JpaRepository<RequetePointService, Long> {}
