package com.mfinder.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mfinder.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavoriteListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteListDTO.class);
        FavoriteListDTO favoriteListDTO1 = new FavoriteListDTO();
        favoriteListDTO1.setId(1L);
        FavoriteListDTO favoriteListDTO2 = new FavoriteListDTO();
        assertThat(favoriteListDTO1).isNotEqualTo(favoriteListDTO2);
        favoriteListDTO2.setId(favoriteListDTO1.getId());
        assertThat(favoriteListDTO1).isEqualTo(favoriteListDTO2);
        favoriteListDTO2.setId(2L);
        assertThat(favoriteListDTO1).isNotEqualTo(favoriteListDTO2);
        favoriteListDTO1.setId(null);
        assertThat(favoriteListDTO1).isNotEqualTo(favoriteListDTO2);
    }
}
