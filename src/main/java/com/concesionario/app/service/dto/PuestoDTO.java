package com.concesionario.app.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.Puesto} entity.
 */
public class PuestoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String horario;

    private Double salario;


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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PuestoDTO puestoDTO = (PuestoDTO) o;
        if (puestoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puestoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuestoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", horario='" + getHorario() + "'" +
            ", salario=" + getSalario() +
            "}";
    }
}
