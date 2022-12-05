package com.concesionario.app.repository;

import com.concesionario.app.domain.Suscripcion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Suscripcion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

}
