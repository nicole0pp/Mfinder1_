package com.mfinder.app.web.rest;

import com.mfinder.app.domain.Album;
import com.mfinder.app.domain.Authority;
import com.mfinder.app.domain.Event;
import com.mfinder.app.domain.Song;
import com.mfinder.app.repository.AlbumRepository;
import com.mfinder.app.repository.SongRepository;
import com.mfinder.app.security.AuthoritiesConstants;
import com.mfinder.app.security.SecurityUtils;
import com.mfinder.app.service.SongService;
import com.mfinder.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mfinder.app.domain.Song}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SongResource {

    private final Logger log = LoggerFactory.getLogger(SongResource.class);

    private static final String ENTITY_NAME = "song";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SongService songService;

    private final SongRepository songRepository;

    private final AlbumRepository albumRepository;

    public SongResource(SongService songService, SongRepository songRepository, AlbumRepository albumRepository) {
        this.songService = songService;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    /**
     * {@code POST  /songs} : Create a new song.
     *
     * @param song the song to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new song, or with status {@code 400 (Bad Request)} if the song has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) throws URISyntaxException {
        log.debug("REST request to save Song : {}", song);
        if (song.getId() != null) {
            throw new BadRequestAlertException("A new song cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Album album = albumRepository.getReferenceById(song.getAlbum().getId());
        song.setArtists(album.getArtist());
        song.setPicture(album.getPicture());
        song.setPictureContentType(album.getPictureContentType());
        Song result = songService.save(song);
        return ResponseEntity
            .created(new URI("/api/songs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /songs/:id} : Updates an existing song.
     *
     * @param id the id of the song to save.
     * @param song the song to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated song,
     * or with status {@code 400 (Bad Request)} if the song is not valid,
     * or with status {@code 500 (Internal Server Error)} if the song couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Song song)
        throws URISyntaxException {
        log.debug("REST request to update Song : {}, {}", id, song);
        if (song.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, song.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!songRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Song result = songService.update(song);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, song.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /songs/:id} : Partial updates given fields of an existing song, field will ignore if it is null
     *
     * @param id the id of the song to save.
     * @param song the song to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated song,
     * or with status {@code 400 (Bad Request)} if the song is not valid,
     * or with status {@code 404 (Not Found)} if the song is not found,
     * or with status {@code 500 (Internal Server Error)} if the song couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/songs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Song> partialUpdateSong(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Song song
    ) throws URISyntaxException {
        log.debug("REST request to partial update Song partially : {}, {}", id, song);
        if (song.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, song.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!songRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Song> result = songService.partialUpdate(song);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, song.getId().toString())
        );
    }

    /**
     * {@code GET  /songs} : get all the songs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of songs in body.
     */
    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        log.debug("REST request to get a page of Songs");
        List<Song> songs = songService.findAll();

        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        String login = SecurityUtils.getCurrentUserLogin().get();

        songs.forEach(song -> {
            if (song.getArtist().getUser().getLogin().equals(login) && song.getArtist().getUser().getAuthorities().contains(authority)) {
                song.setPuedeEditar(true);
            } else {
                song.setPuedeEditar(false);
            }
        });

        return songs;
    }

    /**
     * {@code GET  /songs/getSongsByAlbumId/:id} : get the "id" album.
     *
     * @param id the id of the album to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the songs of the album, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/songs/getSongsByAlbumId/{id}")
    public ResponseEntity<Set<Song>> getAllSongsByAlbumId(@PathVariable("id") Long id) {
        log.debug("REST request to get Songs : {}", id);
        Set<Song> songs = songRepository.getAllSongsByEvent(id);

        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        String login = SecurityUtils.getCurrentUserLogin().get();

        songs.forEach(song -> {
            if (song.getArtist().getUser().getLogin().equals(login) && song.getArtist().getUser().getAuthorities().contains(authority)) {
                song.setPuedeEditar(true);
            } else {
                song.setPuedeEditar(false);
            }
        });
        return ResponseEntity.ok(songs);
    }

    /**
     * {@code GET  /songs/:id} : get the "id" song.
     *
     * @param id the id of the song to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the song, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Long id) {
        log.debug("REST request to get Song : {}", id);
        Optional<Song> song = songService.findOne(id);

        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        String login = SecurityUtils.getCurrentUserLogin().get();

        if (
            song.get().getArtist().getUser().getLogin().equals(login) &&
            song.get().getArtist().getUser().getAuthorities().contains(authority)
        ) {
            song.get().setPuedeEditar(true);
        } else {
            song.get().setPuedeEditar(false);
        }

        return ResponseUtil.wrapOrNotFound(song);
    }

    /**
     * {@code GET  /songs/getRapSongs/:genre} : get the "genre" song.
     *
     *  @param genre the city of the event to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the event, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/songs/getGenreSongs/{genre}")
    public ResponseEntity<List<Song>> getSongsByGenre(@PathVariable("genre") String genre) {
        log.debug("REST request to get a page of Events");
        List<Song> songs = songRepository.getAllSongsByGenre(genre);
        return ResponseEntity.ok(songs);
    }

    /**
     * {@code DELETE  /songs/:id} : delete the "id" song.
     *
     * @param id the id of the song to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        log.debug("REST request to delete Song : {}", id);
        songService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
