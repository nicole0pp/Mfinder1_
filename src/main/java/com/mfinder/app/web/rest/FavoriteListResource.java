package com.mfinder.app.web.rest;

import com.mfinder.app.repository.FavoriteListRepository;
import com.mfinder.app.service.FavoriteListService;
import com.mfinder.app.service.dto.FavoriteListDTO;
import com.mfinder.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mfinder.app.domain.FavoriteList}.
 */
@RestController
@RequestMapping("/api")
public class FavoriteListResource {

    private final Logger log = LoggerFactory.getLogger(FavoriteListResource.class);

    private static final String ENTITY_NAME = "favoriteList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavoriteListService favoriteListService;

    private final FavoriteListRepository favoriteListRepository;

    public FavoriteListResource(FavoriteListService favoriteListService, FavoriteListRepository favoriteListRepository) {
        this.favoriteListService = favoriteListService;
        this.favoriteListRepository = favoriteListRepository;
    }

    /**
     * {@code POST  /favorite-lists} : Create a new favoriteList.
     *
     * @param favoriteListDTO the favoriteListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoriteListDTO, or with status {@code 400 (Bad Request)} if the favoriteList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favorite-lists")
    public ResponseEntity<FavoriteListDTO> createFavoriteList(@Valid @RequestBody FavoriteListDTO favoriteListDTO)
        throws URISyntaxException {
        log.debug("REST request to save FavoriteList : {}", favoriteListDTO);
        if (favoriteListDTO.getId() != null) {
            throw new BadRequestAlertException("A new favoriteList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavoriteListDTO result = favoriteListService.save(favoriteListDTO);
        return ResponseEntity
            .created(new URI("/api/favorite-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favorite-lists/:id} : Updates an existing favoriteList.
     *
     * @param id the id of the favoriteListDTO to save.
     * @param favoriteListDTO the favoriteListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoriteListDTO,
     * or with status {@code 400 (Bad Request)} if the favoriteListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favoriteListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favorite-lists/{id}")
    public ResponseEntity<FavoriteListDTO> updateFavoriteList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FavoriteListDTO favoriteListDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FavoriteList : {}, {}", id, favoriteListDTO);
        if (favoriteListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favoriteListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favoriteListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FavoriteListDTO result = favoriteListService.update(favoriteListDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoriteListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /favorite-lists/:id} : Partial updates given fields of an existing favoriteList, field will ignore if it is null
     *
     * @param id the id of the favoriteListDTO to save.
     * @param favoriteListDTO the favoriteListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoriteListDTO,
     * or with status {@code 400 (Bad Request)} if the favoriteListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the favoriteListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the favoriteListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/favorite-lists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FavoriteListDTO> partialUpdateFavoriteList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FavoriteListDTO favoriteListDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FavoriteList partially : {}, {}", id, favoriteListDTO);
        if (favoriteListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favoriteListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favoriteListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FavoriteListDTO> result = favoriteListService.partialUpdate(favoriteListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoriteListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /favorite-lists} : get all the favoriteLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favoriteLists in body.
     */
    @GetMapping("/favorite-lists")
    public ResponseEntity<List<FavoriteListDTO>> getAllFavoriteLists(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FavoriteLists");
        Page<FavoriteListDTO> page = favoriteListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /favorite-lists/:id} : get the "id" favoriteList.
     *
     * @param id the id of the favoriteListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favoriteListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favorite-lists/{id}")
    public ResponseEntity<FavoriteListDTO> getFavoriteList(@PathVariable Long id) {
        log.debug("REST request to get FavoriteList : {}", id);
        Optional<FavoriteListDTO> favoriteListDTO = favoriteListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favoriteListDTO);
    }

    /**
     * {@code DELETE  /favorite-lists/:id} : delete the "id" favoriteList.
     *
     * @param id the id of the favoriteListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favorite-lists/{id}")
    public ResponseEntity<Void> deleteFavoriteList(@PathVariable Long id) {
        log.debug("REST request to delete FavoriteList : {}", id);
        favoriteListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
