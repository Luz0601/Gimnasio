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
 * A ClienteSuscripcion.
 */
@Entity
@Table(name = "cliente_suscripcion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClienteSuscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ultimo_pago", nullable = false)
    private LocalDate ultimoPago;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("clienteSuscripcions")
    private Suscripcion suscripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUltimoPago() {
        return ultimoPago;
    }

    public ClienteSuscripcion ultimoPago(LocalDate ultimoPago) {
        this.ultimoPago = ultimoPago;
        return this;
    }

    public void setUltimoPago(LocalDate ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public ClienteSuscripcion metodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ClienteSuscripcion cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public ClienteSuscripcion suscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
        return this;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteSuscripcion)) {
            return false;
        }
        return id != null && id.equals(((ClienteSuscripcion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClienteSuscripcion{" +
            "id=" + getId() +
            ", ultimoPago='" + getUltimoPago() + "'" +
            ", metodoPago='" + getMetodoPago() + "'" +
            "}";
    }
}
