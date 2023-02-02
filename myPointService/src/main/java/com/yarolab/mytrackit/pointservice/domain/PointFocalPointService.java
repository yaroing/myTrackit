package com.yarolab.mytrackit.pointservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PointFocalPointService.
 */
@Entity
@Table(name = "point_focal_point_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointFocalPointService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "point_os_id")
    private Integer pointOsId;

    @Column(name = "nom_pf")
    private String nomPf;

    @Column(name = "fonction_pf")
    private String fonctionPf;

    @Column(name = "gsm_pf")
    private String gsmPf;

    @Column(name = "email_pf")
    private String emailPf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointFocalPointService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPointOsId() {
        return this.pointOsId;
    }

    public PointFocalPointService pointOsId(Integer pointOsId) {
        this.setPointOsId(pointOsId);
        return this;
    }

    public void setPointOsId(Integer pointOsId) {
        this.pointOsId = pointOsId;
    }

    public String getNomPf() {
        return this.nomPf;
    }

    public PointFocalPointService nomPf(String nomPf) {
        this.setNomPf(nomPf);
        return this;
    }

    public void setNomPf(String nomPf) {
        this.nomPf = nomPf;
    }

    public String getFonctionPf() {
        return this.fonctionPf;
    }

    public PointFocalPointService fonctionPf(String fonctionPf) {
        this.setFonctionPf(fonctionPf);
        return this;
    }

    public void setFonctionPf(String fonctionPf) {
        this.fonctionPf = fonctionPf;
    }

    public String getGsmPf() {
        return this.gsmPf;
    }

    public PointFocalPointService gsmPf(String gsmPf) {
        this.setGsmPf(gsmPf);
        return this;
    }

    public void setGsmPf(String gsmPf) {
        this.gsmPf = gsmPf;
    }

    public String getEmailPf() {
        return this.emailPf;
    }

    public PointFocalPointService emailPf(String emailPf) {
        this.setEmailPf(emailPf);
        return this;
    }

    public void setEmailPf(String emailPf) {
        this.emailPf = emailPf;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointFocalPointService)) {
            return false;
        }
        return id != null && id.equals(((PointFocalPointService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointFocalPointService{" +
            "id=" + getId() +
            ", pointOsId=" + getPointOsId() +
            ", nomPf='" + getNomPf() + "'" +
            ", fonctionPf='" + getFonctionPf() + "'" +
            ", gsmPf='" + getGsmPf() + "'" +
            ", emailPf='" + getEmailPf() + "'" +
            "}";
    }
}
