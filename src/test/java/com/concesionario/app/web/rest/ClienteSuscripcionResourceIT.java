package com.concesionario.app.web.rest;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.domain.ClienteSuscripcion;
import com.concesionario.app.repository.ClienteSuscripcionRepository;
import com.concesionario.app.service.ClienteSuscripcionService;
import com.concesionario.app.service.dto.ClienteSuscripcionDTO;
import com.concesionario.app.service.mapper.ClienteSuscripcionMapper;
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
 * Integration tests for the {@Link ClienteSuscripcionResource} REST controller.
 */
@SpringBootTest(classes = GimnasioApp.class)
public class ClienteSuscripcionResourceIT {

    private static final LocalDate DEFAULT_ULTIMO_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ULTIMO_PAGO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_METODO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_METODO_PAGO = "BBBBBBBBBB";

    @Autowired
    private ClienteSuscripcionRepository clienteSuscripcionRepository;

    @Autowired
    private ClienteSuscripcionMapper clienteSuscripcionMapper;

    @Autowired
    private ClienteSuscripcionService clienteSuscripcionService;

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

    private MockMvc restClienteSuscripcionMockMvc;

    private ClienteSuscripcion clienteSuscripcion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteSuscripcionResource clienteSuscripcionResource = new ClienteSuscripcionResource(clienteSuscripcionService);
        this.restClienteSuscripcionMockMvc = MockMvcBuilders.standaloneSetup(clienteSuscripcionResource)
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
    public static ClienteSuscripcion createEntity(EntityManager em) {
        ClienteSuscripcion clienteSuscripcion = new ClienteSuscripcion()
            .ultimoPago(DEFAULT_ULTIMO_PAGO)
            .metodoPago(DEFAULT_METODO_PAGO);
        return clienteSuscripcion;
    }

    @BeforeEach
    public void initTest() {
        clienteSuscripcion = createEntity(em);
    }

