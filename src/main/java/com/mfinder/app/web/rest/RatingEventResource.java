package com.mfinder.app.web.rest;

import com.mfinder.app.domain.RatingEvent;
import com.mfinder.app.repository.RatingEventRepository;
import com.mfinder.app.repository.UserRepository;
import com.mfinder.app.service.RatingEventService;
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
 * REST controller for managing {@link com.mfinder.app.domain.RatingEvent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RatingEventResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "comment", "ratingEvent", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate")
    );

    private final Logger log = LoggerFactory.getLogger(RatingEventResource.class);

    private static final String ENTITY_NAME = "ratingEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RatingEventRepository ratingEventRepository;

    private final RatingEventService ratingEventService;

    private final AccountResource accountResource;

    private UserRepository userRepository;

    public RatingEventResource(
        RatingEventRepository ratingEventRepository,
        AccountResource accountResource,
        RatingEventService ratingEventService,
        UserRepository userRepository
    ) {
        this.ratingEventRepository = ratingEventRepository;
        this.accountResource = accountResource;
        this.ratingEventService = ratingEventService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /ratingsEvent} : Create a new ratingEvent.
     *
     * @param ratingEvent the ratingEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ratingEvent, or with status {@code 400 (Bad Request)} if the ratingEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ratingsEvent")
    public ResponseEntity<RatingEvent> createRating(@Valid @RequestBody RatingEvent ratingEvent) throws URISyntaxException {
        log.debug("REST request to save RatingEvent : {}", ratingEvent);
        if (ratingEvent.getId() != null) {
            throw new BadRequestAlertException("A new ratingEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingEvent result = ratingEventRepository.save(ratingEvent);
        byte[] imageProfile = userRepository.getImageProfile(result.getCreatedBy());
        ratingEvent.setImage(imageProfile);
        String imageProfileContentType = userRepository.getImageProfileContentType(result.getCreatedBy());
        ratingEvent.setImageContentType(imageProfileContentType);

        updateRating(ratingEvent.getId(), ratingEvent);
        return ResponseEntity
            .created(new URI("/api/ratingsEvent/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ratingsEvent/:id} : Updates an existing ratingEvent.
     *
     * @param id the id of the ratingEvent to save.
     * @param ratingEvent the ratingEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingEvent,
     * or with status {@code 400 (Bad Request)} if the ratingEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ratingEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ratingsEvent/{id}")
    public ResponseEntity<RatingEvent> updateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RatingEvent ratingEvent
    ) throws URISyntaxException {
        log.debug("REST request to update RatingEvent : {}, {}", id, ratingEvent);
        if (ratingEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingEvent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RatingEvent result = ratingEventRepository.save(ratingEvent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ratingsEvent/:id} : Partial updates given fields of an existing ratingEvent, field will ignore if it is null
     *
     * @param id the id of the ratingEvent to save.
     * @param ratingEvent the ratingEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ratingEvent,
     * or with status {@code 400 (Bad Request)} if the ratingEvent is not valid,
     * or with status {@code 404 (Not Found)} if the ratingEvent is not found,
     * or with status {@code 500 (Internal Server Error)} if the ratingEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ratingsEvent/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RatingEvent> partialUpdateRating(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RatingEvent ratingEvent
    ) throws URISyntaxException {
        log.debug("REST request to partial update RatingEvent partially : {}, {}", id, ratingEvent);
        if (ratingEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ratingEvent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ratingEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RatingEvent> result = ratingEventRepository
            .findById(ratingEvent.getId())
            .map(existingRating -> {
                if (ratingEvent.getComment() != null) {
                    existingRating.setComment(ratingEvent.getComment());
                }
                if (ratingEvent.getRating() != null) {
                    existingRating.setRating(ratingEvent.getRating());
                }
                return existingRating;
            })
            .map(ratingEventRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ratingEvent.getId().toString())
        );
    }

    // /**
    //  * {@code GET  /ratingsEvent} : get all the ratingsEvent.
    //  *
    //  * @param pageable the pagination information.
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ratingsEvent in body.
    //  */
    // @GetMapping("/ratingsEvent")
    // public ResponseEntity<List<RatingEvent>> getAllRatings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    //     if (!onlyContainsAllowedProperties(pageable)) {
    //         return ResponseEntity.badRequest().build();
    //     }
    //     log.debug("REST request to get a page of RatingsEvent");
    //     Page<RatingEvent> page = ratingEventService.findAll(pageable);
    //     HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    //     return ResponseEntity.ok().headers(headers).body(page.getContent());
    // }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * Get all the rating by the eventId
     * @param eventId
     * @return
     */
    @GetMapping("/ratingsEvent/eventId/{eventId}")
    public ResponseEntity<List<RatingEvent>> getAllRatingByEventId(@PathVariable("eventId") Long eventId) {
        log.debug("REST request to get a page of RatingsEvent");
        // Long id =  Long.parseLong(eventId);
        return ResponseEntity.ok().body(ratingEventService.getRatingsByEventId(eventId));
    }

    /**
     * {@code GET  /ratingsEvent/:id} : get the "id" ratingEvent.
     *
     * @param id the id of the ratingEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ratingEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ratingsEvent/{id}")
    public ResponseEntity<RatingEvent> getRating(@PathVariable Long id) {
        log.debug("REST request to get RatingEvent : {}", id);
        Optional<RatingEvent> ratingEvent = ratingEventRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ratingEvent);
    }

    /**
     * {@code DELETE  /ratingsEvent/:id} : delete the "id" ratingEvent.
     *
     * @param id the id of the ratingEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ratingsEvent/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.debug("REST request to delete RatingEvent : {}", id);
        ratingEventRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
