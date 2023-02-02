package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockPointServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockPointService.class);
        StockPointService stockPointService1 = new StockPointService();
        stockPointService1.setId(1L);
        StockPointService stockPointService2 = new StockPointService();
        stockPointService2.setId(stockPointService1.getId());
        assertThat(stockPointService1).isEqualTo(stockPointService2);
        stockPointService2.setId(2L);
        assertThat(stockPointService1).isNotEqualTo(stockPointService2);
        stockPointService1.setId(null);
        assertThat(stockPointService1).isNotEqualTo(stockPointService2);
    }
}
