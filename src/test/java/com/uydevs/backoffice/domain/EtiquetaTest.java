package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class EtiquetaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etiqueta.class);
        Etiqueta etiqueta1 = new Etiqueta();
        etiqueta1.setId(1L);
        Etiqueta etiqueta2 = new Etiqueta();
        etiqueta2.setId(etiqueta1.getId());
        assertThat(etiqueta1).isEqualTo(etiqueta2);
        etiqueta2.setId(2L);
        assertThat(etiqueta1).isNotEqualTo(etiqueta2);
        etiqueta1.setId(null);
        assertThat(etiqueta1).isNotEqualTo(etiqueta2);
    }
}
