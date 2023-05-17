package com.mfinder.app.web.rest;

import com.mfinder.app.repository.EventRepository;
import com.mfinder.app.service.EventService;
import com.mfinder.app.service.dto.EventDTO;
import com.mfinder.app.service.dto.controller.EventController;
import com.mfinder.app.service.mapper.EventMapper;
import com.mfinder.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventService eventService;

    private final EventRepository eventRepository;

    private final EventController eventController;

    public EventResource(
        EventService eventService,
        EventRepository eventRepository,
        EventController eventController,
        EventMapper eventMapper
    ) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.eventController = eventController;
    }

    /**
     * {@code POST  /events} : Create a new event.
     *
     * @param eventDTO the eventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventDTO, or with status {@code 400 (Bad Request)} if the event has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws ParseException
     */
    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(
        @Valid @RequestBody EventDTO eventDTO,
        @RequestParam(value = "startTime") String startTime,
        @RequestParam(value = "endTime") String endTime
    ) throws URISyntaxException, ParseException {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date horaStart = simpleDateFormat.parse(startTime);
        Date horaEnd = simpleDateFormat.parse(endTime);

        Long fechaHoraStart = eventDTO.getStartDate().getTime() + horaStart.getTime();
        Long fechaHoraEnd = eventDTO.getEndDate().getTime() + horaEnd.getTime();

        Date fechaStart = new Date();
        Date fechaEnd = new Date();

        fechaStart.setTime(fechaHoraStart);
        fechaEnd.setTime(fechaHoraEnd);

        eventDTO.setStartDate(fechaStart);
        eventDTO.setEndDate(fechaEnd);

        log.debug("REST request to save Event : {}", eventDTO);
        if (eventDTO.getId() != null) {
            throw new BadRequestAlertException("A new event cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventDTO result = eventController.save(eventDTO);
        return ResponseEntity
            .created(new URI("/api/event/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /events/:id} : Updates an existing event.
     *
     * @param id the id of the eventDTO to save.
     * @param eventDTO the eventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventDTO,
     * or with status {@code 400 (Bad Request)} if the eventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventDTO eventDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Event : {}, {}", id, eventDTO);
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventDTO result = eventController.update(eventDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /events/:id} : Partial updates given fields of an existing event, field will ignore if it is null
     *
     * @param id the id of the eventDTO to save.
     * @param eventDTO the eventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventDTO,
     * or with status {@code 400 (Bad Request)} if the eventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventDTO> partialUpdateEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventDTO eventDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Event partially : {}, {}", id, eventDTO);
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventDTO> result = eventController.partialUpdate(eventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /events} : get all the events.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of events in body.
     */
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getAllEvents(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Events");
        Page<EventDTO> page = eventController.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /events/:id} : get the "id" event.
     *
     * @param id the id of the eventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getevent(@PathVariable Long id) {
        log.debug("REST request to get event : {}", id);
        Optional<EventDTO> event = eventController.findOne(id);
        return ResponseUtil.wrapOrNotFound(event);
    }

    /**
     * Gets a list of all the events.
     * @return a long list of all the events.
     */
    @GetMapping("/getEvents")
    public List<Long> getevents() {
        return eventService.getEvents();
    }

    /**
     * {@code DELETE  /events/:id} : delete the "id" event.
     *
     * @param id the id of the eventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        eventController.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
