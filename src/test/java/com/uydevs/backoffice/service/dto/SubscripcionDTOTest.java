package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.SubscripcionDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class SubscripcionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscripcionDTO.class);
        SubscripcionDTO subscripcionDTO1 = new SubscripcionDTO();
        subscripcionDTO1.setId(1L);
        SubscripcionDTO subscripcionDTO2 = new SubscripcionDTO();
        assertThat(subscripcionDTO1).isNotEqualTo(subscripcionDTO2);
        subscripcionDTO2.setId(subscripcionDTO1.getId());
        assertThat(subscripcionDTO1).isEqualTo(subscripcionDTO2);
        subscripcionDTO2.setId(2L);
        assertThat(subscripcionDTO1).isNotEqualTo(subscripcionDTO2);
        subscripcionDTO1.setId(null);
        assertThat(subscripcionDTO1).isNotEqualTo(subscripcionDTO2);
    }
}
