package com.mfinder.app.repository;

import com.mfinder.app.domain.Event;
import com.mfinder.app.service.dto.EventDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Event entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM event e WHERE e.id = :id", nativeQuery = true)
    Optional<EventDTO> findEventById(@Param("id") Long id);
}
