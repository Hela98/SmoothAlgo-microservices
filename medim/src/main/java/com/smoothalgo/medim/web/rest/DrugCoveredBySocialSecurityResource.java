package com.smoothalgo.medim.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smoothalgo.medim.domain.DrugCoveredBySocialSecurity;
import com.smoothalgo.medim.repository.DrugCoveredBySocialSecurityRepository;
import com.smoothalgo.medim.web.rest.errors.BadRequestAlertException;
import com.smoothalgo.medim.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DrugCoveredBySocialSecurity.
 */
@RestController
@RequestMapping("/api")
public class DrugCoveredBySocialSecurityResource {

    private final Logger log = LoggerFactory.getLogger(DrugCoveredBySocialSecurityResource.class);

    private static final String ENTITY_NAME = "drugCoveredBySocialSecurity";

    private final DrugCoveredBySocialSecurityRepository drugCoveredBySocialSecurityRepository;

    public DrugCoveredBySocialSecurityResource(DrugCoveredBySocialSecurityRepository drugCoveredBySocialSecurityRepository) {
        this.drugCoveredBySocialSecurityRepository = drugCoveredBySocialSecurityRepository;
    }

    /**
     * POST  /drug-covered-by-social-securities : Create a new drugCoveredBySocialSecurity.
     *
     * @param drugCoveredBySocialSecurity the drugCoveredBySocialSecurity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugCoveredBySocialSecurity, or with status 400 (Bad Request) if the drugCoveredBySocialSecurity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-drug-covered-by-social-securities")
    @Timed
    public ResponseEntity<DrugCoveredBySocialSecurity> createDrugCoveredBySocialSecurity(@RequestBody DrugCoveredBySocialSecurity drugCoveredBySocialSecurity) throws URISyntaxException {
        log.debug("REST request to save DrugCoveredBySocialSecurity : {}", drugCoveredBySocialSecurity);
        if (drugCoveredBySocialSecurity.getId() != null) {
            throw new BadRequestAlertException("A new drugCoveredBySocialSecurity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugCoveredBySocialSecurity result = drugCoveredBySocialSecurityRepository.save(drugCoveredBySocialSecurity);
        return ResponseEntity.created(new URI("/api/create-drug-covered-by-social-securities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drug-covered-by-social-securities : Updates an existing drugCoveredBySocialSecurity.
     *
     * @param drugCoveredBySocialSecurity the drugCoveredBySocialSecurity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugCoveredBySocialSecurity,
     * or with status 400 (Bad Request) if the drugCoveredBySocialSecurity is not valid,
     * or with status 500 (Internal Server Error) if the drugCoveredBySocialSecurity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-drug-covered-by-social-securities")
    @Timed
    public ResponseEntity<DrugCoveredBySocialSecurity> updateDrugCoveredBySocialSecurity(@RequestBody DrugCoveredBySocialSecurity drugCoveredBySocialSecurity) throws URISyntaxException {
        log.debug("REST request to update DrugCoveredBySocialSecurity : {}", drugCoveredBySocialSecurity);
        if (drugCoveredBySocialSecurity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrugCoveredBySocialSecurity result = drugCoveredBySocialSecurityRepository.save(drugCoveredBySocialSecurity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drugCoveredBySocialSecurity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drug-covered-by-social-securities : get all the drugCoveredBySocialSecurities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugCoveredBySocialSecurities in body
     */
    @GetMapping("/drug-covered-by-social-securities")
    @Timed
    public List<DrugCoveredBySocialSecurity> getAllDrugCoveredBySocialSecurities() {
        log.debug("REST request to get all DrugCoveredBySocialSecurities");
        return drugCoveredBySocialSecurityRepository.findAll();
    }

    /**
     * GET  /drug-covered-by-social-securities/:id : get the "id" drugCoveredBySocialSecurity.
     *
     * @param id the id of the drugCoveredBySocialSecurity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugCoveredBySocialSecurity, or with status 404 (Not Found)
     */
    @GetMapping("/get-drug-covered-by-social-securities-by-id/{id}")
    @Timed
    public ResponseEntity<DrugCoveredBySocialSecurity> getDrugCoveredBySocialSecurityById(@PathVariable Long id) {
        log.debug("REST request to get DrugCoveredBySocialSecurity by id: {}", id);
        Optional<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurity = drugCoveredBySocialSecurityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(drugCoveredBySocialSecurity);
    }

    @GetMapping("/get-drug-covered-by-social-securities-by-codeCip/{codeCip}")
    @Timed
    public ResponseEntity<DrugCoveredBySocialSecurity> getDrugCoveredBySocialSecurityByCodeCip(@PathVariable String codeCip) {
        log.debug("REST request to get DrugCoveredBySocialSecurity by codeCip: {}", codeCip);
        Optional<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurity = drugCoveredBySocialSecurityRepository.findByCodeCip(codeCip);
        return ResponseUtil.wrapOrNotFound(drugCoveredBySocialSecurity);
    }

    @GetMapping("/get-drug-covered-by-social-securities-by-name/{drugName}")
    @Timed
    public ResponseEntity<DrugCoveredBySocialSecurity> getDrugCoveredBySocialSecurityByDrugName(@PathVariable String drugName) {
        log.debug("REST request to get DrugCoveredBySocialSecurity by name: {}", drugName);
        Optional<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurity = drugCoveredBySocialSecurityRepository.findByDrugName(drugName);
        return ResponseUtil.wrapOrNotFound(drugCoveredBySocialSecurity);
    }

    /**
     * DELETE  /drug-covered-by-social-securities/:id : delete the "id" drugCoveredBySocialSecurity.
     *
     * @param id the id of the drugCoveredBySocialSecurity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delete-drug-covered-by-social-securities-by-id/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrugCoveredBySocialSecurityById(@PathVariable Long id) {
        log.debug("REST request to delete DrugCoveredBySocialSecurity by id : {}", id);

        drugCoveredBySocialSecurityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @DeleteMapping("/delete-drug-covered-by-social-securities-by-CodeCip/{codeCip}")
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteDrugCoveredBySocialSecurityByCodeCip(@PathVariable String codeCip) {
        log.debug("REST request to delete DrugCoveredBySocialSecurity by codeCip : {}", codeCip);

        drugCoveredBySocialSecurityRepository.deleteByCodeCip(codeCip);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, codeCip)).build();
    }
}
