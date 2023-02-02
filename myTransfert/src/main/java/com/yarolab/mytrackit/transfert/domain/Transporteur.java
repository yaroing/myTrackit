package com.yarolab.mytrackit.transfert.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transporteur.
 */
@Entity
@Table(name = "transporteur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transporteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_transporteur")
    private String nomTransporteur;

    @Column(name = "nom_directeur")
    private String nomDirecteur;

    @Column(name = "phone_transporteur")
    private String phoneTransporteur;

    @Column(name = "email_transporteur")
    private String emailTransporteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transporteur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTransporteur() {
        return this.nomTransporteur;
    }

    public Transporteur nomTransporteur(String nomTransporteur) {
        this.setNomTransporteur(nomTransporteur);
        return this;
    }

    public void setNomTransporteur(String nomTransporteur) {
        this.nomTransporteur = nomTransporteur;
    }

    public String getNomDirecteur() {
        return this.nomDirecteur;
    }

    public Transporteur nomDirecteur(String nomDirecteur) {
        this.setNomDirecteur(nomDirecteur);
        return this;
    }

    public void setNomDirecteur(String nomDirecteur) {
        this.nomDirecteur = nomDirecteur;
    }

    public String getPhoneTransporteur() {
        return this.phoneTransporteur;
    }

    public Transporteur phoneTransporteur(String phoneTransporteur) {
        this.setPhoneTransporteur(phoneTransporteur);
        return this;
    }

    public void setPhoneTransporteur(String phoneTransporteur) {
        this.phoneTransporteur = phoneTransporteur;
    }

    public String getEmailTransporteur() {
        return this.emailTransporteur;
    }

    public Transporteur emailTransporteur(String emailTransporteur) {
        this.setEmailTransporteur(emailTransporteur);
        return this;
    }

    public void setEmailTransporteur(String emailTransporteur) {
        this.emailTransporteur = emailTransporteur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transporteur)) {
            return false;
        }
        return id != null && id.equals(((Transporteur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transporteur{" +
            "id=" + getId() +
            ", nomTransporteur='" + getNomTransporteur() + "'" +
            ", nomDirecteur='" + getNomDirecteur() + "'" +
            ", phoneTransporteur='" + getPhoneTransporteur() + "'" +
            ", emailTransporteur='" + getEmailTransporteur() + "'" +
            "}";
    }
}
