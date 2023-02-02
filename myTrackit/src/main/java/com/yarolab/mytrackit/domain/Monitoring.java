package com.yarolab.mytrackit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Monitoring.
 */
@Table("monitoring")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Monitoring implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("atpe_annee")
    private Integer atpeAnnee;

    @Column("atpe_mois")
    private Integer atpeMois;

    @Column("atpe_stock")
    private String atpeStock;

    @Column("atpe_dispo")
    private Double atpeDispo;

    @Column("atpe_endom")
    private Double atpeEndom;

    @Column("atpe_perime")
    private Double atpePerime;

    @Column("atpe_rupture")
    private String atpeRupture;

    @Column("atpe_njour")
    private Integer atpeNjour;

    @Column("atpe_magasin")
    private String atpeMagasin;

    @Column("atpe_palette")
    private String atpePalette;

    @Column("atpe_position")
    private String atpePosition;

    @Column("atpe_hauteur")
    private Double atpeHauteur;

    @Column("atpe_personnel")
    private String atpePersonnel;

    @Column("atpe_admission")
    private Integer atpeAdmission;

    @Column("atpe_sortie")
    private Integer atpeSortie;

    @Column("atpe_gueris")
    private Integer atpeGueris;

    @Column("atpe_abandon")
    private Integer atpeAbandon;

    @Column("atpe_poids")
    private Integer atpePoids;

    @Column("atpe_trasnsfert")
    private Integer atpeTrasnsfert;

    @Column("atpe_parent")
    private Integer atpeParent;

    @Transient
    @JsonIgnoreProperties(value = { "monitorings", "requetePointServices", "stockPointServices" }, allowSetters = true)
    private PointService pointService;

    @Column("point_service_id")
    private Long pointServiceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Monitoring id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAtpeAnnee() {
        return this.atpeAnnee;
    }

    public Monitoring atpeAnnee(Integer atpeAnnee) {
        this.setAtpeAnnee(atpeAnnee);
        return this;
    }

    public void setAtpeAnnee(Integer atpeAnnee) {
        this.atpeAnnee = atpeAnnee;
    }

    public Integer getAtpeMois() {
        return this.atpeMois;
    }

    public Monitoring atpeMois(Integer atpeMois) {
        this.setAtpeMois(atpeMois);
        return this;
    }

    public void setAtpeMois(Integer atpeMois) {
        this.atpeMois = atpeMois;
    }

    public String getAtpeStock() {
        return this.atpeStock;
    }

    public Monitoring atpeStock(String atpeStock) {
        this.setAtpeStock(atpeStock);
        return this;
    }

    public void setAtpeStock(String atpeStock) {
        this.atpeStock = atpeStock;
    }

    public Double getAtpeDispo() {
        return this.atpeDispo;
    }

    public Monitoring atpeDispo(Double atpeDispo) {
        this.setAtpeDispo(atpeDispo);
        return this;
    }

    public void setAtpeDispo(Double atpeDispo) {
        this.atpeDispo = atpeDispo;
    }

    public Double getAtpeEndom() {
        return this.atpeEndom;
    }

    public Monitoring atpeEndom(Double atpeEndom) {
        this.setAtpeEndom(atpeEndom);
        return this;
    }

    public void setAtpeEndom(Double atpeEndom) {
        this.atpeEndom = atpeEndom;
    }

    public Double getAtpePerime() {
        return this.atpePerime;
    }

    public Monitoring atpePerime(Double atpePerime) {
        this.setAtpePerime(atpePerime);
        return this;
    }

    public void setAtpePerime(Double atpePerime) {
        this.atpePerime = atpePerime;
    }

    public String getAtpeRupture() {
        return this.atpeRupture;
    }

    public Monitoring atpeRupture(String atpeRupture) {
        this.setAtpeRupture(atpeRupture);
        return this;
    }

    public void setAtpeRupture(String atpeRupture) {
        this.atpeRupture = atpeRupture;
    }

    public Integer getAtpeNjour() {
        return this.atpeNjour;
    }

    public Monitoring atpeNjour(Integer atpeNjour) {
        this.setAtpeNjour(atpeNjour);
        return this;
    }

    public void setAtpeNjour(Integer atpeNjour) {
        this.atpeNjour = atpeNjour;
    }

    public String getAtpeMagasin() {
        return this.atpeMagasin;
    }

    public Monitoring atpeMagasin(String atpeMagasin) {
        this.setAtpeMagasin(atpeMagasin);
        return this;
    }

    public void setAtpeMagasin(String atpeMagasin) {
        this.atpeMagasin = atpeMagasin;
    }

    public String getAtpePalette() {
        return this.atpePalette;
    }

    public Monitoring atpePalette(String atpePalette) {
        this.setAtpePalette(atpePalette);
        return this;
    }

    public void setAtpePalette(String atpePalette) {
        this.atpePalette = atpePalette;
    }

    public String getAtpePosition() {
        return this.atpePosition;
    }

    public Monitoring atpePosition(String atpePosition) {
        this.setAtpePosition(atpePosition);
        return this;
    }

    public void setAtpePosition(String atpePosition) {
        this.atpePosition = atpePosition;
    }

    public Double getAtpeHauteur() {
        return this.atpeHauteur;
    }

    public Monitoring atpeHauteur(Double atpeHauteur) {
        this.setAtpeHauteur(atpeHauteur);
        return this;
    }

    public void setAtpeHauteur(Double atpeHauteur) {
        this.atpeHauteur = atpeHauteur;
    }

    public String getAtpePersonnel() {
        return this.atpePersonnel;
    }

    public Monitoring atpePersonnel(String atpePersonnel) {
        this.setAtpePersonnel(atpePersonnel);
        return this;
    }

    public void setAtpePersonnel(String atpePersonnel) {
        this.atpePersonnel = atpePersonnel;
    }

    public Integer getAtpeAdmission() {
        return this.atpeAdmission;
    }

    public Monitoring atpeAdmission(Integer atpeAdmission) {
        this.setAtpeAdmission(atpeAdmission);
        return this;
    }

    public void setAtpeAdmission(Integer atpeAdmission) {
        this.atpeAdmission = atpeAdmission;
    }

    public Integer getAtpeSortie() {
        return this.atpeSortie;
    }

    public Monitoring atpeSortie(Integer atpeSortie) {
        this.setAtpeSortie(atpeSortie);
        return this;
    }

    public void setAtpeSortie(Integer atpeSortie) {
        this.atpeSortie = atpeSortie;
    }

    public Integer getAtpeGueris() {
        return this.atpeGueris;
    }

    public Monitoring atpeGueris(Integer atpeGueris) {
        this.setAtpeGueris(atpeGueris);
        return this;
    }

    public void setAtpeGueris(Integer atpeGueris) {
        this.atpeGueris = atpeGueris;
    }

    public Integer getAtpeAbandon() {
        return this.atpeAbandon;
    }

    public Monitoring atpeAbandon(Integer atpeAbandon) {
        this.setAtpeAbandon(atpeAbandon);
        return this;
    }

    public void setAtpeAbandon(Integer atpeAbandon) {
        this.atpeAbandon = atpeAbandon;
    }

    public Integer getAtpePoids() {
        return this.atpePoids;
    }

    public Monitoring atpePoids(Integer atpePoids) {
        this.setAtpePoids(atpePoids);
        return this;
    }

    public void setAtpePoids(Integer atpePoids) {
        this.atpePoids = atpePoids;
    }

    public Integer getAtpeTrasnsfert() {
        return this.atpeTrasnsfert;
    }

    public Monitoring atpeTrasnsfert(Integer atpeTrasnsfert) {
        this.setAtpeTrasnsfert(atpeTrasnsfert);
        return this;
    }

    public void setAtpeTrasnsfert(Integer atpeTrasnsfert) {
        this.atpeTrasnsfert = atpeTrasnsfert;
    }

    public Integer getAtpeParent() {
        return this.atpeParent;
    }

    public Monitoring atpeParent(Integer atpeParent) {
        this.setAtpeParent(atpeParent);
        return this;
    }

    public void setAtpeParent(Integer atpeParent) {
        this.atpeParent = atpeParent;
    }

    public PointService getPointService() {
        return this.pointService;
    }

    public void setPointService(PointService pointService) {
        this.pointService = pointService;
        this.pointServiceId = pointService != null ? pointService.getId() : null;
    }

    public Monitoring pointService(PointService pointService) {
        this.setPointService(pointService);
        return this;
    }

    public Long getPointServiceId() {
        return this.pointServiceId;
    }

    public void setPointServiceId(Long pointService) {
        this.pointServiceId = pointService;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Monitoring)) {
            return false;
        }
        return id != null && id.equals(((Monitoring) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Monitoring{" +
            "id=" + getId() +
            ", atpeAnnee=" + getAtpeAnnee() +
            ", atpeMois=" + getAtpeMois() +
            ", atpeStock='" + getAtpeStock() + "'" +
            ", atpeDispo=" + getAtpeDispo() +
            ", atpeEndom=" + getAtpeEndom() +
            ", atpePerime=" + getAtpePerime() +
            ", atpeRupture='" + getAtpeRupture() + "'" +
            ", atpeNjour=" + getAtpeNjour() +
            ", atpeMagasin='" + getAtpeMagasin() + "'" +
            ", atpePalette='" + getAtpePalette() + "'" +
            ", atpePosition='" + getAtpePosition() + "'" +
            ", atpeHauteur=" + getAtpeHauteur() +
            ", atpePersonnel='" + getAtpePersonnel() + "'" +
            ", atpeAdmission=" + getAtpeAdmission() +
            ", atpeSortie=" + getAtpeSortie() +
            ", atpeGueris=" + getAtpeGueris() +
            ", atpeAbandon=" + getAtpeAbandon() +
            ", atpePoids=" + getAtpePoids() +
            ", atpeTrasnsfert=" + getAtpeTrasnsfert() +
            ", atpeParent=" + getAtpeParent() +
            "}";
    }
}
