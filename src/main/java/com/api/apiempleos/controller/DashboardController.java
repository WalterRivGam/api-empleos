package com.api.apiempleos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.apiempleos.dto.VacantePostulacion;
import com.api.apiempleos.service.PostulacionService;
import com.api.apiempleos.service.VacanteService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private VacanteService vacanteService;

    @Autowired
    private PostulacionService postulacionService;

    /**
     * Obtiene contadores de vacantes
     * 
     * @return cantidad de vacantes activas y expiradas
     */
    @GetMapping("/contadores")
    public ResponseEntity<Map<String, Long>> obtenerContadores() {
        try {
            Map<String, Long> contadores = vacanteService.obtenerContadores();
            return ResponseEntity.ok(contadores);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/top5postulaciones")
    public ResponseEntity<List<VacantePostulacion>> obtenerTop5PostulacionesPorVacante() {
        try {
            List<VacantePostulacion> top5 = postulacionService.obtenerTop5PostulacionesPorVacante();
            return ResponseEntity.ok(top5);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
