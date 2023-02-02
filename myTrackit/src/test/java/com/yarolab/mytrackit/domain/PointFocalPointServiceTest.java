package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointFocalPointServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointFocalPointService.class);
        PointFocalPointService pointFocalPointService1 = new PointFocalPointService();
        pointFocalPointService1.setId(1L);
        PointFocalPointService pointFocalPointService2 = new PointFocalPointService();
        pointFocalPointService2.setId(pointFocalPointService1.getId());
        assertThat(pointFocalPointService1).isEqualTo(pointFocalPointService2);
        pointFocalPointService2.setId(2L);
        assertThat(pointFocalPointService1).isNotEqualTo(pointFocalPointService2);
        pointFocalPointService1.setId(null);
        assertThat(pointFocalPointService1).isNotEqualTo(pointFocalPointService2);
    }
}
