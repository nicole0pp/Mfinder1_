package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.Rating;
import com.mfinder.app.service.dto.RatingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {}
