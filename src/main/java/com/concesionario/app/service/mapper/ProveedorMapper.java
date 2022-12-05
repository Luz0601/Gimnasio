package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proveedor} and its DTO {@link ProveedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {



    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
