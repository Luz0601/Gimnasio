package com.concesionario.app.repository;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.Incidencia;
import com.concesionario.app.service.dto.ClaseDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("SELECT c FROM Clase c WHERE c.inicio>:minDate AND c.inicio<:maxDate")
    Optional<List<Clase>> findAllBetweenDates(@Param("minDate") Timestamp minDate, @Param("maxDate") Timestamp maxDate);

}
