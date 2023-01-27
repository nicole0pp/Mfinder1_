package com.mfinder.app.service.impl;

import com.mfinder.app.domain.FavoriteList;
import com.mfinder.app.repository.FavoriteListRepository;
import com.mfinder.app.service.FavoriteListService;
import com.mfinder.app.service.dto.FavoriteListDTO;
import com.mfinder.app.service.mapper.FavoriteListMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FavoriteList}.
 */
@Service
@Transactional
public class FavoriteListServiceImpl implements FavoriteListService {

    private final Logger log = LoggerFactory.getLogger(FavoriteListServiceImpl.class);

    private final FavoriteListRepository favoriteListRepository;

    private final FavoriteListMapper favoriteListMapper;

    public FavoriteListServiceImpl(FavoriteListRepository favoriteListRepository, FavoriteListMapper favoriteListMapper) {
        this.favoriteListRepository = favoriteListRepository;
        this.favoriteListMapper = favoriteListMapper;
    }

    @Override
    public FavoriteListDTO save(FavoriteListDTO favoriteListDTO) {
        log.debug("Request to save FavoriteList : {}", favoriteListDTO);
        FavoriteList favoriteList = favoriteListMapper.toEntity(favoriteListDTO);
        favoriteList = favoriteListRepository.save(favoriteList);
        return favoriteListMapper.toDto(favoriteList);
    }

    @Override
    public FavoriteListDTO update(FavoriteListDTO favoriteListDTO) {
        log.debug("Request to update FavoriteList : {}", favoriteListDTO);
        FavoriteList favoriteList = favoriteListMapper.toEntity(favoriteListDTO);
        favoriteList = favoriteListRepository.save(favoriteList);
        return favoriteListMapper.toDto(favoriteList);
    }

    @Override
    public Optional<FavoriteListDTO> partialUpdate(FavoriteListDTO favoriteListDTO) {
        log.debug("Request to partially update FavoriteList : {}", favoriteListDTO);

        return favoriteListRepository
            .findById(favoriteListDTO.getId())
            .map(existingFavoriteList -> {
                favoriteListMapper.partialUpdate(existingFavoriteList, favoriteListDTO);

                return existingFavoriteList;
            })
            .map(favoriteListRepository::save)
            .map(favoriteListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavoriteListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FavoriteLists");
        return favoriteListRepository.findAll(pageable).map(favoriteListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FavoriteListDTO> findOne(Long id) {
        log.debug("Request to get FavoriteList : {}", id);
        return favoriteListRepository.findById(id).map(favoriteListMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FavoriteList : {}", id);
        favoriteListRepository.deleteById(id);
    }
}
