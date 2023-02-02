package com.yarolab.mytrackit.transfert.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SuiviMission.
 */
@Entity
@Table(name = "suivi_mission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SuiviMission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mission_eum_id")
    private Integer missionEumId;

    @Lob
    @Column(name = "probleme_constate")
    private String problemeConstate;

    @Lob
    @Column(name = "action_recommandee")
    private String actionRecommandee;

    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "date_echeance")
    private String dateEcheance;

    @Column(name = "statut_recom_id")
    private Integer statutRecomId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SuiviMission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMissionEumId() {
        return this.missionEumId;
    }

    public SuiviMission missionEumId(Integer missionEumId) {
        this.setMissionEumId(missionEumId);
        return this;
    }

    public void setMissionEumId(Integer missionEumId) {
        this.missionEumId = missionEumId;
    }

    public String getProblemeConstate() {
        return this.problemeConstate;
    }

    public SuiviMission problemeConstate(String problemeConstate) {
        this.setProblemeConstate(problemeConstate);
        return this;
    }

    public void setProblemeConstate(String problemeConstate) {
        this.problemeConstate = problemeConstate;
    }

    public String getActionRecommandee() {
        return this.actionRecommandee;
    }

    public SuiviMission actionRecommandee(String actionRecommandee) {
        this.setActionRecommandee(actionRecommandee);
        return this;
    }

    public void setActionRecommandee(String actionRecommandee) {
        this.actionRecommandee = actionRecommandee;
    }

    public Integer getStaffId() {
        return this.staffId;
    }

    public SuiviMission staffId(Integer staffId) {
        this.setStaffId(staffId);
        return this;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getDateEcheance() {
        return this.dateEcheance;
    }

    public SuiviMission dateEcheance(String dateEcheance) {
        this.setDateEcheance(dateEcheance);
        return this;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public Integer getStatutRecomId() {
        return this.statutRecomId;
    }

    public SuiviMission statutRecomId(Integer statutRecomId) {
        this.setStatutRecomId(statutRecomId);
        return this;
    }

    public void setStatutRecomId(Integer statutRecomId) {
        this.statutRecomId = statutRecomId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SuiviMission)) {
            return false;
        }
        return id != null && id.equals(((SuiviMission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuiviMission{" +
            "id=" + getId() +
            ", missionEumId=" + getMissionEumId() +
            ", problemeConstate='" + getProblemeConstate() + "'" +
            ", actionRecommandee='" + getActionRecommandee() + "'" +
            ", staffId=" + getStaffId() +
            ", dateEcheance='" + getDateEcheance() + "'" +
            ", statutRecomId=" + getStatutRecomId() +
            "}";
    }
}
