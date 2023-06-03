package com.mfinder.app.web.rest;

import com.mfinder.app.domain.RatingSong;
import com.mfinder.app.repository.RatingSongRepository;
import com.mfinder.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mfinder.app.domain.RatingSong}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RatingSongResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "comment", "ratingSong", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate")
    );

    private final Logger log = LoggerFactory.getLogger(RatingSongResource.class);

    private static final String ENTITY_NAME = "ratingSong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingSongRepository ratingSongRepository;

    public RatingSongResource(RatingSongRepository ratingSongRepository) {
        this.ratingSongRepository = ratingSongRepository;
    }

    /**
     * {@code POST  /ratingsSong} : Create a new ratingSong.
     *
     * @param ratingSong the ratingSong to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingSong, or with status {@code 400 (Bad Request)} if the ratingSong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ratingsSong")
    public ResponseEntity<RatingSong> createRating(@Valid @RequestBody RatingSong ratingSong) throws URISyntaxException {
        log.debug("REST request to save RatingSong : {}", ratingSong);
        if (ratingSong.getId() != null) {
            throw new BadRequestAlertException("A new ratingSong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingSong result = ratingSongRepository.save(ratingSong);
        return ResponseEntity
            .created(new URI("/api/ratingsSong/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ratingsSong/:id} : Updates an existing ratingSong.
     *
     * @param id the id of the ratingSong to save.
     * @param ratingSong the ratingSong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingSong,
     * or with status {@code 400 (Bad Request)} if the ratingSong is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingSong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ratingsSong/{id}")
    public ResponseEntity<RatingSong> updateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RatingSong ratingSong
    ) throws URISyntaxException {
        log.debug("REST request to update RatingSong : {}, {}", id, ratingSong);
        if (ratingSong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingSong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingSongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingSong result = ratingSongRepository.save(ratingSong);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingSong.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ratingsSong/:id} : Partial updates given fields of an existing ratingSong, field will ignore if it is null
     *
     * @param id the id of the ratingSong to save.
     * @param ratingSong the ratingSong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingSong,
     * or with status {@code 400 (Bad Request)} if the ratingSong is not valid,
     * or with status {@code 404 (Not Found)} if the ratingSong is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingSong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ratingsSong/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingSong> partialUpdateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RatingSong ratingSong
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatingSong partially : {}, {}", id, ratingSong);
        if (ratingSong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingSong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingSongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingSong> result = ratingSongRepository
            .findById(ratingSong.getId())
            .map(existingRating -> {
                if (ratingSong.getComment() != null) {
                    existingRating.setComment(ratingSong.getComment());
                }
                if (ratingSong.getRating() != null) {
                    existingRating.setRating(ratingSong.getRating());
                }
                return existingRating;
            })
            .map(ratingSongRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingSong.getId().toString())
        );
    }

    /**
     * {@code GET  /ratingsSong} : get all the ratingsSong.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratingsSong in body.
     */
    @GetMapping("/ratingsSong")
    public ResponseEntity<List<RatingSong>> getAllRatings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }
        log.debug("REST request to get a page of RatingsSong");
        Page<RatingSong> page = ratingSongRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET  /ratingsSong/:id} : get the "id" ratingSong.
     *
     * @param id the id of the ratingSong to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingSong, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ratingsSong/{id}")
    public ResponseEntity<RatingSong> getRating(@PathVariable Long id) {
        log.debug("REST request to get RatingSong : {}", id);
        Optional<RatingSong> ratingSong = ratingSongRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ratingSong);
    }

    /**
     * {@code DELETE  /ratingsSong/:id} : delete the "id" ratingSong.
     *
     * @param id the id of the ratingSong to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ratingsSong/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.debug("REST request to delete RatingSong : {}", id);
        ratingSongRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
