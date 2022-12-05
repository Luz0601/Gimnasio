package com.concesionario.app.service.impl;

import com.concesionario.app.service.ClienteSuscripcionService;
import com.concesionario.app.domain.ClienteSuscripcion;
import com.concesionario.app.repository.ClienteSuscripcionRepository;
import com.concesionario.app.service.dto.ClienteSuscripcionDTO;
import com.concesionario.app.service.mapper.ClienteSuscripcionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClienteSuscripcion}.
 */
@Service
@Transactional
public class ClienteSuscripcionServiceImpl implements ClienteSuscripcionService {

    private final Logger log = LoggerFactory.getLogger(ClienteSuscripcionServiceImpl.class);

    private final ClienteSuscripcionRepository clienteSuscripcionRepository;

    private final ClienteSuscripcionMapper clienteSuscripcionMapper;

    public ClienteSuscripcionServiceImpl(ClienteSuscripcionRepository clienteSuscripcionRepository, ClienteSuscripcionMapper clienteSuscripcionMapper) {
        this.clienteSuscripcionRepository = clienteSuscripcionRepository;
        this.clienteSuscripcionMapper = clienteSuscripcionMapper;
    }

    /**
     * Save a clienteSuscripcion.
     *
     * @param clienteSuscripcionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClienteSuscripcionDTO save(ClienteSuscripcionDTO clienteSuscripcionDTO) {
        log.debug("Request to save ClienteSuscripcion : {}", clienteSuscripcionDTO);
        ClienteSuscripcion clienteSuscripcion = clienteSuscripcionMapper.toEntity(clienteSuscripcionDTO);
        clienteSuscripcion = clienteSuscripcionRepository.save(clienteSuscripcion);
        return clienteSuscripcionMapper.toDto(clienteSuscripcion);
    }

    /**
     * Get all the clienteSuscripcions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteSuscripcionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteSuscripcions");
        return clienteSuscripcionRepository.findAll(pageable)
            .map(clienteSuscripcionMapper::toDto);
    }


    /**
     * Get one clienteSuscripcion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteSuscripcionDTO> findOne(Long id) {
        log.debug("Request to get ClienteSuscripcion : {}", id);
        return clienteSuscripcionRepository.findById(id)
            .map(clienteSuscripcionMapper::toDto);
    }

    /**
     * Delete the clienteSuscripcion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClienteSuscripcion : {}", id);
        clienteSuscripcionRepository.deleteById(id);
    }
}
