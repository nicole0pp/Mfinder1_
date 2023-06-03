package com.mfinder.app.repository;

import com.mfinder.app.domain.RatingSong;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RatingSong entity.
 */
@Repository
public interface RatingSongRepository extends JpaRepository<RatingSong, Long> {}
