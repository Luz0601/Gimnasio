package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.*;
import com.concesionario.app.service.dto.ClienteSuscripcionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClienteSuscripcion} and its DTO {@link ClienteSuscripcionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, SuscripcionMapper.class})
public interface ClienteSuscripcionMapper extends EntityMapper<ClienteSuscripcionDTO, ClienteSuscripcion> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "suscripcion.id", target = "suscripcionId")
    ClienteSuscripcionDTO toDto(ClienteSuscripcion clienteSuscripcion);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "suscripcionId", target = "suscripcion")
    ClienteSuscripcion toEntity(ClienteSuscripcionDTO clienteSuscripcionDTO);

    default ClienteSuscripcion fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClienteSuscripcion clienteSuscripcion = new ClienteSuscripcion();
        clienteSuscripcion.setId(id);
        return clienteSuscripcion;
    }
}
