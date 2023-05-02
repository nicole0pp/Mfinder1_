package com.mfinder.app.web.rest;

import com.mfinder.app.domain.Rating;
import com.mfinder.app.repository.RatingRepository;
import com.mfinder.app.service.RatingService;
import com.mfinder.app.service.dto.RatingDTO;
import com.mfinder.app.service.impl.RatingServiceImpl;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mfinder.app.domain.Rating}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RatingResource {

    private final Logger log = LoggerFactory.getLogger(RatingResource.class);

    private static final String ENTITY_NAME = "rating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingService ratingService;

    private final RatingRepository ratingRepository;

    private final RatingServiceImpl ratingServiceImpl;

    public RatingResource(RatingService ratingService, RatingRepository ratingRepository, RatingServiceImpl ratingServiceImpl) {
        this.ratingService = ratingService;
        this.ratingRepository = ratingRepository;
        this.ratingServiceImpl = ratingServiceImpl;
    }

    /**
     * {@code POST  /ratings} : Create a new rating.
     *
     * @param rating the rating to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rating, or with status {@code 400 (Bad Request)} if the rating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ratings")
    public ResponseEntity<RatingDTO> createRating(@Valid @RequestBody RatingDTO ratingDTO) throws URISyntaxException {
        log.debug("REST request to save RatingDTO : {}", ratingDTO);
        if (ratingDTO.getId() != null) {
            throw new BadRequestAlertException("A new rating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingDTO result = ratingService.save(ratingDTO);
        return ResponseEntity
            .created(new URI("/api/ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ratings/:id} : Updates an existing rating.
     *
     * @param id the id of the rating to save.
     * @param ratingDTO the rating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rating,
     * or with status {@code 400 (Bad Request)} if the rating is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ratings/{id}")
    public ResponseEntity<RatingDTO> updateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RatingDTO ratingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rating : {}, {}", id, ratingDTO);
        if (ratingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingDTO result = ratingService.update(ratingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ratings/:id} : Partial updates given fields of an existing rating, field will ignore if it is null
     *
     * @param id the id of the rating to save.
     * @param artistDTO the rating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rating,
     * or with status {@code 400 (Bad Request)} if the rating is not valid,
     * or with status {@code 404 (Not Found)} if the rating is not found,
     * or with status {@code 500 (Internal Server Error)} if the rating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ratings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingDTO> partialUpdateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RatingDTO ratingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rating partially : {}, {}", id, ratingDTO);
        if (ratingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingDTO> result = ratingService.partialUpdate(ratingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ratings} : get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratings in body.
     */
    @GetMapping("/ratings")
    public ResponseEntity<List<RatingDTO>> getAllRatings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ratings");
        Page<RatingDTO> page = ratingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ratings/:id} : get the "id" rating.
     *
     * @param id the id of the rating to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rating, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ratings/{id}")
    public ResponseEntity<RatingDTO> getRating(@PathVariable Long id) {
        log.debug("REST request to get Rating : {}", id);
        Optional<RatingDTO> ratingDTO = ratingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingDTO);
    }

    /**
     * {@code DELETE  /ratings/:id} : delete the "id" rating.
     *
     * @param id the id of the rating to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.debug("REST request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
