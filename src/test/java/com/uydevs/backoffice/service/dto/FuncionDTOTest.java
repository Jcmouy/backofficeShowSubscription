package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.FuncionDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class FuncionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncionDTO.class);
        FuncionDTO funcionDTO1 = new FuncionDTO();
        funcionDTO1.setId(1L);
        FuncionDTO funcionDTO2 = new FuncionDTO();
        assertThat(funcionDTO1).isNotEqualTo(funcionDTO2);
        funcionDTO2.setId(funcionDTO1.getId());
        assertThat(funcionDTO1).isEqualTo(funcionDTO2);
        funcionDTO2.setId(2L);
        assertThat(funcionDTO1).isNotEqualTo(funcionDTO2);
        funcionDTO1.setId(null);
        assertThat(funcionDTO1).isNotEqualTo(funcionDTO2);
    }
}
