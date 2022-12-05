package com.concesionario.app.service.impl;

import com.concesionario.app.service.ClaseClienteService;
import com.concesionario.app.domain.ClaseCliente;
import com.concesionario.app.repository.ClaseClienteRepository;
import com.concesionario.app.service.dto.ClaseClienteDTO;
import com.concesionario.app.service.mapper.ClaseClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClaseCliente}.
 */
@Service
@Transactional
public class ClaseClienteServiceImpl implements ClaseClienteService {

    private final Logger log = LoggerFactory.getLogger(ClaseClienteServiceImpl.class);

    private final ClaseClienteRepository claseClienteRepository;

    private final ClaseClienteMapper claseClienteMapper;

    public ClaseClienteServiceImpl(ClaseClienteRepository claseClienteRepository, ClaseClienteMapper claseClienteMapper) {
        this.claseClienteRepository = claseClienteRepository;
        this.claseClienteMapper = claseClienteMapper;
    }

    /**
     * Save a claseCliente.
     *
     * @param claseClienteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClaseClienteDTO save(ClaseClienteDTO claseClienteDTO) {
        log.debug("Request to save ClaseCliente : {}", claseClienteDTO);
        ClaseCliente claseCliente = claseClienteMapper.toEntity(claseClienteDTO);
        claseCliente = claseClienteRepository.save(claseCliente);
        return claseClienteMapper.toDto(claseCliente);
    }

    /**
     * Get all the claseClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClaseClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClaseClientes");
        return claseClienteRepository.findAll(pageable)
            .map(claseClienteMapper::toDto);
    }


    /**
     * Get one claseCliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClaseClienteDTO> findOne(Long id) {
        log.debug("Request to get ClaseCliente : {}", id);
        return claseClienteRepository.findById(id)
            .map(claseClienteMapper::toDto);
    }

    /**
     * Delete the claseCliente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClaseCliente : {}", id);
        claseClienteRepository.deleteById(id);
    }
}
