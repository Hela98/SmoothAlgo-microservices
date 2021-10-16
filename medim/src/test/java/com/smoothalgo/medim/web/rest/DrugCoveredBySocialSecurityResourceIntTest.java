package com.smoothalgo.medim.web.rest;

import com.smoothalgo.medim.MedimApp;

import com.smoothalgo.medim.domain.DrugCoveredBySocialSecurity;
import com.smoothalgo.medim.repository.DrugCoveredBySocialSecurityRepository;
import com.smoothalgo.medim.web.rest.errors.ExceptionTranslator;

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


import static com.smoothalgo.medim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DrugCoveredBySocialSecurityResource REST controller.
 *
 * @see DrugCoveredBySocialSecurityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MedimApp.class)
public class DrugCoveredBySocialSecurityResourceIntTest {

    private static final String DEFAULT_CODE_CIP = "12258895893124";
    private static final String UPDATED_CODE_CIP = "84851525554602";

    private static final String DEFAULT_DRUG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRUG_NAME = "BBBBBBBBBB";

    @Autowired
    private DrugCoveredBySocialSecurityRepository drugCoveredBySocialSecurityRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDrugCoveredBySocialSecurityMockMvc;

    private DrugCoveredBySocialSecurity drugCoveredBySocialSecurity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugCoveredBySocialSecurityResource drugCoveredBySocialSecurityResource = new DrugCoveredBySocialSecurityResource(drugCoveredBySocialSecurityRepository);
        this.restDrugCoveredBySocialSecurityMockMvc = MockMvcBuilders.standaloneSetup(drugCoveredBySocialSecurityResource)
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
    public static DrugCoveredBySocialSecurity createEntity(EntityManager em) {
        DrugCoveredBySocialSecurity drugCoveredBySocialSecurity = new DrugCoveredBySocialSecurity()
            .codeCip(DEFAULT_CODE_CIP)
            .drugName(DEFAULT_DRUG_NAME);
        return drugCoveredBySocialSecurity;
    }

