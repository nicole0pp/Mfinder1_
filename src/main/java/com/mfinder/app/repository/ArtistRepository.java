package com.mfinder.app.repository;

import com.mfinder.app.domain.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    // @Query("select * from artist where user_id =:id")
    // List<Artist> findArtistByUserId(@Param("id")Long user_id);
}
