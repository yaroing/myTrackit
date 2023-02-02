package com.yarolab.mytrackit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A DetailsRequete.
 */
@Table("details_requete")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetailsRequete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("quantite_demandee")
    private Double quantiteDemandee;

    @Column("quantite_approuvee")
    private Double quantiteApprouvee;

    @Column("quantite_recue")
    private Double quantiteRecue;

    @Column("item_obs")
    private String itemObs;

    @Transient
    @JsonIgnoreProperties(value = { "detailsRequetes" }, allowSetters = true)
    private RequetePartenaire requetePartenaire;

    @Column("requete_partenaire_id")
    private Long requetePartenaireId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetailsRequete id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantiteDemandee() {
        return this.quantiteDemandee;
    }

    public DetailsRequete quantiteDemandee(Double quantiteDemandee) {
        this.setQuantiteDemandee(quantiteDemandee);
        return this;
    }

    public void setQuantiteDemandee(Double quantiteDemandee) {
        this.quantiteDemandee = quantiteDemandee;
    }

    public Double getQuantiteApprouvee() {
        return this.quantiteApprouvee;
    }

    public DetailsRequete quantiteApprouvee(Double quantiteApprouvee) {
        this.setQuantiteApprouvee(quantiteApprouvee);
        return this;
    }

    public void setQuantiteApprouvee(Double quantiteApprouvee) {
        this.quantiteApprouvee = quantiteApprouvee;
    }

    public Double getQuantiteRecue() {
        return this.quantiteRecue;
    }

    public DetailsRequete quantiteRecue(Double quantiteRecue) {
        this.setQuantiteRecue(quantiteRecue);
        return this;
    }

    public void setQuantiteRecue(Double quantiteRecue) {
        this.quantiteRecue = quantiteRecue;
    }

    public String getItemObs() {
        return this.itemObs;
    }

    public DetailsRequete itemObs(String itemObs) {
        this.setItemObs(itemObs);
        return this;
    }

    public void setItemObs(String itemObs) {
        this.itemObs = itemObs;
    }

    public RequetePartenaire getRequetePartenaire() {
        return this.requetePartenaire;
    }

    public void setRequetePartenaire(RequetePartenaire requetePartenaire) {
        this.requetePartenaire = requetePartenaire;
        this.requetePartenaireId = requetePartenaire != null ? requetePartenaire.getId() : null;
    }

    public DetailsRequete requetePartenaire(RequetePartenaire requetePartenaire) {
        this.setRequetePartenaire(requetePartenaire);
        return this;
    }

    public Long getRequetePartenaireId() {
        return this.requetePartenaireId;
    }

    public void setRequetePartenaireId(Long requetePartenaire) {
        this.requetePartenaireId = requetePartenaire;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailsRequete)) {
            return false;
        }
        return id != null && id.equals(((DetailsRequete) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailsRequete{" +
            "id=" + getId() +
            ", quantiteDemandee=" + getQuantiteDemandee() +
            ", quantiteApprouvee=" + getQuantiteApprouvee() +
            ", quantiteRecue=" + getQuantiteRecue() +
            ", itemObs='" + getItemObs() + "'" +
            "}";
    }
}
