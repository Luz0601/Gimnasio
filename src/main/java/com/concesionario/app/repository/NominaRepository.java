package com.concesionario.app.repository;

import com.concesionario.app.domain.Nomina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nomina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NominaRepository extends JpaRepository<Nomina, Long> {

}
