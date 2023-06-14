package com.mfinder.app.repository;

import com.mfinder.app.domain.RatingEvent;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingEventRepository extends JpaRepository<RatingEvent, Long> {
    @Query(value = "SELECT * FROM rating_event r WHERE r.created_by = :login", nativeQuery = true)
    List<RatingEvent> findRatingByUserLogin(@Param("login") String login);

    @Query("SELECT r FROM RatingEvent r WHERE r.event.id = :eventId")
    List<RatingEvent> findRatingsByEventId(@Param("eventId") Long eventId);
}
