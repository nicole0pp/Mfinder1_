// package com.mfinder.app.service.impl;

// import com.mfinder.app.domain.Event;
// import com.mfinder.app.repository.EventRepository;
// import com.mfinder.app.service.dto.EventDTO;
// import com.mfinder.app.service.dto.controller.EventController;
// import com.mfinder.app.service.mapper.EventMapper;
// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// /**
//  * Service Implementation for managing {@link Event}.
//  */
// @Service
// @Transactional
// public class EventServiceImpl implements EventController {

//     private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

//     private final EventRepository eventRepository;

//     private final EventMapper eventMapper;

//     public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
//         this.eventRepository = eventRepository;
//         this.eventMapper = eventMapper;
//     }

//     @Override
//     public EventDTO save(EventDTO eventDTO) {
//         log.debug("Request to save Event : {}", eventDTO);
//         Event event = eventMapper.toEntity(eventDTO);
//         event = eventRepository.save(event);
//         return eventMapper.toDto(event);
//     }

//     @Override
//     public EventDTO update(EventDTO eventDTO) {
//         log.debug("Request to update Event : {}", eventDTO);
//         Event event = eventMapper.toEntity(eventDTO);
//         event = eventRepository.save(event);
//         return eventMapper.toDto(event);
//     }

//     @Override
//     public Optional<EventDTO> partialUpdate(EventDTO eventDTO) {
//         log.debug("Request to partially update Event : {}", eventDTO);

//         return eventRepository
//             .findById(eventDTO.getId())
//             .map(existingevent -> {
//                 eventMapper.partialUpdate(existingevent, eventDTO);

//                 return existingevent;
//             })
//             .map(eventRepository::save)
//             .map(eventMapper::toDto);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<EventDTO> findAll(Pageable pageable) {
//         log.debug("Request to get all Events");
//         return eventRepository.findAll(pageable).map(eventMapper::toDto);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Optional<EventDTO> findOne(Long id) {
//         log.debug("Request to get Event : {}", id);
//         return eventRepository.findById(id).map(eventMapper::toDto);
//     }

//     @Override
//     public void delete(Long id) {
//         log.debug("Request to delete Event : {}", id);
//         eventRepository.deleteById(id);
//     }
// }
