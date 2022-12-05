package com.concesionario.app.repository;

import com.concesionario.app.domain.Incidencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Incidencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

}
