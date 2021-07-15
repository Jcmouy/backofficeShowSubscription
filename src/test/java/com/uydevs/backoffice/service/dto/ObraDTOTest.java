package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.ObraDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class ObraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObraDTO.class);
        ObraDTO obraDTO1 = new ObraDTO();
        obraDTO1.setId(1L);
        ObraDTO obraDTO2 = new ObraDTO();
        assertThat(obraDTO1).isNotEqualTo(obraDTO2);
        obraDTO2.setId(obraDTO1.getId());
        assertThat(obraDTO1).isEqualTo(obraDTO2);
        obraDTO2.setId(2L);
        assertThat(obraDTO1).isNotEqualTo(obraDTO2);
        obraDTO1.setId(null);
        assertThat(obraDTO1).isNotEqualTo(obraDTO2);
    }
}
