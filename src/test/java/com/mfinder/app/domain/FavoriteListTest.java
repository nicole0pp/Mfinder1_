package com.mfinder.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mfinder.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavoriteListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteList.class);
        FavoriteList favoriteList1 = new FavoriteList();
        favoriteList1.setId(1L);
        FavoriteList favoriteList2 = new FavoriteList();
        favoriteList2.setId(favoriteList1.getId());
        assertThat(favoriteList1).isEqualTo(favoriteList2);
        favoriteList2.setId(2L);
        assertThat(favoriteList1).isNotEqualTo(favoriteList2);
        favoriteList1.setId(null);
        assertThat(favoriteList1).isNotEqualTo(favoriteList2);
    }
}
