package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequetePartenaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequetePartenaire.class);
        RequetePartenaire requetePartenaire1 = new RequetePartenaire();
        requetePartenaire1.setId(1L);
        RequetePartenaire requetePartenaire2 = new RequetePartenaire();
        requetePartenaire2.setId(requetePartenaire1.getId());
        assertThat(requetePartenaire1).isEqualTo(requetePartenaire2);
        requetePartenaire2.setId(2L);
        assertThat(requetePartenaire1).isNotEqualTo(requetePartenaire2);
        requetePartenaire1.setId(null);
        assertThat(requetePartenaire1).isNotEqualTo(requetePartenaire2);
    }
}
