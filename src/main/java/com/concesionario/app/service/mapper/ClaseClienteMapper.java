package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.ClaseClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClaseCliente} and its DTO {@link ClaseClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ClaseMapper.class})
public interface ClaseClienteMapper extends EntityMapper<ClaseClienteDTO, ClaseCliente> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "clase.id", target = "claseId")
    ClaseClienteDTO toDto(ClaseCliente claseCliente);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "claseId", target = "clase")
    ClaseCliente toEntity(ClaseClienteDTO claseClienteDTO);

    default ClaseCliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClaseCliente claseCliente = new ClaseCliente();
        claseCliente.setId(id);
        return claseCliente;
    }
}
