package com.yarolab.mytrackit.coreservice.repository;

import com.yarolab.mytrackit.coreservice.domain.Zrosts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zrosts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZrostsRepository extends JpaRepository<Zrosts, Long> {}
