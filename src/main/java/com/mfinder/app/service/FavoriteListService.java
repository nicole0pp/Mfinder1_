package com.mfinder.app.service;

import com.mfinder.app.domain.FavoriteList;
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
     * @param favoriteList the entity to save.
     * @return the persisted entity.
     */
    FavoriteList save(FavoriteList favoriteList);

    /**
     * Updates a favoriteList.
     *
     * @param favoriteList the entity to update.
     * @return the persisted entity.
     */
    FavoriteList update(FavoriteList favoriteList);

    /**
     * Partially updates a favoriteList.
     *
     * @param favoriteList the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FavoriteList> partialUpdate(FavoriteList favoriteList);

    /**
     * Get all the favoriteLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FavoriteList> findAll(Pageable pageable);

    /**
     * Get the "id" favoriteList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FavoriteList> findOne(Long id);

    /**
     * Delete the "id" favoriteList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
