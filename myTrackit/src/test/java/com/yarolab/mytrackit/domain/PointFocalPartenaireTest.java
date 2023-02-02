package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointFocalPartenaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointFocalPartenaire.class);
        PointFocalPartenaire pointFocalPartenaire1 = new PointFocalPartenaire();
        pointFocalPartenaire1.setId(1L);
        PointFocalPartenaire pointFocalPartenaire2 = new PointFocalPartenaire();
        pointFocalPartenaire2.setId(pointFocalPartenaire1.getId());
        assertThat(pointFocalPartenaire1).isEqualTo(pointFocalPartenaire2);
        pointFocalPartenaire2.setId(2L);
        assertThat(pointFocalPartenaire1).isNotEqualTo(pointFocalPartenaire2);
        pointFocalPartenaire1.setId(null);
        assertThat(pointFocalPartenaire1).isNotEqualTo(pointFocalPartenaire2);
    }
}
