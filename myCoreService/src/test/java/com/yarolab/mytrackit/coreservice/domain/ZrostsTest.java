package com.yarolab.mytrackit.coreservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.coreservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZrostsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zrosts.class);
        Zrosts zrosts1 = new Zrosts();
        zrosts1.setId(1L);
        Zrosts zrosts2 = new Zrosts();
        zrosts2.setId(zrosts1.getId());
        assertThat(zrosts1).isEqualTo(zrosts2);
        zrosts2.setId(2L);
        assertThat(zrosts1).isNotEqualTo(zrosts2);
        zrosts1.setId(null);
        assertThat(zrosts1).isNotEqualTo(zrosts2);
    }
}
