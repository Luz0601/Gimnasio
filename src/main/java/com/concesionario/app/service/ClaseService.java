package com.concesionario.app.service;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.service.dto.ClaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Clase}.
 */
public interface ClaseService {

    /**
     * Save a clase.
     *
     * @param claseDTO the entity to save.
     * @return the persisted entity.
     */
    ClaseDTO save(ClaseDTO claseDTO);

    /**
     * Get all the clases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClaseDTO> findOne(Long id);

    /**
     * Delete the "id" clase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the clases between 2 dates.
     *
     * @param minDate the lowest date.
     * @param maxDate the highest date
     * @return the list of entities.
     */
    Optional<List<Clase>> findAllBetweenDates(Timestamp minDate, Timestamp maxDate);
}
