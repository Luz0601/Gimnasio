package com.concesionario.app.service.impl;

import com.concesionario.app.service.NominaService;
import com.concesionario.app.domain.Nomina;
import com.concesionario.app.repository.NominaRepository;
import com.concesionario.app.service.dto.NominaDTO;
import com.concesionario.app.service.mapper.NominaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Nomina}.
 */
@Service
@Transactional
public class NominaServiceImpl implements NominaService {

    private final Logger log = LoggerFactory.getLogger(NominaServiceImpl.class);

    private final NominaRepository nominaRepository;

    private final NominaMapper nominaMapper;

    public NominaServiceImpl(NominaRepository nominaRepository, NominaMapper nominaMapper) {
        this.nominaRepository = nominaRepository;
        this.nominaMapper = nominaMapper;
    }

    /**
     * Save a nomina.
     *
     * @param nominaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NominaDTO save(NominaDTO nominaDTO) {
        log.debug("Request to save Nomina : {}", nominaDTO);
        Nomina nomina = nominaMapper.toEntity(nominaDTO);
        nomina = nominaRepository.save(nomina);
        return nominaMapper.toDto(nomina);
    }

    /**
     * Get all the nominas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NominaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nominas");
        return nominaRepository.findAll(pageable)
            .map(nominaMapper::toDto);
    }


    /**
     * Get one nomina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NominaDTO> findOne(Long id) {
        log.debug("Request to get Nomina : {}", id);
        return nominaRepository.findById(id)
            .map(nominaMapper::toDto);
    }

    /**
     * Delete the nomina by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nomina : {}", id);
        nominaRepository.deleteById(id);
    }
}
