package com.mfinder.app.service.impl;

import com.mfinder.app.domain.FavoriteList;
import com.mfinder.app.repository.FavoriteListRepository;
import com.mfinder.app.service.FavoriteListService;
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

    public FavoriteListServiceImpl(FavoriteListRepository favoriteListRepository) {
        this.favoriteListRepository = favoriteListRepository;
    }

    @Override
    public FavoriteList save(FavoriteList favoriteList) {
        log.debug("Request to save FavoriteList : {}", favoriteList);
        favoriteList = favoriteListRepository.save(favoriteList);
        return favoriteList;
    }

    @Override
    public FavoriteList update(FavoriteList favoriteList) {
        log.debug("Request to update FavoriteList : {}", favoriteList);
        favoriteList = favoriteListRepository.save(favoriteList);
        return favoriteList;
    }

    @Override
    public Optional<FavoriteList> partialUpdate(FavoriteList list) {
        log.debug("Request to partially update FavoriteList : {}", list);

        return favoriteListRepository
            .findById(list.getId())
            .map(existingList -> {
                if (list.getName() != null) {
                    existingList.setName(list.getName());
                }
                if (list.getPicture() != null) {
                    existingList.setPicture(list.getPicture());
                }
                if (list.getPictureContentType() != null) {
                    existingList.setPictureContentType(list.getPictureContentType());
                }
                return existingList;
            })
            .map(favoriteListRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavoriteList> findAll(Pageable pageable) {
        log.debug("Request to get all FavoriteLists");
        return favoriteListRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FavoriteList> findOne(Long id) {
        log.debug("Request to get FavoriteList : {}", id);
        return favoriteListRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FavoriteList : {}", id);
        favoriteListRepository.deleteById(id);
    }
}
