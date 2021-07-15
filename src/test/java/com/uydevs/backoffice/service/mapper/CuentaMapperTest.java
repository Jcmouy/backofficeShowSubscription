package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CuentaMapperTest {

    private CuentaMapper cuentaMapper;

    @BeforeEach
    public void setUp() {
        cuentaMapper = new CuentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cuentaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cuentaMapper.fromId(null)).isNull();
    }
}
