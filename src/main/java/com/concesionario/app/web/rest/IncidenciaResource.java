package com.concesionario.app.web.rest;

import com.concesionario.app.service.IncidenciaService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.IncidenciaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.concesionario.app.domain.Incidencia}.
 */
@RestController
@RequestMapping("/api")
public class IncidenciaResource {

    private final Logger log = LoggerFactory.getLogger(IncidenciaResource.class);

    private static final String ENTITY_NAME = "incidencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncidenciaService incidenciaService;

    public IncidenciaResource(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    /**
     * {@code POST  /incidencias} : Create a new incidencia.
     *
     * @param incidenciaDTO the incidenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incidenciaDTO, or with status {@code 400 (Bad Request)} if the incidencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incidencias")
    public ResponseEntity<IncidenciaDTO> createIncidencia(@Valid @RequestBody IncidenciaDTO incidenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Incidencia : {}", incidenciaDTO);
        if (incidenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidenciaDTO result = incidenciaService.save(incidenciaDTO);
        return ResponseEntity.created(new URI("/api/incidencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incidencias} : Updates an existing incidencia.
     *
     * @param incidenciaDTO the incidenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidenciaDTO,
     * or with status {@code 400 (Bad Request)} if the incidenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incidenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incidencias")
    public ResponseEntity<IncidenciaDTO> updateIncidencia(@Valid @RequestBody IncidenciaDTO incidenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Incidencia : {}", incidenciaDTO);
        if (incidenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncidenciaDTO result = incidenciaService.save(incidenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incidenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incidencias} : get all the incidencias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidencias in body.
     */
    @GetMapping("/incidencias")
    public ResponseEntity<List<IncidenciaDTO>> getAllIncidencias(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Incidencias");
        Page<IncidenciaDTO> page = incidenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incidencias/:id} : get the "id" incidencia.
     *
     * @param id the id of the incidenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incidencias/{id}")
    public ResponseEntity<IncidenciaDTO> getIncidencia(@PathVariable Long id) {
        log.debug("REST request to get Incidencia : {}", id);
        Optional<IncidenciaDTO> incidenciaDTO = incidenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidenciaDTO);
    }

    /**
     * {@code DELETE  /incidencias/:id} : delete the "id" incidencia.
     *
     * @param id the id of the incidenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incidencias/{id}")
    public ResponseEntity<Void> deleteIncidencia(@PathVariable Long id) {
        log.debug("REST request to delete Incidencia : {}", id);
        incidenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
