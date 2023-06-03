package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.RatingEvent;
import com.mfinder.app.service.dto.RatingEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface RatingEventMapper extends EntityMapper<RatingEventDTO, RatingEvent> {}
