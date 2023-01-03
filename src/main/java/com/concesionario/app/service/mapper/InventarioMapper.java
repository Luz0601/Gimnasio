package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.InventarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventario} and its DTO {@link InventarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class})
public interface InventarioMapper extends EntityMapper<InventarioDTO, Inventario> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "proveedor.nombre", target = "proveedorNombre")
    InventarioDTO toDto(Inventario inventario);

    @Mapping(source = "proveedorId", target = "proveedor")
    Inventario toEntity(InventarioDTO inventarioDTO);

    default Inventario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inventario inventario = new Inventario();
        inventario.setId(id);
        return inventario;
    }
}
