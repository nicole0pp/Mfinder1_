package com.mfinder.app.web.rest;

import com.mfinder.app.repository.ListDetailsRepository;
import com.mfinder.app.service.ListDetailsService;
import com.mfinder.app.service.dto.ListDetailsDTO;
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
 * REST controller for managing {@link com.mfinder.app.domain.ListDetails}.
 */
@RestController
@RequestMapping("/api")
public class ListDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ListDetailsResource.class);

    private static final String ENTITY_NAME = "listDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListDetailsService listDetailsService;

    private final ListDetailsRepository listDetailsRepository;

    public ListDetailsResource(ListDetailsService listDetailsService, ListDetailsRepository listDetailsRepository) {
        this.listDetailsService = listDetailsService;
        this.listDetailsRepository = listDetailsRepository;
    }

    /**
     * {@code POST  /list-details} : Create a new listDetails.
     *
     * @param listDetailsDTO the listDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listDetailsDTO, or with status {@code 400 (Bad Request)} if the listDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-details")
    public ResponseEntity<ListDetailsDTO> createListDetails(@RequestBody ListDetailsDTO listDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ListDetails : {}", listDetailsDTO);
        if (listDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new listDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListDetailsDTO result = listDetailsService.save(listDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/list-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-details/:id} : Updates an existing listDetails.
     *
     * @param id the id of the listDetailsDTO to save.
     * @param listDetailsDTO the listDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the listDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-details/{id}")
    public ResponseEntity<ListDetailsDTO> updateListDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListDetailsDTO listDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ListDetails : {}, {}", id, listDetailsDTO);
        if (listDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListDetailsDTO result = listDetailsService.update(listDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /list-details/:id} : Partial updates given fields of an existing listDetails, field will ignore if it is null
     *
     * @param id the id of the listDetailsDTO to save.
     * @param listDetailsDTO the listDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the listDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the listDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the listDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/list-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ListDetailsDTO> partialUpdateListDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListDetailsDTO listDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListDetails partially : {}, {}", id, listDetailsDTO);
        if (listDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListDetailsDTO> result = listDetailsService.partialUpdate(listDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /list-details} : get all the listDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listDetails in body.
     */
    @GetMapping("/list-details")
    public ResponseEntity<List<ListDetailsDTO>> getAllListDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ListDetails");
        Page<ListDetailsDTO> page = listDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /list-details/:id} : get the "id" listDetails.
     *
     * @param id the id of the listDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-details/{id}")
    public ResponseEntity<ListDetailsDTO> getListDetails(@PathVariable Long id) {
        log.debug("REST request to get ListDetails : {}", id);
        Optional<ListDetailsDTO> listDetailsDTO = listDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listDetailsDTO);
    }

    /**
     * {@code DELETE  /list-details/:id} : delete the "id" listDetails.
     *
     * @param id the id of the listDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-details/{id}")
    public ResponseEntity<Void> deleteListDetails(@PathVariable Long id) {
        log.debug("REST request to delete ListDetails : {}", id);
        listDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
