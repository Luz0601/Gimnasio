package com.concesionario.app.service;

import com.concesionario.app.service.dto.IncidenciaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Incidencia}.
 */
public interface IncidenciaService {

    /**
     * Save a incidencia.
     *
     * @param incidenciaDTO the entity to save.
     * @return the persisted entity.
     */
    IncidenciaDTO save(IncidenciaDTO incidenciaDTO);

    /**
     * Get all the incidencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncidenciaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" incidencia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncidenciaDTO> findOne(Long id);

    /**
     * Delete the "id" incidencia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
