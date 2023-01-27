package com.mfinder.app.service.impl;

import com.mfinder.app.domain.ListDetails;
import com.mfinder.app.repository.ListDetailsRepository;
import com.mfinder.app.service.ListDetailsService;
import com.mfinder.app.service.dto.ListDetailsDTO;
import com.mfinder.app.service.mapper.ListDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ListDetails}.
 */
@Service
@Transactional
public class ListDetailsServiceImpl implements ListDetailsService {

    private final Logger log = LoggerFactory.getLogger(ListDetailsServiceImpl.class);

    private final ListDetailsRepository listDetailsRepository;

    private final ListDetailsMapper listDetailsMapper;

    public ListDetailsServiceImpl(ListDetailsRepository listDetailsRepository, ListDetailsMapper listDetailsMapper) {
        this.listDetailsRepository = listDetailsRepository;
        this.listDetailsMapper = listDetailsMapper;
    }

    @Override
    public ListDetailsDTO save(ListDetailsDTO listDetailsDTO) {
        log.debug("Request to save ListDetails : {}", listDetailsDTO);
        ListDetails listDetails = listDetailsMapper.toEntity(listDetailsDTO);
        listDetails = listDetailsRepository.save(listDetails);
        return listDetailsMapper.toDto(listDetails);
    }

    @Override
    public ListDetailsDTO update(ListDetailsDTO listDetailsDTO) {
        log.debug("Request to update ListDetails : {}", listDetailsDTO);
        ListDetails listDetails = listDetailsMapper.toEntity(listDetailsDTO);
        // no save call needed as we have no fields that can be updated
        return listDetailsMapper.toDto(listDetails);
    }

    @Override
    public Optional<ListDetailsDTO> partialUpdate(ListDetailsDTO listDetailsDTO) {
        log.debug("Request to partially update ListDetails : {}", listDetailsDTO);

        return listDetailsRepository
            .findById(listDetailsDTO.getId())
            .map(existingListDetails -> {
                listDetailsMapper.partialUpdate(existingListDetails, listDetailsDTO);

                return existingListDetails;
            })
            // .map(listDetailsRepository::save)
            .map(listDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ListDetails");
        return listDetailsRepository.findAll(pageable).map(listDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListDetailsDTO> findOne(Long id) {
        log.debug("Request to get ListDetails : {}", id);
        return listDetailsRepository.findById(id).map(listDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListDetails : {}", id);
        listDetailsRepository.deleteById(id);
    }
}
