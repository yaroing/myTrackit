package com.yarolab.mytrackit.pointservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StockPointService.
 */
@Entity
@Table(name = "stock_point_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockPointService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock_annee")
    private String stockAnnee;

    @Column(name = "stock_mois")
    private String stockMois;

    @Column(name = "entree_mois")
    private Double entreeMois;

    @Column(name = "sortie_mois")
    private Double sortieMois;

    @Column(name = "stock_finmois")
    private Double stockFinmois;

    @Column(name = "stock_debut")
    private Double stockDebut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "monitorings", "requetePointServices", "stockPointServices" }, allowSetters = true)
    private PointService pointService;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StockPointService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockAnnee() {
        return this.stockAnnee;
    }

    public StockPointService stockAnnee(String stockAnnee) {
        this.setStockAnnee(stockAnnee);
        return this;
    }

    public void setStockAnnee(String stockAnnee) {
        this.stockAnnee = stockAnnee;
    }

    public String getStockMois() {
        return this.stockMois;
    }

    public StockPointService stockMois(String stockMois) {
        this.setStockMois(stockMois);
        return this;
    }

    public void setStockMois(String stockMois) {
        this.stockMois = stockMois;
    }

    public Double getEntreeMois() {
        return this.entreeMois;
    }

    public StockPointService entreeMois(Double entreeMois) {
        this.setEntreeMois(entreeMois);
        return this;
    }

    public void setEntreeMois(Double entreeMois) {
        this.entreeMois = entreeMois;
    }

    public Double getSortieMois() {
        return this.sortieMois;
    }

    public StockPointService sortieMois(Double sortieMois) {
        this.setSortieMois(sortieMois);
        return this;
    }

    public void setSortieMois(Double sortieMois) {
        this.sortieMois = sortieMois;
    }

    public Double getStockFinmois() {
        return this.stockFinmois;
    }

    public StockPointService stockFinmois(Double stockFinmois) {
        this.setStockFinmois(stockFinmois);
        return this;
    }

    public void setStockFinmois(Double stockFinmois) {
        this.stockFinmois = stockFinmois;
    }

    public Double getStockDebut() {
        return this.stockDebut;
    }

    public StockPointService stockDebut(Double stockDebut) {
        this.setStockDebut(stockDebut);
        return this;
    }

    public void setStockDebut(Double stockDebut) {
        this.stockDebut = stockDebut;
    }

    public PointService getPointService() {
        return this.pointService;
    }

    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }

    public StockPointService pointService(PointService pointService) {
        this.setPointService(pointService);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockPointService)) {
            return false;
        }
        return id != null && id.equals(((StockPointService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockPointService{" +
            "id=" + getId() +
            ", stockAnnee='" + getStockAnnee() + "'" +
            ", stockMois='" + getStockMois() + "'" +
            ", entreeMois=" + getEntreeMois() +
            ", sortieMois=" + getSortieMois() +
            ", stockFinmois=" + getStockFinmois() +
            ", stockDebut=" + getStockDebut() +
            "}";
    }
}
