package com.yarolab.mytrackit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A RequetePartenaire.
 */
@Table("requete_partenaire")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequetePartenaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("requete_date")
    private Instant requeteDate;

    @Column("fichier_atache")
    private byte[] fichierAtache;

    @Column("fichier_atache_content_type")
    private String fichierAtacheContentType;

    @Column("requete_obs")
    private String requeteObs;

    @Column("req_traitee")
    private Integer reqTraitee;

    @Transient
    @JsonIgnoreProperties(value = { "requetePartenaire" }, allowSetters = true)
    private Set<DetailsRequete> detailsRequetes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequetePartenaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRequeteDate() {
        return this.requeteDate;
    }

    public RequetePartenaire requeteDate(Instant requeteDate) {
        this.setRequeteDate(requeteDate);
        return this;
    }

    public void setRequeteDate(Instant requeteDate) {
        this.requeteDate = requeteDate;
    }

    public byte[] getFichierAtache() {
        return this.fichierAtache;
    }

    public RequetePartenaire fichierAtache(byte[] fichierAtache) {
        this.setFichierAtache(fichierAtache);
        return this;
    }

    public void setFichierAtache(byte[] fichierAtache) {
        this.fichierAtache = fichierAtache;
    }

    public String getFichierAtacheContentType() {
        return this.fichierAtacheContentType;
    }

    public RequetePartenaire fichierAtacheContentType(String fichierAtacheContentType) {
        this.fichierAtacheContentType = fichierAtacheContentType;
        return this;
    }

    public void setFichierAtacheContentType(String fichierAtacheContentType) {
        this.fichierAtacheContentType = fichierAtacheContentType;
    }

    public String getRequeteObs() {
        return this.requeteObs;
    }

    public RequetePartenaire requeteObs(String requeteObs) {
        this.setRequeteObs(requeteObs);
        return this;
    }

    public void setRequeteObs(String requeteObs) {
        this.requeteObs = requeteObs;
    }

    public Integer getReqTraitee() {
        return this.reqTraitee;
    }

    public RequetePartenaire reqTraitee(Integer reqTraitee) {
        this.setReqTraitee(reqTraitee);
        return this;
    }

    public void setReqTraitee(Integer reqTraitee) {
        this.reqTraitee = reqTraitee;
    }

    public Set<DetailsRequete> getDetailsRequetes() {
        return this.detailsRequetes;
    }

    public void setDetailsRequetes(Set<DetailsRequete> detailsRequetes) {
        if (this.detailsRequetes != null) {
            this.detailsRequetes.forEach(i -> i.setRequetePartenaire(null));
        }
        if (detailsRequetes != null) {
            detailsRequetes.forEach(i -> i.setRequetePartenaire(this));
        }
        this.detailsRequetes = detailsRequetes;
    }

    public RequetePartenaire detailsRequetes(Set<DetailsRequete> detailsRequetes) {
        this.setDetailsRequetes(detailsRequetes);
        return this;
    }

    public RequetePartenaire addDetailsRequete(DetailsRequete detailsRequete) {
        this.detailsRequetes.add(detailsRequete);
        detailsRequete.setRequetePartenaire(this);
        return this;
    }

    public RequetePartenaire removeDetailsRequete(DetailsRequete detailsRequete) {
        this.detailsRequetes.remove(detailsRequete);
        detailsRequete.setRequetePartenaire(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequetePartenaire)) {
            return false;
        }
        return id != null && id.equals(((RequetePartenaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequetePartenaire{" +
            "id=" + getId() +
            ", requeteDate='" + getRequeteDate() + "'" +
            ", fichierAtache='" + getFichierAtache() + "'" +
            ", fichierAtacheContentType='" + getFichierAtacheContentType() + "'" +
            ", requeteObs='" + getRequeteObs() + "'" +
            ", reqTraitee=" + getReqTraitee() +
            "}";
    }
}
