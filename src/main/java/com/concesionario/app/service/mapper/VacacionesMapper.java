package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.VacacionesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vacaciones} and its DTO {@link VacacionesDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpleadoMapper.class})
public interface VacacionesMapper extends EntityMapper<VacacionesDTO, Vacaciones> {

    @Mapping(source = "empleado.id", target = "empleadoId")
    VacacionesDTO toDto(Vacaciones vacaciones);

    @Mapping(source = "empleadoId", target = "empleado")
    Vacaciones toEntity(VacacionesDTO vacacionesDTO);

    default Vacaciones fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vacaciones vacaciones = new Vacaciones();
        vacaciones.setId(id);
        return vacaciones;
    }
}
