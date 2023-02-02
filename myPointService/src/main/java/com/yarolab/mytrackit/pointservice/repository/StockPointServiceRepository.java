package com.yarolab.mytrackit.pointservice.repository;

import com.yarolab.mytrackit.pointservice.domain.StockPointService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StockPointService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockPointServiceRepository extends JpaRepository<StockPointService, Long> {}
