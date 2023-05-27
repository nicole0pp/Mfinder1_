package com.mfinder.app.repository;

import com.mfinder.app.domain.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EventRepositoryWithBagRelationships {
    Optional<Event> fetchBagRelationships(Optional<Event> event);

    List<Event> fetchBagRelationships(List<Event> event);

    Page<Event> fetchBagRelationships(Page<Event> event);
}
