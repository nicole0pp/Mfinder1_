package com.mfinder.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mfinder.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListDetails.class);
        ListDetails listDetails1 = new ListDetails();
        listDetails1.setId(1L);
        ListDetails listDetails2 = new ListDetails();
        listDetails2.setId(listDetails1.getId());
        assertThat(listDetails1).isEqualTo(listDetails2);
        listDetails2.setId(2L);
        assertThat(listDetails1).isNotEqualTo(listDetails2);
        listDetails1.setId(null);
        assertThat(listDetails1).isNotEqualTo(listDetails2);
    }
}
