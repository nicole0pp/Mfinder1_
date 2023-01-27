package com.mfinder.app.repository;

import com.mfinder.app.domain.ListDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ListDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListDetailsRepository extends JpaRepository<ListDetails, Long> {}
