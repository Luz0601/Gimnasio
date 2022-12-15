package com.concesionario.app.repository;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.Incidencia;
import com.concesionario.app.service.dto.IncidenciaDTO;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Incidencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    @Query("SELECT c FROM Incidencia c WHERE c.clase.id IS :claseId")
    Optional<Incidencia> findByClaseId(@Param("claseId") Long claseId);

        @Modifying
    @Query("UPDATE Clase c SET c.incidencias=:inBool WHERE c.id IS :claseId")
    int updateClaseIncidenciaBoolean(@Param("claseId") Long claseId, @Param("inBool") Boolean incidencias);

}
