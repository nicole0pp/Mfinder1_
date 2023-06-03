package com.mfinder.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mfinder.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatingEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatingEvent.class);
        RatingEvent ratingEvent1 = new RatingEvent();
        ratingEvent1.setId(1L);
        RatingEvent ratingEvent2 = new RatingEvent();
        ratingEvent2.setId(ratingEvent1.getId());
        assertThat(ratingEvent1).isEqualTo(ratingEvent2);
        ratingEvent2.setId(2L);
        assertThat(ratingEvent1).isNotEqualTo(ratingEvent2);
        ratingEvent1.setId(null);
        assertThat(ratingEvent1).isNotEqualTo(ratingEvent2);
    }
}
