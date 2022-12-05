package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Nomina;
import com.concesionario.app.repository.NominaRepository;
import com.concesionario.app.service.NominaService;
import com.concesionario.app.service.dto.NominaDTO;
import com.concesionario.app.service.mapper.NominaMapper;
import com.concesionario.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.concesionario.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.concesionario.app.domain.enumeration.TipoNomina;
/**
 * Integration tests for the {@Link NominaResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class NominaResourceIT {

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final TipoNomina DEFAULT_TIPO_CONTRATO = TipoNomina.Temporal;
    private static final TipoNomina UPDATED_TIPO_CONTRATO = TipoNomina.Indefinido;

    @Autowired
    private NominaRepository nominaRepository;

    @Autowired
    private NominaMapper nominaMapper;

    @Autowired
    private NominaService nominaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restNominaMockMvc;

    private Nomina nomina;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NominaResource nominaResource = new NominaResource(nominaService);
        this.restNominaMockMvc = MockMvcBuilders.standaloneSetup(nominaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nomina createEntity(EntityManager em) {
        Nomina nomina = new Nomina()
            .iban(DEFAULT_IBAN)
            .tipoContrato(DEFAULT_TIPO_CONTRATO);
        return nomina;
    }

    @BeforeEach
    public void initTest() {
        nomina = createEntity(em);
    }

    @Test
    @Transactional
    public void createNomina() throws Exception {
        int databaseSizeBeforeCreate = nominaRepository.findAll().size();

        // Create the Nomina
        NominaDTO nominaDTO = nominaMapper.toDto(nomina);
        restNominaMockMvc.perform(post("/api/nominas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nominaDTO)))
            .andExpect(status().isCreated());

        // Validate the Nomina in the database
        List<Nomina> nominaList = nominaRepository.findAll();
        assertThat(nominaList).hasSize(databaseSizeBeforeCreate + 1);
        Nomina testNomina = nominaList.get(nominaList.size() - 1);
        assertThat(testNomina.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testNomina.getTipoContrato()).isEqualTo(DEFAULT_TIPO_CONTRATO);
    }

    @Test
    @Transactional
    public void createNominaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nominaRepository.findAll().size();

        // Create the Nomina with an existing ID
        nomina.setId(1L);
        NominaDTO nominaDTO = nominaMapper.toDto(nomina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNominaMockMvc.perform(post("/api/nominas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nominaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nomina in the database
        List<Nomina> nominaList = nominaRepository.findAll();
        assertThat(nominaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNominas() throws Exception {
        // Initialize the database
        nominaRepository.saveAndFlush(nomina);

        // Get all the nominaList
        restNominaMockMvc.perform(get("/api/nominas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nomina.getId().intValue())))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN.toString())))
            .andExpect(jsonPath("$.[*].tipoContrato").value(hasItem(DEFAULT_TIPO_CONTRATO.toString())));
    }
    
    @Test
    @Transactional
    public void getNomina() throws Exception {
        // Initialize the database
        nominaRepository.saveAndFlush(nomina);

        // Get the nomina
        restNominaMockMvc.perform(get("/api/nominas/{id}", nomina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nomina.getId().intValue()))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN.toString()))
            .andExpect(jsonPath("$.tipoContrato").value(DEFAULT_TIPO_CONTRATO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNomina() throws Exception {
        // Get the nomina
        restNominaMockMvc.perform(get("/api/nominas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNomina() throws Exception {
        // Initialize the database
        nominaRepository.saveAndFlush(nomina);

        int databaseSizeBeforeUpdate = nominaRepository.findAll().size();

        // Update the nomina
        Nomina updatedNomina = nominaRepository.findById(nomina.getId()).get();
        // Disconnect from session so that the updates on updatedNomina are not directly saved in db
        em.detach(updatedNomina);
        updatedNomina
            .iban(UPDATED_IBAN)
            .tipoContrato(UPDATED_TIPO_CONTRATO);
        NominaDTO nominaDTO = nominaMapper.toDto(updatedNomina);

        restNominaMockMvc.perform(put("/api/nominas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nominaDTO)))
            .andExpect(status().isOk());

        // Validate the Nomina in the database
        List<Nomina> nominaList = nominaRepository.findAll();
        assertThat(nominaList).hasSize(databaseSizeBeforeUpdate);
        Nomina testNomina = nominaList.get(nominaList.size() - 1);
        assertThat(testNomina.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testNomina.getTipoContrato()).isEqualTo(UPDATED_TIPO_CONTRATO);
    }

    @Test
    @Transactional
    public void updateNonExistingNomina() throws Exception {
        int databaseSizeBeforeUpdate = nominaRepository.findAll().size();

        // Create the Nomina
        NominaDTO nominaDTO = nominaMapper.toDto(nomina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNominaMockMvc.perform(put("/api/nominas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nominaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nomina in the database
        List<Nomina> nominaList = nominaRepository.findAll();
        assertThat(nominaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNomina() throws Exception {
        // Initialize the database
        nominaRepository.saveAndFlush(nomina);

        int databaseSizeBeforeDelete = nominaRepository.findAll().size();

        // Delete the nomina
        restNominaMockMvc.perform(delete("/api/nominas/{id}", nomina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Nomina> nominaList = nominaRepository.findAll();
        assertThat(nominaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nomina.class);
        Nomina nomina1 = new Nomina();
        nomina1.setId(1L);
        Nomina nomina2 = new Nomina();
        nomina2.setId(nomina1.getId());
        assertThat(nomina1).isEqualTo(nomina2);
        nomina2.setId(2L);
        assertThat(nomina1).isNotEqualTo(nomina2);
        nomina1.setId(null);
        assertThat(nomina1).isNotEqualTo(nomina2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NominaDTO.class);
        NominaDTO nominaDTO1 = new NominaDTO();
        nominaDTO1.setId(1L);
        NominaDTO nominaDTO2 = new NominaDTO();
        assertThat(nominaDTO1).isNotEqualTo(nominaDTO2);
        nominaDTO2.setId(nominaDTO1.getId());
        assertThat(nominaDTO1).isEqualTo(nominaDTO2);
        nominaDTO2.setId(2L);
        assertThat(nominaDTO1).isNotEqualTo(nominaDTO2);
        nominaDTO1.setId(null);
        assertThat(nominaDTO1).isNotEqualTo(nominaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nominaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nominaMapper.fromId(null)).isNull();
    }
}
