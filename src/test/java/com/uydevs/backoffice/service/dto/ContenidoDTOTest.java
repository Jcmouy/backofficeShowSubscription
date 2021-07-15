package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.ContenidoDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class ContenidoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContenidoDTO.class);
        ContenidoDTO contenidoDTO1 = new ContenidoDTO();
        contenidoDTO1.setId(1L);
        ContenidoDTO contenidoDTO2 = new ContenidoDTO();
        assertThat(contenidoDTO1).isNotEqualTo(contenidoDTO2);
        contenidoDTO2.setId(contenidoDTO1.getId());
        assertThat(contenidoDTO1).isEqualTo(contenidoDTO2);
        contenidoDTO2.setId(2L);
        assertThat(contenidoDTO1).isNotEqualTo(contenidoDTO2);
        contenidoDTO1.setId(null);
        assertThat(contenidoDTO1).isNotEqualTo(contenidoDTO2);
    }
}
