package com.concesionario.app.service;

import com.concesionario.app.service.dto.InventarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Inventario}.
 */
public interface InventarioService {

    /**
     * Save a inventario.
     *
     * @param inventarioDTO the entity to save.
     * @return the persisted entity.
     */
    InventarioDTO save(InventarioDTO inventarioDTO);

    /**
     * Get all the inventarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InventarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" inventario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventarioDTO> findOne(Long id);

    /**
     * Delete the "id" inventario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
