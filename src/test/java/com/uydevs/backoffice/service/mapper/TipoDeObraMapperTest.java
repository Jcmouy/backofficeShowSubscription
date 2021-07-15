package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoDeObraMapperTest {

    private TipoDeObraMapper tipoDeObraMapper;

    @BeforeEach
    public void setUp() {
        tipoDeObraMapper = new TipoDeObraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoDeObraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoDeObraMapper.fromId(null)).isNull();
    }
}
