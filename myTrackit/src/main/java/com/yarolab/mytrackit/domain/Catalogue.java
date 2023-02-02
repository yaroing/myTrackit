package com.yarolab.mytrackit.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Catalogue.
 */
@Table("catalogue")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("material_code")
    private String materialCode;

    @Column("material_desc")
    private String materialDesc;

    @Column("material_group")
    private String materialGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Catalogue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return this.materialCode;
    }

    public Catalogue materialCode(String materialCode) {
        this.setMaterialCode(materialCode);
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialDesc() {
        return this.materialDesc;
    }

    public Catalogue materialDesc(String materialDesc) {
        this.setMaterialDesc(materialDesc);
        return this;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getMaterialGroup() {
        return this.materialGroup;
    }

    public Catalogue materialGroup(String materialGroup) {
        this.setMaterialGroup(materialGroup);
        return this;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Catalogue)) {
            return false;
        }
        return id != null && id.equals(((Catalogue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Catalogue{" +
            "id=" + getId() +
            ", materialCode='" + getMaterialCode() + "'" +
            ", materialDesc='" + getMaterialDesc() + "'" +
            ", materialGroup='" + getMaterialGroup() + "'" +
            "}";
    }
}
