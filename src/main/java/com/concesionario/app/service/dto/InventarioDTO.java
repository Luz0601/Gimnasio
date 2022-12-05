package com.concesionario.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.Inventario} entity.
 */
public class InventarioDTO implements Serializable {

    private Long id;

    private String ref;

    private String nombre;

    private String descripcion;

    private Integer cantidad;

    private Boolean estado;

    private LocalDate ultRevision;

    private Integer periodoRevision;


    private Long proveedorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getUltRevision() {
        return ultRevision;
    }

    public void setUltRevision(LocalDate ultRevision) {
        this.ultRevision = ultRevision;
    }

    public Integer getPeriodoRevision() {
        return periodoRevision;
    }

    public void setPeriodoRevision(Integer periodoRevision) {
        this.periodoRevision = periodoRevision;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventarioDTO inventarioDTO = (InventarioDTO) o;
        if (inventarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventarioDTO{" +
            "id=" + getId() +
            ", ref='" + getRef() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad=" + getCantidad() +
            ", estado='" + isEstado() + "'" +
            ", ultRevision='" + getUltRevision() + "'" +
            ", periodoRevision=" + getPeriodoRevision() +
            ", proveedor=" + getProveedorId() +
            "}";
    }
}
