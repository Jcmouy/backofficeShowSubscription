package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class ProcesoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proceso.class);
        Proceso proceso1 = new Proceso();
        proceso1.setId(1L);
        Proceso proceso2 = new Proceso();
        proceso2.setId(proceso1.getId());
        assertThat(proceso1).isEqualTo(proceso2);
        proceso2.setId(2L);
        assertThat(proceso1).isNotEqualTo(proceso2);
        proceso1.setId(null);
        assertThat(proceso1).isNotEqualTo(proceso2);
    }
}
