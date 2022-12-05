package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Vacaciones;
import com.concesionario.app.repository.VacacionesRepository;
import com.concesionario.app.service.VacacionesService;
import com.concesionario.app.service.dto.VacacionesDTO;
import com.concesionario.app.service.mapper.VacacionesMapper;
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
 * Integration tests for the {@Link VacacionesResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class VacacionesResourceIT {

    private static final LocalDate DEFAULT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VacacionesRepository vacacionesRepository;

    @Autowired
    private VacacionesMapper vacacionesMapper;

    @Autowired
    private VacacionesService vacacionesService;

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

    private MockMvc restVacacionesMockMvc;

    private Vacaciones vacaciones;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VacacionesResource vacacionesResource = new VacacionesResource(vacacionesService);
        this.restVacacionesMockMvc = MockMvcBuilders.standaloneSetup(vacacionesResource)
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
    public static Vacaciones createEntity(EntityManager em) {
        Vacaciones vacaciones = new Vacaciones()
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN);
        return vacaciones;
    }

    @BeforeEach
    public void initTest() {
        vacaciones = createEntity(em);
    }

    @Test
    @Transactional
    public void createVacaciones() throws Exception {
        int databaseSizeBeforeCreate = vacacionesRepository.findAll().size();

        // Create the Vacaciones
        VacacionesDTO vacacionesDTO = vacacionesMapper.toDto(vacaciones);
        restVacacionesMockMvc.perform(post("/api/vacaciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacacionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Vacaciones in the database
        List<Vacaciones> vacacionesList = vacacionesRepository.findAll();
        assertThat(vacacionesList).hasSize(databaseSizeBeforeCreate + 1);
        Vacaciones testVacaciones = vacacionesList.get(vacacionesList.size() - 1);
        assertThat(testVacaciones.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testVacaciones.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    public void createVacacionesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vacacionesRepository.findAll().size();

        // Create the Vacaciones with an existing ID
        vacaciones.setId(1L);
        VacacionesDTO vacacionesDTO = vacacionesMapper.toDto(vacaciones);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVacacionesMockMvc.perform(post("/api/vacaciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacacionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vacaciones in the database
        List<Vacaciones> vacacionesList = vacacionesRepository.findAll();
        assertThat(vacacionesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVacaciones() throws Exception {
        // Initialize the database
        vacacionesRepository.saveAndFlush(vacaciones);

        // Get all the vacacionesList
        restVacacionesMockMvc.perform(get("/api/vacaciones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vacaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getVacaciones() throws Exception {
        // Initialize the database
        vacacionesRepository.saveAndFlush(vacaciones);

        // Get the vacaciones
        restVacacionesMockMvc.perform(get("/api/vacaciones/{id}", vacaciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vacaciones.getId().intValue()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVacaciones() throws Exception {
        // Get the vacaciones
        restVacacionesMockMvc.perform(get("/api/vacaciones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVacaciones() throws Exception {
        // Initialize the database
        vacacionesRepository.saveAndFlush(vacaciones);

        int databaseSizeBeforeUpdate = vacacionesRepository.findAll().size();

        // Update the vacaciones
        Vacaciones updatedVacaciones = vacacionesRepository.findById(vacaciones.getId()).get();
        // Disconnect from session so that the updates on updatedVacaciones are not directly saved in db
        em.detach(updatedVacaciones);
        updatedVacaciones
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN);
        VacacionesDTO vacacionesDTO = vacacionesMapper.toDto(updatedVacaciones);

        restVacacionesMockMvc.perform(put("/api/vacaciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacacionesDTO)))
            .andExpect(status().isOk());

        // Validate the Vacaciones in the database
        List<Vacaciones> vacacionesList = vacacionesRepository.findAll();
        assertThat(vacacionesList).hasSize(databaseSizeBeforeUpdate);
        Vacaciones testVacaciones = vacacionesList.get(vacacionesList.size() - 1);
        assertThat(testVacaciones.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testVacaciones.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingVacaciones() throws Exception {
        int databaseSizeBeforeUpdate = vacacionesRepository.findAll().size();

        // Create the Vacaciones
        VacacionesDTO vacacionesDTO = vacacionesMapper.toDto(vacaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVacacionesMockMvc.perform(put("/api/vacaciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vacacionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vacaciones in the database
        List<Vacaciones> vacacionesList = vacacionesRepository.findAll();
        assertThat(vacacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVacaciones() throws Exception {
        // Initialize the database
        vacacionesRepository.saveAndFlush(vacaciones);

        int databaseSizeBeforeDelete = vacacionesRepository.findAll().size();

        // Delete the vacaciones
        restVacacionesMockMvc.perform(delete("/api/vacaciones/{id}", vacaciones.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Vacaciones> vacacionesList = vacacionesRepository.findAll();
        assertThat(vacacionesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vacaciones.class);
        Vacaciones vacaciones1 = new Vacaciones();
        vacaciones1.setId(1L);
        Vacaciones vacaciones2 = new Vacaciones();
        vacaciones2.setId(vacaciones1.getId());
        assertThat(vacaciones1).isEqualTo(vacaciones2);
        vacaciones2.setId(2L);
        assertThat(vacaciones1).isNotEqualTo(vacaciones2);
        vacaciones1.setId(null);
        assertThat(vacaciones1).isNotEqualTo(vacaciones2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VacacionesDTO.class);
        VacacionesDTO vacacionesDTO1 = new VacacionesDTO();
        vacacionesDTO1.setId(1L);
        VacacionesDTO vacacionesDTO2 = new VacacionesDTO();
        assertThat(vacacionesDTO1).isNotEqualTo(vacacionesDTO2);
        vacacionesDTO2.setId(vacacionesDTO1.getId());
        assertThat(vacacionesDTO1).isEqualTo(vacacionesDTO2);
        vacacionesDTO2.setId(2L);
        assertThat(vacacionesDTO1).isNotEqualTo(vacacionesDTO2);
        vacacionesDTO1.setId(null);
        assertThat(vacacionesDTO1).isNotEqualTo(vacacionesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vacacionesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vacacionesMapper.fromId(null)).isNull();
    }
}
