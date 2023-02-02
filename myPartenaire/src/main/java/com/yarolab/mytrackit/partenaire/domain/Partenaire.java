package com.yarolab.mytrackit.partenaire.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Partenaire.
 */
@Entity
@Table(name = "partenaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Partenaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_partenaire")
    private String nomPartenaire;

    @Column(name = "autre_nom")
    private String autreNom;

    @Column(name = "log_phone")
    private String logPhone;

    @Column(name = "email_partenaire")
    private String emailPartenaire;

    @Column(name = "loc_partenaire")
    private String locPartenaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partenaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPartenaire() {
        return this.nomPartenaire;
    }

    public Partenaire nomPartenaire(String nomPartenaire) {
        this.setNomPartenaire(nomPartenaire);
        return this;
    }

    public void setNomPartenaire(String nomPartenaire) {
        this.nomPartenaire = nomPartenaire;
    }

    public String getAutreNom() {
        return this.autreNom;
    }

    public Partenaire autreNom(String autreNom) {
        this.setAutreNom(autreNom);
        return this;
    }

    public void setAutreNom(String autreNom) {
        this.autreNom = autreNom;
    }

    public String getLogPhone() {
        return this.logPhone;
    }

    public Partenaire logPhone(String logPhone) {
        this.setLogPhone(logPhone);
        return this;
    }

    public void setLogPhone(String logPhone) {
        this.logPhone = logPhone;
    }

    public String getEmailPartenaire() {
        return this.emailPartenaire;
    }

    public Partenaire emailPartenaire(String emailPartenaire) {
        this.setEmailPartenaire(emailPartenaire);
        return this;
    }

    public void setEmailPartenaire(String emailPartenaire) {
        this.emailPartenaire = emailPartenaire;
    }

    public String getLocPartenaire() {
        return this.locPartenaire;
    }

    public Partenaire locPartenaire(String locPartenaire) {
        this.setLocPartenaire(locPartenaire);
        return this;
    }

    public void setLocPartenaire(String locPartenaire) {
        this.locPartenaire = locPartenaire;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partenaire)) {
            return false;
        }
        return id != null && id.equals(((Partenaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partenaire{" +
            "id=" + getId() +
            ", nomPartenaire='" + getNomPartenaire() + "'" +
            ", autreNom='" + getAutreNom() + "'" +
            ", logPhone='" + getLogPhone() + "'" +
            ", emailPartenaire='" + getEmailPartenaire() + "'" +
            ", locPartenaire='" + getLocPartenaire() + "'" +
            "}";
    }
}
