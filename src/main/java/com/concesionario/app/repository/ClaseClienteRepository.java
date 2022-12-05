package com.concesionario.app.repository;

import com.concesionario.app.domain.ClaseCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClaseCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseClienteRepository extends JpaRepository<ClaseCliente, Long> {

}
