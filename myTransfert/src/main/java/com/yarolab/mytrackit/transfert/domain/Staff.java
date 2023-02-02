package com.yarolab.mytrackit.transfert.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Staff.
 */
@Entity
@Table(name = "staff")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "staff_fname")
    private String staffFname;

    @Column(name = "staff_lname")
    private String staffLname;

    @Column(name = "staff_title")
    private String staffTitle;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "staff_email")
    private String staffEmail;

    @Column(name = "staff_phone")
    private String staffPhone;

    @Column(name = "section_id")
    private Integer sectionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Staff id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffFname() {
        return this.staffFname;
    }

    public Staff staffFname(String staffFname) {
        this.setStaffFname(staffFname);
        return this;
    }

    public void setStaffFname(String staffFname) {
        this.staffFname = staffFname;
    }

    public String getStaffLname() {
        return this.staffLname;
    }

    public Staff staffLname(String staffLname) {
        this.setStaffLname(staffLname);
        return this;
    }

    public void setStaffLname(String staffLname) {
        this.staffLname = staffLname;
    }

    public String getStaffTitle() {
        return this.staffTitle;
    }

    public Staff staffTitle(String staffTitle) {
        this.setStaffTitle(staffTitle);
        return this;
    }

    public void setStaffTitle(String staffTitle) {
        this.staffTitle = staffTitle;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public Staff staffName(String staffName) {
        this.setStaffName(staffName);
        return this;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return this.staffEmail;
    }

    public Staff staffEmail(String staffEmail) {
        this.setStaffEmail(staffEmail);
        return this;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffPhone() {
        return this.staffPhone;
    }

    public Staff staffPhone(String staffPhone) {
        this.setStaffPhone(staffPhone);
        return this;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public Staff sectionId(Integer sectionId) {
        this.setSectionId(sectionId);
        return this;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staff)) {
            return false;
        }
        return id != null && id.equals(((Staff) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Staff{" +
            "id=" + getId() +
            ", staffFname='" + getStaffFname() + "'" +
            ", staffLname='" + getStaffLname() + "'" +
            ", staffTitle='" + getStaffTitle() + "'" +
            ", staffName='" + getStaffName() + "'" +
            ", staffEmail='" + getStaffEmail() + "'" +
            ", staffPhone='" + getStaffPhone() + "'" +
            ", sectionId=" + getSectionId() +
            "}";
    }
}
