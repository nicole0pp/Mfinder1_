package com.mfinder.app.repository;

import com.mfinder.app.domain.Client;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT * FROM client c WHERE c.user_id = :id", nativeQuery = true)
    Client findByUserId(@Param("id") Long id);
}
