package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.SuscripcionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suscripcion} and its DTO {@link SuscripcionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SuscripcionMapper extends EntityMapper<SuscripcionDTO, Suscripcion> {



    default Suscripcion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setId(id);
        return suscripcion;
    }
}
