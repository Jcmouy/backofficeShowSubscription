package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class SubscripcionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscripcion.class);
        Subscripcion subscripcion1 = new Subscripcion();
        subscripcion1.setId(1L);
        Subscripcion subscripcion2 = new Subscripcion();
        subscripcion2.setId(subscripcion1.getId());
        assertThat(subscripcion1).isEqualTo(subscripcion2);
        subscripcion2.setId(2L);
        assertThat(subscripcion1).isNotEqualTo(subscripcion2);
        subscripcion1.setId(null);
        assertThat(subscripcion1).isNotEqualTo(subscripcion2);
    }
}
