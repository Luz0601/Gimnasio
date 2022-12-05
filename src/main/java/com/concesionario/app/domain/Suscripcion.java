package com.concesionario.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.concesionario.app.domain.enumeration.PeriodoSuscripcion;

/**
 * A Suscripcion.
 */
@Entity
@Table(name = "suscripcion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio")
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodo")
    private PeriodoSuscripcion periodo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public Suscripcion precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public PeriodoSuscripcion getPeriodo() {
        return periodo;
    }

    public Suscripcion periodo(PeriodoSuscripcion periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(PeriodoSuscripcion periodo) {
        this.periodo = periodo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suscripcion)) {
            return false;
        }
        return id != null && id.equals(((Suscripcion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Suscripcion{" +
            "id=" + getId() +
            ", precio=" + getPrecio() +
            ", periodo='" + getPeriodo() + "'" +
            "}";
    }
}
