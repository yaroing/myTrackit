package com.yarolab.mytrackit.transfert.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_action")
    private Instant dateAction;

    @Lob
    @Column(name = "rapport_action")
    private String rapportAction;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions", "itemTransferts" }, allowSetters = true)
    private Transfert transfert;

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
    }

    public Action transfert(Transfert transfert) {
        this.setTransfert(transfert);
        return this;
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
