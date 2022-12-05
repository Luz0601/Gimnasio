package com.concesionario.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Vacaciones.
 */
@Entity
@Table(name = "vacaciones")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vacaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inicio")
    private LocalDate inicio;

    @Column(name = "fin")
    private LocalDate fin;

    @OneToOne
    @JoinColumn(unique = true)
    private Empleado empleado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public Vacaciones inicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public Vacaciones fin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Vacaciones empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vacaciones)) {
            return false;
        }
        return id != null && id.equals(((Vacaciones) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vacaciones{" +
            "id=" + getId() +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            "}";
    }
}
