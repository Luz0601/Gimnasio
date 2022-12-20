package com.concesionario.app.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.concesionario.app.domain.ClaseCliente;
import com.concesionario.app.service.dto.ClaseDTO;
import com.concesionario.app.service.dto.HorarioClaseDTO;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ClaseMapper.class})
public interface HorarioClaseMapper extends EntityMapper<HorarioClaseDTO, ClaseCliente> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "clase.id", target = "claseId")
    HorarioClaseDTO toHorarioClaseDTO(ClaseCliente claseCliente);

    default ClaseCliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClaseCliente claseCliente = new ClaseCliente();
        claseCliente.setId(id);
        return claseCliente;
    }

}
