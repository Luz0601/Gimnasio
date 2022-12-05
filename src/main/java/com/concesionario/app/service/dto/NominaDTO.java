package com.concesionario.app.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.concesionario.app.domain.enumeration.TipoNomina;

/**
 * A DTO for the {@link com.concesionario.app.domain.Nomina} entity.
 */
public class NominaDTO implements Serializable {

    private Long id;

    private String iban;

    private TipoNomina tipoContrato;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public TipoNomina getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoNomina tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NominaDTO nominaDTO = (NominaDTO) o;
        if (nominaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nominaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NominaDTO{" +
            "id=" + getId() +
            ", iban='" + getIban() + "'" +
            ", tipoContrato='" + getTipoContrato() + "'" +
            "}";
    }
}
