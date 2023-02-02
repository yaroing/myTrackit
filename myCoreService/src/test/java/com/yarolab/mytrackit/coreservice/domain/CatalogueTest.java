package com.yarolab.mytrackit.coreservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.coreservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CatalogueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Catalogue.class);
        Catalogue catalogue1 = new Catalogue();
        catalogue1.setId(1L);
        Catalogue catalogue2 = new Catalogue();
        catalogue2.setId(catalogue1.getId());
        assertThat(catalogue1).isEqualTo(catalogue2);
        catalogue2.setId(2L);
        assertThat(catalogue1).isNotEqualTo(catalogue2);
        catalogue1.setId(null);
        assertThat(catalogue1).isNotEqualTo(catalogue2);
    }
}
