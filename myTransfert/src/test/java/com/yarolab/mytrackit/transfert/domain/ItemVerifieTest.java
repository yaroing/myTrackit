package com.yarolab.mytrackit.transfert.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.transfert.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemVerifieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemVerifie.class);
        ItemVerifie itemVerifie1 = new ItemVerifie();
        itemVerifie1.setId(1L);
        ItemVerifie itemVerifie2 = new ItemVerifie();
        itemVerifie2.setId(itemVerifie1.getId());
        assertThat(itemVerifie1).isEqualTo(itemVerifie2);
        itemVerifie2.setId(2L);
        assertThat(itemVerifie1).isNotEqualTo(itemVerifie2);
        itemVerifie1.setId(null);
        assertThat(itemVerifie1).isNotEqualTo(itemVerifie2);
    }
}
