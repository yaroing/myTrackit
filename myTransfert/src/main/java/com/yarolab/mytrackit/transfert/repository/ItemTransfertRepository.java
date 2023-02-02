package com.yarolab.mytrackit.transfert.repository;

import com.yarolab.mytrackit.transfert.domain.ItemTransfert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemTransfertRepository extends JpaRepository<ItemTransfert, Long> {}
