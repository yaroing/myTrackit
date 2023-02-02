package com.yarolab.mytrackit.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Section.
 */
@Table("section")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("section_nom")
    private String sectionNom;

    @Column("chef_section")
    private String chefSection;

    @Column("email_chef")
    private String emailChef;

    @Column("phone_chef")
    private String phoneChef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Section id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectionNom() {
        return this.sectionNom;
    }

    public Section sectionNom(String sectionNom) {
        this.setSectionNom(sectionNom);
        return this;
    }

    public void setSectionNom(String sectionNom) {
        this.sectionNom = sectionNom;
    }

    public String getChefSection() {
        return this.chefSection;
    }

    public Section chefSection(String chefSection) {
        this.setChefSection(chefSection);
        return this;
    }

    public void setChefSection(String chefSection) {
        this.chefSection = chefSection;
    }

    public String getEmailChef() {
        return this.emailChef;
    }

    public Section emailChef(String emailChef) {
        this.setEmailChef(emailChef);
        return this;
    }

    public void setEmailChef(String emailChef) {
        this.emailChef = emailChef;
    }

    public String getPhoneChef() {
        return this.phoneChef;
    }

    public Section phoneChef(String phoneChef) {
        this.setPhoneChef(phoneChef);
        return this;
    }

    public void setPhoneChef(String phoneChef) {
        this.phoneChef = phoneChef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Section)) {
            return false;
        }
        return id != null && id.equals(((Section) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Section{" +
            "id=" + getId() +
            ", sectionNom='" + getSectionNom() + "'" +
            ", chefSection='" + getChefSection() + "'" +
            ", emailChef='" + getEmailChef() + "'" +
            ", phoneChef='" + getPhoneChef() + "'" +
            "}";
    }
}
