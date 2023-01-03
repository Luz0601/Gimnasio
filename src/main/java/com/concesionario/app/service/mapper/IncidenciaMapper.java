package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.IncidenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incidencia} and its DTO {@link IncidenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClaseMapper.class})
public interface IncidenciaMapper extends EntityMapper<IncidenciaDTO, Incidencia> {

    @Mapping(source = "clase.id", target = "claseId")
    @Mapping(source = "clase.nombre", target = "claseNombre")
    IncidenciaDTO toDto(Incidencia incidencia);

    @Mapping(source = "claseId", target = "clase")
    Incidencia toEntity(IncidenciaDTO incidenciaDTO);

    default Incidencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incidencia incidencia = new Incidencia();
        incidencia.setId(id);
        return incidencia;
    }
}
