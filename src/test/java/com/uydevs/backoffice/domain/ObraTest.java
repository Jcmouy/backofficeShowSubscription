package com.uydevs.backoffice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uydevs.backoffice.web.rest.TestUtil;

public class ObraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obra.class);
        Obra obra1 = new Obra();
        obra1.setId(1L);
        Obra obra2 = new Obra();
        obra2.setId(obra1.getId());
        assertThat(obra1).isEqualTo(obra2);
        obra2.setId(2L);
        assertThat(obra1).isNotEqualTo(obra2);
        obra1.setId(null);
        assertThat(obra1).isNotEqualTo(obra2);
    }
}
