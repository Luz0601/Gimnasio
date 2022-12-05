package com.concesionario.app.service;

import com.concesionario.app.service.dto.NominaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Nomina}.
 */
public interface NominaService {

    /**
     * Save a nomina.
     *
     * @param nominaDTO the entity to save.
     * @return the persisted entity.
     */
    NominaDTO save(NominaDTO nominaDTO);

    /**
     * Get all the nominas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NominaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nomina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NominaDTO> findOne(Long id);

    /**
     * Delete the "id" nomina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
