package com.concesionario.app.web.rest;

import com.concesionario.app.service.ClaseService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.ClaseDTO;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.concesionario.app.domain.Clase}.
 */
@RestController
@RequestMapping("/api")
public class ClaseResource {

    private final Logger log = LoggerFactory.getLogger(ClaseResource.class);

    private static final String ENTITY_NAME = "clase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaseService claseService;

    public ClaseResource(ClaseService claseService) {
        this.claseService = claseService;
    }

    /**
     * {@code POST  /clases} : Create a new clase.
     *
     * @param claseDTO the claseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claseDTO, or with status {@code 400 (Bad Request)} if the clase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clases")
<<<<<<< HEAD
=======
    @PreAuthorize("hasAuthority('ROLE_MONITOR') or hasAuthority('ROLE_ADMIN')")
>>>>>>> Clase-Incidencia
    public ResponseEntity<ClaseDTO> createClase(@Valid @RequestBody ClaseDTO claseDTO) throws URISyntaxException {
        log.debug("REST request to save Clase : {}", claseDTO);
        if (claseDTO.getId() != null) {
            throw new BadRequestAlertException("A new clase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaseDTO result = claseService.save(claseDTO);
        return ResponseEntity.created(new URI("/api/clases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clases} : Updates an existing clase.
     *
     * @param claseDTO the claseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claseDTO,
     * or with status {@code 400 (Bad Request)} if the claseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clases")
    public ResponseEntity<ClaseDTO> updateClase(@Valid @RequestBody ClaseDTO claseDTO) throws URISyntaxException {
        log.debug("REST request to update Clase : {}", claseDTO);
        if (claseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClaseDTO result = claseService.save(claseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, claseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clases} : get all the clases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clases in body.
     */
    @GetMapping("/clases")
    public ResponseEntity<List<ClaseDTO>> getAllClases(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Clases");
        Page<ClaseDTO> page = claseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clases/:id} : get the "id" clase.
     *
     * @param id the id of the claseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clases/{id}")
    public ResponseEntity<ClaseDTO> getClase(@PathVariable Long id) {
        log.debug("REST request to get Clase : {}", id);
        Optional<ClaseDTO> claseDTO = claseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claseDTO);
    }

    /**
     * {@code DELETE  /clases/:id} : delete the "id" clase.
     *
     * @param id the id of the claseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clases/{id}")
    public ResponseEntity<Void> deleteClase(@PathVariable Long id) {
        log.debug("REST request to delete Clase : {}", id);
        claseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
