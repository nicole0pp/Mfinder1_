package com.mfinder.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mfinder.app.IntegrationTest;
import com.mfinder.app.domain.FavoriteList;
import com.mfinder.app.repository.FavoriteListRepository;
import com.mfinder.app.service.dto.FavoriteListDTO;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FavoriteListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FavoriteListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/favorite-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FavoriteListRepository favoriteListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavoriteListMockMvc;

    private FavoriteList favoriteList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteList createEntity(EntityManager em) {
        FavoriteList favoriteList = new FavoriteList()
            .name(DEFAULT_NAME)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE);
        return favoriteList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteList createUpdatedEntity(EntityManager em) {
        FavoriteList favoriteList = new FavoriteList()
            .name(UPDATED_NAME)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);
        return favoriteList;
    }

    @BeforeEach
    public void initTest() {
        favoriteList = createEntity(em);
    }

    @Test
    @Transactional
    void createFavoriteList() throws Exception {
        int databaseSizeBeforeCreate = favoriteListRepository.findAll().size();
        // Create the FavoriteList
        restFavoriteListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteList)))
            .andExpect(status().isCreated());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeCreate + 1);
        FavoriteList testFavoriteList = favoriteListList.get(favoriteListList.size() - 1);
        assertThat(testFavoriteList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFavoriteList.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testFavoriteList.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFavoriteListWithExistingId() throws Exception {
        // Create the FavoriteList with an existing ID
        favoriteList.setId(1L);

        int databaseSizeBeforeCreate = favoriteListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoriteListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteList)))
            .andExpect(status().isBadRequest());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = favoriteListRepository.findAll().size();
        // set the field null
        favoriteList.setName(null);

        // Create the FavoriteList, which fails.

        restFavoriteListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteList)))
            .andExpect(status().isBadRequest());

        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFavoriteLists() throws Exception {
        // Initialize the database
        favoriteListRepository.saveAndFlush(favoriteList);

        // Get all the favoriteListList
        restFavoriteListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoriteList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))));
    }

    @Test
    @Transactional
    void getFavoriteList() throws Exception {
        // Initialize the database
        favoriteListRepository.saveAndFlush(favoriteList);

        // Get the favoriteList
        restFavoriteListMockMvc
            .perform(get(ENTITY_API_URL_ID, favoriteList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favoriteList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)));
    }

    @Test
    @Transactional
    void getNonExistingFavoriteList() throws Exception {
        // Get the favoriteList
        restFavoriteListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFavoriteList() throws Exception {
        // Initialize the database
        favoriteListRepository.saveAndFlush(favoriteList);

        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();

        // Update the favoriteList
        FavoriteList updatedFavoriteList = favoriteListRepository.findById(favoriteList.getId()).get();
        // Disconnect from session so that the updates on updatedFavoriteList are not directly saved in db
        em.detach(updatedFavoriteList);
        updatedFavoriteList.name(UPDATED_NAME).picture(UPDATED_PICTURE).pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restFavoriteListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favoriteList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favoriteList))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
        FavoriteList testFavoriteList = favoriteListList.get(favoriteListList.size() - 1);
        assertThat(testFavoriteList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFavoriteList.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testFavoriteList.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFavoriteList() throws Exception {
        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();
        favoriteList.setId(count.incrementAndGet());

        // Create the FavoriteList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favoriteList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favoriteList))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFavoriteList() throws Exception {
        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();
        favoriteList.setId(count.incrementAndGet());

        // Create the FavoriteList

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favoriteList))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFavoriteList() throws Exception {
        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();
        favoriteList.setId(count.incrementAndGet());

        // Create the FavoriteList

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteList)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFavoriteListWithPatch() throws Exception {
        // Initialize the database
        favoriteListRepository.saveAndFlush(favoriteList);

        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();

        // Update the favoriteList using partial update
        FavoriteList partialUpdatedFavoriteList = new FavoriteList();
        partialUpdatedFavoriteList.setId(favoriteList.getId());

        partialUpdatedFavoriteList.name(UPDATED_NAME).picture(UPDATED_PICTURE).pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restFavoriteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoriteList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFavoriteList))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
        FavoriteList testFavoriteList = favoriteListList.get(favoriteListList.size() - 1);
        assertThat(testFavoriteList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFavoriteList.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testFavoriteList.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFavoriteListWithPatch() throws Exception {
        // Initialize the database
        favoriteListRepository.saveAndFlush(favoriteList);

        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();

        // Update the favoriteList using partial update
        FavoriteList partialUpdatedFavoriteList = new FavoriteList();
        partialUpdatedFavoriteList.setId(favoriteList.getId());

        partialUpdatedFavoriteList.name(UPDATED_NAME).picture(UPDATED_PICTURE).pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restFavoriteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoriteList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFavoriteList))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
        FavoriteList testFavoriteList = favoriteListList.get(favoriteListList.size() - 1);
        assertThat(testFavoriteList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFavoriteList.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testFavoriteList.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFavoriteList() throws Exception {
        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();
        favoriteList.setId(count.incrementAndGet());

        // Create the FavoriteList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, favoriteList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favoriteList))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFavoriteList() throws Exception {
        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();
        favoriteList.setId(count.incrementAndGet());

        // Create the FavoriteList

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favoriteList))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFavoriteList() throws Exception {
        int databaseSizeBeforeUpdate = favoriteListRepository.findAll().size();
        favoriteList.setId(count.incrementAndGet());

        // Create the FavoriteList
        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteListMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(favoriteList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FavoriteList in the database
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFavoriteList() throws Exception {
        // Initialize the database
        favoriteListRepository.saveAndFlush(favoriteList);

        int databaseSizeBeforeDelete = favoriteListRepository.findAll().size();

        // Delete the favoriteList
        restFavoriteListMockMvc
            .perform(delete(ENTITY_API_URL_ID, favoriteList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FavoriteList> favoriteListList = favoriteListRepository.findAll();
        assertThat(favoriteListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
