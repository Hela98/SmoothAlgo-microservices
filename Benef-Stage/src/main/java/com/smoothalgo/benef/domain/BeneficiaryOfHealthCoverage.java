package com.smoothalgo.benef.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BeneficiaryOfHealthCoverage.
 */
@Entity
@Table(name = "beneficiary_of_health_coverage")
public class BeneficiaryOfHealthCoverage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    //@Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$")
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    //@Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$")
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "social_security_number", nullable = false, unique = true)
    private String socialSecurityNumber;

    @NotNull
    @Column(name = "health_coverage_start_date", nullable = false)
    private LocalDate healthCoverageStartDate;

    @NotNull
    @Column(name = "health_coverage_end_date", nullable = false)
    private LocalDate healthCoverageEndDate;

    @NotNull
    @Column(name = "reimbursement_ceiling", nullable = false)
    private BigDecimal reimbursementCeiling;

    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;




    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public BeneficiaryOfHealthCoverage firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public String getLastName() {
        return this.lastName;
    }

    public BeneficiaryOfHealthCoverage lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }



    public BeneficiaryOfHealthCoverage dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    public BeneficiaryOfHealthCoverage socialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
        return this;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public LocalDate getHealthCoverageStartDate() {
        return this.healthCoverageStartDate;
    }

    public BeneficiaryOfHealthCoverage healthCoverageStartDate(LocalDate healthCoverageStartDate) {
        this.healthCoverageStartDate = healthCoverageStartDate;
        return this;
    }

    public void setHealthCoverageStartDate(LocalDate healthCoverageStartDate) {
        this.healthCoverageStartDate = healthCoverageStartDate;
    }

    public LocalDate getHealthCoverageEndDate() {
        return this.healthCoverageEndDate;
    }

    public BeneficiaryOfHealthCoverage healthCoverageEndDate(LocalDate healthCoverageEndDate) {
        this.healthCoverageEndDate = healthCoverageEndDate;
        return this;
    }

    public void setHealthCoverageEndDate(LocalDate healthCoverageEndDate) {
        this.healthCoverageEndDate = healthCoverageEndDate;
    }

    public BigDecimal getReimbursementCeiling() {
        return this.reimbursementCeiling;
    }

    public BeneficiaryOfHealthCoverage reimbursementCeiling(BigDecimal reimbursementCeiling) {
        this.reimbursementCeiling = reimbursementCeiling;
        return this;
    }

    public void setReimbursementCeiling(BigDecimal reimbursementCeiling) {
        this.reimbursementCeiling = reimbursementCeiling;
    }

    public BigDecimal getRemainingAmount() {
        return this.remainingAmount;
    }

    public BeneficiaryOfHealthCoverage remainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
        return this;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    @Override
    public String toString() {
        return "BeneficiaryOfHealthCoverage{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
            ", healthCoverageStartDate=" + healthCoverageStartDate +
            ", healthCoverageEndDate=" + healthCoverageEndDate +
            ", reimbursementCeiling=" + reimbursementCeiling +
            ", remainingAmount=" + remainingAmount +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficiaryOfHealthCoverage that = (BeneficiaryOfHealthCoverage) o;
        return id.equals(that.id) && socialSecurityNumber.equals(that.socialSecurityNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, socialSecurityNumber);
    }
}
