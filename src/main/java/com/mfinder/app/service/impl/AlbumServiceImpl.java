// package com.mfinder.app.service.impl;

// import com.mfinder.app.domain.Album;
// import com.mfinder.app.repository.AlbumRepository;
// import com.mfinder.app.service.AlbumService;
// import com.mfinder.app.service.dto.AlbumDTO;
// import com.mfinder.app.service.mapper.AlbumMapper;
// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// /**
//  * Service Implementation for managing {@link Album}.
//  */
// @Service
// @Transactional
// public class AlbumServiceImpl implements AlbumService {

//     private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);

//     private final AlbumRepository albumRepository;

//     private final AlbumMapper albumMapper;

//     public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper) {
//         this.albumRepository = albumRepository;
//         this.albumMapper = albumMapper;
//     }

//     @Override
//     public AlbumDTO save(AlbumDTO albumDTO) {
//         log.debug("Request to save Album : {}", albumDTO);
//         Album album = albumMapper.toEntity(albumDTO);
//         album = albumRepository.save(album);
//         return albumMapper.toDto(album);
//     }

//     @Override
//     public AlbumDTO update(AlbumDTO albumDTO) {
//         log.debug("Request to update Album : {}", albumDTO);
//         Album album = albumMapper.toEntity(albumDTO);
//         album = albumRepository.save(album);
//         return albumMapper.toDto(album);
//     }

//     @Override
//     public Optional<AlbumDTO> partialUpdate(AlbumDTO albumDTO) {
//         log.debug("Request to partially update Album : {}", albumDTO);

//         return albumRepository
//             .findById(albumDTO.getId())
//             .map(existingAlbum -> {
//                 albumMapper.partialUpdate(existingAlbum, albumDTO);

//                 return existingAlbum;
//             })
//             .map(albumRepository::save)
//             .map(albumMapper::toDto);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<AlbumDTO> findAll(Pageable pageable) {
//         log.debug("Request to get all Albums");
//         return albumRepository.findAll(pageable).map(albumMapper::toDto);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Optional<AlbumDTO> findOne(Long id) {
//         log.debug("Request to get Album : {}", id);
//         return albumRepository.findById(id).map(albumMapper::toDto);
//     }

//     @Override
//     public void delete(Long id) {
//         log.debug("Request to delete Album : {}", id);
//         albumRepository.deleteById(id);
//     }
// }
