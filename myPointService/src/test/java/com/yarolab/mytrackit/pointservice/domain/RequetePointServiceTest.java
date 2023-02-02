package com.yarolab.mytrackit.pointservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.pointservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequetePointServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequetePointService.class);
        RequetePointService requetePointService1 = new RequetePointService();
        requetePointService1.setId(1L);
        RequetePointService requetePointService2 = new RequetePointService();
        requetePointService2.setId(requetePointService1.getId());
        assertThat(requetePointService1).isEqualTo(requetePointService2);
        requetePointService2.setId(2L);
        assertThat(requetePointService1).isNotEqualTo(requetePointService2);
        requetePointService1.setId(null);
        assertThat(requetePointService1).isNotEqualTo(requetePointService2);
    }
}
