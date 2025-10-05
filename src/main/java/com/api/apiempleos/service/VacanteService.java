package com.api.apiempleos.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.api.apiempleos.entity.Vacante;

public interface VacanteService {
    List<Vacante> obtenerVacantesActivas();

    Optional<Vacante> obtenerVacantePorId(Integer id);

    Vacante actualizarVacante(Vacante vacante);

    Vacante crearVacante(Vacante vacante);

    Map<String, Long> obtenerContadores();

}
