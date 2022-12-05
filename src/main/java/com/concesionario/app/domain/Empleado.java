package com.concesionario.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "dias_vacaciones")
    private Integer diasVacaciones;

    @Column(name = "especialidad")
    private String especialidad;

    @OneToOne
    @JoinColumn(unique = true)
    private Nomina nomina;

    @ManyToOne
    @JsonIgnoreProperties("empleados")
    private Puesto puesto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public Empleado dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public Empleado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Empleado apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Empleado telefono(Integer telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Empleado fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public Empleado email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public Empleado direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getDiasVacaciones() {
        return diasVacaciones;
    }

    public Empleado diasVacaciones(Integer diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
        return this;
    }

    public void setDiasVacaciones(Integer diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Empleado especialidad(String especialidad) {
        this.especialidad = especialidad;
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public Empleado nomina(Nomina nomina) {
        this.nomina = nomina;
        return this;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public Empleado puesto(Puesto puesto) {
        this.puesto = puesto;
        return this;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleado)) {
            return false;
        }
        return id != null && id.equals(((Empleado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empleado{" +
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
            "}";
    }
}
