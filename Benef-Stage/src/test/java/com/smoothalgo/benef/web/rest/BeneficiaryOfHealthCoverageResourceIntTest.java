package com.smoothalgo.benef.web.rest;

import com.smoothalgo.benef.BenefApp;

import com.smoothalgo.benef.domain.BeneficiaryOfHealthCoverage;
import com.smoothalgo.benef.repository.BeneficiaryOfHealthCoverageRepository;
import com.smoothalgo.benef.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.smoothalgo.benef.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BeneficiaryOfHealthCoverageResource REST controller.
 *
 * @see BeneficiaryOfHealthCoverageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BenefApp.class)
public class BeneficiaryOfHealthCoverageResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    @Autowired
    private BeneficiaryOfHealthCoverageRepository beneficiaryOfHealthCoverageRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBeneficiaryOfHealthCoverageMockMvc;

    private BeneficiaryOfHealthCoverage beneficiaryOfHealthCoverage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficiaryOfHealthCoverageResource beneficiaryOfHealthCoverageResource = new BeneficiaryOfHealthCoverageResource(beneficiaryOfHealthCoverageRepository);
        this.restBeneficiaryOfHealthCoverageMockMvc = MockMvcBuilders.standaloneSetup(beneficiaryOfHealthCoverageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BeneficiaryOfHealthCoverage createEntity(EntityManager em) {
        BeneficiaryOfHealthCoverage beneficiaryOfHealthCoverage = new BeneficiaryOfHealthCoverage()
            .firstName(DEFAULT_FIRST_NAME);
        return beneficiaryOfHealthCoverage;
    }

    @Before
    public void initTest() {
        beneficiaryOfHealthCoverage = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiaryOfHealthCoverage() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryOfHealthCoverageRepository.findAll().size();

        // Create the BeneficiaryOfHealthCoverage
        restBeneficiaryOfHealthCoverageMockMvc.perform(post("/api/beneficiary-of-health-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryOfHealthCoverage)))
            .andExpect(status().isCreated());

        // Validate the BeneficiaryOfHealthCoverage in the database
        List<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverageList = beneficiaryOfHealthCoverageRepository.findAll();
        assertThat(beneficiaryOfHealthCoverageList).hasSize(databaseSizeBeforeCreate + 1);
        BeneficiaryOfHealthCoverage testBeneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageList.get(beneficiaryOfHealthCoverageList.size() - 1);
        assertThat(testBeneficiaryOfHealthCoverage.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void createBeneficiaryOfHealthCoverageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryOfHealthCoverageRepository.findAll().size();

        // Create the BeneficiaryOfHealthCoverage with an existing ID
        beneficiaryOfHealthCoverage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaryOfHealthCoverageMockMvc.perform(post("/api/beneficiary-of-health-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryOfHealthCoverage)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryOfHealthCoverage in the database
        List<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverageList = beneficiaryOfHealthCoverageRepository.findAll();
        assertThat(beneficiaryOfHealthCoverageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryOfHealthCoverageRepository.findAll().size();
        // set the field null
        beneficiaryOfHealthCoverage.setFirstName(null);

        // Create the BeneficiaryOfHealthCoverage, which fails.

        restBeneficiaryOfHealthCoverageMockMvc.perform(post("/api/beneficiary-of-health-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryOfHealthCoverage)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverageList = beneficiaryOfHealthCoverageRepository.findAll();
        assertThat(beneficiaryOfHealthCoverageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficiaryOfHealthCoverages() throws Exception {
        // Initialize the database
        beneficiaryOfHealthCoverageRepository.saveAndFlush(beneficiaryOfHealthCoverage);

        // Get all the beneficiaryOfHealthCoverageList
        restBeneficiaryOfHealthCoverageMockMvc.perform(get("/api/beneficiary-of-health-coverages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaryOfHealthCoverage.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getBeneficiaryOfHealthCoverage() throws Exception {
        // Initialize the database
        beneficiaryOfHealthCoverageRepository.saveAndFlush(beneficiaryOfHealthCoverage);

        // Get the beneficiaryOfHealthCoverage
        restBeneficiaryOfHealthCoverageMockMvc.perform(get("/api/beneficiary-of-health-coverages/{id}", beneficiaryOfHealthCoverage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiaryOfHealthCoverage.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBeneficiaryOfHealthCoverage() throws Exception {
        // Get the beneficiaryOfHealthCoverage
        restBeneficiaryOfHealthCoverageMockMvc.perform(get("/api/beneficiary-of-health-coverages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiaryOfHealthCoverage() throws Exception {
        // Initialize the database
        beneficiaryOfHealthCoverageRepository.saveAndFlush(beneficiaryOfHealthCoverage);

        int databaseSizeBeforeUpdate = beneficiaryOfHealthCoverageRepository.findAll().size();

        // Update the beneficiaryOfHealthCoverage
        BeneficiaryOfHealthCoverage updatedBeneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageRepository.findById(beneficiaryOfHealthCoverage.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiaryOfHealthCoverage are not directly saved in db
        em.detach(updatedBeneficiaryOfHealthCoverage);
        updatedBeneficiaryOfHealthCoverage
            .firstName(UPDATED_FIRST_NAME);

        restBeneficiaryOfHealthCoverageMockMvc.perform(put("/api/beneficiary-of-health-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeneficiaryOfHealthCoverage)))
            .andExpect(status().isOk());

        // Validate the BeneficiaryOfHealthCoverage in the database
        List<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverageList = beneficiaryOfHealthCoverageRepository.findAll();
        assertThat(beneficiaryOfHealthCoverageList).hasSize(databaseSizeBeforeUpdate);
        BeneficiaryOfHealthCoverage testBeneficiaryOfHealthCoverage = beneficiaryOfHealthCoverageList.get(beneficiaryOfHealthCoverageList.size() - 1);
        assertThat(testBeneficiaryOfHealthCoverage.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiaryOfHealthCoverage() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaryOfHealthCoverageRepository.findAll().size();

        // Create the BeneficiaryOfHealthCoverage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBeneficiaryOfHealthCoverageMockMvc.perform(put("/api/beneficiary-of-health-coverages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryOfHealthCoverage)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryOfHealthCoverage in the database
        List<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverageList = beneficiaryOfHealthCoverageRepository.findAll();
        assertThat(beneficiaryOfHealthCoverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeneficiaryOfHealthCoverage() throws Exception {
        // Initialize the database
        beneficiaryOfHealthCoverageRepository.saveAndFlush(beneficiaryOfHealthCoverage);

        int databaseSizeBeforeDelete = beneficiaryOfHealthCoverageRepository.findAll().size();

        // Get the beneficiaryOfHealthCoverage
        restBeneficiaryOfHealthCoverageMockMvc.perform(delete("/api/beneficiary-of-health-coverages/{id}", beneficiaryOfHealthCoverage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BeneficiaryOfHealthCoverage> beneficiaryOfHealthCoverageList = beneficiaryOfHealthCoverageRepository.findAll();
        assertThat(beneficiaryOfHealthCoverageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryOfHealthCoverage.class);
        BeneficiaryOfHealthCoverage beneficiaryOfHealthCoverage1 = new BeneficiaryOfHealthCoverage();
        beneficiaryOfHealthCoverage1.setId(1L);
        BeneficiaryOfHealthCoverage beneficiaryOfHealthCoverage2 = new BeneficiaryOfHealthCoverage();
        beneficiaryOfHealthCoverage2.setId(beneficiaryOfHealthCoverage1.getId());
        assertThat(beneficiaryOfHealthCoverage1).isEqualTo(beneficiaryOfHealthCoverage2);
        beneficiaryOfHealthCoverage2.setId(2L);
        assertThat(beneficiaryOfHealthCoverage1).isNotEqualTo(beneficiaryOfHealthCoverage2);
        beneficiaryOfHealthCoverage1.setId(null);
        assertThat(beneficiaryOfHealthCoverage1).isNotEqualTo(beneficiaryOfHealthCoverage2);
    }
}
