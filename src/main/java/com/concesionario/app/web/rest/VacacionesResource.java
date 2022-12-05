package com.concesionario.app.web.rest;

import com.concesionario.app.service.VacacionesService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.VacacionesDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.concesionario.app.domain.Vacaciones}.
 */
@RestController
@RequestMapping("/api")
public class VacacionesResource {

    private final Logger log = LoggerFactory.getLogger(VacacionesResource.class);

    private static final String ENTITY_NAME = "vacaciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VacacionesService vacacionesService;

    public VacacionesResource(VacacionesService vacacionesService) {
        this.vacacionesService = vacacionesService;
    }

    /**
     * {@code POST  /vacaciones} : Create a new vacaciones.
     *
     * @param vacacionesDTO the vacacionesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vacacionesDTO, or with status {@code 400 (Bad Request)} if the vacaciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vacaciones")
    public ResponseEntity<VacacionesDTO> createVacaciones(@RequestBody VacacionesDTO vacacionesDTO) throws URISyntaxException {
        log.debug("REST request to save Vacaciones : {}", vacacionesDTO);
        if (vacacionesDTO.getId() != null) {
            throw new BadRequestAlertException("A new vacaciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VacacionesDTO result = vacacionesService.save(vacacionesDTO);
        return ResponseEntity.created(new URI("/api/vacaciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vacaciones} : Updates an existing vacaciones.
     *
     * @param vacacionesDTO the vacacionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vacacionesDTO,
     * or with status {@code 400 (Bad Request)} if the vacacionesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vacacionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vacaciones")
    public ResponseEntity<VacacionesDTO> updateVacaciones(@RequestBody VacacionesDTO vacacionesDTO) throws URISyntaxException {
        log.debug("REST request to update Vacaciones : {}", vacacionesDTO);
        if (vacacionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VacacionesDTO result = vacacionesService.save(vacacionesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vacacionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vacaciones} : get all the vacaciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vacaciones in body.
     */
    @GetMapping("/vacaciones")
    public ResponseEntity<List<VacacionesDTO>> getAllVacaciones(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Vacaciones");
        Page<VacacionesDTO> page = vacacionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vacaciones/:id} : get the "id" vacaciones.
     *
     * @param id the id of the vacacionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vacacionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vacaciones/{id}")
    public ResponseEntity<VacacionesDTO> getVacaciones(@PathVariable Long id) {
        log.debug("REST request to get Vacaciones : {}", id);
        Optional<VacacionesDTO> vacacionesDTO = vacacionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vacacionesDTO);
    }

    /**
     * {@code DELETE  /vacaciones/:id} : delete the "id" vacaciones.
     *
     * @param id the id of the vacacionesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vacaciones/{id}")
    public ResponseEntity<Void> deleteVacaciones(@PathVariable Long id) {
        log.debug("REST request to delete Vacaciones : {}", id);
        vacacionesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
