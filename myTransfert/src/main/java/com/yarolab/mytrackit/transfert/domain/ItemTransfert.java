package com.yarolab.mytrackit.transfert.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ItemTransfert.
 */
@Entity
@Table(name = "item_transfert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemTransfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "wayb_id")
    private Integer waybId;

    @Column(name = "ro_id")
    private Integer roId;

    @Column(name = "ro_date")
    private Instant roDate;

    @Column(name = "material_id")
    private String materialId;

    @Column(name = "mat_desc")
    private String matDesc;

    @Column(name = "unit")
    private String unit;

    @Column(name = "del_qty")
    private Double delQty;

    @Column(name = "value")
    private Double value;

    @Column(name = "batch")
    private String batch;

    @Column(name = "bb_date")
    private Instant bbDate;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "rec_qty")
    private Double recQty;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions", "itemTransferts" }, allowSetters = true)
    private Transfert transfert;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemTransfert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWaybId() {
        return this.waybId;
    }

    public ItemTransfert waybId(Integer waybId) {
        this.setWaybId(waybId);
        return this;
    }

    public void setWaybId(Integer waybId) {
        this.waybId = waybId;
    }

    public Integer getRoId() {
        return this.roId;
    }

    public ItemTransfert roId(Integer roId) {
        this.setRoId(roId);
        return this;
    }

    public void setRoId(Integer roId) {
        this.roId = roId;
    }

    public Instant getRoDate() {
        return this.roDate;
    }

    public ItemTransfert roDate(Instant roDate) {
        this.setRoDate(roDate);
        return this;
    }

    public void setRoDate(Instant roDate) {
        this.roDate = roDate;
    }

    public String getMaterialId() {
        return this.materialId;
    }

    public ItemTransfert materialId(String materialId) {
        this.setMaterialId(materialId);
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMatDesc() {
        return this.matDesc;
    }

    public ItemTransfert matDesc(String matDesc) {
        this.setMatDesc(matDesc);
        return this;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc;
    }

    public String getUnit() {
        return this.unit;
    }

    public ItemTransfert unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getDelQty() {
        return this.delQty;
    }

    public ItemTransfert delQty(Double delQty) {
        this.setDelQty(delQty);
        return this;
    }

    public void setDelQty(Double delQty) {
        this.delQty = delQty;
    }

    public Double getValue() {
        return this.value;
    }

    public ItemTransfert value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getBatch() {
        return this.batch;
    }

    public ItemTransfert batch(String batch) {
        this.setBatch(batch);
        return this;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Instant getBbDate() {
        return this.bbDate;
    }

    public ItemTransfert bbDate(Instant bbDate) {
        this.setBbDate(bbDate);
        return this;
    }

    public void setBbDate(Instant bbDate) {
        this.bbDate = bbDate;
    }

    public Double getWeight() {
        return this.weight;
    }

    public ItemTransfert weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return this.volume;
    }

    public ItemTransfert volume(Double volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getRecQty() {
        return this.recQty;
    }

    public ItemTransfert recQty(Double recQty) {
        this.setRecQty(recQty);
        return this;
    }

    public void setRecQty(Double recQty) {
        this.recQty = recQty;
    }

    public Transfert getTransfert() {
        return this.transfert;
    }

    public void setTransfert(Transfert transfert) {
        this.transfert = transfert;
    }

    public ItemTransfert transfert(Transfert transfert) {
        this.setTransfert(transfert);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemTransfert)) {
            return false;
        }
        return id != null && id.equals(((ItemTransfert) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemTransfert{" +
            "id=" + getId() +
            ", waybId=" + getWaybId() +
            ", roId=" + getRoId() +
            ", roDate='" + getRoDate() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", matDesc='" + getMatDesc() + "'" +
            ", unit='" + getUnit() + "'" +
            ", delQty=" + getDelQty() +
            ", value=" + getValue() +
            ", batch='" + getBatch() + "'" +
            ", bbDate='" + getBbDate() + "'" +
            ", weight=" + getWeight() +
            ", volume=" + getVolume() +
            ", recQty=" + getRecQty() +
            "}";
    }
}