    @Test
    @Transactional
    public void createClienteSuscripcion() throws Exception {
        int databaseSizeBeforeCreate = clienteSuscripcionRepository.findAll().size();

        // Create the ClienteSuscripcion
        ClienteSuscripcionDTO clienteSuscripcionDTO = clienteSuscripcionMapper.toDto(clienteSuscripcion);
        restClienteSuscripcionMockMvc.perform(post("/api/cliente-suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteSuscripcionDTO)))
            .andExpect(status().isCreated());

        // Validate the ClienteSuscripcion in the database
        List<ClienteSuscripcion> clienteSuscripcionList = clienteSuscripcionRepository.findAll();
        assertThat(clienteSuscripcionList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteSuscripcion testClienteSuscripcion = clienteSuscripcionList.get(clienteSuscripcionList.size() - 1);
        assertThat(testClienteSuscripcion.getUltimoPago()).isEqualTo(DEFAULT_ULTIMO_PAGO);
        assertThat(testClienteSuscripcion.getMetodoPago()).isEqualTo(DEFAULT_METODO_PAGO);
    }

    @Test
    @Transactional
    public void createClienteSuscripcionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteSuscripcionRepository.findAll().size();

        // Create the ClienteSuscripcion with an existing ID
        clienteSuscripcion.setId(1L);
        ClienteSuscripcionDTO clienteSuscripcionDTO = clienteSuscripcionMapper.toDto(clienteSuscripcion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteSuscripcionMockMvc.perform(post("/api/cliente-suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteSuscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteSuscripcion in the database
        List<ClienteSuscripcion> clienteSuscripcionList = clienteSuscripcionRepository.findAll();
        assertThat(clienteSuscripcionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUltimoPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteSuscripcionRepository.findAll().size();
        // set the field null
        clienteSuscripcion.setUltimoPago(null);

        // Create the ClienteSuscripcion, which fails.
        ClienteSuscripcionDTO clienteSuscripcionDTO = clienteSuscripcionMapper.toDto(clienteSuscripcion);

        restClienteSuscripcionMockMvc.perform(post("/api/cliente-suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteSuscripcionDTO)))
            .andExpect(status().isBadRequest());

        List<ClienteSuscripcion> clienteSuscripcionList = clienteSuscripcionRepository.findAll();
        assertThat(clienteSuscripcionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClienteSuscripcions() throws Exception {
        // Initialize the database
        clienteSuscripcionRepository.saveAndFlush(clienteSuscripcion);

        // Get all the clienteSuscripcionList
        restClienteSuscripcionMockMvc.perform(get("/api/cliente-suscripcions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteSuscripcion.getId().intValue())))
            .andExpect(jsonPath("$.[*].ultimoPago").value(hasItem(DEFAULT_ULTIMO_PAGO.toString())))
            .andExpect(jsonPath("$.[*].metodoPago").value(hasItem(DEFAULT_METODO_PAGO.toString())));
    }
    
    @Test
    @Transactional
    public void getClienteSuscripcion() throws Exception {
        // Initialize the database
        clienteSuscripcionRepository.saveAndFlush(clienteSuscripcion);

        // Get the clienteSuscripcion
        restClienteSuscripcionMockMvc.perform(get("/api/cliente-suscripcions/{id}", clienteSuscripcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clienteSuscripcion.getId().intValue()))
            .andExpect(jsonPath("$.ultimoPago").value(DEFAULT_ULTIMO_PAGO.toString()))
            .andExpect(jsonPath("$.metodoPago").value(DEFAULT_METODO_PAGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClienteSuscripcion() throws Exception {
        // Get the clienteSuscripcion
        restClienteSuscripcionMockMvc.perform(get("/api/cliente-suscripcions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClienteSuscripcion() throws Exception {
        // Initialize the database
        clienteSuscripcionRepository.saveAndFlush(clienteSuscripcion);

        int databaseSizeBeforeUpdate = clienteSuscripcionRepository.findAll().size();

        // Update the clienteSuscripcion
        ClienteSuscripcion updatedClienteSuscripcion = clienteSuscripcionRepository.findById(clienteSuscripcion.getId()).get();
        // Disconnect from session so that the updates on updatedClienteSuscripcion are not directly saved in db
        em.detach(updatedClienteSuscripcion);
        updatedClienteSuscripcion
            .ultimoPago(UPDATED_ULTIMO_PAGO)
            .metodoPago(UPDATED_METODO_PAGO);
        ClienteSuscripcionDTO clienteSuscripcionDTO = clienteSuscripcionMapper.toDto(updatedClienteSuscripcion);

        restClienteSuscripcionMockMvc.perform(put("/api/cliente-suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteSuscripcionDTO)))
            .andExpect(status().isOk());

        // Validate the ClienteSuscripcion in the database
        List<ClienteSuscripcion> clienteSuscripcionList = clienteSuscripcionRepository.findAll();
        assertThat(clienteSuscripcionList).hasSize(databaseSizeBeforeUpdate);
        ClienteSuscripcion testClienteSuscripcion = clienteSuscripcionList.get(clienteSuscripcionList.size() - 1);
        assertThat(testClienteSuscripcion.getUltimoPago()).isEqualTo(UPDATED_ULTIMO_PAGO);
        assertThat(testClienteSuscripcion.getMetodoPago()).isEqualTo(UPDATED_METODO_PAGO);
    }

    @Test
    @Transactional
    public void updateNonExistingClienteSuscripcion() throws Exception {
        int databaseSizeBeforeUpdate = clienteSuscripcionRepository.findAll().size();

        // Create the ClienteSuscripcion
        ClienteSuscripcionDTO clienteSuscripcionDTO = clienteSuscripcionMapper.toDto(clienteSuscripcion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteSuscripcionMockMvc.perform(put("/api/cliente-suscripcions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteSuscripcionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteSuscripcion in the database
        List<ClienteSuscripcion> clienteSuscripcionList = clienteSuscripcionRepository.findAll();
        assertThat(clienteSuscripcionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClienteSuscripcion() throws Exception {
        // Initialize the database
        clienteSuscripcionRepository.saveAndFlush(clienteSuscripcion);

        int databaseSizeBeforeDelete = clienteSuscripcionRepository.findAll().size();

        // Delete the clienteSuscripcion
        restClienteSuscripcionMockMvc.perform(delete("/api/cliente-suscripcions/{id}", clienteSuscripcion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ClienteSuscripcion> clienteSuscripcionList = clienteSuscripcionRepository.findAll();
        assertThat(clienteSuscripcionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteSuscripcion.class);
        ClienteSuscripcion clienteSuscripcion1 = new ClienteSuscripcion();
        clienteSuscripcion1.setId(1L);
        ClienteSuscripcion clienteSuscripcion2 = new ClienteSuscripcion();
        clienteSuscripcion2.setId(clienteSuscripcion1.getId());
        assertThat(clienteSuscripcion1).isEqualTo(clienteSuscripcion2);
        clienteSuscripcion2.setId(2L);
        assertThat(clienteSuscripcion1).isNotEqualTo(clienteSuscripcion2);
        clienteSuscripcion1.setId(null);
        assertThat(clienteSuscripcion1).isNotEqualTo(clienteSuscripcion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteSuscripcionDTO.class);
        ClienteSuscripcionDTO clienteSuscripcionDTO1 = new ClienteSuscripcionDTO();
        clienteSuscripcionDTO1.setId(1L);
        ClienteSuscripcionDTO clienteSuscripcionDTO2 = new ClienteSuscripcionDTO();
        assertThat(clienteSuscripcionDTO1).isNotEqualTo(clienteSuscripcionDTO2);
        clienteSuscripcionDTO2.setId(clienteSuscripcionDTO1.getId());
        assertThat(clienteSuscripcionDTO1).isEqualTo(clienteSuscripcionDTO2);
        clienteSuscripcionDTO2.setId(2L);
        assertThat(clienteSuscripcionDTO1).isNotEqualTo(clienteSuscripcionDTO2);
        clienteSuscripcionDTO1.setId(null);
        assertThat(clienteSuscripcionDTO1).isNotEqualTo(clienteSuscripcionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteSuscripcionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteSuscripcionMapper.fromId(null)).isNull();
    }
}
