package com.mfinder.app.repository;

import com.mfinder.app.domain.FavoriteList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FavoriteList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {}
