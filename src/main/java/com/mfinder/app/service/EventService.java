// package com.mfinder.app.service;

// import com.mfinder.app.domain.Event;
// import com.mfinder.app.repository.EventRepository;
// import com.mfinder.app.service.dto.EventDTO;
// import com.mfinder.app.service.dto.controller.EventController;
// import com.mfinder.app.service.mapper.EventMapper;

// import tech.jhipster.web.util.HeaderUtil;
// import tech.jhipster.web.util.ResponseUtil;

// import java.net.URI;
// import java.net.URISyntaxException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.LocalTime;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// @Transactional
// public class EventService {

//     private final EventRepository eventRepository;

//     private final EventController eventController;

//     private final EventMapper eventMapper;

//     private static final String ENTITY_NAME = "event";

//     @Value("${jhipster.clientApp.name}")
//     private String applicationName;

//     public EventService(EventRepository eventRepository, EventController eventController, EventMapper eventMapper) {
//         this.eventRepository = eventRepository;
//         this.eventController = eventController;
//         this.eventMapper = eventMapper;
//     }

//     @Transactional(readOnly = true)
//     public Optional<EventDTO> getEventById(Long id) {
//         return eventRepository.findEventById(id);
//     }

//     @Transactional(readOnly = true)
//     public List<Long> getEvents() {
//         return eventRepository.findAll().stream().map(Event::getId).collect(Collectors.toList());
//     }

//     // public ResponseEntity<Event> crearEvento (Event event) throws URISyntaxException{

//     //     Event result = eventRepository.save(event);
//     //     return ResponseEntity
//     //     .created(new URI("/api/event/" + result.getId()))
//     //     .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//     //     .body(result);

//     // }

//     // public ResponseEntity<Event>  getEvento (Long id){

//     //     Optional<Event> event = eventRepository.findOneWithEagerRelationships(id);
//     //     return ResponseUtil.wrapOrNotFound(event);
//     // }

// }
