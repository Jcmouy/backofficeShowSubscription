package com.uydevs.backoffice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FuncionMapperTest {

    private FuncionMapper funcionMapper;

    @BeforeEach
    public void setUp() {
        funcionMapper = new FuncionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(funcionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(funcionMapper.fromId(null)).isNull();
    }
}
