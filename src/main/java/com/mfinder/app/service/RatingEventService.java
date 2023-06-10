package com.mfinder.app.service;

import com.mfinder.app.domain.RatingEvent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mfinder.app.domain.Rating}.
 */
public interface RatingEventService {
    /**
     * Save a rating.
     *
     * @param ratingEvent the entity to save.
     * @return the persisted entity.
     */
    RatingEvent save(RatingEvent ratingEvent);

    /**
     * Updates a rating.
     *
     * @param ratingEvent the entity to update.
     * @return the persisted entity.
     */
    RatingEvent update(RatingEvent ratingEvent);

    /**
     * Partially updates a rating.
     *
     * @param ratingEvent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RatingEvent> partialUpdate(RatingEvent ratingEvent);

    /**
     * Get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RatingEvent> findAll(Pageable pageable);

    /**
     * Get the "id" rating.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RatingEvent> findOne(Long id);

    /**
     * Delete the "id" rating.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get All ratings by the Event Id
     *
     * @param eventId
     * @return the list of entities
     */
    List<RatingEvent> getRatingsByEventId(Long eventId);
}
