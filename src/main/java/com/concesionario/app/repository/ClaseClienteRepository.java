package com.concesionario.app.repository;

import com.concesionario.app.domain.Clase;
import com.concesionario.app.domain.ClaseCliente;
import com.concesionario.app.service.dto.ClaseClienteDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClaseCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaseClienteRepository extends JpaRepository<ClaseCliente, Long> {

    @Query("SELECT c FROM ClaseCliente c WHERE c.clase IN :clases")
    Optional<List<ClaseCliente>> findAllFromClases(@Param("clases") List<Clase> clases);

    @Query("SELECT c FROM ClaseCliente c WHERE c.clase.inicio>:minDate AND c.clase.inicio<:maxDate")
    Optional<List<ClaseCliente>> findAllBetweenDates(Timestamp minDate, Timestamp maxDate);

}
