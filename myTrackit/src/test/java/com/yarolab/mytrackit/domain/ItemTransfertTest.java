package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemTransfertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemTransfert.class);
        ItemTransfert itemTransfert1 = new ItemTransfert();
        itemTransfert1.setId(1L);
        ItemTransfert itemTransfert2 = new ItemTransfert();
        itemTransfert2.setId(itemTransfert1.getId());
        assertThat(itemTransfert1).isEqualTo(itemTransfert2);
        itemTransfert2.setId(2L);
        assertThat(itemTransfert1).isNotEqualTo(itemTransfert2);
        itemTransfert1.setId(null);
        assertThat(itemTransfert1).isNotEqualTo(itemTransfert2);
    }
}
