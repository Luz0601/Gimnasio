package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Puesto;
import com.concesionario.app.repository.PuestoRepository;
import com.concesionario.app.service.PuestoService;
import com.concesionario.app.service.dto.PuestoDTO;
import com.concesionario.app.service.mapper.PuestoMapper;
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

/**
 * Integration tests for the {@Link PuestoResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class PuestoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";

    private static final Double DEFAULT_SALARIO = 1D;
    private static final Double UPDATED_SALARIO = 2D;

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private PuestoMapper puestoMapper;

    @Autowired
    private PuestoService puestoService;

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

    private MockMvc restPuestoMockMvc;

    private Puesto puesto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuestoResource puestoResource = new PuestoResource(puestoService);
        this.restPuestoMockMvc = MockMvcBuilders.standaloneSetup(puestoResource)
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
    public static Puesto createEntity(EntityManager em) {
        Puesto puesto = new Puesto()
            .nombre(DEFAULT_NOMBRE)
            .horario(DEFAULT_HORARIO)
            .salario(DEFAULT_SALARIO);
        return puesto;
    }

    @BeforeEach
    public void initTest() {
        puesto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuesto() throws Exception {
        int databaseSizeBeforeCreate = puestoRepository.findAll().size();

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);
        restPuestoMockMvc.perform(post("/api/puestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isCreated());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeCreate + 1);
        Puesto testPuesto = puestoList.get(puestoList.size() - 1);
        assertThat(testPuesto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPuesto.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testPuesto.getSalario()).isEqualTo(DEFAULT_SALARIO);
    }

    @Test
    @Transactional
    public void createPuestoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puestoRepository.findAll().size();

        // Create the Puesto with an existing ID
        puesto.setId(1L);
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuestoMockMvc.perform(post("/api/puestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = puestoRepository.findAll().size();
        // set the field null
        puesto.setNombre(null);

        // Create the Puesto, which fails.
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        restPuestoMockMvc.perform(post("/api/puestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isBadRequest());

        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPuestos() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        // Get all the puestoList
        restPuestoMockMvc.perform(get("/api/puestos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puesto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPuesto() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        // Get the puesto
        restPuestoMockMvc.perform(get("/api/puestos/{id}", puesto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puesto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.salario").value(DEFAULT_SALARIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPuesto() throws Exception {
        // Get the puesto
        restPuestoMockMvc.perform(get("/api/puestos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuesto() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();

        // Update the puesto
        Puesto updatedPuesto = puestoRepository.findById(puesto.getId()).get();
        // Disconnect from session so that the updates on updatedPuesto are not directly saved in db
        em.detach(updatedPuesto);
        updatedPuesto
            .nombre(UPDATED_NOMBRE)
            .horario(UPDATED_HORARIO)
            .salario(UPDATED_SALARIO);
        PuestoDTO puestoDTO = puestoMapper.toDto(updatedPuesto);

        restPuestoMockMvc.perform(put("/api/puestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isOk());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
        Puesto testPuesto = puestoList.get(puestoList.size() - 1);
        assertThat(testPuesto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPuesto.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testPuesto.getSalario()).isEqualTo(UPDATED_SALARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuestoMockMvc.perform(put("/api/puestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuesto() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        int databaseSizeBeforeDelete = puestoRepository.findAll().size();

        // Delete the puesto
        restPuestoMockMvc.perform(delete("/api/puestos/{id}", puesto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Puesto.class);
        Puesto puesto1 = new Puesto();
        puesto1.setId(1L);
        Puesto puesto2 = new Puesto();
        puesto2.setId(puesto1.getId());
        assertThat(puesto1).isEqualTo(puesto2);
        puesto2.setId(2L);
        assertThat(puesto1).isNotEqualTo(puesto2);
        puesto1.setId(null);
        assertThat(puesto1).isNotEqualTo(puesto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuestoDTO.class);
        PuestoDTO puestoDTO1 = new PuestoDTO();
        puestoDTO1.setId(1L);
        PuestoDTO puestoDTO2 = new PuestoDTO();
        assertThat(puestoDTO1).isNotEqualTo(puestoDTO2);
        puestoDTO2.setId(puestoDTO1.getId());
        assertThat(puestoDTO1).isEqualTo(puestoDTO2);
        puestoDTO2.setId(2L);
        assertThat(puestoDTO1).isNotEqualTo(puestoDTO2);
        puestoDTO1.setId(null);
        assertThat(puestoDTO1).isNotEqualTo(puestoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(puestoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(puestoMapper.fromId(null)).isNull();
    }
}
