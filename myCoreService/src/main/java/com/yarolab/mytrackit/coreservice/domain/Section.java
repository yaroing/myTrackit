package com.yarolab.mytrackit.coreservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "section_nom")
    private String sectionNom;

    @Column(name = "chef_section")
    private String chefSection;

    @Column(name = "email_chef")
    private String emailChef;

    @Column(name = "phone_chef")
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
