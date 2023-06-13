package com.mfinder.app.service.impl;

import com.mfinder.app.domain.Song;
import com.mfinder.app.repository.SongRepository;
import com.mfinder.app.service.SongService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Song}.
 */
@Service
@Transactional
public class SongServiceImpl implements SongService {

    private final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song save(Song song) {
        log.debug("Request to save Song : {}", song);
        return songRepository.save(song);
    }

    @Override
    public Song update(Song song) {
        log.debug("Request to update Song : {}", song);
        return songRepository.save(song);
    }

    @Override
    public Optional<Song> partialUpdate(Song song) {
        log.debug("Request to partially update Song : {}", song);

        return songRepository
            .findById(song.getId())
            .map(existingSong -> {
                if (song.getName() != null) {
                    existingSong.setName(song.getName());
                }
                if (song.getPicture() != null) {
                    existingSong.setPicture(song.getPicture());
                }
                if (song.getPictureContentType() != null) {
                    existingSong.setPictureContentType(song.getPictureContentType());
                }
                if (song.getMusicGenre() != null) {
                    existingSong.setMusicGenre(song.getMusicGenre());
                }
                if (song.getDuration() != null) {
                    existingSong.setDuration(song.getDuration());
                }
                if (song.getAudio() != null) {
                    existingSong.setAudio(song.getAudio());
                }
                if (song.getAudioContentType() != null) {
                    existingSong.setAudioContentType(song.getAudioContentType());
                }

                return existingSong;
            })
            .map(songRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Song> findAll() {
        log.debug("Request to get all Songs");
        return songRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Song> findOne(Long id) {
        log.debug("Request to get Song : {}", id);
        return songRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Song : {}", id);
        songRepository.deleteById(id);
    }
}
