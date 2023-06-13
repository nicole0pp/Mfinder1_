package com.mfinder.app.repository;

import com.mfinder.app.domain.Event;
import com.mfinder.app.domain.Song;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Song entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query(value = "SELECT * FROM song s WHERE s.album_id = :album", nativeQuery = true)
    Set<Song> getAllSongsByEvent(@Param("album") Long id);
}
