package com.smoothalgo.benef.repository;

import com.smoothalgo.benef.domain.BeneficiaryOfHealthCoverage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the BeneficiaryOfHealthCoverage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiaryOfHealthCoverageRepository extends JpaRepository<BeneficiaryOfHealthCoverage, Long> {

    List<BeneficiaryOfHealthCoverage> findBeneficiaryOfHealthCoverageRepositoryByLastNameAndFirstName (String lastname, String firstname);
    Optional<BeneficiaryOfHealthCoverage> findBeneficiaryOfHealthCoverageRepositoryBySocialSecurityNumber (String socialSecurityNumber);
    List<BeneficiaryOfHealthCoverage> findBeneficiaryOfHealthCoverageRepositoryByHealthCoverageEndDateBefore (LocalDate currentDate);
    List<BeneficiaryOfHealthCoverage> findBeneficiaryOfHealthCoverageRepositoryByRemainingAmountLessThanEqual (BigDecimal amount);
    void deleteBeneficiaryOfHealthCoverageBySocialSecurityNumber (String socialSecurityNumber);






}
