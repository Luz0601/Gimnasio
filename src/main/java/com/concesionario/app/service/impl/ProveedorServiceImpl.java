package com.concesionario.app.service.impl;

import com.concesionario.app.service.ProveedorService;
import com.concesionario.app.domain.Proveedor;
import com.concesionario.app.repository.ProveedorRepository;
import com.concesionario.app.service.dto.ProveedorDTO;
import com.concesionario.app.service.mapper.ProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Proveedor}.
 */
@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    private final ProveedorRepository proveedorRepository;

    private final ProveedorMapper proveedorMapper;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
    }

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProveedorDTO save(ProveedorDTO proveedorDTO) {
        log.debug("Request to save Proveedor : {}", proveedorDTO);
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(proveedor);
    }

    /**
     * Get all the proveedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proveedors");
        return proveedorRepository.findAll(pageable)
            .map(proveedorMapper::toDto);
    }


    /**
     * Get one proveedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProveedorDTO> findOne(Long id) {
        log.debug("Request to get Proveedor : {}", id);
        return proveedorRepository.findById(id)
            .map(proveedorMapper::toDto);
    }

    /**
     * Delete the proveedor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proveedor : {}", id);
        proveedorRepository.deleteById(id);
    }
}
