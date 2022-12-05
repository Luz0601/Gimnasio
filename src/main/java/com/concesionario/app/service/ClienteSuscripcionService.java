package com.concesionario.app.service;

import com.concesionario.app.service.dto.ClienteSuscripcionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.ClienteSuscripcion}.
 */
public interface ClienteSuscripcionService {

    /**
     * Save a clienteSuscripcion.
     *
     * @param clienteSuscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    ClienteSuscripcionDTO save(ClienteSuscripcionDTO clienteSuscripcionDTO);

    /**
     * Get all the clienteSuscripcions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteSuscripcionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clienteSuscripcion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClienteSuscripcionDTO> findOne(Long id);

    /**
     * Delete the "id" clienteSuscripcion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
