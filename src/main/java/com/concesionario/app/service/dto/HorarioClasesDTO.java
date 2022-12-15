package com.concesionario.app.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HorarioClasesDTO implements Serializable {

    private Long claseId;

    private String claseNombre;

    private Long monitorId;

    private String monitorNombre;

    private LocalDateTime inicio;

    private LocalDateTime fin;

    private ClienteDTO[] clientes;

    private Integer numClientes;

    private Integer maxClientes;


}
