package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ObraMapperTest {

    private ObraMapper obraMapper;

    @BeforeEach
    public void setUp() {
        obraMapper = new ObraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(obraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(obraMapper.fromId(null)).isNull();
    }
}
