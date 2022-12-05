package com.concesionario.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.ClaseCliente} entity.
 */
public class ClaseClienteDTO implements Serializable {

    private Long id;


    private Long clienteId;

    private Long claseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getClaseId() {
        return claseId;
    }

    public void setClaseId(Long claseId) {
        this.claseId = claseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClaseClienteDTO claseClienteDTO = (ClaseClienteDTO) o;
        if (claseClienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), claseClienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClaseClienteDTO{" +
            "id=" + getId() +
            ", cliente=" + getClienteId() +
            ", clase=" + getClaseId() +
            "}";
    }
}
