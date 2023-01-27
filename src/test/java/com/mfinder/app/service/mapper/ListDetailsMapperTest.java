package com.mfinder.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListDetailsMapperTest {

    private ListDetailsMapper listDetailsMapper;

    @BeforeEach
    public void setUp() {
        listDetailsMapper = new ListDetailsMapperImpl();
    }
}
