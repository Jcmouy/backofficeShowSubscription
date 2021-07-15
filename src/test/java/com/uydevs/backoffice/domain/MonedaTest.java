package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class MonedaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Moneda.class);
        Moneda moneda1 = new Moneda();
        moneda1.setId(1L);
        Moneda moneda2 = new Moneda();
        moneda2.setId(moneda1.getId());
        assertThat(moneda1).isEqualTo(moneda2);
        moneda2.setId(2L);
        assertThat(moneda1).isNotEqualTo(moneda2);
        moneda1.setId(null);
        assertThat(moneda1).isNotEqualTo(moneda2);
    }
}
