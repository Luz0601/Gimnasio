package com.concesionario.app.service;

import com.concesionario.app.service.dto.SuscripcionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Suscripcion}.
 */
public interface SuscripcionService {

    /**
     * Save a suscripcion.
     *
     * @param suscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    SuscripcionDTO save(SuscripcionDTO suscripcionDTO);

    /**
     * Get all the suscripcions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SuscripcionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" suscripcion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SuscripcionDTO> findOne(Long id);

    /**
     * Delete the "id" suscripcion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
