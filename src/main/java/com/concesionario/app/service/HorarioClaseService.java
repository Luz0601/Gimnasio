package com.concesionario.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.concesionario.app.service.dto.HorarioClaseDTO;

public interface HorarioClaseService {

    public List<HorarioClaseDTO> findAll(String date, String month, String week, String day);

}
