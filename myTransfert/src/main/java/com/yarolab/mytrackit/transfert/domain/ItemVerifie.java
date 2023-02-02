package com.yarolab.mytrackit.transfert.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ItemVerifie.
 */
@Entity
@Table(name = "item_verifie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemVerifie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mission_id")
    private Integer missionId;

    @Column(name = "catalogue_id")
    private String catalogueId;

    @Column(name = "quantite_transfert")
    private Double quantiteTransfert;

    @Column(name = "quantite_recu")
    private Double quantiteRecu;

    @Column(name = "quantite_utilisee")
    private Double quantiteUtilisee;

    @Column(name = "quantite_disponible")
    private Double quantiteDisponible;

    @Column(name = "quantite_ecart")
    private Double quantiteEcart;

    @ManyToOne
    @JsonIgnoreProperties(value = { "itemVerifies" }, allowSetters = true)
    private Mission mission;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemVerifie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMissionId() {
        return this.missionId;
    }

    public ItemVerifie missionId(Integer missionId) {
        this.setMissionId(missionId);
        return this;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getCatalogueId() {
        return this.catalogueId;
    }

    public ItemVerifie catalogueId(String catalogueId) {
        this.setCatalogueId(catalogueId);
        return this;
    }

    public void setCatalogueId(String catalogueId) {
        this.catalogueId = catalogueId;
    }

    public Double getQuantiteTransfert() {
        return this.quantiteTransfert;
    }

    public ItemVerifie quantiteTransfert(Double quantiteTransfert) {
        this.setQuantiteTransfert(quantiteTransfert);
        return this;
    }

    public void setQuantiteTransfert(Double quantiteTransfert) {
        this.quantiteTransfert = quantiteTransfert;
    }

    public Double getQuantiteRecu() {
        return this.quantiteRecu;
    }

    public ItemVerifie quantiteRecu(Double quantiteRecu) {
        this.setQuantiteRecu(quantiteRecu);
        return this;
    }

    public void setQuantiteRecu(Double quantiteRecu) {
        this.quantiteRecu = quantiteRecu;
    }

    public Double getQuantiteUtilisee() {
        return this.quantiteUtilisee;
    }

    public ItemVerifie quantiteUtilisee(Double quantiteUtilisee) {
        this.setQuantiteUtilisee(quantiteUtilisee);
        return this;
    }

    public void setQuantiteUtilisee(Double quantiteUtilisee) {
        this.quantiteUtilisee = quantiteUtilisee;
    }

    public Double getQuantiteDisponible() {
        return this.quantiteDisponible;
    }

    public ItemVerifie quantiteDisponible(Double quantiteDisponible) {
        this.setQuantiteDisponible(quantiteDisponible);
        return this;
    }

    public void setQuantiteDisponible(Double quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public Double getQuantiteEcart() {
        return this.quantiteEcart;
    }

    public ItemVerifie quantiteEcart(Double quantiteEcart) {
        this.setQuantiteEcart(quantiteEcart);
        return this;
    }

    public void setQuantiteEcart(Double quantiteEcart) {
        this.quantiteEcart = quantiteEcart;
    }

    public Mission getMission() {
        return this.mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public ItemVerifie mission(Mission mission) {
        this.setMission(mission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemVerifie)) {
            return false;
        }
        return id != null && id.equals(((ItemVerifie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemVerifie{" +
            "id=" + getId() +
            ", missionId=" + getMissionId() +
            ", catalogueId='" + getCatalogueId() + "'" +
            ", quantiteTransfert=" + getQuantiteTransfert() +
            ", quantiteRecu=" + getQuantiteRecu() +
            ", quantiteUtilisee=" + getQuantiteUtilisee() +
            ", quantiteDisponible=" + getQuantiteDisponible() +
            ", quantiteEcart=" + getQuantiteEcart() +
            "}";
    }
}
