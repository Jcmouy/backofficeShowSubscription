package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EtiquetaMapperTest {

    private EtiquetaMapper etiquetaMapper;

    @BeforeEach
    public void setUp() {
        etiquetaMapper = new EtiquetaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(etiquetaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(etiquetaMapper.fromId(null)).isNull();
    }
}
