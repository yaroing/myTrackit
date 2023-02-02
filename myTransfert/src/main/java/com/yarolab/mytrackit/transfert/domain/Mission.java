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
 * A Mission.
 */
@Entity
@Table(name = "mission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_mission")
    private Instant dateMission;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "date_fin")
    private Instant dateFin;

    @Lob
    @Column(name = "rapport_mission")
    private byte[] rapportMission;

    @Column(name = "rapport_mission_content_type")
    private String rapportMissionContentType;

    @Column(name = "debut_mission")
    private Instant debutMission;

    @Column(name = "fin_mission")
    private Instant finMission;

    @Column(name = "field_10")
    private String field10;

    @Column(name = "fin")
    private String fin;

    @OneToMany(mappedBy = "mission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mission" }, allowSetters = true)
    private Set<ItemVerifie> itemVerifies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateMission() {
        return this.dateMission;
    }

    public Mission dateMission(Instant dateMission) {
        this.setDateMission(dateMission);
        return this;
    }

    public void setDateMission(Instant dateMission) {
        this.dateMission = dateMission;
    }

    public Instant getDateDebut() {
        return this.dateDebut;
    }

    public Mission dateDebut(Instant dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public Mission dateFin(Instant dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public byte[] getRapportMission() {
        return this.rapportMission;
    }

    public Mission rapportMission(byte[] rapportMission) {
        this.setRapportMission(rapportMission);
        return this;
    }

    public void setRapportMission(byte[] rapportMission) {
        this.rapportMission = rapportMission;
    }

    public String getRapportMissionContentType() {
        return this.rapportMissionContentType;
    }

    public Mission rapportMissionContentType(String rapportMissionContentType) {
        this.rapportMissionContentType = rapportMissionContentType;
        return this;
    }

    public void setRapportMissionContentType(String rapportMissionContentType) {
        this.rapportMissionContentType = rapportMissionContentType;
    }

    public Instant getDebutMission() {
        return this.debutMission;
    }

    public Mission debutMission(Instant debutMission) {
        this.setDebutMission(debutMission);
        return this;
    }

    public void setDebutMission(Instant debutMission) {
        this.debutMission = debutMission;
    }

    public Instant getFinMission() {
        return this.finMission;
    }

    public Mission finMission(Instant finMission) {
        this.setFinMission(finMission);
        return this;
    }

    public void setFinMission(Instant finMission) {
        this.finMission = finMission;
    }

    public String getField10() {
        return this.field10;
    }

    public Mission field10(String field10) {
        this.setField10(field10);
        return this;
    }

    public void setField10(String field10) {
        this.field10 = field10;
    }

    public String getFin() {
        return this.fin;
    }

    public Mission fin(String fin) {
        this.setFin(fin);
        return this;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public Set<ItemVerifie> getItemVerifies() {
        return this.itemVerifies;
    }

    public void setItemVerifies(Set<ItemVerifie> itemVerifies) {
        if (this.itemVerifies != null) {
            this.itemVerifies.forEach(i -> i.setMission(null));
        }
        if (itemVerifies != null) {
            itemVerifies.forEach(i -> i.setMission(this));
        }
        this.itemVerifies = itemVerifies;
    }

    public Mission itemVerifies(Set<ItemVerifie> itemVerifies) {
        this.setItemVerifies(itemVerifies);
        return this;
    }

    public Mission addItemVerifie(ItemVerifie itemVerifie) {
        this.itemVerifies.add(itemVerifie);
        itemVerifie.setMission(this);
        return this;
    }

    public Mission removeItemVerifie(ItemVerifie itemVerifie) {
        this.itemVerifies.remove(itemVerifie);
        itemVerifie.setMission(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mission)) {
            return false;
        }
        return id != null && id.equals(((Mission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mission{" +
            "id=" + getId() +
            ", dateMission='" + getDateMission() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", rapportMission='" + getRapportMission() + "'" +
            ", rapportMissionContentType='" + getRapportMissionContentType() + "'" +
            ", debutMission='" + getDebutMission() + "'" +
            ", finMission='" + getFinMission() + "'" +
            ", field10='" + getField10() + "'" +
            ", fin='" + getFin() + "'" +
            "}";
    }
}
