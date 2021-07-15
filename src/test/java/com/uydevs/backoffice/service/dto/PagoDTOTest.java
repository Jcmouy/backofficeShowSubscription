package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.PagoDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class PagoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagoDTO.class);
        PagoDTO pagoDTO1 = new PagoDTO();
        pagoDTO1.setId(1L);
        PagoDTO pagoDTO2 = new PagoDTO();
        assertThat(pagoDTO1).isNotEqualTo(pagoDTO2);
        pagoDTO2.setId(pagoDTO1.getId());
        assertThat(pagoDTO1).isEqualTo(pagoDTO2);
        pagoDTO2.setId(2L);
        assertThat(pagoDTO1).isNotEqualTo(pagoDTO2);
        pagoDTO1.setId(null);
        assertThat(pagoDTO1).isNotEqualTo(pagoDTO2);
    }
}
