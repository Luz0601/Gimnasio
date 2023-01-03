package com.concesionario.app.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;

import com.concesionario.app.domain.enumeration.PeriodoSuscripcion;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.ClienteSuscripcion} entity.
 */
public class ClienteSuscripcionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate ultimoPago;

    private String metodoPago;


    private Long clienteId;

    private String clienteNombre;

    private Long suscripcionId;

    private PeriodoSuscripcion suscripcionPeriodo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(LocalDate ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(Long suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public PeriodoSuscripcion getSuscripcionPeriodo() {
        return suscripcionPeriodo;
    }

    public void setSuscripcionPeriodo(PeriodoSuscripcion suscripcionPeriodo) {
        this.suscripcionPeriodo = suscripcionPeriodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteSuscripcionDTO clienteSuscripcionDTO = (ClienteSuscripcionDTO) o;
        if (clienteSuscripcionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteSuscripcionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteSuscripcionDTO{" +
            "id=" + getId() +
            ", ultimoPago='" + getUltimoPago() + "'" +
            ", metodoPago='" + getMetodoPago() + "'" +
            ", cliente=" + getClienteId() +
            ", suscripcion=" + getSuscripcionId() +
            "}";
    }
}
