package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.ClaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clase} and its DTO {@link ClaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpleadoMapper.class})
public interface ClaseMapper extends EntityMapper<ClaseDTO, Clase> {

    @Mapping(source = "monitor.id", target = "monitorId")
    ClaseDTO toDto(Clase clase);

    @Mapping(source = "monitorId", target = "monitor")
    Clase toEntity(ClaseDTO claseDTO);

    default Clase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clase clase = new Clase();
        clase.setId(id);
        return clase;
    }
}
