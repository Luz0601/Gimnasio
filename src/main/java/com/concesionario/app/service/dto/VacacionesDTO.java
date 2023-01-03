package com.concesionario.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.Vacaciones} entity.
 */
public class VacacionesDTO implements Serializable {

    private Long id;

    private LocalDate inicio;

    private LocalDate fin;


    private Long empleadoId;

    private String empleadoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VacacionesDTO vacacionesDTO = (VacacionesDTO) o;
        if (vacacionesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vacacionesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VacacionesDTO{" +
            "id=" + getId() +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", empleado=" + getEmpleadoId() +
            "}";
    }
}
