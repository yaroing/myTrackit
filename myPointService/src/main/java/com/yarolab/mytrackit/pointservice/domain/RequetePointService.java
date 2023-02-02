package com.yarolab.mytrackit.pointservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RequetePointService.
 */
@Entity
@Table(name = "requete_point_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequetePointService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock_disponible")
    private Double stockDisponible;

    @Column(name = "quant_dem")
    private Double quantDem;

    @Column(name = "quant_trs")
    private Double quantTrs;

    @Column(name = "quant_rec")
    private Double quantRec;

    @Column(name = "req_traitee")
    private Integer reqTraitee;

    @Column(name = "date_req")
    private Instant dateReq;

    @Column(name = "date_rec")
    private Instant dateRec;

    @Column(name = "date_transfert")
    private Instant dateTransfert;

    @ManyToOne
    @JsonIgnoreProperties(value = { "monitorings", "requetePointServices", "stockPointServices" }, allowSetters = true)
    private PointService pointService;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequetePointService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStockDisponible() {
        return this.stockDisponible;
    }

    public RequetePointService stockDisponible(Double stockDisponible) {
        this.setStockDisponible(stockDisponible);
        return this;
    }

    public void setStockDisponible(Double stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public Double getQuantDem() {
        return this.quantDem;
    }

    public RequetePointService quantDem(Double quantDem) {
        this.setQuantDem(quantDem);
        return this;
    }

    public void setQuantDem(Double quantDem) {
        this.quantDem = quantDem;
    }

    public Double getQuantTrs() {
        return this.quantTrs;
    }

    public RequetePointService quantTrs(Double quantTrs) {
        this.setQuantTrs(quantTrs);
        return this;
    }

    public void setQuantTrs(Double quantTrs) {
        this.quantTrs = quantTrs;
    }

    public Double getQuantRec() {
        return this.quantRec;
    }

    public RequetePointService quantRec(Double quantRec) {
        this.setQuantRec(quantRec);
        return this;
    }

    public void setQuantRec(Double quantRec) {
        this.quantRec = quantRec;
    }

    public Integer getReqTraitee() {
        return this.reqTraitee;
    }

    public RequetePointService reqTraitee(Integer reqTraitee) {
        this.setReqTraitee(reqTraitee);
        return this;
    }

    public void setReqTraitee(Integer reqTraitee) {
        this.reqTraitee = reqTraitee;
    }

    public Instant getDateReq() {
        return this.dateReq;
    }

    public RequetePointService dateReq(Instant dateReq) {
        this.setDateReq(dateReq);
        return this;
    }

    public void setDateReq(Instant dateReq) {
        this.dateReq = dateReq;
    }

    public Instant getDateRec() {
        return this.dateRec;
    }

    public RequetePointService dateRec(Instant dateRec) {
        this.setDateRec(dateRec);
        return this;
    }

    public void setDateRec(Instant dateRec) {
        this.dateRec = dateRec;
    }

    public Instant getDateTransfert() {
        return this.dateTransfert;
    }

    public RequetePointService dateTransfert(Instant dateTransfert) {
        this.setDateTransfert(dateTransfert);
        return this;
    }

    public void setDateTransfert(Instant dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public PointService getPointService() {
        return this.pointService;
    }

    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }

    public RequetePointService pointService(PointService pointService) {
        this.setPointService(pointService);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequetePointService)) {
            return false;
        }
        return id != null && id.equals(((RequetePointService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequetePointService{" +
            "id=" + getId() +
            ", stockDisponible=" + getStockDisponible() +
            ", quantDem=" + getQuantDem() +
            ", quantTrs=" + getQuantTrs() +
            ", quantRec=" + getQuantRec() +
            ", reqTraitee=" + getReqTraitee() +
            ", dateReq='" + getDateReq() + "'" +
            ", dateRec='" + getDateRec() + "'" +
            ", dateTransfert='" + getDateTransfert() + "'" +
            "}";
    }
}
