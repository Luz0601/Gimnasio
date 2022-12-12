package com.concesionario.app.service.impl;

import com.concesionario.app.service.ClaseService;
import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.Incidencia;
import com.concesionario.app.repository.ClaseRepository;
import com.concesionario.app.service.dto.ClaseDTO;
import com.concesionario.app.service.mapper.ClaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Clase}.
 */
@Service
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private final Logger log = LoggerFactory.getLogger(ClaseServiceImpl.class);

    private final ClaseRepository claseRepository;

    private final ClaseMapper claseMapper;

    public ClaseServiceImpl(ClaseRepository claseRepository, ClaseMapper claseMapper) {
        this.claseRepository = claseRepository;
        this.claseMapper = claseMapper;
    }

    /**
     * Save a clase.
     *
     * @param claseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClaseDTO save(ClaseDTO claseDTO) {
        log.debug("Request to save Clase : {}", claseDTO);
        Clase clase = claseMapper.toEntity(claseDTO);
        clase = claseRepository.save(clase);
        return claseMapper.toDto(clase);
    }

    /**
     * Get all the clases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clases");
        return claseRepository.findAll(pageable)
            .map(claseMapper::toDto).map(this::getIncidenciaId);
    }


    /**
     * Get one clase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClaseDTO> findOne(Long id) {
        log.debug("Request to get Clase : {}", id);
        return claseRepository.findById(id)
            .map(claseMapper::toDto);
    }

    /**
     * Delete the clase by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clase : {}", id);
        claseRepository.deleteById(id);
    }

    private ClaseDTO getIncidenciaId(ClaseDTO claseDTO) {
        Incidencia incidencia = claseRepository.getIncidencia(claseMapper.toEntity(claseDTO));
        if (incidencia != null)
            claseDTO.setIncidenciaId(incidencia.getId());

        return claseDTO;
    }
}
