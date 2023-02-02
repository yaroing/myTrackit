package com.yarolab.mytrackit.pointservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PointService.
 */
@Entity
@Table(name = "point_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "partenaire_id")
    private String partenaireId;

    @Column(name = "nom_pos")
    private String nomPos;

    @Column(name = "pos_lon")
    private Double posLon;

    @Column(name = "pos_lat")
    private Double posLat;

    @Column(name = "pos_contact")
    private String posContact;

    @Column(name = "pos_gsm")
    private String posGsm;

    @OneToMany(mappedBy = "pointService")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pointService" }, allowSetters = true)
    private Set<Monitoring> monitorings = new HashSet<>();

    @OneToMany(mappedBy = "pointService")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pointService" }, allowSetters = true)
    private Set<RequetePointService> requetePointServices = new HashSet<>();

    @OneToMany(mappedBy = "pointService")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pointService" }, allowSetters = true)
    private Set<StockPointService> stockPointServices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartenaireId() {
        return this.partenaireId;
    }

    public PointService partenaireId(String partenaireId) {
        this.setPartenaireId(partenaireId);
        return this;
    }

    public void setPartenaireId(String partenaireId) {
        this.partenaireId = partenaireId;
    }

    public String getNomPos() {
        return this.nomPos;
    }

    public PointService nomPos(String nomPos) {
        this.setNomPos(nomPos);
        return this;
    }

    public void setNomPos(String nomPos) {
        this.nomPos = nomPos;
    }

    public Double getPosLon() {
        return this.posLon;
    }

    public PointService posLon(Double posLon) {
        this.setPosLon(posLon);
        return this;
    }

    public void setPosLon(Double posLon) {
        this.posLon = posLon;
    }

    public Double getPosLat() {
        return this.posLat;
    }

    public PointService posLat(Double posLat) {
        this.setPosLat(posLat);
        return this;
    }

    public void setPosLat(Double posLat) {
        this.posLat = posLat;
    }

    public String getPosContact() {
        return this.posContact;
    }

    public PointService posContact(String posContact) {
        this.setPosContact(posContact);
        return this;
    }

    public void setPosContact(String posContact) {
        this.posContact = posContact;
    }

    public String getPosGsm() {
        return this.posGsm;
    }

    public PointService posGsm(String posGsm) {
        this.setPosGsm(posGsm);
        return this;
    }

    public void setPosGsm(String posGsm) {
        this.posGsm = posGsm;
    }

    public Set<Monitoring> getMonitorings() {
        return this.monitorings;
    }

    public void setMonitorings(Set<Monitoring> monitorings) {
        if (this.monitorings != null) {
            this.monitorings.forEach(i -> i.setPointService(null));
        }
        if (monitorings != null) {
            monitorings.forEach(i -> i.setPointService(this));
        }
        this.monitorings = monitorings;
    }

    public PointService monitorings(Set<Monitoring> monitorings) {
        this.setMonitorings(monitorings);
        return this;
    }

    public PointService addMonitoring(Monitoring monitoring) {
        this.monitorings.add(monitoring);
        monitoring.setPointService(this);
        return this;
    }

    public PointService removeMonitoring(Monitoring monitoring) {
        this.monitorings.remove(monitoring);
        monitoring.setPointService(null);
        return this;
    }

    public Set<RequetePointService> getRequetePointServices() {
        return this.requetePointServices;
    }

    public void setRequetePointServices(Set<RequetePointService> requetePointServices) {
        if (this.requetePointServices != null) {
            this.requetePointServices.forEach(i -> i.setPointService(null));
        }
        if (requetePointServices != null) {
            requetePointServices.forEach(i -> i.setPointService(this));
        }
        this.requetePointServices = requetePointServices;
    }

    public PointService requetePointServices(Set<RequetePointService> requetePointServices) {
        this.setRequetePointServices(requetePointServices);
        return this;
    }

    public PointService addRequetePointService(RequetePointService requetePointService) {
        this.requetePointServices.add(requetePointService);
        requetePointService.setPointService(this);
        return this;
    }

    public PointService removeRequetePointService(RequetePointService requetePointService) {
        this.requetePointServices.remove(requetePointService);
        requetePointService.setPointService(null);
        return this;
    }

    public Set<StockPointService> getStockPointServices() {
        return this.stockPointServices;
    }

    public void setStockPointServices(Set<StockPointService> stockPointServices) {
        if (this.stockPointServices != null) {
            this.stockPointServices.forEach(i -> i.setPointService(null));
        }
        if (stockPointServices != null) {
            stockPointServices.forEach(i -> i.setPointService(this));
        }
        this.stockPointServices = stockPointServices;
    }

    public PointService stockPointServices(Set<StockPointService> stockPointServices) {
        this.setStockPointServices(stockPointServices);
        return this;
    }

    public PointService addStockPointService(StockPointService stockPointService) {
        this.stockPointServices.add(stockPointService);
        stockPointService.setPointService(this);
        return this;
    }

    public PointService removeStockPointService(StockPointService stockPointService) {
        this.stockPointServices.remove(stockPointService);
        stockPointService.setPointService(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointService)) {
            return false;
        }
        return id != null && id.equals(((PointService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointService{" +
            "id=" + getId() +
            ", partenaireId='" + getPartenaireId() + "'" +
            ", nomPos='" + getNomPos() + "'" +
            ", posLon=" + getPosLon() +
            ", posLat=" + getPosLat() +
            ", posContact='" + getPosContact() + "'" +
            ", posGsm='" + getPosGsm() + "'" +
            "}";
    }
}
