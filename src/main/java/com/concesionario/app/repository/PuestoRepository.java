package com.concesionario.app.repository;

import com.concesionario.app.domain.Puesto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Puesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {

}
