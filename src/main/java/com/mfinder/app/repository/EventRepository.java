package com.mfinder.app.repository;

import com.mfinder.app.domain.Event;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Event entity.
 */
@Repository
public interface EventRepository extends EventRepositoryWithBagRelationships, JpaRepository<Event, Long> {
    default Optional<Event> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Event> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Event> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query(value = "SELECT * FROM event e WHERE e.id = :id", nativeQuery = true)
    Optional<Event> findEventById(@Param("id") Long id);

    @Query(value = "SELECT * FROM event e WHERE e.city = :city", nativeQuery = true)
    List<Event> getAllEventByCity(@Param("city") String city);

    @Query(value = "SELECT * FROM event e WHERE e.end_date < :date ORDER BY end_date DESC", nativeQuery = true)
    List<Event> getAllPastEvents(@Param("date") Instant date);

    @Query(value = "SELECT * FROM event e WHERE e.created_By = :user", nativeQuery = true)
    List<Event> getAllEventsByUser(@Param("user") String user);
}
