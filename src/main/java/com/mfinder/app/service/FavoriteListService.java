package com.mfinder.app.service;

import com.mfinder.app.service.dto.FavoriteListDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mfinder.app.domain.FavoriteList}.
 */
public interface FavoriteListService {
    /**
     * Save a favoriteList.
     *
     * @param favoriteListDTO the entity to save.
     * @return the persisted entity.
     */
    FavoriteListDTO save(FavoriteListDTO favoriteListDTO);

    /**
     * Updates a favoriteList.
     *
     * @param favoriteListDTO the entity to update.
     * @return the persisted entity.
     */
    FavoriteListDTO update(FavoriteListDTO favoriteListDTO);

    /**
     * Partially updates a favoriteList.
     *
     * @param favoriteListDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FavoriteListDTO> partialUpdate(FavoriteListDTO favoriteListDTO);

    /**
     * Get all the favoriteLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FavoriteListDTO> findAll(Pageable pageable);

    /**
     * Get the "id" favoriteList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FavoriteListDTO> findOne(Long id);

    /**
     * Delete the "id" favoriteList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
