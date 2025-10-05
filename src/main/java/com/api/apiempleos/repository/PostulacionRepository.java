package com.api.apiempleos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.apiempleos.entity.Postulacion;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {
    @Query(value = "CALL SP_TOP5_POSTULACIONES_POR_VACANTE()", nativeQuery = true)
    List<Object[]> obtenerTop5PostulacionesPorVacanteSP();
}
