package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.Inventario;
import com.concesionario.app.repository.InventarioRepository;
import com.concesionario.app.service.InventarioService;
import com.concesionario.app.service.dto.InventarioDTO;
import com.concesionario.app.service.mapper.InventarioMapper;
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
 * Integration tests for the {@Link InventarioResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class InventarioResourceIT {

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final LocalDate DEFAULT_ULT_REVISION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ULT_REVISION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PERIODO_REVISION = 1;
    private static final Integer UPDATED_PERIODO_REVISION = 2;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private InventarioService inventarioService;

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

    private MockMvc restInventarioMockMvc;

    private Inventario inventario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventarioResource inventarioResource = new InventarioResource(inventarioService);
        this.restInventarioMockMvc = MockMvcBuilders.standaloneSetup(inventarioResource)
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
    public static Inventario createEntity(EntityManager em) {
        Inventario inventario = new Inventario()
            .ref(DEFAULT_REF)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .cantidad(DEFAULT_CANTIDAD)
            .estado(DEFAULT_ESTADO)
            .ultRevision(DEFAULT_ULT_REVISION)
            .periodoRevision(DEFAULT_PERIODO_REVISION);
        return inventario;
    }

    @BeforeEach
    public void initTest() {
        inventario = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventario() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // Create the Inventario
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);
        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate + 1);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testInventario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInventario.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testInventario.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testInventario.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testInventario.getUltRevision()).isEqualTo(DEFAULT_ULT_REVISION);
        assertThat(testInventario.getPeriodoRevision()).isEqualTo(DEFAULT_PERIODO_REVISION);
    }

    @Test
    @Transactional
    public void createInventarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // Create the Inventario with an existing ID
        inventario.setId(1L);
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventarios() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        // Get all the inventarioList
        restInventarioMockMvc.perform(get("/api/inventarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventario.getId().intValue())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].ultRevision").value(hasItem(DEFAULT_ULT_REVISION.toString())))
            .andExpect(jsonPath("$.[*].periodoRevision").value(hasItem(DEFAULT_PERIODO_REVISION)));
    }
    
    @Test
    @Transactional
    public void getInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        // Get the inventario
        restInventarioMockMvc.perform(get("/api/inventarios/{id}", inventario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventario.getId().intValue()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.ultRevision").value(DEFAULT_ULT_REVISION.toString()))
            .andExpect(jsonPath("$.periodoRevision").value(DEFAULT_PERIODO_REVISION));
    }

    @Test
    @Transactional
    public void getNonExistingInventario() throws Exception {
        // Get the inventario
        restInventarioMockMvc.perform(get("/api/inventarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Update the inventario
        Inventario updatedInventario = inventarioRepository.findById(inventario.getId()).get();
        // Disconnect from session so that the updates on updatedInventario are not directly saved in db
        em.detach(updatedInventario);
        updatedInventario
            .ref(UPDATED_REF)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .estado(UPDATED_ESTADO)
            .ultRevision(UPDATED_ULT_REVISION)
            .periodoRevision(UPDATED_PERIODO_REVISION);
        InventarioDTO inventarioDTO = inventarioMapper.toDto(updatedInventario);

        restInventarioMockMvc.perform(put("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isOk());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testInventario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInventario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testInventario.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testInventario.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testInventario.getUltRevision()).isEqualTo(UPDATED_ULT_REVISION);
        assertThat(testInventario.getPeriodoRevision()).isEqualTo(UPDATED_PERIODO_REVISION);
    }

    @Test
    @Transactional
    public void updateNonExistingInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Create the Inventario
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventarioMockMvc.perform(put("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        int databaseSizeBeforeDelete = inventarioRepository.findAll().size();

        // Delete the inventario
        restInventarioMockMvc.perform(delete("/api/inventarios/{id}", inventario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventario.class);
        Inventario inventario1 = new Inventario();
        inventario1.setId(1L);
        Inventario inventario2 = new Inventario();
        inventario2.setId(inventario1.getId());
        assertThat(inventario1).isEqualTo(inventario2);
        inventario2.setId(2L);
        assertThat(inventario1).isNotEqualTo(inventario2);
        inventario1.setId(null);
        assertThat(inventario1).isNotEqualTo(inventario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventarioDTO.class);
        InventarioDTO inventarioDTO1 = new InventarioDTO();
        inventarioDTO1.setId(1L);
        InventarioDTO inventarioDTO2 = new InventarioDTO();
        assertThat(inventarioDTO1).isNotEqualTo(inventarioDTO2);
        inventarioDTO2.setId(inventarioDTO1.getId());
        assertThat(inventarioDTO1).isEqualTo(inventarioDTO2);
        inventarioDTO2.setId(2L);
        assertThat(inventarioDTO1).isNotEqualTo(inventarioDTO2);
        inventarioDTO1.setId(null);
        assertThat(inventarioDTO1).isNotEqualTo(inventarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inventarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inventarioMapper.fromId(null)).isNull();
    }
}
