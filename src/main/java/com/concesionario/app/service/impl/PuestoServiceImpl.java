package com.concesionario.app.service.impl;

import com.concesionario.app.service.PuestoService;
import com.concesionario.app.domain.Puesto;
import com.concesionario.app.repository.PuestoRepository;
import com.concesionario.app.service.dto.PuestoDTO;
import com.concesionario.app.service.mapper.PuestoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Puesto}.
 */
@Service
@Transactional
public class PuestoServiceImpl implements PuestoService {

    private final Logger log = LoggerFactory.getLogger(PuestoServiceImpl.class);

    private final PuestoRepository puestoRepository;

    private final PuestoMapper puestoMapper;

    public PuestoServiceImpl(PuestoRepository puestoRepository, PuestoMapper puestoMapper) {
        this.puestoRepository = puestoRepository;
        this.puestoMapper = puestoMapper;
    }

    /**
     * Save a puesto.
     *
     * @param puestoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PuestoDTO save(PuestoDTO puestoDTO) {
        log.debug("Request to save Puesto : {}", puestoDTO);
        Puesto puesto = puestoMapper.toEntity(puestoDTO);
        puesto = puestoRepository.save(puesto);
        return puestoMapper.toDto(puesto);
    }

    /**
     * Get all the puestos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PuestoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Puestos");
        return puestoRepository.findAll(pageable)
            .map(puestoMapper::toDto);
    }


    /**
     * Get one puesto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PuestoDTO> findOne(Long id) {
        log.debug("Request to get Puesto : {}", id);
        return puestoRepository.findById(id)
            .map(puestoMapper::toDto);
    }

    /**
     * Delete the puesto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Puesto : {}", id);
        puestoRepository.deleteById(id);
    }
}
