package com.concesionario.app.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.ClaseCliente;
import com.concesionario.app.repository.ClaseClienteRepository;
import com.concesionario.app.repository.ClaseRepository;
import com.concesionario.app.service.ClaseClienteService;
import com.concesionario.app.service.ClaseService;
import com.concesionario.app.service.HorarioClaseService;
import com.concesionario.app.service.dto.ClaseClienteDTO;
import com.concesionario.app.service.dto.ClaseDTO;
import com.concesionario.app.service.dto.ClienteDTO;
import com.concesionario.app.service.dto.HorarioClaseDTO;
import com.concesionario.app.service.mapper.HorarioClaseMapper;

@Service
public class HorarioClaseServiceImpl implements HorarioClaseService {

    private final Logger log = LoggerFactory.getLogger(HorarioClaseServiceImpl.class);

    private final ClaseService claseService;

    @Autowired
    private final ClaseClienteService claseClienteService;

    private final HorarioClaseMapper horarioClaseMapper;

    public HorarioClaseServiceImpl(ClaseService claseService, ClaseClienteService claseClienteService, ClaseRepository claseRepository, HorarioClaseMapper horarioClaseMapper) {
        this.claseService = claseService;
        this.claseClienteService = claseClienteService;
        this.horarioClaseMapper = horarioClaseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HorarioClaseDTO> findAll(String dateS, String monthS, String weekS, String dayS) {
        log.debug("Request to get all HorarioClase");
        String undefined = "undefined";
        Integer month;

        if (monthS.equals(undefined))
            month = null;
        //Integer month = Integer.parseInt(monthS), week = Integer.parseInt(weekS), day = Integer.parseInt(dayS);

        LocalDateTime minDate = Timestamp.valueOf(dateS).toLocalDateTime()
            .withHour(0)
            .withMinute(0)
            .withSecond(0);
        LocalDateTime maxDate;

        //yyyy-[m]m-[d]d hh:mm:ss[.f...]

        // Primer dia del mes
        minDate = minDate.withDayOfMonth(1);
        // Primer día del siguiente mes
        maxDate = minDate.plusMonths(1);


        // if (day != null && day > 0) {
        //     maxDate = minDate.plusDays(1);
        // } else if (week != null && week > 0) {
        //     int dayOfWeek = minDate.getDayOfWeek().getValue();
        //     if (dayOfWeek == 1)
        //         maxDate = minDate.plusWeeks(1);
        //     else {
        //     }
        // } else {
        // }

        Optional<List<Clase>> clases = claseService.findAllBetweenDates(Timestamp.valueOf(minDate), Timestamp.valueOf(maxDate));

        if (clases.isPresent()){
            Optional<List<ClaseCliente>> claseClientes = claseClienteService.findAllClientesFromClases(clases);

            List<HorarioClaseDTO> horarioClases = clases.map(horarioClaseMapper::toHorarioClaseDTO).get();
            if (claseClientes.isPresent()){
                horarioClases = horarioClaseMapper.toHorarioClaseclientesDTO(horarioClases, claseClientes.get());
            }
            return horarioClases;
        }

        return null;
    }

}
