package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class ContenidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contenido.class);
        Contenido contenido1 = new Contenido();
        contenido1.setId(1L);
        Contenido contenido2 = new Contenido();
        contenido2.setId(contenido1.getId());
        assertThat(contenido1).isEqualTo(contenido2);
        contenido2.setId(2L);
        assertThat(contenido1).isNotEqualTo(contenido2);
        contenido1.setId(null);
        assertThat(contenido1).isNotEqualTo(contenido2);
    }
}
