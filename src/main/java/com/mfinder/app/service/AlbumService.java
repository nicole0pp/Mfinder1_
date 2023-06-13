package com.mfinder.app.service;

import com.mfinder.app.domain.Album;
import com.mfinder.app.service.dto.AlbumDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mfinder.app.domain.Album}.
 */
public interface AlbumService {
    /**
     * Save a album.
     *
     * @param albumDTO the entity to save.
     * @return the persisted entity.
     */
    AlbumDTO save(AlbumDTO albumDTO);

    /**
     * Updates a album.
     *
     * @param albumDTO the entity to update.
     * @return the persisted entity.
     */
    AlbumDTO update(AlbumDTO albumDTO);

    /**
     * Partially updates a album.
     *
     * @param albumDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlbumDTO> partialUpdate(AlbumDTO albumDTO);

    /**
     * Get all the albums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlbumDTO> findAll(Pageable pageable);

    /**
     * Get the "id" album.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlbumDTO> findOne(Long id);

    /**
     * Delete the "id" album.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the album by id.
     * @param id the id of the album.
     */
    Album findOneById(Long id);
}
