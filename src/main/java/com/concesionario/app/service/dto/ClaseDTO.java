package com.concesionario.app.service.dto;
import io.swagger.annotations.ApiModel;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.Clase} entity.
 */
@ApiModel(description = "not an ignored comment")
public class ClaseDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String descripcion;

    private String lugar;

    @NotNull
    private Instant inicio;

    @NotNull
    private Instant fin;

    private Boolean incidencias;


    private Long monitorId;


    private IncidenciaDTO incidencia;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Instant getInicio() {
        return inicio;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFin() {
        return fin;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Boolean isIncidencias() {
        return incidencias;
    }

    public void setIncidencias(Boolean incidencias) {
        this.incidencias = incidencias;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long empleadoId) {
        this.monitorId = empleadoId;
    }

    public IncidenciaDTO getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(IncidenciaDTO incidencia) {
        this.incidencia = incidencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClaseDTO claseDTO = (ClaseDTO) o;
        if (claseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), claseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClaseDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", lugar='" + getLugar() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", incidencias='" + isIncidencias() + "'" +
            ", monitor=" + getMonitorId() +
            "}";
    }
}
