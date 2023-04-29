package com.mfinder.app.service;

import com.mfinder.app.domain.Artist;
import com.mfinder.app.repository.ArtistRepository;
import com.mfinder.app.service.dto.ArtistDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public List<String> getArtists() {
        return artistRepository.findAll().stream().map(Artist::getArtistName).collect(Collectors.toList());
    }
}
