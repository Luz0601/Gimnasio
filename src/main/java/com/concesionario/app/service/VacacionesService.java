package com.concesionario.app.service;

import com.concesionario.app.service.dto.VacacionesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Vacaciones}.
 */
public interface VacacionesService {

    /**
     * Save a vacaciones.
     *
     * @param vacacionesDTO the entity to save.
     * @return the persisted entity.
     */
    VacacionesDTO save(VacacionesDTO vacacionesDTO);

    /**
     * Get all the vacaciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VacacionesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vacaciones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VacacionesDTO> findOne(Long id);

    /**
     * Delete the "id" vacaciones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
