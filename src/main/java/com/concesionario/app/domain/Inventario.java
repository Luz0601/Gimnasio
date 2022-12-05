package com.concesionario.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Inventario.
 */
@Entity
@Table(name = "inventario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_ref")
    private String ref;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "ult_revision")
    private LocalDate ultRevision;

    @Column(name = "periodo_revision")
    private Integer periodoRevision;

    @ManyToOne
    @JsonIgnoreProperties("inventarios")
    private Proveedor proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public Inventario ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNombre() {
        return nombre;
    }

    public Inventario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Inventario descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Inventario cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Inventario estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getUltRevision() {
        return ultRevision;
    }

    public Inventario ultRevision(LocalDate ultRevision) {
        this.ultRevision = ultRevision;
        return this;
    }

    public void setUltRevision(LocalDate ultRevision) {
        this.ultRevision = ultRevision;
    }

    public Integer getPeriodoRevision() {
        return periodoRevision;
    }

    public Inventario periodoRevision(Integer periodoRevision) {
        this.periodoRevision = periodoRevision;
        return this;
    }

    public void setPeriodoRevision(Integer periodoRevision) {
        this.periodoRevision = periodoRevision;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Inventario proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventario)) {
            return false;
        }
        return id != null && id.equals(((Inventario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inventario{" +
            "id=" + getId() +
            ", ref='" + getRef() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad=" + getCantidad() +
            ", estado='" + isEstado() + "'" +
            ", ultRevision='" + getUltRevision() + "'" +
            ", periodoRevision=" + getPeriodoRevision() +
            "}";
    }
}
