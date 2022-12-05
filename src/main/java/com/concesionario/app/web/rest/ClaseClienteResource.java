package com.concesionario.app.web.rest;

import com.concesionario.app.service.ClaseClienteService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.ClaseClienteDTO;

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
 * REST controller for managing {@link com.concesionario.app.domain.ClaseCliente}.
 */
@RestController
@RequestMapping("/api")
public class ClaseClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClaseClienteResource.class);

    private static final String ENTITY_NAME = "claseCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaseClienteService claseClienteService;

    public ClaseClienteResource(ClaseClienteService claseClienteService) {
        this.claseClienteService = claseClienteService;
    }

    /**
     * {@code POST  /clase-clientes} : Create a new claseCliente.
     *
     * @param claseClienteDTO the claseClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claseClienteDTO, or with status {@code 400 (Bad Request)} if the claseCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clase-clientes")
    public ResponseEntity<ClaseClienteDTO> createClaseCliente(@RequestBody ClaseClienteDTO claseClienteDTO) throws URISyntaxException {
        log.debug("REST request to save ClaseCliente : {}", claseClienteDTO);
        if (claseClienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new claseCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaseClienteDTO result = claseClienteService.save(claseClienteDTO);
        return ResponseEntity.created(new URI("/api/clase-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clase-clientes} : Updates an existing claseCliente.
     *
     * @param claseClienteDTO the claseClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claseClienteDTO,
     * or with status {@code 400 (Bad Request)} if the claseClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claseClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clase-clientes")
    public ResponseEntity<ClaseClienteDTO> updateClaseCliente(@RequestBody ClaseClienteDTO claseClienteDTO) throws URISyntaxException {
        log.debug("REST request to update ClaseCliente : {}", claseClienteDTO);
        if (claseClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClaseClienteDTO result = claseClienteService.save(claseClienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, claseClienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clase-clientes} : get all the claseClientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claseClientes in body.
     */
    @GetMapping("/clase-clientes")
    public ResponseEntity<List<ClaseClienteDTO>> getAllClaseClientes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ClaseClientes");
        Page<ClaseClienteDTO> page = claseClienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clase-clientes/:id} : get the "id" claseCliente.
     *
     * @param id the id of the claseClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claseClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clase-clientes/{id}")
    public ResponseEntity<ClaseClienteDTO> getClaseCliente(@PathVariable Long id) {
        log.debug("REST request to get ClaseCliente : {}", id);
        Optional<ClaseClienteDTO> claseClienteDTO = claseClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claseClienteDTO);
    }

    /**
     * {@code DELETE  /clase-clientes/:id} : delete the "id" claseCliente.
     *
     * @param id the id of the claseClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clase-clientes/{id}")
    public ResponseEntity<Void> deleteClaseCliente(@PathVariable Long id) {
        log.debug("REST request to delete ClaseCliente : {}", id);
        claseClienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
