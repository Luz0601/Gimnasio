package com.concesionario.app.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.concesionario.app.service.HorarioClaseService;
import com.concesionario.app.service.dto.HorarioClaseDTO;

import io.github.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class HorarioClaseResource {

    private final Logger log = LoggerFactory.getLogger(HorarioClaseResource.class);

    private static final String ENTITY_NAME = "horarioClase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorarioClaseService horarioClaseService;

    public HorarioClaseResource(HorarioClaseService horarioClaseService) {
        this.horarioClaseService = horarioClaseService;
    }

    // Devolvemos las clases del mes que recibimos por parámetro
    @GetMapping("/horario-clase")
    public ResponseEntity<List<HorarioClaseDTO>> getAllHorarioClases(@RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a month of HorarioClase");
        List<HorarioClaseDTO> list = horarioClaseService.findAll();
        // hay que pasar pageable de <HorarioClase>
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), null);
        return ResponseEntity.ok().headers(headers).body(list);
    }

}
