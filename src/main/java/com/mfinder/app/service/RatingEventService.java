package com.mfinder.app.service;

import com.mfinder.app.service.dto.RatingEventDTO;
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
     * @param ratingEventDTO the entity to save.
     * @return the persisted entity.
     */
    RatingEventDTO save(RatingEventDTO ratingEventDTO);

    /**
     * Updates a rating.
     *
     * @param ratingEventDTO the entity to update.
     * @return the persisted entity.
     */
    RatingEventDTO update(RatingEventDTO ratingEventDTO);

    /**
     * Partially updates a rating.
     *
     * @param ratingEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RatingEventDTO> partialUpdate(RatingEventDTO ratingEventDTO);

    /**
     * Get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RatingEventDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rating.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RatingEventDTO> findOne(Long id);

    /**
     * Delete the "id" rating.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
