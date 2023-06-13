package com.mfinder.app.repository;

import com.mfinder.app.domain.Album;
import com.mfinder.app.domain.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Album entity.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT a FROM Album a JOIN FETCH a.songs WHERE a.id = :id")
    Album findAllWithSongsById(@Param("id") Long id);

    @Query("SELECT a FROM Album a WHERE a.id = :id")
    Album findAlbumById(@Param("id") Long id);
    // @Query(value = "SELECT * FROM album a WHERE a.id = :id", nativeQuery = true)
    // Album getAlbumByAlbumId(@Param("id") Long id);

}
