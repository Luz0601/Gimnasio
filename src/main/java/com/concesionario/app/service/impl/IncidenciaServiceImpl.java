package com.concesionario.app.service.impl;

import com.concesionario.app.service.IncidenciaService;
import com.concesionario.app.domain.Incidencia;
import com.concesionario.app.repository.IncidenciaRepository;
import com.concesionario.app.service.dto.IncidenciaDTO;
import com.concesionario.app.service.mapper.IncidenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Incidencia}.
 */
@Service
@Transactional
public class IncidenciaServiceImpl implements IncidenciaService {

    private final Logger log = LoggerFactory.getLogger(IncidenciaServiceImpl.class);

    private final IncidenciaRepository incidenciaRepository;

    private final IncidenciaMapper incidenciaMapper;

    public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository, IncidenciaMapper incidenciaMapper) {
        this.incidenciaRepository = incidenciaRepository;
        this.incidenciaMapper = incidenciaMapper;
    }

    /**
     * Save a incidencia.
     *
     * @param incidenciaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IncidenciaDTO save(IncidenciaDTO incidenciaDTO) {
        log.debug("Request to save Incidencia : {}", incidenciaDTO);
        Incidencia incidencia = incidenciaMapper.toEntity(incidenciaDTO);
        incidencia = incidenciaRepository.save(incidencia);
        return incidenciaMapper.toDto(incidencia);
    }

    /**
     * Get all the incidencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncidenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidencias");
        return incidenciaRepository.findAll(pageable)
            .map(incidenciaMapper::toDto);
    }


    /**
     * Get one incidencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IncidenciaDTO> findOne(Long id) {
        log.debug("Request to get Incidencia : {}", id);
        return incidenciaRepository.findById(id)
            .map(incidenciaMapper::toDto);
    }

    /**
     * Delete the incidencia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incidencia : {}", id);
        incidenciaRepository.deleteById(id);
    }
}
