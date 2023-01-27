package com.mfinder.app.service.mapper;

import com.mfinder.app.domain.ListDetails;
import com.mfinder.app.service.dto.ListDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ListDetails} and its DTO {@link ListDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ListDetailsMapper extends EntityMapper<ListDetailsDTO, ListDetails> {}
