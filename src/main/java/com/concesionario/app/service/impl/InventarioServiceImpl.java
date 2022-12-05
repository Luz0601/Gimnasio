package com.concesionario.app.service.impl;

import com.concesionario.app.service.InventarioService;
import com.concesionario.app.domain.Inventario;
import com.concesionario.app.repository.InventarioRepository;
import com.concesionario.app.service.dto.InventarioDTO;
import com.concesionario.app.service.mapper.InventarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Inventario}.
 */
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);

    private final InventarioRepository inventarioRepository;

    private final InventarioMapper inventarioMapper;

    public InventarioServiceImpl(InventarioRepository inventarioRepository, InventarioMapper inventarioMapper) {
        this.inventarioRepository = inventarioRepository;
        this.inventarioMapper = inventarioMapper;
    }

    /**
     * Save a inventario.
     *
     * @param inventarioDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InventarioDTO save(InventarioDTO inventarioDTO) {
        log.debug("Request to save Inventario : {}", inventarioDTO);
        Inventario inventario = inventarioMapper.toEntity(inventarioDTO);
        inventario = inventarioRepository.save(inventario);
        return inventarioMapper.toDto(inventario);
    }

    /**
     * Get all the inventarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InventarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll(pageable)
            .map(inventarioMapper::toDto);
    }


    /**
     * Get one inventario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InventarioDTO> findOne(Long id) {
        log.debug("Request to get Inventario : {}", id);
        return inventarioRepository.findById(id)
            .map(inventarioMapper::toDto);
    }

    /**
     * Delete the inventario by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inventario : {}", id);
        inventarioRepository.deleteById(id);
    }
}
