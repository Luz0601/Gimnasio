package com.concesionario.app.service;

import com.concesionario.app.service.dto.ClaseClienteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.ClaseCliente}.
 */
public interface ClaseClienteService {

    /**
     * Save a claseCliente.
     *
     * @param claseClienteDTO the entity to save.
     * @return the persisted entity.
     */
    ClaseClienteDTO save(ClaseClienteDTO claseClienteDTO);

    /**
     * Get all the claseClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClaseClienteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" claseCliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClaseClienteDTO> findOne(Long id);

    /**
     * Delete the "id" claseCliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
