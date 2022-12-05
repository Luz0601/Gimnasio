package com.concesionario.app.repository;

import com.concesionario.app.domain.Vacaciones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vacaciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VacacionesRepository extends JpaRepository<Vacaciones, Long> {

}
