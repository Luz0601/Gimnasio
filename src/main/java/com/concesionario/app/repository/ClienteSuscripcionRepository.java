package com.concesionario.app.repository;

import com.concesionario.app.domain.ClienteSuscripcion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClienteSuscripcion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteSuscripcionRepository extends JpaRepository<ClienteSuscripcion, Long> {

}
