package com.yarolab.mytrackit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Action.
 */
@Table("action")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("date_action")
    private Instant dateAction;

    @Column("rapport_action")
    private String rapportAction;

    @Transient
    @JsonIgnoreProperties(value = { "actions", "itemTransferts" }, allowSetters = true)
    private Transfert transfert;

    @Column("transfert_id")
    private Long transfertId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Action id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateAction() {
        return this.dateAction;
    }

    public Action dateAction(Instant dateAction) {
        this.setDateAction(dateAction);
        return this;
    }

    public void setDateAction(Instant dateAction) {
        this.dateAction = dateAction;
    }

    public String getRapportAction() {
        return this.rapportAction;
    }

    public Action rapportAction(String rapportAction) {
        this.setRapportAction(rapportAction);
        return this;
    }

    public void setRapportAction(String rapportAction) {
        this.rapportAction = rapportAction;
    }

    public Transfert getTransfert() {
        return this.transfert;
    }

    public void setTransfert(Transfert transfert) {
        this.transfert = transfert;
        this.transfertId = transfert != null ? transfert.getId() : null;
    }

    public Action transfert(Transfert transfert) {
        this.setTransfert(transfert);
        return this;
    }

    public Long getTransfertId() {
        return this.transfertId;
    }

    public void setTransfertId(Long transfert) {
        this.transfertId = transfert;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        return id != null && id.equals(((Action) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", dateAction='" + getDateAction() + "'" +
            ", rapportAction='" + getRapportAction() + "'" +
            "}";
    }
}
