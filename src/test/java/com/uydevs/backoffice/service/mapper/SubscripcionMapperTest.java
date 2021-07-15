package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SubscripcionMapperTest {

    private SubscripcionMapper subscripcionMapper;

    @BeforeEach
    public void setUp() {
        subscripcionMapper = new SubscripcionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(subscripcionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(subscripcionMapper.fromId(null)).isNull();
    }
}
