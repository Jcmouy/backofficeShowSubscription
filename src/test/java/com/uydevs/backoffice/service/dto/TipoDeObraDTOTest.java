package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.TipoDeObraDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class TipoDeObraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeObraDTO.class);
        TipoDeObraDTO tipoDeObraDTO1 = new TipoDeObraDTO();
        tipoDeObraDTO1.setId(1L);
        TipoDeObraDTO tipoDeObraDTO2 = new TipoDeObraDTO();
        assertThat(tipoDeObraDTO1).isNotEqualTo(tipoDeObraDTO2);
        tipoDeObraDTO2.setId(tipoDeObraDTO1.getId());
        assertThat(tipoDeObraDTO1).isEqualTo(tipoDeObraDTO2);
        tipoDeObraDTO2.setId(2L);
        assertThat(tipoDeObraDTO1).isNotEqualTo(tipoDeObraDTO2);
        tipoDeObraDTO1.setId(null);
        assertThat(tipoDeObraDTO1).isNotEqualTo(tipoDeObraDTO2);
    }
}
