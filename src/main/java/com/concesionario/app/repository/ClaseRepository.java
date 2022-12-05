package com.concesionario.app.repository;

import com.concesionario.app.domain.Clase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

}
