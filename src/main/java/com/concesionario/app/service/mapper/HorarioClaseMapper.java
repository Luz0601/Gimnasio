package com.concesionario.app.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.ClaseCliente;
import com.concesionario.app.domain.Cliente;
import com.concesionario.app.domain.Empleado;
import com.concesionario.app.service.dto.ClienteDTO;
import com.concesionario.app.service.dto.HorarioClaseDTO;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ClaseMapper.class})
public interface HorarioClaseMapper extends EntityMapper<HorarioClaseDTO, ClaseCliente> {

    // @Mapping(source = "id", target = "claseId")
    // HorarioClaseDTO toHorarioClaseDTO(Clase clase);

    @Mapping(source = "clase.id", target = "claseId")
    HorarioClaseDTO toDto(ClaseCliente entity);

    @Mapping(source = "claseId", target = "clase")
    ClaseCliente toEntity(HorarioClaseDTO dto);

    default HorarioClaseDTO toHorarioClaseDTO(Clase clase) {
        if ( clase == null ) {
            return null;
        }

        HorarioClaseDTO horarioClaseDTO = new HorarioClaseDTO();

        horarioClaseDTO.setClaseId( clase.getId() );
        horarioClaseDTO.setClaseNombre( clase.getNombre() );
        horarioClaseDTO.setFin( clase.getFin() );
        horarioClaseDTO.setInicio( clase.getInicio() );
        horarioClaseDTO.setNumClientes( 0 );
        Empleado monitor = entityMonitor( clase );
        if (monitor != null){
            horarioClaseDTO.setMonitorId( monitor.getId() );
            horarioClaseDTO.setMonitorNombre( monitor.getNombre() );
        }

        return horarioClaseDTO;
    }

    default ClaseCliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClaseCliente claseCliente = new ClaseCliente();
        claseCliente.setId(id);
        return claseCliente;
    }

    default List<HorarioClaseDTO> toHorarioClaseDTO(List<Clase> claseList)  {
        if ( claseList == null ) {
            return null;
        }

        List<HorarioClaseDTO> list = new ArrayList<HorarioClaseDTO>( claseList.size() );
        for ( Clase clase : claseList ) {
            list.add( toHorarioClaseDTO( clase ) );
        }

        return list;
    }

    default List<HorarioClaseDTO> toHorarioClaseclientesDTO(List<HorarioClaseDTO> horarioClasesDTO, List<ClaseCliente> list) {
        List<HorarioClaseDTO> hClases = new ArrayList<HorarioClaseDTO>();

        for (HorarioClaseDTO horarioClaseDTO : horarioClasesDTO) {
            hClases.add( horarioClaseWithClientes( horarioClaseDTO, list ) );
        }
        return hClases;
    }

    default HorarioClaseDTO horarioClaseWithClientes (HorarioClaseDTO horarioClaseDTO, List<ClaseCliente> list) {
        List<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
        ClienteMapper clienteMapper = new ClienteMapperImpl();

        for (ClaseCliente claseCliente: list) {
            if (claseCliente.getClase().getId() == horarioClaseDTO.getClaseId())
                clientes.add( clienteMapper.toDto( claseCliente.getCliente() ));
        }

        if (!clientes.isEmpty())
            horarioClaseDTO.setClientes( clientes.toArray( new ClienteDTO[clientes.size()] ) );

        horarioClaseDTO.setNumClientes(clientes.size());

        return horarioClaseDTO;
    }

    default Empleado entityMonitor(Clase clase) {
        if ( clase == null ) {
            return null;
        }
        Empleado monitor = clase.getMonitor();
        if ( monitor == null ) {
            return null;
        }
        return monitor;
    }

}
