package com.yarolab.mytrackit.pointservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Monitoring.
 */
@Entity
@Table(name = "monitoring")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Monitoring implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "atpe_annee")
    private Integer atpeAnnee;

    @Column(name = "atpe_mois")
    private Integer atpeMois;

    @Column(name = "atpe_stock")
    private String atpeStock;

    @Column(name = "atpe_dispo")
    private Double atpeDispo;

    @Column(name = "atpe_endom")
    private Double atpeEndom;

    @Column(name = "atpe_perime")
    private Double atpePerime;

    @Column(name = "atpe_rupture")
    private String atpeRupture;

    @Column(name = "atpe_njour")
    private Integer atpeNjour;

    @Column(name = "atpe_magasin")
    private String atpeMagasin;

    @Column(name = "atpe_palette")
    private String atpePalette;

    @Column(name = "atpe_position")
    private String atpePosition;

    @Column(name = "atpe_hauteur")
    private Double atpeHauteur;

    @Column(name = "atpe_personnel")
    private String atpePersonnel;

    @Column(name = "atpe_admission")
    private Integer atpeAdmission;

    @Column(name = "atpe_sortie")
    private Integer atpeSortie;

    @Column(name = "atpe_gueris")
    private Integer atpeGueris;

    @Column(name = "atpe_abandon")
    private Integer atpeAbandon;

    @Column(name = "atpe_poids")
    private Integer atpePoids;

    @Column(name = "atpe_trasnsfert")
    private Integer atpeTrasnsfert;

    @Column(name = "atpe_parent")
    private Integer atpeParent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "monitorings", "requetePointServices", "stockPointServices" }, allowSetters = true)
    private PointService pointService;

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
    }

    public Monitoring pointService(PointService pointService) {
        this.setPointService(pointService);
        return this;
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