    @Before
    public void initTest() {
        drugCoveredBySocialSecurity = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugCoveredBySocialSecurity() throws Exception {
        int databaseSizeBeforeCreate = drugCoveredBySocialSecurityRepository.findAll().size();

        // Create the DrugCoveredBySocialSecurity
        restDrugCoveredBySocialSecurityMockMvc.perform(post("/api/drug-covered-by-social-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugCoveredBySocialSecurity)))
            .andExpect(status().isCreated());

        // Validate the DrugCoveredBySocialSecurity in the database
        List<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurityList = drugCoveredBySocialSecurityRepository.findAll();
        assertThat(drugCoveredBySocialSecurityList).hasSize(databaseSizeBeforeCreate + 1);
        DrugCoveredBySocialSecurity testDrugCoveredBySocialSecurity = drugCoveredBySocialSecurityList.get(drugCoveredBySocialSecurityList.size() - 1);
        assertThat(testDrugCoveredBySocialSecurity.getCodeCip()).isEqualTo(DEFAULT_CODE_CIP);
        assertThat(testDrugCoveredBySocialSecurity.getDrugName()).isEqualTo(DEFAULT_DRUG_NAME);
    }

    @Test
    @Transactional
    public void createDrugCoveredBySocialSecurityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugCoveredBySocialSecurityRepository.findAll().size();

        // Create the DrugCoveredBySocialSecurity with an existing ID
        drugCoveredBySocialSecurity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugCoveredBySocialSecurityMockMvc.perform(post("/api/drug-covered-by-social-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugCoveredBySocialSecurity)))
            .andExpect(status().isBadRequest());

        // Validate the DrugCoveredBySocialSecurity in the database
        List<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurityList = drugCoveredBySocialSecurityRepository.findAll();
        assertThat(drugCoveredBySocialSecurityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDrugCoveredBySocialSecurities() throws Exception {
        // Initialize the database
        drugCoveredBySocialSecurityRepository.saveAndFlush(drugCoveredBySocialSecurity);

        // Get all the drugCoveredBySocialSecurityList
        restDrugCoveredBySocialSecurityMockMvc.perform(get("/api/drug-covered-by-social-securities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugCoveredBySocialSecurity.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCip").value(hasItem(DEFAULT_CODE_CIP.toString())))
            .andExpect(jsonPath("$.[*].drugName").value(hasItem(DEFAULT_DRUG_NAME.toString())));
    }


    @Test
    @Transactional
    public void getDrugCoveredBySocialSecurity() throws Exception {
        // Initialize the database
        drugCoveredBySocialSecurityRepository.saveAndFlush(drugCoveredBySocialSecurity);

        // Get the drugCoveredBySocialSecurity
        restDrugCoveredBySocialSecurityMockMvc.perform(get("/api/drug-covered-by-social-securities/{id}", drugCoveredBySocialSecurity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugCoveredBySocialSecurity.getId().intValue()))
            .andExpect(jsonPath("$.codeCip").value(DEFAULT_CODE_CIP.toString()))
            .andExpect(jsonPath("$.drugName").value(DEFAULT_DRUG_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDrugCoveredBySocialSecurity() throws Exception {
        // Get the drugCoveredBySocialSecurity
        restDrugCoveredBySocialSecurityMockMvc.perform(get("/api/drug-covered-by-social-securities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugCoveredBySocialSecurity() throws Exception {
        // Initialize the database
        drugCoveredBySocialSecurityRepository.saveAndFlush(drugCoveredBySocialSecurity);

        int databaseSizeBeforeUpdate = drugCoveredBySocialSecurityRepository.findAll().size();

        // Update the drugCoveredBySocialSecurity
        DrugCoveredBySocialSecurity updatedDrugCoveredBySocialSecurity = drugCoveredBySocialSecurityRepository.findById(drugCoveredBySocialSecurity.getId()).get();
        // Disconnect from session so that the updates on updatedDrugCoveredBySocialSecurity are not directly saved in db
        em.detach(updatedDrugCoveredBySocialSecurity);
        updatedDrugCoveredBySocialSecurity
            .codeCip(UPDATED_CODE_CIP)
            .drugName(UPDATED_DRUG_NAME);

        restDrugCoveredBySocialSecurityMockMvc.perform(put("/api/drug-covered-by-social-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrugCoveredBySocialSecurity)))
            .andExpect(status().isOk());

        // Validate the DrugCoveredBySocialSecurity in the database
        List<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurityList = drugCoveredBySocialSecurityRepository.findAll();
        assertThat(drugCoveredBySocialSecurityList).hasSize(databaseSizeBeforeUpdate);
        DrugCoveredBySocialSecurity testDrugCoveredBySocialSecurity = drugCoveredBySocialSecurityList.get(drugCoveredBySocialSecurityList.size() - 1);
        assertThat(testDrugCoveredBySocialSecurity.getCodeCip()).isEqualTo(UPDATED_CODE_CIP);
        assertThat(testDrugCoveredBySocialSecurity.getDrugName()).isEqualTo(UPDATED_DRUG_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugCoveredBySocialSecurity() throws Exception {
        int databaseSizeBeforeUpdate = drugCoveredBySocialSecurityRepository.findAll().size();

        // Create the DrugCoveredBySocialSecurity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDrugCoveredBySocialSecurityMockMvc.perform(put("/api/drug-covered-by-social-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugCoveredBySocialSecurity)))
            .andExpect(status().isBadRequest());

        // Validate the DrugCoveredBySocialSecurity in the database
        List<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurityList = drugCoveredBySocialSecurityRepository.findAll();
        assertThat(drugCoveredBySocialSecurityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrugCoveredBySocialSecurity() throws Exception {
        // Initialize the database
        drugCoveredBySocialSecurityRepository.saveAndFlush(drugCoveredBySocialSecurity);

        int databaseSizeBeforeDelete = drugCoveredBySocialSecurityRepository.findAll().size();

        // Get the drugCoveredBySocialSecurity
        restDrugCoveredBySocialSecurityMockMvc.perform(delete("/api/drug-covered-by-social-securities/{id}", drugCoveredBySocialSecurity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DrugCoveredBySocialSecurity> drugCoveredBySocialSecurityList = drugCoveredBySocialSecurityRepository.findAll();
        assertThat(drugCoveredBySocialSecurityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugCoveredBySocialSecurity.class);
        DrugCoveredBySocialSecurity drugCoveredBySocialSecurity1 = new DrugCoveredBySocialSecurity();
        drugCoveredBySocialSecurity1.setId(1L);
        DrugCoveredBySocialSecurity drugCoveredBySocialSecurity2 = new DrugCoveredBySocialSecurity();
        drugCoveredBySocialSecurity2.setId(drugCoveredBySocialSecurity1.getId());
        assertThat(drugCoveredBySocialSecurity1).isEqualTo(drugCoveredBySocialSecurity2);
        drugCoveredBySocialSecurity2.setId(2L);
        assertThat(drugCoveredBySocialSecurity1).isNotEqualTo(drugCoveredBySocialSecurity2);
        drugCoveredBySocialSecurity1.setId(null);
        assertThat(drugCoveredBySocialSecurity1).isNotEqualTo(drugCoveredBySocialSecurity2);
    }
}
