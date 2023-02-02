package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAction.class);
        TypeAction typeAction1 = new TypeAction();
        typeAction1.setId(1L);
        TypeAction typeAction2 = new TypeAction();
        typeAction2.setId(typeAction1.getId());
        assertThat(typeAction1).isEqualTo(typeAction2);
        typeAction2.setId(2L);
        assertThat(typeAction1).isNotEqualTo(typeAction2);
        typeAction1.setId(null);
        assertThat(typeAction1).isNotEqualTo(typeAction2);
    }
}
