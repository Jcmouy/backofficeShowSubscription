package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcesoMapperTest {

    private ProcesoMapper procesoMapper;

    @BeforeEach
    public void setUp() {
        procesoMapper = new ProcesoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(procesoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(procesoMapper.fromId(null)).isNull();
    }
}
