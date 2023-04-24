package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.Event;
import com.mfinder.app.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {}
