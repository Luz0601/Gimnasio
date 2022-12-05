package com.concesionario.app.service.impl;

import com.concesionario.app.service.SuscripcionService;
import com.concesionario.app.domain.Suscripcion;
import com.concesionario.app.repository.SuscripcionRepository;
import com.concesionario.app.service.dto.SuscripcionDTO;
import com.concesionario.app.service.mapper.SuscripcionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Suscripcion}.
 */
@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    private final Logger log = LoggerFactory.getLogger(SuscripcionServiceImpl.class);

    private final SuscripcionRepository suscripcionRepository;

    private final SuscripcionMapper suscripcionMapper;

    public SuscripcionServiceImpl(SuscripcionRepository suscripcionRepository, SuscripcionMapper suscripcionMapper) {
        this.suscripcionRepository = suscripcionRepository;
        this.suscripcionMapper = suscripcionMapper;
    }

    /**
     * Save a suscripcion.
     *
     * @param suscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SuscripcionDTO save(SuscripcionDTO suscripcionDTO) {
        log.debug("Request to save Suscripcion : {}", suscripcionDTO);
        Suscripcion suscripcion = suscripcionMapper.toEntity(suscripcionDTO);
        suscripcion = suscripcionRepository.save(suscripcion);
        return suscripcionMapper.toDto(suscripcion);
    }

    /**
     * Get all the suscripcions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuscripcionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suscripcions");
        return suscripcionRepository.findAll(pageable)
            .map(suscripcionMapper::toDto);
    }


    /**
     * Get one suscripcion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SuscripcionDTO> findOne(Long id) {
        log.debug("Request to get Suscripcion : {}", id);
        return suscripcionRepository.findById(id)
            .map(suscripcionMapper::toDto);
    }

    /**
     * Delete the suscripcion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suscripcion : {}", id);
        suscripcionRepository.deleteById(id);
    }
}
