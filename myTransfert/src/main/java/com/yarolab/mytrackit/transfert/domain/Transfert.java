package com.yarolab.mytrackit.transfert.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transfert.
 */
@Entity
@Table(name = "transfert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_exp")
    private Instant dateExp;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "partenaire_id")
    private String partenaireId;

    @Column(name = "transporteur_id")
    private String transporteurId;

    @Column(name = "nom_chauffeur")
    private String nomChauffeur;

    @Column(name = "date_rec")
    private Instant dateRec;

    @Column(name = "statut_rec_id")
    private Integer statutRecId;

    @Column(name = "cphone")
    private String cphone;

    @OneToMany(mappedBy = "transfert")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transfert" }, allowSetters = true)
    private Set<Action> actions = new HashSet<>();

    @OneToMany(mappedBy = "transfert")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transfert" }, allowSetters = true)
    private Set<ItemTransfert> itemTransferts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transfert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateExp() {
        return this.dateExp;
    }

    public Transfert dateExp(Instant dateExp) {
        this.setDateExp(dateExp);
        return this;
    }

    public void setDateExp(Instant dateExp) {
        this.dateExp = dateExp;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public Transfert sectionId(Integer sectionId) {
        this.setSectionId(sectionId);
        return this;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getStaffId() {
        return this.staffId;
    }

    public Transfert staffId(Integer staffId) {
        this.setStaffId(staffId);
        return this;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getPartenaireId() {
        return this.partenaireId;
    }

    public Transfert partenaireId(String partenaireId) {
        this.setPartenaireId(partenaireId);
        return this;
    }

    public void setPartenaireId(String partenaireId) {
        this.partenaireId = partenaireId;
    }

    public String getTransporteurId() {
        return this.transporteurId;
    }

    public Transfert transporteurId(String transporteurId) {
        this.setTransporteurId(transporteurId);
        return this;
    }

    public void setTransporteurId(String transporteurId) {
        this.transporteurId = transporteurId;
    }

    public String getNomChauffeur() {
        return this.nomChauffeur;
    }

    public Transfert nomChauffeur(String nomChauffeur) {
        this.setNomChauffeur(nomChauffeur);
        return this;
    }

    public void setNomChauffeur(String nomChauffeur) {
        this.nomChauffeur = nomChauffeur;
    }

    public Instant getDateRec() {
        return this.dateRec;
    }

    public Transfert dateRec(Instant dateRec) {
        this.setDateRec(dateRec);
        return this;
    }

    public void setDateRec(Instant dateRec) {
        this.dateRec = dateRec;
    }

    public Integer getStatutRecId() {
        return this.statutRecId;
    }

    public Transfert statutRecId(Integer statutRecId) {
        this.setStatutRecId(statutRecId);
        return this;
    }

    public void setStatutRecId(Integer statutRecId) {
        this.statutRecId = statutRecId;
    }

    public String getCphone() {
        return this.cphone;
    }

    public Transfert cphone(String cphone) {
        this.setCphone(cphone);
        return this;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public Set<Action> getActions() {
        return this.actions;
    }

    public void setActions(Set<Action> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setTransfert(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setTransfert(this));
        }
        this.actions = actions;
    }

    public Transfert actions(Set<Action> actions) {
        this.setActions(actions);
        return this;
    }

    public Transfert addAction(Action action) {
        this.actions.add(action);
        action.setTransfert(this);
        return this;
    }

    public Transfert removeAction(Action action) {
        this.actions.remove(action);
        action.setTransfert(null);
        return this;
    }

    public Set<ItemTransfert> getItemTransferts() {
        return this.itemTransferts;
    }

    public void setItemTransferts(Set<ItemTransfert> itemTransferts) {
        if (this.itemTransferts != null) {
            this.itemTransferts.forEach(i -> i.setTransfert(null));
        }
        if (itemTransferts != null) {
            itemTransferts.forEach(i -> i.setTransfert(this));
        }
        this.itemTransferts = itemTransferts;
    }

    public Transfert itemTransferts(Set<ItemTransfert> itemTransferts) {
        this.setItemTransferts(itemTransferts);
        return this;
    }

    public Transfert addItemTransfert(ItemTransfert itemTransfert) {
        this.itemTransferts.add(itemTransfert);
        itemTransfert.setTransfert(this);
        return this;
    }

    public Transfert removeItemTransfert(ItemTransfert itemTransfert) {
        this.itemTransferts.remove(itemTransfert);
        itemTransfert.setTransfert(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfert)) {
            return false;
        }
        return id != null && id.equals(((Transfert) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfert{" +
            "id=" + getId() +
            ", dateExp='" + getDateExp() + "'" +
            ", sectionId=" + getSectionId() +
            ", staffId=" + getStaffId() +
            ", partenaireId='" + getPartenaireId() + "'" +
            ", transporteurId='" + getTransporteurId() + "'" +
            ", nomChauffeur='" + getNomChauffeur() + "'" +
            ", dateRec='" + getDateRec() + "'" +
            ", statutRecId=" + getStatutRecId() +
            ", cphone='" + getCphone() + "'" +
            "}";
    }
}
