package com.concesionario.app.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.concesionario.app.domain.Empleado} entity.
 */
public class EmpleadoDTO implements Serializable {

    private Long id;

    @NotNull
    private String dni;

    private String nombre;

    private String apellido;

    private Integer telefono;

    private LocalDate fechaNacimiento;

    
    private String email;

    private String direccion;

    private Integer diasVacaciones;

    private String especialidad;


    private Long nominaId;

    private Long puestoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getDiasVacaciones() {
        return diasVacaciones;
    }

    public void setDiasVacaciones(Integer diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Long getNominaId() {
        return nominaId;
    }

    public void setNominaId(Long nominaId) {
        this.nominaId = nominaId;
    }

    public Long getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(Long puestoId) {
        this.puestoId = puestoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpleadoDTO empleadoDTO = (EmpleadoDTO) o;
        if (empleadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" +
            "id=" + getId() +
            ", dni='" + getDni() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", telefono=" + getTelefono() +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", email='" + getEmail() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", diasVacaciones=" + getDiasVacaciones() +
            ", especialidad='" + getEspecialidad() + "'" +
            ", nomina=" + getNominaId() +
            ", puesto=" + getPuestoId() +
            "}";
    }
}
