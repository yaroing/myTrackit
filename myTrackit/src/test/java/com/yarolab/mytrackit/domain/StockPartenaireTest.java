package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockPartenaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockPartenaire.class);
        StockPartenaire stockPartenaire1 = new StockPartenaire();
        stockPartenaire1.setId(1L);
        StockPartenaire stockPartenaire2 = new StockPartenaire();
        stockPartenaire2.setId(stockPartenaire1.getId());
        assertThat(stockPartenaire1).isEqualTo(stockPartenaire2);
        stockPartenaire2.setId(2L);
        assertThat(stockPartenaire1).isNotEqualTo(stockPartenaire2);
        stockPartenaire1.setId(null);
        assertThat(stockPartenaire1).isNotEqualTo(stockPartenaire2);
    }
}
