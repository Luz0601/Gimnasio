package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.NominaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nomina} and its DTO {@link NominaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NominaMapper extends EntityMapper<NominaDTO, Nomina> {



    default Nomina fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nomina nomina = new Nomina();
        nomina.setId(id);
        return nomina;
    }
}
