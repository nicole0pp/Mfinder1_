package com.mfinder.app.service.impl;

import com.mfinder.app.domain.RatingEvent;
import com.mfinder.app.repository.RatingEventRepository;
import com.mfinder.app.service.RatingEventService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RatingEvent}.
 */
@Service
@Transactional
public class RatingEventServiceImpl implements RatingEventService {

    private final Logger log = LoggerFactory.getLogger(RatingEventServiceImpl.class);

    private final RatingEventRepository ratingEventRepository;

    public RatingEventServiceImpl(RatingEventRepository ratingEventRepository) {
        this.ratingEventRepository = ratingEventRepository;
    }

    @Override
    public RatingEvent save(RatingEvent ratingEvent) {
        log.debug("Request to save RatingEvent : {}", ratingEvent);
        return ratingEventRepository.save(ratingEvent);
    }

    @Override
    public RatingEvent update(RatingEvent ratingEvent) {
        log.debug("Request to update RatingEvent : {}", ratingEvent);
        return ratingEventRepository.save(ratingEvent);
    }

    @Override
    public Optional<RatingEvent> partialUpdate(RatingEvent ratingEvent) {
        log.debug("Request to partially update RatingEvent : {}", ratingEvent);

        return ratingEventRepository
            .findById(ratingEvent.getId())
            .map(existingRating -> {
                if (ratingEvent.getComment() != null) {
                    existingRating.setComment(ratingEvent.getComment());
                }
                if (ratingEvent.getRating() != null) {
                    existingRating.setRating(ratingEvent.getRating());
                }

                return existingRating;
            })
            .map(ratingEventRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RatingEvent> findAll(Pageable pageable) {
        log.debug("Request to get all RatingEvent");
        return ratingEventRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RatingEvent> findOne(Long id) {
        log.debug("Request to get RatingEvent : {}", id);
        return ratingEventRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RatingEvent : {}", id);
        ratingEventRepository.deleteById(id);
    }

    @Override
    public List<RatingEvent> getRatingsByEventId(Long eventId) {
        return ratingEventRepository.findRatingsByEventId(eventId);
    }
}
