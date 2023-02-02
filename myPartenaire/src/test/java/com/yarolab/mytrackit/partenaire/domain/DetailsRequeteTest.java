package com.yarolab.mytrackit.partenaire.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.partenaire.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailsRequeteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsRequete.class);
        DetailsRequete detailsRequete1 = new DetailsRequete();
        detailsRequete1.setId(1L);
        DetailsRequete detailsRequete2 = new DetailsRequete();
        detailsRequete2.setId(detailsRequete1.getId());
        assertThat(detailsRequete1).isEqualTo(detailsRequete2);
        detailsRequete2.setId(2L);
        assertThat(detailsRequete1).isNotEqualTo(detailsRequete2);
        detailsRequete1.setId(null);
        assertThat(detailsRequete1).isNotEqualTo(detailsRequete2);
    }
}
