package com.concesionario.app.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import com.concesionario.app.GimnasioApp;
import com.concesionario.app.service.HorarioClaseService;
import com.concesionario.app.service.dto.HorarioClaseDTO;
import com.concesionario.app.web.rest.errors.ExceptionTranslator;

@SpringBootTest(classes = GimnasioApp.class)
public class HorarioClaseResourceIT {

    @Autowired
    private HorarioClaseService horarioClaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restHorarioClaseMockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorarioClaseResource horarioClaseResource = new HorarioClaseResource(horarioClaseService);
        this.restHorarioClaseMockMvc = MockMvcBuilders.standaloneSetup(horarioClaseResource)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create a {@link FormattingConversionService} which use ISO date format, instead of the localized one.
     * @return the {@link FormattingConversionService}.
     */
	public static FormattingConversionService createFormattingConversionService() {
		DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService();
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(dfcs);
        return dfcs;
	}

}
