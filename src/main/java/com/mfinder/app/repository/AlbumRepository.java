package com.mfinder.app.repository;

import com.mfinder.app.domain.Album;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Album entity.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {}
