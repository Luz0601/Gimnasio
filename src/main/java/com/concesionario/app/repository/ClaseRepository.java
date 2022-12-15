package com.concesionario.app.repository;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.Incidencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

}
