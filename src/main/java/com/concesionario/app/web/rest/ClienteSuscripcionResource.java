package com.concesionario.app.web.rest;

import com.concesionario.app.service.ClienteSuscripcionService;
import com.concesionario.app.web.rest.errors.BadRequestAlertException;
import com.concesionario.app.service.dto.ClienteSuscripcionDTO;

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
 * REST controller for managing {@link com.concesionario.app.domain.ClienteSuscripcion}.
 */
@RestController
@RequestMapping("/api")
public class ClienteSuscripcionResource {

    private final Logger log = LoggerFactory.getLogger(ClienteSuscripcionResource.class);

    private static final String ENTITY_NAME = "clienteSuscripcion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteSuscripcionService clienteSuscripcionService;

    public ClienteSuscripcionResource(ClienteSuscripcionService clienteSuscripcionService) {
        this.clienteSuscripcionService = clienteSuscripcionService;
    }

    /**
     * {@code POST  /cliente-suscripcions} : Create a new clienteSuscripcion.
     *
     * @param clienteSuscripcionDTO the clienteSuscripcionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clienteSuscripcionDTO, or with status {@code 400 (Bad Request)} if the clienteSuscripcion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cliente-suscripcions")
    public ResponseEntity<ClienteSuscripcionDTO> createClienteSuscripcion(@Valid @RequestBody ClienteSuscripcionDTO clienteSuscripcionDTO) throws URISyntaxException {
        log.debug("REST request to save ClienteSuscripcion : {}", clienteSuscripcionDTO);
        if (clienteSuscripcionDTO.getId() != null) {
            throw new BadRequestAlertException("A new clienteSuscripcion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteSuscripcionDTO result = clienteSuscripcionService.save(clienteSuscripcionDTO);
        return ResponseEntity.created(new URI("/api/cliente-suscripcions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cliente-suscripcions} : Updates an existing clienteSuscripcion.
     *
     * @param clienteSuscripcionDTO the clienteSuscripcionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteSuscripcionDTO,
     * or with status {@code 400 (Bad Request)} if the clienteSuscripcionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clienteSuscripcionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cliente-suscripcions")
    public ResponseEntity<ClienteSuscripcionDTO> updateClienteSuscripcion(@Valid @RequestBody ClienteSuscripcionDTO clienteSuscripcionDTO) throws URISyntaxException {
        log.debug("REST request to update ClienteSuscripcion : {}", clienteSuscripcionDTO);
        if (clienteSuscripcionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClienteSuscripcionDTO result = clienteSuscripcionService.save(clienteSuscripcionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteSuscripcionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cliente-suscripcions} : get all the clienteSuscripcions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clienteSuscripcions in body.
     */
    @GetMapping("/cliente-suscripcions")
    public ResponseEntity<List<ClienteSuscripcionDTO>> getAllClienteSuscripcions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ClienteSuscripcions");
        Page<ClienteSuscripcionDTO> page = clienteSuscripcionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cliente-suscripcions/:id} : get the "id" clienteSuscripcion.
     *
     * @param id the id of the clienteSuscripcionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clienteSuscripcionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cliente-suscripcions/{id}")
    public ResponseEntity<ClienteSuscripcionDTO> getClienteSuscripcion(@PathVariable Long id) {
        log.debug("REST request to get ClienteSuscripcion : {}", id);
        Optional<ClienteSuscripcionDTO> clienteSuscripcionDTO = clienteSuscripcionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteSuscripcionDTO);
    }

    /**
     * {@code DELETE  /cliente-suscripcions/:id} : delete the "id" clienteSuscripcion.
     *
     * @param id the id of the clienteSuscripcionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cliente-suscripcions/{id}")
    public ResponseEntity<Void> deleteClienteSuscripcion(@PathVariable Long id) {
        log.debug("REST request to delete ClienteSuscripcion : {}", id);
        clienteSuscripcionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
