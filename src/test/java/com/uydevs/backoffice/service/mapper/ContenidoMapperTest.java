package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContenidoMapperTest {

    private ContenidoMapper contenidoMapper;

    @BeforeEach
    public void setUp() {
        contenidoMapper = new ContenidoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contenidoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contenidoMapper.fromId(null)).isNull();
    }
}
