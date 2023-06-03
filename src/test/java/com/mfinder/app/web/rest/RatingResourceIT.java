package com.mfinder.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mfinder.app.IntegrationTest;
import com.mfinder.app.domain.RatingEvent;
import com.mfinder.app.repository.RatingEventRepository;
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
 * Integration tests for the {@link RatingEventEventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RatingEventResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Double DEFAULT_RATINGEVENT = 0D;
    private static final Double UPDATED_RATINGEVENT = 1D;

    private static final String ENTITY_API_URL = "/api/ratingEvents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingEventRepository ratingEventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingEventMockMvc;

    private RatingEvent ratingEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingEvent createEntity(EntityManager em) {
        RatingEvent ratingEvent = new RatingEvent().comment(DEFAULT_COMMENT).rating(DEFAULT_RATINGEVENT);
        return ratingEvent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RatingEvent createUpdatedEntity(EntityManager em) {
        RatingEvent ratingEvent = new RatingEvent().comment(UPDATED_COMMENT).rating(UPDATED_RATINGEVENT);
        return ratingEvent;
    }

    @BeforeEach
    public void initTest() {
        ratingEvent = createEntity(em);
    }

    @Test
    @Transactional
    void createRatingEvent() throws Exception {
        int databaseSizeBeforeCreate = ratingEventRepository.findAll().size();
        // Create the RatingEvent
        restRatingEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingEvent)))
            .andExpect(status().isCreated());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeCreate + 1);
        RatingEvent testRatingEvent = ratingEventList.get(ratingEventList.size() - 1);
        assertThat(testRatingEvent.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testRatingEvent.getRating()).isEqualTo(DEFAULT_RATINGEVENT);
    }

    @Test
    @Transactional
    void createRatingEventWithExistingId() throws Exception {
        // Create the RatingEvent with an existing ID
        ratingEvent.setId(1L);

        int databaseSizeBeforeCreate = ratingEventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingEvent)))
            .andExpect(status().isBadRequest());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRatingEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingEventRepository.findAll().size();
        // set the field null
        ratingEvent.setRating(null);

        // Create the RatingEvent, which fails.

        restRatingEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingEvent)))
            .andExpect(status().isBadRequest());

        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRatingEvents() throws Exception {
        // Initialize the database
        ratingEventRepository.saveAndFlush(ratingEvent);

        // Get all the ratingEventList
        restRatingEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratingEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATINGEVENT.doubleValue())));
    }

    @Test
    @Transactional
    void getRatingEvent() throws Exception {
        // Initialize the database
        ratingEventRepository.saveAndFlush(ratingEvent);

        // Get the ratingEvent
        restRatingEventMockMvc
            .perform(get(ENTITY_API_URL_ID, ratingEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ratingEvent.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATINGEVENT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRatingEvent() throws Exception {
        // Get the ratingEvent
        restRatingEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRatingEvent() throws Exception {
        // Initialize the database
        ratingEventRepository.saveAndFlush(ratingEvent);

        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();

        // Update the ratingEvent
        RatingEvent updatedRatingEvent = ratingEventRepository.findById(ratingEvent.getId()).get();
        // Disconnect from session so that the updates on updatedRatingEvent are not directly saved in db
        em.detach(updatedRatingEvent);
        updatedRatingEvent.comment(UPDATED_COMMENT).rating(UPDATED_RATINGEVENT);

        restRatingEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRatingEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRatingEvent))
            )
            .andExpect(status().isOk());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
        RatingEvent testRatingEvent = ratingEventList.get(ratingEventList.size() - 1);
        assertThat(testRatingEvent.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRatingEvent.getRating()).isEqualTo(UPDATED_RATINGEVENT);
    }

    @Test
    @Transactional
    void putNonExistingRatingEvent() throws Exception {
        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();
        ratingEvent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRatingEvent() throws Exception {
        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();
        ratingEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRatingEvent() throws Exception {
        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();
        ratingEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingEvent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRatingEventWithPatch() throws Exception {
        // Initialize the database
        ratingEventRepository.saveAndFlush(ratingEvent);

        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();

        // Update the ratingEvent using partial update
        RatingEvent partialUpdatedRatingEvent = new RatingEvent();
        partialUpdatedRatingEvent.setId(ratingEvent.getId());

        partialUpdatedRatingEvent.comment(UPDATED_COMMENT).rating(UPDATED_RATINGEVENT);

        restRatingEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingEvent))
            )
            .andExpect(status().isOk());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
        RatingEvent testRatingEvent = ratingEventList.get(ratingEventList.size() - 1);
        assertThat(testRatingEvent.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRatingEvent.getRating()).isEqualTo(UPDATED_RATINGEVENT);
    }

    @Test
    @Transactional
    void fullUpdateRatingEventWithPatch() throws Exception {
        // Initialize the database
        ratingEventRepository.saveAndFlush(ratingEvent);

        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();

        // Update the ratingEvent using partial update
        RatingEvent partialUpdatedRatingEvent = new RatingEvent();
        partialUpdatedRatingEvent.setId(ratingEvent.getId());

        partialUpdatedRatingEvent.comment(UPDATED_COMMENT).rating(UPDATED_RATINGEVENT);

        restRatingEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRatingEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRatingEvent))
            )
            .andExpect(status().isOk());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
        RatingEvent testRatingEvent = ratingEventList.get(ratingEventList.size() - 1);
        assertThat(testRatingEvent.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRatingEvent.getRating()).isEqualTo(UPDATED_RATINGEVENT);
    }

    @Test
    @Transactional
    void patchNonExistingRatingEvent() throws Exception {
        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();
        ratingEvent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRatingEvent() throws Exception {
        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();
        ratingEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRatingEvent() throws Exception {
        int databaseSizeBeforeUpdate = ratingEventRepository.findAll().size();
        ratingEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingEventMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ratingEvent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RatingEvent in the database
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRatingEvent() throws Exception {
        // Initialize the database
        ratingEventRepository.saveAndFlush(ratingEvent);

        int databaseSizeBeforeDelete = ratingEventRepository.findAll().size();

        // Delete the ratingEvent
        restRatingEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, ratingEvent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RatingEvent> ratingEventList = ratingEventRepository.findAll();
        assertThat(ratingEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
