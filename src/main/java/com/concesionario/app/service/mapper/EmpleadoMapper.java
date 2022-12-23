package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.EmpleadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleado} and its DTO {@link EmpleadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {NominaMapper.class, PuestoMapper.class})
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {

    @Mapping(source = "nomina.id", target = "nominaId")
    @Mapping(source = "puesto.id", target = "puestoId")
    @Mapping(source = "puesto.nombre", target = "puestoNombre")
    EmpleadoDTO toDto(Empleado empleado);

    @Mapping(source = "nominaId", target = "nomina")
    @Mapping(source = "puestoId", target = "puesto")
    Empleado toEntity(EmpleadoDTO empleadoDTO);

    default Empleado fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empleado empleado = new Empleado();
        empleado.setId(id);
        return empleado;
    }
}
