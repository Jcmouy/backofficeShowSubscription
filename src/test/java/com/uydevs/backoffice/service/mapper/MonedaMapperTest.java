package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MonedaMapperTest {

    private MonedaMapper monedaMapper;

    @BeforeEach
    public void setUp() {
        monedaMapper = new MonedaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(monedaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(monedaMapper.fromId(null)).isNull();
    }
}
