package com.smoothalgo.benef.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smoothalgo.benef.domain.BeneficiaryOfHealthCoverage;
import com.smoothalgo.benef.repository.BeneficiaryOfHealthCoverageRepository;
import com.smoothalgo.benef.web.rest.errors.BadRequestAlertException;
import com.smoothalgo.benef.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BeneficiaryOfHealthCoverage.
 */
@RestController
@RequestMapping("/api")
public class BeneficiaryOfHealthCoverageResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryOfHealthCoverageResource.class);

    private static final String ENTITY_NAME = "beneficiaryOfHealthCoverage";

    private final BeneficiaryOfHealthCoverageRepository beneficiaryOfHealthCoverageRepository;

    public BeneficiaryOfHealthCoverageResource(BeneficiaryOfHealthCoverageRepository beneficiaryOfHealthCoverageRepository) {
        this.beneficiaryOfHealthCoverageRepository = beneficiaryOfHealthCoverageRepository;
    }

    /**
     * POST  /beneficiary-of-health-coverages : Create a new beneficiaryOfHealthCoverage.
     *
     * @param beneficiaryOfHealthCoverage the beneficiaryOfHealthCoverage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beneficiaryOfHealthCoverage, or with status 400 (Bad Request) if the beneficiaryOfHealthCoverage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-beneficiary-of-health-coverages")
    @Timed
    public ResponseEntity<BeneficiaryOfHealthCoverage> createBeneficiaryOfHealthCoverage(@Valid @RequestBody BeneficiaryOfHealthCoverage beneficiaryOfHealthCoverage) throws URISyntaxException {
        log.debug("REST request to save BeneficiaryOfHealthCoverage : {}", beneficiaryOfHealthCoverage);
        if (beneficiaryOfHealthCoverage.getId() != null) {
            throw new BadRequestAlertException("A new beneficiaryOfHealthCoverage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiaryOfHealthCoverage result = beneficiaryOfHealthCoverageRepository.save(beneficiaryOfHealthCoverage);
        return ResponseEntity.created(new URI("/api/create-beneficiary-of-health-coverages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beneficiary-of-health-coverages : Updates an existing beneficiaryOfHealthCoverage.
     *
     * @param beneficiaryOfHealthCoverage the beneficiaryOfHealthCoverage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beneficiaryOfHealthCoverage,
     * or with status 400 (Bad Request) if the beneficiaryOfHealthCoverage is not valid,
     * or with status 500 (Internal Server Error) if the beneficiaryOfHealthCoverage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-beneficiary-of-health-coverages")
    @Timed
    public ResponseEntity<BeneficiaryOfHealthCoverage> updateBeneficiaryOfHealthCoverage(@Valid @RequestBody BeneficiaryOfHealthCoverage beneficiaryOfHealthCoverage) throws URISyntaxException {
        log.debug("REST request to update BeneficiaryOfHealthCoverage : {}", beneficiaryOfHealthCoverage);
        if (beneficiaryOfHealthCoverage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeneficiaryOfHealthCoverage result = beneficiaryOfHealthCoverageRepository.save(beneficiaryOfHealthCoverage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beneficiaryOfHealthCoverage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beneficiary-of-health-coverages : get all the beneficiaryOfHealthCoverages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beneficiaryOfHealthCoverages in body
     */
    @GetMapping("/beneficiary-of-health-coverages")
    @Timed
    public List<BeneficiaryOfHealthCoverage> getAllBeneficiaryOfHealthCoverages() {
        log.debug("REST request to get all BeneficiaryOfHealthCoverages");
        return beneficiaryOfHealthCoverageRepository.findAll();
    }

    @GetMapping("/beneficiary-of-health-coverages/{lastname}/{firstname}")
    @Timed
    public List<BeneficiaryOfHealthCoverage> getAllBeneficiaryOfHealthCoveragesByLastnameAndFirstname(@PathVariable String lastname, @PathVariable String firstname){
        log.debug("REST request to get all BeneficiaryOfHealthCoverages by firstname and lastname");
        return beneficiaryOfHealthCoverageRepository.findBeneficiaryOfHealthCoverageRepositoryByLastNameAndFirstName(lastname, firstname);
    }

    @GetMapping("/beneficiary-of-health-coverages-amount-less-or-equal-than/{remainingAmount}")
    @Timed
    public List<BeneficiaryOfHealthCoverage> getAllBeneficiaryOfHealthCoveragesHavingAmountLessThanEqual(@PathVariable BigDecimal remainingAmount) {
        log.debug("REST request to get all BeneficiaryOfHealthCoverages having amount less or equal than : {}",remainingAmount);
        return beneficiaryOfHealthCoverageRepository.findBeneficiaryOfHealthCoverageRepositoryByRemainingAmountLessThanEqual(remainingAmount);
    }

    @GetMapping("/beneficiary-of-health-coverages-end-date-before/{currentDate}")
    @Timed
    public List<BeneficiaryOfHealthCoverage> getAllBeneficiaryOfHealthCoveragesHavingEndDateBeforeCurrentDate(@PathVariable LocalDate currentDate) {
        log.debug("REST request to get all BeneficiaryOfHealthCoverages having end date before  : {}",currentDate);
        return beneficiaryOfHealthCoverageRepository.findBeneficiaryOfHealthCoverageRepositoryByHealthCoverageEndDateBefore(currentDate);
    }

    /**
     * GET  /beneficiary-of-health-coverages/:id : get the "id" beneficiaryOfHealthCoverage.
     *
     * @param id the id of the beneficiaryOfHealthCoverage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beneficiaryOfHealthCoverage, or with status 404 (Not Found)
     */
    //@RolesAllowed("MANAGER")
    @GetMapping("/beneficiary-of-health-coverages-by-id/{id}")
    @Timed
    public ResponseEntity<BeneficiaryOfHealthCoverage> getBeneficiaryOfHealthCoverage(@PathVariable Long id) {
        log.debug("REST request to get BeneficiaryOfHealthCoverage : {}", id);
        Optional<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beneficiaryOfHealthCoverage);
    }

    @GetMapping("/beneficiary-of-health-coverages-by-socialSecurityNumber/{socialSecurityNumber}")
    @Timed
    public ResponseEntity<BeneficiaryOfHealthCoverage> getBeneficiaryOfHealthCoverageBySocialSecurityNumber(@PathVariable String socialSecurityNumber) {
        log.debug("REST request to get BeneficiaryOfHealthCoverage : {}", socialSecurityNumber);
        Optional<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageRepository.findBeneficiaryOfHealthCoverageRepositoryBySocialSecurityNumber(socialSecurityNumber);
        return ResponseUtil.wrapOrNotFound(beneficiaryOfHealthCoverage);
    }

    @PostMapping("/check-eligibility-of-beneficiary-by-socialSecurityNumber/{socialSecurityNumber}/{treatmentDate}")
    @Timed
    public boolean checkEligibilityOfBeneficiaryOfHealthCoverage(@PathVariable String socialSecurityNumber, @PathVariable LocalDate treatmentDate) throws URISyntaxException{
        log.debug("REST request to check eligibility of BeneficiaryOfHealthCoverage : {}", socialSecurityNumber);
        Optional<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageRepository.findBeneficiaryOfHealthCoverageRepositoryBySocialSecurityNumber(socialSecurityNumber);
        if (beneficiaryOfHealthCoverage.isPresent()){
            if(beneficiaryOfHealthCoverage.get().getRemainingAmount().compareTo(BigDecimal.ZERO) > 0 && beneficiaryOfHealthCoverage.get().getHealthCoverageEndDate().compareTo(treatmentDate)>=0){
                return true;

            }
        }
        else{
            throw new BadRequestAlertException("beneficiary not found", ENTITY_NAME,"Invalid socialSecurityNumber");
        }
        return false;
    }

    @PostMapping("/check-eligibility-of-beneficiary-by-id/{id}/{treatmentDate}")
    @Timed
    public boolean checkEligibilityOfBeneficiaryOfHealthCoverage(@PathVariable Long id, @PathVariable LocalDate treatmentDate) throws URISyntaxException {
        log.debug("REST request to check eligibility of BeneficiaryOfHealthCoverage : {}", id);
        Optional<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageRepository.findById(id);
        if (beneficiaryOfHealthCoverage.isPresent()){
            if(beneficiaryOfHealthCoverage.get().getRemainingAmount().compareTo(BigDecimal.ZERO) > 0 && beneficiaryOfHealthCoverage.get().getHealthCoverageEndDate().compareTo(treatmentDate)>=0){
                return true;

            }
        }
        else{
            throw new BadRequestAlertException("beneficiary not found", ENTITY_NAME,"Invalid id");
        }
        return false;
    }

    /**
     * DELETE  /beneficiary-of-health-coverages/:id : delete the "id" beneficiaryOfHealthCoverage.
     *
     * @param id the id of the beneficiaryOfHealthCoverage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delete-beneficiary-of-health-coverages-by-id/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeneficiaryOfHealthCoverage(@PathVariable Long id) {
        log.debug("REST request to delete BeneficiaryOfHealthCoverage : {}", id);

        beneficiaryOfHealthCoverageRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/delete-beneficiary-of-health-coverages-by-socialSecurityNumber/{socialSecurityNumber}")
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteBeneficiaryOfHealthCoverageBySocialSecurityNumber(@PathVariable String socialSecurityNumber) {
        log.debug("REST request to delete BeneficiaryOfHealthCoverage : {}", socialSecurityNumber);

        beneficiaryOfHealthCoverageRepository.deleteBeneficiaryOfHealthCoverageBySocialSecurityNumber(socialSecurityNumber);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, socialSecurityNumber)).build();
    }
}
