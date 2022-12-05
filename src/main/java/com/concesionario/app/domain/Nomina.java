package com.concesionario.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.concesionario.app.domain.enumeration.TipoNomina;

/**
 * A Nomina.
 */
@Entity
@Table(name = "nomina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nomina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iban")
    private String iban;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato")
    private TipoNomina tipoContrato;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public Nomina iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public TipoNomina getTipoContrato() {
        return tipoContrato;
    }

    public Nomina tipoContrato(TipoNomina tipoContrato) {
        this.tipoContrato = tipoContrato;
        return this;
    }

    public void setTipoContrato(TipoNomina tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nomina)) {
            return false;
        }
        return id != null && id.equals(((Nomina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nomina{" +
            "id=" + getId() +
            ", iban='" + getIban() + "'" +
            ", tipoContrato='" + getTipoContrato() + "'" +
            "}";
    }
}
