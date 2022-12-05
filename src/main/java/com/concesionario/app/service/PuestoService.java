package com.concesionario.app.service;

import com.concesionario.app.service.dto.PuestoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Puesto}.
 */
public interface PuestoService {

    /**
     * Save a puesto.
     *
     * @param puestoDTO the entity to save.
     * @return the persisted entity.
     */
    PuestoDTO save(PuestoDTO puestoDTO);

    /**
     * Get all the puestos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PuestoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" puesto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PuestoDTO> findOne(Long id);

    /**
     * Delete the "id" puesto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
