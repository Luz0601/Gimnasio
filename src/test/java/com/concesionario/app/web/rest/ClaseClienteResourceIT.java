package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.ClaseCliente;
import com.concesionario.app.repository.ClaseClienteRepository;
import com.concesionario.app.service.ClaseClienteService;
import com.concesionario.app.service.dto.ClaseClienteDTO;
import com.concesionario.app.service.mapper.ClaseClienteMapper;
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
 * Integration tests for the {@Link ClaseClienteResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class ClaseClienteResourceIT {

    @Autowired
    private ClaseClienteRepository claseClienteRepository;

    @Autowired
    private ClaseClienteMapper claseClienteMapper;

    @Autowired
    private ClaseClienteService claseClienteService;

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

    private MockMvc restClaseClienteMockMvc;

    private ClaseCliente claseCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClaseClienteResource claseClienteResource = new ClaseClienteResource(claseClienteService);
        this.restClaseClienteMockMvc = MockMvcBuilders.standaloneSetup(claseClienteResource)
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
    public static ClaseCliente createEntity(EntityManager em) {
        ClaseCliente claseCliente = new ClaseCliente();
        return claseCliente;
    }

    @BeforeEach
    public void initTest() {
        claseCliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createClaseCliente() throws Exception {
        int databaseSizeBeforeCreate = claseClienteRepository.findAll().size();

        // Create the ClaseCliente
        ClaseClienteDTO claseClienteDTO = claseClienteMapper.toDto(claseCliente);
        restClaseClienteMockMvc.perform(post("/api/clase-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseClienteDTO)))
            .andExpect(status().isCreated());

        // Validate the ClaseCliente in the database
        List<ClaseCliente> claseClienteList = claseClienteRepository.findAll();
        assertThat(claseClienteList).hasSize(databaseSizeBeforeCreate + 1);
        ClaseCliente testClaseCliente = claseClienteList.get(claseClienteList.size() - 1);
    }

    @Test
    @Transactional
    public void createClaseClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = claseClienteRepository.findAll().size();

        // Create the ClaseCliente with an existing ID
        claseCliente.setId(1L);
        ClaseClienteDTO claseClienteDTO = claseClienteMapper.toDto(claseCliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaseClienteMockMvc.perform(post("/api/clase-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseClienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClaseCliente in the database
        List<ClaseCliente> claseClienteList = claseClienteRepository.findAll();
        assertThat(claseClienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClaseClientes() throws Exception {
        // Initialize the database
        claseClienteRepository.saveAndFlush(claseCliente);

        // Get all the claseClienteList
        restClaseClienteMockMvc.perform(get("/api/clase-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claseCliente.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getClaseCliente() throws Exception {
        // Initialize the database
        claseClienteRepository.saveAndFlush(claseCliente);

        // Get the claseCliente
        restClaseClienteMockMvc.perform(get("/api/clase-clientes/{id}", claseCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(claseCliente.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClaseCliente() throws Exception {
        // Get the claseCliente
        restClaseClienteMockMvc.perform(get("/api/clase-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClaseCliente() throws Exception {
        // Initialize the database
        claseClienteRepository.saveAndFlush(claseCliente);

        int databaseSizeBeforeUpdate = claseClienteRepository.findAll().size();

        // Update the claseCliente
        ClaseCliente updatedClaseCliente = claseClienteRepository.findById(claseCliente.getId()).get();
        // Disconnect from session so that the updates on updatedClaseCliente are not directly saved in db
        em.detach(updatedClaseCliente);
        ClaseClienteDTO claseClienteDTO = claseClienteMapper.toDto(updatedClaseCliente);

        restClaseClienteMockMvc.perform(put("/api/clase-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseClienteDTO)))
            .andExpect(status().isOk());

        // Validate the ClaseCliente in the database
        List<ClaseCliente> claseClienteList = claseClienteRepository.findAll();
        assertThat(claseClienteList).hasSize(databaseSizeBeforeUpdate);
        ClaseCliente testClaseCliente = claseClienteList.get(claseClienteList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClaseCliente() throws Exception {
        int databaseSizeBeforeUpdate = claseClienteRepository.findAll().size();

        // Create the ClaseCliente
        ClaseClienteDTO claseClienteDTO = claseClienteMapper.toDto(claseCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaseClienteMockMvc.perform(put("/api/clase-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claseClienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClaseCliente in the database
        List<ClaseCliente> claseClienteList = claseClienteRepository.findAll();
        assertThat(claseClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClaseCliente() throws Exception {
        // Initialize the database
        claseClienteRepository.saveAndFlush(claseCliente);

        int databaseSizeBeforeDelete = claseClienteRepository.findAll().size();

        // Delete the claseCliente
        restClaseClienteMockMvc.perform(delete("/api/clase-clientes/{id}", claseCliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ClaseCliente> claseClienteList = claseClienteRepository.findAll();
        assertThat(claseClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaseCliente.class);
        ClaseCliente claseCliente1 = new ClaseCliente();
        claseCliente1.setId(1L);
        ClaseCliente claseCliente2 = new ClaseCliente();
        claseCliente2.setId(claseCliente1.getId());
        assertThat(claseCliente1).isEqualTo(claseCliente2);
        claseCliente2.setId(2L);
        assertThat(claseCliente1).isNotEqualTo(claseCliente2);
        claseCliente1.setId(null);
        assertThat(claseCliente1).isNotEqualTo(claseCliente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaseClienteDTO.class);
        ClaseClienteDTO claseClienteDTO1 = new ClaseClienteDTO();
        claseClienteDTO1.setId(1L);
        ClaseClienteDTO claseClienteDTO2 = new ClaseClienteDTO();
        assertThat(claseClienteDTO1).isNotEqualTo(claseClienteDTO2);
        claseClienteDTO2.setId(claseClienteDTO1.getId());
        assertThat(claseClienteDTO1).isEqualTo(claseClienteDTO2);
        claseClienteDTO2.setId(2L);
        assertThat(claseClienteDTO1).isNotEqualTo(claseClienteDTO2);
        claseClienteDTO1.setId(null);
        assertThat(claseClienteDTO1).isNotEqualTo(claseClienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(claseClienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(claseClienteMapper.fromId(null)).isNull();
    }
}
