package com.yarolab.mytrackit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A ItemVerifie.
 */
@Table("item_verifie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemVerifie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("quantite_transfert")
    private Double quantiteTransfert;

    @Column("quantite_recu")
    private Double quantiteRecu;

    @Column("quantite_utilisee")
    private Double quantiteUtilisee;

    @Column("quantite_disponible")
    private Double quantiteDisponible;

    @Column("quantite_ecart")
    private Double quantiteEcart;

    @Transient
    @JsonIgnoreProperties(value = { "itemVerifies" }, allowSetters = true)
    private Mission mission;

    @Column("mission_id")
    private Long missionId;

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
        this.missionId = mission != null ? mission.getId() : null;
    }

    public ItemVerifie mission(Mission mission) {
        this.setMission(mission);
        return this;
    }

    public Long getMissionId() {
        return this.missionId;
    }

    public void setMissionId(Long mission) {
        this.missionId = mission;
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
            ", quantiteTransfert=" + getQuantiteTransfert() +
            ", quantiteRecu=" + getQuantiteRecu() +
            ", quantiteUtilisee=" + getQuantiteUtilisee() +
            ", quantiteDisponible=" + getQuantiteDisponible() +
            ", quantiteEcart=" + getQuantiteEcart() +
            "}";
    }
}
