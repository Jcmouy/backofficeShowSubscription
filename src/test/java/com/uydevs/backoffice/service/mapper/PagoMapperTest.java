package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PagoMapperTest {

    private PagoMapper pagoMapper;

    @BeforeEach
    public void setUp() {
        pagoMapper = new PagoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pagoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pagoMapper.fromId(null)).isNull();
    }
}
