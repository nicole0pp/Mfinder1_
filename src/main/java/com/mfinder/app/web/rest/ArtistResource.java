package com.mfinder.app.web.rest;

import com.mfinder.app.domain.Artist;
import com.mfinder.app.repository.ArtistRepository;
import com.mfinder.app.security.SecurityUtils;
import com.mfinder.app.service.ArtistService;
import com.mfinder.app.service.dto.AdminUserDTO;
import com.mfinder.app.service.dto.ArtistDTO;
import com.mfinder.app.service.impl.ArtistServiceImpl;
import com.mfinder.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mfinder.app.domain.Artist}.
 */
@RestController
@RequestMapping("/api")
public class ArtistResource {

    private final Logger log = LoggerFactory.getLogger(ArtistResource.class);

    private static final String ENTITY_NAME = "artist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtistService artistService;

    private final ArtistServiceImpl artistServiceImpl;

    private final ArtistRepository artistRepository;

    private final AccountResource accountResource;

    public ArtistResource(
        ArtistService artistService,
        ArtistRepository artistRepository,
        ArtistServiceImpl artistServiceImpl,
        AccountResource accountResource
    ) {
        this.artistService = artistService;
        this.artistRepository = artistRepository;
        this.artistServiceImpl = artistServiceImpl;
        this.accountResource = accountResource;
    }

    /**
     * {@code POST  /artists} : Create a new artist.
     *
     * @param artistDTO the artistDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artistDTO, or with status {@code 400 (Bad Request)} if the artist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artists")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) throws URISyntaxException {
        log.debug("REST request to save Artist : {}", artist);
        if (artist.getId() != null) {
            throw new BadRequestAlertException("A new artist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Artist result = artistRepository.save(artist);
        return ResponseEntity
            .created(new URI("/api/artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artists/:id} : Updates an existing artist.
     *
     * @param id the id of the artistDTO to save.
     * @param artistDTO the artistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistDTO,
     * or with status {@code 400 (Bad Request)} if the artistDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artists/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistDTO artistDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Artist : {}, {}", id, artistDTO);
        if (artistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtistDTO result = artistService.update(artistDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artistDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artists/:id} : Partial updates given fields of an existing artist, field will ignore if it is null
     *
     * @param id the id of the artistDTO to save.
     * @param artistDTO the artistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistDTO,
     * or with status {@code 400 (Bad Request)} if the artistDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artistDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtistDTO> partialUpdateArtist(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtistDTO artistDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Artist partially : {}, {}", id, artistDTO);
        if (artistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtistDTO> result = artistService.partialUpdate(artistDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artistDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artists} : get all the artists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artists in body.
     */
    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getAllArtists(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Artists");
        Page<Artist> page = artistRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artists/:id} : get the "id" artist.
     *
     * @param id the id of the artistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long id) {
        log.debug("REST request to get Artist : {}", id);
        Optional<ArtistDTO> artistDTO = artistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artistDTO);
    }

    /**
     * {@code GET  /artists/:userId} : get the "id" artist.
     *
     * @param userId the id of the artistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artists/user/{userId}")
    public Artist getArtistByUserId(@PathVariable Long userId) {
        log.debug("REST request to get Artist : {}", userId);
        Artist artist = artistRepository.findArtistByUserId(userId);
        return artist;
    }

    /**
     * Gets a list of all the artists.
     * @return a string list of all the artists.
     */
    @GetMapping("/artistsString")
    public List<String> getArtists() {
        return artistServiceImpl.getArtists();
    }

    /**
     * {@code DELETE  /artists/:id} : delete the "id" artist.
     *
     * @param id the id of the artistDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artists/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        log.debug("REST request to delete Artist : {}", id);
        artistService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    // /**
    //  * Gets a list of all the artists.
    //  * @return a string list of all the artists.
    //  */
    // @GetMapping("/artist-comprobacion")
    // public Boolean comprobacionEsPropio(Long artistId) {
    //     return artistServiceImpl.getEsPropio(artistId);
    // }

}
