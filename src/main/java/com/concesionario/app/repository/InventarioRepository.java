package com.concesionario.app.repository;

import com.concesionario.app.domain.Inventario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Inventario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

}
