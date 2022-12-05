package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.PuestoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Puesto} and its DTO {@link PuestoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PuestoMapper extends EntityMapper<PuestoDTO, Puesto> {



    default Puesto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Puesto puesto = new Puesto();
        puesto.setId(id);
        return puesto;
    }
}
