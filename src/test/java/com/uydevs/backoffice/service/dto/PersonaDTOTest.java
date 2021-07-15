package com.uydevs.backoffice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.uydevs.backoffice.dto.domain.PersonaDTO;
import com.uydevs.backoffice.web.rest.TestUtil;

public class PersonaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaDTO.class);
        PersonaDTO personaDTO1 = new PersonaDTO();
        personaDTO1.setId(1L);
        PersonaDTO personaDTO2 = new PersonaDTO();
        assertThat(personaDTO1).isNotEqualTo(personaDTO2);
        personaDTO2.setId(personaDTO1.getId());
        assertThat(personaDTO1).isEqualTo(personaDTO2);
        personaDTO2.setId(2L);
        assertThat(personaDTO1).isNotEqualTo(personaDTO2);
        personaDTO1.setId(null);
        assertThat(personaDTO1).isNotEqualTo(personaDTO2);
    }
}
