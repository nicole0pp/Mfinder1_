package com.mfinder.app.service.impl;

import com.mfinder.app.domain.Rating;
import com.mfinder.app.repository.RatingRepository;
import com.mfinder.app.service.RatingService;
import com.mfinder.app.service.dto.RatingDTO;
import com.mfinder.app.service.mapper.RatingMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rating}.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }

    @Override
    public RatingDTO update(RatingDTO ratingDTO) {
        log.debug("Request to update Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }

    @Override
    public Optional<RatingDTO> partialUpdate(RatingDTO ratingDTO) {
        log.debug("Request to partially update Rating : {}", ratingDTO);

        return ratingRepository
            .findById(ratingDTO.getId())
            .map(existingRating -> {
                ratingMapper.partialUpdate(existingRating, ratingDTO);

                return existingRating;
            })
            .map(ratingRepository::save)
            .map(ratingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rating");
        return ratingRepository.findAll(pageable).map(ratingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RatingDTO> findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        return ratingRepository.findById(id).map(ratingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
    }
}
