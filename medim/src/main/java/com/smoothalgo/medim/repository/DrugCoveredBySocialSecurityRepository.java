package com.smoothalgo.medim.repository;

import com.smoothalgo.medim.domain.DrugCoveredBySocialSecurity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DrugCoveredBySocialSecurity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugCoveredBySocialSecurityRepository extends JpaRepository<DrugCoveredBySocialSecurity, Long> {

    Optional<DrugCoveredBySocialSecurity> findByCodeCip(String codeCip);
    Optional<DrugCoveredBySocialSecurity> findByDrugName(String drugName);
    void deleteByCodeCip(String codeCip);
}
