package com.smoothalgo.medim.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DrugCoveredBySocialSecurity.
 */
@Entity
@Table(name = "drug_covered_by_social_security")
public class DrugCoveredBySocialSecurity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 13, max = 13)
    @Column(name = "code_cip", nullable = false)
    private String codeCip;

    @NotNull
    @Column(name = "drug_name", nullable = false)
    private String drugName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCip() {
        return codeCip;
    }

    public DrugCoveredBySocialSecurity codeCip(String codeCip) {
        this.codeCip = codeCip;
        return this;
    }

    public void setCodeCip(String codeCip) {
        this.codeCip = codeCip;
    }

    public String getDrugName() {
        return drugName;
    }

    public DrugCoveredBySocialSecurity drugName(String drugName) {
        this.drugName = drugName;
        return this;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrugCoveredBySocialSecurity drugCoveredBySocialSecurity = (DrugCoveredBySocialSecurity) o;
        if (drugCoveredBySocialSecurity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drugCoveredBySocialSecurity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrugCoveredBySocialSecurity{" +
            "id=" + getId() +
            ", codeCip=" + getCodeCip() +
            ", drugName='" + getDrugName() + "'" +
            "}";
    }
}
