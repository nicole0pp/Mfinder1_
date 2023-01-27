package com.mfinder.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mfinder.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListDetailsDTO.class);
        ListDetailsDTO listDetailsDTO1 = new ListDetailsDTO();
        listDetailsDTO1.setId(1L);
        ListDetailsDTO listDetailsDTO2 = new ListDetailsDTO();
        assertThat(listDetailsDTO1).isNotEqualTo(listDetailsDTO2);
        listDetailsDTO2.setId(listDetailsDTO1.getId());
        assertThat(listDetailsDTO1).isEqualTo(listDetailsDTO2);
        listDetailsDTO2.setId(2L);
        assertThat(listDetailsDTO1).isNotEqualTo(listDetailsDTO2);
        listDetailsDTO1.setId(null);
        assertThat(listDetailsDTO1).isNotEqualTo(listDetailsDTO2);
    }
}
