package com.concesionario.app.web.rest;

import com.concesionario.app.service.NominaService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.NominaDTO;

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
 * REST controller for managing {@link com.concesionario.app.domain.Nomina}.
 */
@RestController
@RequestMapping("/api")
public class NominaResource {

    private final Logger log = LoggerFactory.getLogger(NominaResource.class);

    private static final String ENTITY_NAME = "nomina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NominaService nominaService;

    public NominaResource(NominaService nominaService) {
        this.nominaService = nominaService;
    }

    /**
     * {@code POST  /nominas} : Create a new nomina.
     *
     * @param nominaDTO the nominaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nominaDTO, or with status {@code 400 (Bad Request)} if the nomina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nominas")
    public ResponseEntity<NominaDTO> createNomina(@RequestBody NominaDTO nominaDTO) throws URISyntaxException {
        log.debug("REST request to save Nomina : {}", nominaDTO);
        if (nominaDTO.getId() != null) {
            throw new BadRequestAlertException("A new nomina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NominaDTO result = nominaService.save(nominaDTO);
        return ResponseEntity.created(new URI("/api/nominas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nominas} : Updates an existing nomina.
     *
     * @param nominaDTO the nominaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nominaDTO,
     * or with status {@code 400 (Bad Request)} if the nominaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nominaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nominas")
    public ResponseEntity<NominaDTO> updateNomina(@RequestBody NominaDTO nominaDTO) throws URISyntaxException {
        log.debug("REST request to update Nomina : {}", nominaDTO);
        if (nominaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NominaDTO result = nominaService.save(nominaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nominaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nominas} : get all the nominas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nominas in body.
     */
    @GetMapping("/nominas")
    public ResponseEntity<List<NominaDTO>> getAllNominas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Nominas");
        Page<NominaDTO> page = nominaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nominas/:id} : get the "id" nomina.
     *
     * @param id the id of the nominaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nominaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nominas/{id}")
    public ResponseEntity<NominaDTO> getNomina(@PathVariable Long id) {
        log.debug("REST request to get Nomina : {}", id);
        Optional<NominaDTO> nominaDTO = nominaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nominaDTO);
    }

    /**
     * {@code DELETE  /nominas/:id} : delete the "id" nomina.
     *
     * @param id the id of the nominaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nominas/{id}")
    public ResponseEntity<Void> deleteNomina(@PathVariable Long id) {
        log.debug("REST request to delete Nomina : {}", id);
        nominaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
