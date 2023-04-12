package com.mfinder.app.repository;

import com.mfinder.app.domain.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(value = "SELECT * FROM artist a WHERE a.user_id = :id", nativeQuery = true)
    Artist findArtistByUserId(@Param("id") Long id);
}
