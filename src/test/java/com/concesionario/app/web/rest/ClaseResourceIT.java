package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Clase;
import com.concesionario.app.repository.ClaseRepository;
import com.concesionario.app.service.ClaseService;
import com.concesionario.app.service.dto.ClaseDTO;
import com.concesionario.app.service.mapper.ClaseMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.concesionario.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ClaseResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class ClaseResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_LUGAR = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_INCIDENCIAS = false;
    private static final Boolean UPDATED_INCIDENCIAS = true;

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private ClaseMapper claseMapper;

    @Autowired
    private ClaseService claseService;

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

    private MockMvc restClaseMockMvc;

    private Clase clase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClaseResource claseResource = new ClaseResource(claseService);
        this.restClaseMockMvc = MockMvcBuilders.standaloneSetup(claseResource)
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
    public static Clase createEntity(EntityManager em) {
        Clase clase = new Clase()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .lugar(DEFAULT_LUGAR)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .incidencias(DEFAULT_INCIDENCIAS);
        return clase;
    }

    @BeforeEach
    public void initTest() {
        clase = createEntity(em);
    }

    @Test
    @Transactional
    public void createClase() throws Exception {
        int databaseSizeBeforeCreate = claseRepository.findAll().size();

        // Create the Clase
        ClaseDTO claseDTO = claseMapper.toDto(clase);
        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isCreated());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeCreate + 1);
        Clase testClase = claseList.get(claseList.size() - 1);
        assertThat(testClase.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testClase.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testClase.getLugar()).isEqualTo(DEFAULT_LUGAR);
        assertThat(testClase.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testClase.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testClase.isIncidencias()).isEqualTo(DEFAULT_INCIDENCIAS);
    }

    @Test
    @Transactional
    public void createClaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = claseRepository.findAll().size();

        // Create the Clase with an existing ID
        clase.setId(1L);
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = claseRepository.findAll().size();
        // set the field null
        clase.setNombre(null);

        // Create the Clase, which fails.
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = claseRepository.findAll().size();
        // set the field null
        clase.setInicio(null);

        // Create the Clase, which fails.
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = claseRepository.findAll().size();
        // set the field null
        clase.setFin(null);

        // Create the Clase, which fails.
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        restClaseMockMvc.perform(post("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClases() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);

        // Get all the claseList
        restClaseMockMvc.perform(get("/api/clases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clase.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR.toString())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].incidencias").value(hasItem(DEFAULT_INCIDENCIAS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getClase() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);

        // Get the clase
        restClaseMockMvc.perform(get("/api/clases/{id}", clase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clase.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR.toString()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.incidencias").value(DEFAULT_INCIDENCIAS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClase() throws Exception {
        // Get the clase
        restClaseMockMvc.perform(get("/api/clases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClase() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);

        int databaseSizeBeforeUpdate = claseRepository.findAll().size();

        // Update the clase
        Clase updatedClase = claseRepository.findById(clase.getId()).get();
        // Disconnect from session so that the updates on updatedClase are not directly saved in db
        em.detach(updatedClase);
        updatedClase
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .lugar(UPDATED_LUGAR)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .incidencias(UPDATED_INCIDENCIAS);
        ClaseDTO claseDTO = claseMapper.toDto(updatedClase);

        restClaseMockMvc.perform(put("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isOk());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeUpdate);
        Clase testClase = claseList.get(claseList.size() - 1);
        assertThat(testClase.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testClase.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testClase.getLugar()).isEqualTo(UPDATED_LUGAR);
        assertThat(testClase.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testClase.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testClase.isIncidencias()).isEqualTo(UPDATED_INCIDENCIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingClase() throws Exception {
        int databaseSizeBeforeUpdate = claseRepository.findAll().size();

        // Create the Clase
        ClaseDTO claseDTO = claseMapper.toDto(clase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaseMockMvc.perform(put("/api/clases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clase in the database
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClase() throws Exception {
        // Initialize the database
        claseRepository.saveAndFlush(clase);

        int databaseSizeBeforeDelete = claseRepository.findAll().size();

        // Delete the clase
        restClaseMockMvc.perform(delete("/api/clases/{id}", clase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Clase> claseList = claseRepository.findAll();
        assertThat(claseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clase.class);
        Clase clase1 = new Clase();
        clase1.setId(1L);
        Clase clase2 = new Clase();
        clase2.setId(clase1.getId());
        assertThat(clase1).isEqualTo(clase2);
        clase2.setId(2L);
        assertThat(clase1).isNotEqualTo(clase2);
        clase1.setId(null);
        assertThat(clase1).isNotEqualTo(clase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaseDTO.class);
        ClaseDTO claseDTO1 = new ClaseDTO();
        claseDTO1.setId(1L);
        ClaseDTO claseDTO2 = new ClaseDTO();
        assertThat(claseDTO1).isNotEqualTo(claseDTO2);
        claseDTO2.setId(claseDTO1.getId());
        assertThat(claseDTO1).isEqualTo(claseDTO2);
        claseDTO2.setId(2L);
        assertThat(claseDTO1).isNotEqualTo(claseDTO2);
        claseDTO1.setId(null);
        assertThat(claseDTO1).isNotEqualTo(claseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(claseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(claseMapper.fromId(null)).isNull();
    }
}
