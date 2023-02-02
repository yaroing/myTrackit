package com.yarolab.mytrackit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yarolab.mytrackit.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SuiviMissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuiviMission.class);
        SuiviMission suiviMission1 = new SuiviMission();
        suiviMission1.setId(1L);
        SuiviMission suiviMission2 = new SuiviMission();
        suiviMission2.setId(suiviMission1.getId());
        assertThat(suiviMission1).isEqualTo(suiviMission2);
        suiviMission2.setId(2L);
        assertThat(suiviMission1).isNotEqualTo(suiviMission2);
        suiviMission1.setId(null);
        assertThat(suiviMission1).isNotEqualTo(suiviMission2);
    }
}
