package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Incidencia;
import com.concesionario.app.repository.IncidenciaRepository;
import com.concesionario.app.service.IncidenciaService;
import com.concesionario.app.service.dto.IncidenciaDTO;
import com.concesionario.app.service.mapper.IncidenciaMapper;
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
 * Integration tests for the {@Link IncidenciaResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class IncidenciaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private IncidenciaMapper incidenciaMapper;

    @Autowired
    private IncidenciaService incidenciaService;

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

    private MockMvc restIncidenciaMockMvc;

    private Incidencia incidencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidenciaResource incidenciaResource = new IncidenciaResource(incidenciaService);
        this.restIncidenciaMockMvc = MockMvcBuilders.standaloneSetup(incidenciaResource)
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
    public static Incidencia createEntity(EntityManager em) {
        Incidencia incidencia = new Incidencia()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return incidencia;
    }

    @BeforeEach
    public void initTest() {
        incidencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidencia() throws Exception {
        int databaseSizeBeforeCreate = incidenciaRepository.findAll().size();

        // Create the Incidencia
        IncidenciaDTO incidenciaDTO = incidenciaMapper.toDto(incidencia);
        restIncidenciaMockMvc.perform(post("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Incidencia testIncidencia = incidenciaList.get(incidenciaList.size() - 1);
        assertThat(testIncidencia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIncidencia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createIncidenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidenciaRepository.findAll().size();

        // Create the Incidencia with an existing ID
        incidencia.setId(1L);
        IncidenciaDTO incidenciaDTO = incidenciaMapper.toDto(incidencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidenciaMockMvc.perform(post("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidenciaRepository.findAll().size();
        // set the field null
        incidencia.setNombre(null);

        // Create the Incidencia, which fails.
        IncidenciaDTO incidenciaDTO = incidenciaMapper.toDto(incidencia);

        restIncidenciaMockMvc.perform(post("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidenciaRepository.findAll().size();
        // set the field null
        incidencia.setDescripcion(null);

        // Create the Incidencia, which fails.
        IncidenciaDTO incidenciaDTO = incidenciaMapper.toDto(incidencia);

        restIncidenciaMockMvc.perform(post("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncidencias() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);

        // Get all the incidenciaList
        restIncidenciaMockMvc.perform(get("/api/incidencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getIncidencia() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);

        // Get the incidencia
        restIncidenciaMockMvc.perform(get("/api/incidencias/{id}", incidencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidencia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidencia() throws Exception {
        // Get the incidencia
        restIncidenciaMockMvc.perform(get("/api/incidencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidencia() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);

        int databaseSizeBeforeUpdate = incidenciaRepository.findAll().size();

        // Update the incidencia
        Incidencia updatedIncidencia = incidenciaRepository.findById(incidencia.getId()).get();
        // Disconnect from session so that the updates on updatedIncidencia are not directly saved in db
        em.detach(updatedIncidencia);
        updatedIncidencia
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        IncidenciaDTO incidenciaDTO = incidenciaMapper.toDto(updatedIncidencia);

        restIncidenciaMockMvc.perform(put("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeUpdate);
        Incidencia testIncidencia = incidenciaList.get(incidenciaList.size() - 1);
        assertThat(testIncidencia.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIncidencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidencia() throws Exception {
        int databaseSizeBeforeUpdate = incidenciaRepository.findAll().size();

        // Create the Incidencia
        IncidenciaDTO incidenciaDTO = incidenciaMapper.toDto(incidencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidenciaMockMvc.perform(put("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncidencia() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);

        int databaseSizeBeforeDelete = incidenciaRepository.findAll().size();

        // Delete the incidencia
        restIncidenciaMockMvc.perform(delete("/api/incidencias/{id}", incidencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incidencia.class);
        Incidencia incidencia1 = new Incidencia();
        incidencia1.setId(1L);
        Incidencia incidencia2 = new Incidencia();
        incidencia2.setId(incidencia1.getId());
        assertThat(incidencia1).isEqualTo(incidencia2);
        incidencia2.setId(2L);
        assertThat(incidencia1).isNotEqualTo(incidencia2);
        incidencia1.setId(null);
        assertThat(incidencia1).isNotEqualTo(incidencia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidenciaDTO.class);
        IncidenciaDTO incidenciaDTO1 = new IncidenciaDTO();
        incidenciaDTO1.setId(1L);
        IncidenciaDTO incidenciaDTO2 = new IncidenciaDTO();
        assertThat(incidenciaDTO1).isNotEqualTo(incidenciaDTO2);
        incidenciaDTO2.setId(incidenciaDTO1.getId());
        assertThat(incidenciaDTO1).isEqualTo(incidenciaDTO2);
        incidenciaDTO2.setId(2L);
        assertThat(incidenciaDTO1).isNotEqualTo(incidenciaDTO2);
        incidenciaDTO1.setId(null);
        assertThat(incidenciaDTO1).isNotEqualTo(incidenciaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incidenciaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incidenciaMapper.fromId(null)).isNull();
    }
}
