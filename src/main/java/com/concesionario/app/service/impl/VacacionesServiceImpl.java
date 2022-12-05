package com.concesionario.app.service.impl;

import com.concesionario.app.service.VacacionesService;
import com.concesionario.app.domain.Vacaciones;
import com.concesionario.app.repository.VacacionesRepository;
import com.concesionario.app.service.dto.VacacionesDTO;
import com.concesionario.app.service.mapper.VacacionesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Vacaciones}.
 */
@Service
@Transactional
public class VacacionesServiceImpl implements VacacionesService {

    private final Logger log = LoggerFactory.getLogger(VacacionesServiceImpl.class);

    private final VacacionesRepository vacacionesRepository;

    private final VacacionesMapper vacacionesMapper;

    public VacacionesServiceImpl(VacacionesRepository vacacionesRepository, VacacionesMapper vacacionesMapper) {
        this.vacacionesRepository = vacacionesRepository;
        this.vacacionesMapper = vacacionesMapper;
    }

    /**
     * Save a vacaciones.
     *
     * @param vacacionesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VacacionesDTO save(VacacionesDTO vacacionesDTO) {
        log.debug("Request to save Vacaciones : {}", vacacionesDTO);
        Vacaciones vacaciones = vacacionesMapper.toEntity(vacacionesDTO);
        vacaciones = vacacionesRepository.save(vacaciones);
        return vacacionesMapper.toDto(vacaciones);
    }

    /**
     * Get all the vacaciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VacacionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vacaciones");
        return vacacionesRepository.findAll(pageable)
            .map(vacacionesMapper::toDto);
    }


    /**
     * Get one vacaciones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VacacionesDTO> findOne(Long id) {
        log.debug("Request to get Vacaciones : {}", id);
        return vacacionesRepository.findById(id)
            .map(vacacionesMapper::toDto);
    }

    /**
     * Delete the vacaciones by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vacaciones : {}", id);
        vacacionesRepository.deleteById(id);
    }
}
