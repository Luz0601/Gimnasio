package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Suscripcion;
import com.concesionario.app.repository.SuscripcionRepository;
import com.concesionario.app.service.SuscripcionService;
import com.concesionario.app.service.dto.SuscripcionDTO;
import com.concesionario.app.service.mapper.SuscripcionMapper;
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

import com.concesionario.app.domain.enumeration.PeriodoSuscripcion;
/**
 * Integration tests for the {@Link SuscripcionResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class SuscripcionResourceIT {

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final PeriodoSuscripcion DEFAULT_PERIODO = PeriodoSuscripcion.Mensual;
    private static final PeriodoSuscripcion UPDATED_PERIODO = PeriodoSuscripcion.Trimestral;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private SuscripcionMapper suscripcionMapper;

    @Autowired
    private SuscripcionService suscripcionService;

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

    private MockMvc restSuscripcionMockMvc;

    private Suscripcion suscripcion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuscripcionResource suscripcionResource = new SuscripcionResource(suscripcionService);
        this.restSuscripcionMockMvc = MockMvcBuilders.standaloneSetup(suscripcionResource)
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
    public static Suscripcion createEntity(EntityManager em) {
        Suscripcion suscripcion = new Suscripcion()
            .precio(DEFAULT_PRECIO)
            .periodo(DEFAULT_PERIODO);
        return suscripcion;
    }

    @BeforeEach
    public void initTest() {
        suscripcion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuscripcion() throws Exception {
        int databaseSizeBeforeCreate = suscripcionRepository.findAll().size();

        // Create the Suscripcion
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(suscripcion);
        restSuscripcionMockMvc.perform(post("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isCreated());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeCreate + 1);
        Suscripcion testSuscripcion = suscripcionList.get(suscripcionList.size() - 1);
        assertThat(testSuscripcion.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testSuscripcion.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
    }

    @Test
    @Transactional
    public void createSuscripcionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suscripcionRepository.findAll().size();

        // Create the Suscripcion with an existing ID
        suscripcion.setId(1L);
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(suscripcion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuscripcionMockMvc.perform(post("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSuscripcions() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        // Get all the suscripcionList
        restSuscripcionMockMvc.perform(get("/api/suscripcions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suscripcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())));
    }
    
    @Test
    @Transactional
    public void getSuscripcion() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        // Get the suscripcion
        restSuscripcionMockMvc.perform(get("/api/suscripcions/{id}", suscripcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suscripcion.getId().intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuscripcion() throws Exception {
        // Get the suscripcion
        restSuscripcionMockMvc.perform(get("/api/suscripcions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuscripcion() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        int databaseSizeBeforeUpdate = suscripcionRepository.findAll().size();

        // Update the suscripcion
        Suscripcion updatedSuscripcion = suscripcionRepository.findById(suscripcion.getId()).get();
        // Disconnect from session so that the updates on updatedSuscripcion are not directly saved in db
        em.detach(updatedSuscripcion);
        updatedSuscripcion
            .precio(UPDATED_PRECIO)
            .periodo(UPDATED_PERIODO);
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(updatedSuscripcion);

        restSuscripcionMockMvc.perform(put("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isOk());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeUpdate);
        Suscripcion testSuscripcion = suscripcionList.get(suscripcionList.size() - 1);
        assertThat(testSuscripcion.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testSuscripcion.getPeriodo()).isEqualTo(UPDATED_PERIODO);
    }

    @Test
    @Transactional
    public void updateNonExistingSuscripcion() throws Exception {
        int databaseSizeBeforeUpdate = suscripcionRepository.findAll().size();

        // Create the Suscripcion
        SuscripcionDTO suscripcionDTO = suscripcionMapper.toDto(suscripcion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuscripcionMockMvc.perform(put("/api/suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suscripcion in the database
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuscripcion() throws Exception {
        // Initialize the database
        suscripcionRepository.saveAndFlush(suscripcion);

        int databaseSizeBeforeDelete = suscripcionRepository.findAll().size();

        // Delete the suscripcion
        restSuscripcionMockMvc.perform(delete("/api/suscripcions/{id}", suscripcion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Suscripcion> suscripcionList = suscripcionRepository.findAll();
        assertThat(suscripcionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suscripcion.class);
        Suscripcion suscripcion1 = new Suscripcion();
        suscripcion1.setId(1L);
        Suscripcion suscripcion2 = new Suscripcion();
        suscripcion2.setId(suscripcion1.getId());
        assertThat(suscripcion1).isEqualTo(suscripcion2);
        suscripcion2.setId(2L);
        assertThat(suscripcion1).isNotEqualTo(suscripcion2);
        suscripcion1.setId(null);
        assertThat(suscripcion1).isNotEqualTo(suscripcion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuscripcionDTO.class);
        SuscripcionDTO suscripcionDTO1 = new SuscripcionDTO();
        suscripcionDTO1.setId(1L);
        SuscripcionDTO suscripcionDTO2 = new SuscripcionDTO();
        assertThat(suscripcionDTO1).isNotEqualTo(suscripcionDTO2);
        suscripcionDTO2.setId(suscripcionDTO1.getId());
        assertThat(suscripcionDTO1).isEqualTo(suscripcionDTO2);
        suscripcionDTO2.setId(2L);
        assertThat(suscripcionDTO1).isNotEqualTo(suscripcionDTO2);
        suscripcionDTO1.setId(null);
        assertThat(suscripcionDTO1).isNotEqualTo(suscripcionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(suscripcionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(suscripcionMapper.fromId(null)).isNull();
    }
}
