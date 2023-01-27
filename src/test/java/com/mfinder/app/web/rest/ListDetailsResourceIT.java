package com.mfinder.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mfinder.app.IntegrationTest;
import com.mfinder.app.domain.ListDetails;
import com.mfinder.app.repository.ListDetailsRepository;
import com.mfinder.app.service.dto.ListDetailsDTO;
import com.mfinder.app.service.mapper.ListDetailsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ListDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListDetailsResourceIT {

    private static final String ENTITY_API_URL = "/api/list-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListDetailsRepository listDetailsRepository;

    @Autowired
    private ListDetailsMapper listDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListDetailsMockMvc;

    private ListDetails listDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListDetails createEntity(EntityManager em) {
        ListDetails listDetails = new ListDetails();
        return listDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListDetails createUpdatedEntity(EntityManager em) {
        ListDetails listDetails = new ListDetails();
        return listDetails;
    }

    @BeforeEach
    public void initTest() {
        listDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createListDetails() throws Exception {
        int databaseSizeBeforeCreate = listDetailsRepository.findAll().size();
        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);
        restListDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ListDetails testListDetails = listDetailsList.get(listDetailsList.size() - 1);
    }

    @Test
    @Transactional
    void createListDetailsWithExistingId() throws Exception {
        // Create the ListDetails with an existing ID
        listDetails.setId(1L);
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        int databaseSizeBeforeCreate = listDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListDetails() throws Exception {
        // Initialize the database
        listDetailsRepository.saveAndFlush(listDetails);

        // Get all the listDetailsList
        restListDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listDetails.getId().intValue())));
    }

    @Test
    @Transactional
    void getListDetails() throws Exception {
        // Initialize the database
        listDetailsRepository.saveAndFlush(listDetails);

        // Get the listDetails
        restListDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, listDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listDetails.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingListDetails() throws Exception {
        // Get the listDetails
        restListDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingListDetails() throws Exception {
        // Initialize the database
        listDetailsRepository.saveAndFlush(listDetails);

        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();

        // Update the listDetails
        ListDetails updatedListDetails = listDetailsRepository.findById(listDetails.getId()).get();
        // Disconnect from session so that the updates on updatedListDetails are not directly saved in db
        em.detach(updatedListDetails);
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(updatedListDetails);

        restListDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
        ListDetails testListDetails = listDetailsList.get(listDetailsList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingListDetails() throws Exception {
        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();
        listDetails.setId(count.incrementAndGet());

        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListDetails() throws Exception {
        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();
        listDetails.setId(count.incrementAndGet());

        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListDetails() throws Exception {
        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();
        listDetails.setId(count.incrementAndGet());

        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListDetailsWithPatch() throws Exception {
        // Initialize the database
        listDetailsRepository.saveAndFlush(listDetails);

        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();

        // Update the listDetails using partial update
        ListDetails partialUpdatedListDetails = new ListDetails();
        partialUpdatedListDetails.setId(listDetails.getId());

        restListDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListDetails))
            )
            .andExpect(status().isOk());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
        ListDetails testListDetails = listDetailsList.get(listDetailsList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateListDetailsWithPatch() throws Exception {
        // Initialize the database
        listDetailsRepository.saveAndFlush(listDetails);

        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();

        // Update the listDetails using partial update
        ListDetails partialUpdatedListDetails = new ListDetails();
        partialUpdatedListDetails.setId(listDetails.getId());

        restListDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListDetails))
            )
            .andExpect(status().isOk());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
        ListDetails testListDetails = listDetailsList.get(listDetailsList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingListDetails() throws Exception {
        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();
        listDetails.setId(count.incrementAndGet());

        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListDetails() throws Exception {
        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();
        listDetails.setId(count.incrementAndGet());

        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListDetails() throws Exception {
        int databaseSizeBeforeUpdate = listDetailsRepository.findAll().size();
        listDetails.setId(count.incrementAndGet());

        // Create the ListDetails
        ListDetailsDTO listDetailsDTO = listDetailsMapper.toDto(listDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListDetails in the database
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListDetails() throws Exception {
        // Initialize the database
        listDetailsRepository.saveAndFlush(listDetails);

        int databaseSizeBeforeDelete = listDetailsRepository.findAll().size();

        // Delete the listDetails
        restListDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, listDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListDetails> listDetailsList = listDetailsRepository.findAll();
        assertThat(listDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
