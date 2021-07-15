package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.ProcesoDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class ProcesoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcesoDTO.class);
        ProcesoDTO procesoDTO1 = new ProcesoDTO();
        procesoDTO1.setId(1L);
        ProcesoDTO procesoDTO2 = new ProcesoDTO();
        assertThat(procesoDTO1).isNotEqualTo(procesoDTO2);
        procesoDTO2.setId(procesoDTO1.getId());
        assertThat(procesoDTO1).isEqualTo(procesoDTO2);
        procesoDTO2.setId(2L);
        assertThat(procesoDTO1).isNotEqualTo(procesoDTO2);
        procesoDTO1.setId(null);
        assertThat(procesoDTO1).isNotEqualTo(procesoDTO2);
    }
}
