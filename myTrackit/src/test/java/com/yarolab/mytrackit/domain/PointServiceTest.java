package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointService.class);
        PointService pointService1 = new PointService();
        pointService1.setId(1L);
        PointService pointService2 = new PointService();
        pointService2.setId(pointService1.getId());
        assertThat(pointService1).isEqualTo(pointService2);
        pointService2.setId(2L);
        assertThat(pointService1).isNotEqualTo(pointService2);
        pointService1.setId(null);
        assertThat(pointService1).isNotEqualTo(pointService2);
    }
}
