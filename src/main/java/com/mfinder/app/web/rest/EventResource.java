package com.mfinder.app.web.rest;

import com.mfinder.app.domain.Event;
import com.mfinder.app.repository.EventRepository;
import com.mfinder.app.service.dto.AdminUserDTO;
import com.mfinder.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mfinder.app.domain.Event}.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(Arrays.asList("id", "createdBy"));

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    // private final EventService eventService;

    private final EventRepository eventRepository;

    private final AccountResource accountResource;

    public EventResource(
        // EventService eventService,
        EventRepository eventRepository,
        AccountResource accountResource
    ) {
        // this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.accountResource = accountResource;
    }

    /**
     * {@code POST  /events} : Create a new event.
     *
     * @param event the event to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new event, or with status {@code 400 (Bad Request)} if the event has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to save Event : {}", event);
        if (event.getId() != null) {
            throw new BadRequestAlertException("A new event cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Event result = eventRepository.save(event);
        return ResponseEntity
            .created(new URI("/api/event/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /events/:id} : Updates an existing event.
     *
     * @param id the id of the eventDTO to save.
     * @param event the eventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventDTO,
     * or with status {@code 400 (Bad Request)} if the eventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/events/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable(value = "id", required = false) final Long id, @RequestBody Event event)
        throws URISyntaxException {
        log.debug("REST request to update Event : {}, {}", id, event);
        if (event.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, event.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Event result = eventRepository.save(event);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, event.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /events/:id} : Partial updates given fields of an existing event, field will ignore if it is null
     *
     * @param id the id of the eventDTO to save.
     * @param event the eventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventDTO,
     * or with status {@code 400 (Bad Request)} if the eventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Event> partialUpdateEvent(@PathVariable(value = "id", required = false) final Long id, @RequestBody Event event)
        throws URISyntaxException {
        log.debug("REST request to partial update Event partially : {}, {}", id, event);
        if (event.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, event.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Event> result = eventRepository
            .findById(event.getId())
            .map(existingEvent -> {
                if (event.getName() != null) {
                    existingEvent.setName(event.getName());
                }
                if (event.getImage() != null) {
                    existingEvent.setImage(event.getImage());
                }
                if (event.getImageContentType() != null) {
                    existingEvent.setImageContentType(event.getImageContentType());
                }
                if (event.getTipoEvento() != null) {
                    existingEvent.setTipoEvento(event.getTipoEvento());
                }
                if (event.getStartDate() != null) {
                    existingEvent.setStartDate(event.getStartDate());
                }
                if (event.getEndDate() != null) {
                    existingEvent.setEndDate(event.getEndDate());
                }
                if (event.getLocation() != null) {
                    existingEvent.setLocation(event.getLocation());
                }
                if (event.getCity() != null) {
                    existingEvent.setCity(event.getCity());
                }
                if (event.getDescription() != null) {
                    existingEvent.setDescription(event.getDescription());
                }
                if (event.getSeatCapacity() != null) {
                    existingEvent.setSeatCapacity(event.getSeatCapacity());
                }

                return existingEvent;
            })
            .map(eventRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, event.getId().toString())
        );
    }

    /**
     * {@code GET  /events} : get all the events.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of events in body.
     */
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerloa
    ) {
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }
        log.debug("REST request to get a page of Events");
        Page<Event> page;
        if (eagerloa) {
            page = eventRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = eventRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET  /events/:id} : get the "id" event.
     *
     * @param id the id of the event to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the event, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        log.debug("REST request to get event : {}", id);

        Optional<Event> event = eventRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(event);
    }

    /**
     * {@code GET  /events/byLocation/:city } : get the "city" event.
     *
     *  @param city the city of the event to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the event, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/events/city/{city}")
    public ResponseEntity<List<Event>> getEventByLocation(@PathVariable("city") String city) {
        log.debug("REST request to get a page of Events");
        List<Event> events = eventRepository.getAllEventByCity(city);
        return ResponseEntity.ok(events);
    }

    /**
     * {@code GET  /events/byCurrentUser/:city } : get the "city" event.
     *
     *  @param login the city of the event to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the event, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/events/byCurrentUser")
    public ResponseEntity<List<Event>> getEventByCurrentUser() {
        AdminUserDTO user = accountResource.getAccount();

        log.debug("REST request to get a page of Events");
        List<Event> events = eventRepository.getAllEventsByUser(user.getLogin());
        return ResponseEntity.ok(events);
    }

    /**
     * {@code GET  /events/currentTime/:currentTime } : get the "currentTime" event.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the event, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/events/pastEvents")
    public ResponseEntity<List<Event>> getPastEvents() {
        Instant now = Instant.now();

        log.debug("REST request to get a page of Events");
        List<Event> events = eventRepository.getAllPastEvents(now);
        return ResponseEntity.ok(events);
    }

    /**
     * {@code DELETE  /events/:id} : delete the "id" event.
     *
     * @param id the id of the event to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        eventRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
