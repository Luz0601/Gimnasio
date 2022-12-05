package com.concesionario.app.web.rest;

import com.concesionario.app.service.ProveedorService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.ProveedorDTO;

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
 * REST controller for managing {@link com.concesionario.app.domain.Proveedor}.
 */
@RestController
@RequestMapping("/api")
public class ProveedorResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorResource.class);

    private static final String ENTITY_NAME = "proveedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProveedorService proveedorService;

    public ProveedorResource(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    /**
     * {@code POST  /proveedors} : Create a new proveedor.
     *
     * @param proveedorDTO the proveedorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proveedorDTO, or with status {@code 400 (Bad Request)} if the proveedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proveedors")
    public ResponseEntity<ProveedorDTO> createProveedor(@RequestBody ProveedorDTO proveedorDTO) throws URISyntaxException {
        log.debug("REST request to save Proveedor : {}", proveedorDTO);
        if (proveedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new proveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProveedorDTO result = proveedorService.save(proveedorDTO);
        return ResponseEntity.created(new URI("/api/proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proveedors} : Updates an existing proveedor.
     *
     * @param proveedorDTO the proveedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proveedorDTO,
     * or with status {@code 400 (Bad Request)} if the proveedorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proveedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proveedors")
    public ResponseEntity<ProveedorDTO> updateProveedor(@RequestBody ProveedorDTO proveedorDTO) throws URISyntaxException {
        log.debug("REST request to update Proveedor : {}", proveedorDTO);
        if (proveedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProveedorDTO result = proveedorService.save(proveedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proveedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proveedors} : get all the proveedors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proveedors in body.
     */
    @GetMapping("/proveedors")
    public ResponseEntity<List<ProveedorDTO>> getAllProveedors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Proveedors");
        Page<ProveedorDTO> page = proveedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proveedors/:id} : get the "id" proveedor.
     *
     * @param id the id of the proveedorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proveedorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proveedors/{id}")
    public ResponseEntity<ProveedorDTO> getProveedor(@PathVariable Long id) {
        log.debug("REST request to get Proveedor : {}", id);
        Optional<ProveedorDTO> proveedorDTO = proveedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proveedorDTO);
    }

    /**
     * {@code DELETE  /proveedors/:id} : delete the "id" proveedor.
     *
     * @param id the id of the proveedorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proveedors/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        log.debug("REST request to delete Proveedor : {}", id);
        proveedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
