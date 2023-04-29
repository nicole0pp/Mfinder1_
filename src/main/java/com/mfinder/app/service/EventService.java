package com.mfinder.app.service;

import com.mfinder.app.domain.Event;
import com.mfinder.app.repository.EventRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<Long> getEvents() {
        return eventRepository.findAll().stream().map(Event::getId).collect(Collectors.toList());
    }
}
