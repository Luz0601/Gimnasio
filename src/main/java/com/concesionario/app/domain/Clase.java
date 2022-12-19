package com.concesionario.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "clase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "lugar")
    private String lugar;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private Timestamp inicio;

    @NotNull
    @Column(name = "fin", nullable = false)
    private Timestamp fin;

    @Column(name = "incidencias")
    private Boolean incidencias;

    @OneToOne
    @JoinColumn(unique = true)
    private Empleado monitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Clase nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Clase descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public Clase lugar(String lugar) {
        this.lugar = lugar;
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public Clase inicio(Timestamp inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public Timestamp getFin() {
        return fin;
    }

    public Clase fin(Timestamp fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }

    public Boolean isIncidencias() {
        return incidencias;
    }

    public Clase incidencias(Boolean incidencias) {
        this.incidencias = incidencias;
        return this;
    }

    public void setIncidencias(Boolean incidencias) {
        this.incidencias = incidencias;
    }

    public Empleado getMonitor() {
        return monitor;
    }

    public Clase monitor(Empleado empleado) {
        this.monitor = empleado;
        return this;
    }

    public void setMonitor(Empleado empleado) {
        this.monitor = empleado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clase)) {
            return false;
        }
        return id != null && id.equals(((Clase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Clase{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", lugar='" + getLugar() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", incidencias='" + isIncidencias() + "'" +
            "}";
    }
}
