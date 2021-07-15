package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class TipoDeObraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeObra.class);
        TipoDeObra tipoDeObra1 = new TipoDeObra();
        tipoDeObra1.setId(1L);
        TipoDeObra tipoDeObra2 = new TipoDeObra();
        tipoDeObra2.setId(tipoDeObra1.getId());
        assertThat(tipoDeObra1).isEqualTo(tipoDeObra2);
        tipoDeObra2.setId(2L);
        assertThat(tipoDeObra1).isNotEqualTo(tipoDeObra2);
        tipoDeObra1.setId(null);
        assertThat(tipoDeObra1).isNotEqualTo(tipoDeObra2);
    }
}
