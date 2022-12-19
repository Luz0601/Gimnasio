package com.concesionario.app.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class HorarioClaseDTO implements Serializable {

    private Long claseId;

    private String claseNombre;

    private Long monitorId;

    private String monitorNombre;

    private Timestamp inicio;

    private Timestamp fin;

    private ClienteDTO[] clientes;

    private Integer numClientes;

    private Integer maxClientes;

    public HorarioClaseDTO() {

    }

    public Long getClaseId() {
        return claseId;
    }

    public void setClaseId(Long claseId) {
        this.claseId = claseId;
    }

    public String getClaseNombre() {
        return claseNombre;
    }

    public void setClaseNombre(String claseNombre) {
        this.claseNombre = claseNombre;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorNombre() {
        return monitorNombre;
    }

    public void setMonitorNombre(String monitorNombre) {
        this.monitorNombre = monitorNombre;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }

    public ClienteDTO[] getClientes() {
        return clientes;
    }

    public void setClientes(ClienteDTO[] clientes) {
        this.clientes = clientes;
    }

    public Integer getNumClientes() {
        return numClientes;
    }

    public void setNumClientes(Integer numClientes) {
        this.numClientes = numClientes;
    }

    public Integer getMaxClientes() {
        return maxClientes;
    }

    public void setMaxClientes(Integer maxClientes) {
        this.maxClientes = maxClientes;
    }


}
