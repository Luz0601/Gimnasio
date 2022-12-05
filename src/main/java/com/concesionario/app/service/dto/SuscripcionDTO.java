package com.concesionario.app.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.concesionario.app.domain.enumeration.PeriodoSuscripcion;

/**
 * A DTO for the {@link com.concesionario.app.domain.Suscripcion} entity.
 */
public class SuscripcionDTO implements Serializable {

    private Long id;

    private Double precio;

    private PeriodoSuscripcion periodo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public PeriodoSuscripcion getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoSuscripcion periodo) {
        this.periodo = periodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuscripcionDTO suscripcionDTO = (SuscripcionDTO) o;
        if (suscripcionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suscripcionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SuscripcionDTO{" +
            "id=" + getId() +
            ", precio=" + getPrecio() +
            ", periodo='" + getPeriodo() + "'" +
            "}";
    }
}
