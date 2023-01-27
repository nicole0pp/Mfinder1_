package com.mfinder.app.service;

import com.mfinder.app.service.dto.ListDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mfinder.app.domain.ListDetails}.
 */
public interface ListDetailsService {
    /**
     * Save a listDetails.
     *
     * @param listDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ListDetailsDTO save(ListDetailsDTO listDetailsDTO);

    /**
     * Updates a listDetails.
     *
     * @param listDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    ListDetailsDTO update(ListDetailsDTO listDetailsDTO);

    /**
     * Partially updates a listDetails.
     *
     * @param listDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ListDetailsDTO> partialUpdate(ListDetailsDTO listDetailsDTO);

    /**
     * Get all the listDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ListDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" listDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" listDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
