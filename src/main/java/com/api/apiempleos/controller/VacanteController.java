package com.api.apiempleos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.apiempleos.entity.Vacante;
import com.api.apiempleos.service.VacanteService;

@RestController
@RequestMapping("/api")
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    /**
     * Obtener todas las vacantes activas
     * 
     * @return vacantes activas
     */
    @GetMapping("/vacantes")
    public ResponseEntity<List<Vacante>> obtenerVacantes() {

        try {
            List<Vacante> vacantes = vacanteService.obtenerVacantesActivas();
            if (vacantes.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(vacantes);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtener vacante por ID
     * 
     * @param id
     * @return vacante por ID
     */
    @GetMapping("/vacantes/{id}")
    public ResponseEntity<Vacante> obtenerVacantePorId(
            @PathVariable Integer id) {
        try {
            Optional<Vacante> vacantes = vacanteService.obtenerVacantePorId(id);
            if (vacantes.isPresent()) {
                return ResponseEntity.ok(vacantes.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Actualizar vacante
     * 
     * @param id
     * @param vacanteDetalles
     * @return vacante actualizada
     */
    @PutMapping("/vacantes/{id}")
    public ResponseEntity<Vacante> actualizarVacante(
            @PathVariable Integer id,
            @RequestBody Vacante vacanteDetalles) {
        System.out.println(vacanteDetalles);
        try {
            Optional<Vacante> vacanteOptional = vacanteService.obtenerVacantePorId(id);
            if (vacanteOptional.isPresent()) {
                Vacante vacanteExistente = vacanteOptional.get();
                // Actualizar los campos de la vacante existente con los detalles proporcionados
                vacanteExistente.setTitulo(vacanteDetalles.getTitulo());
                vacanteExistente.setDescripcion(vacanteDetalles.getDescripcion());
                vacanteExistente.setUbicacion(vacanteDetalles.getUbicacion());
                vacanteExistente.setSalarioOpc(vacanteDetalles.getSalarioOpc());
                vacanteExistente.setFechaVencimiento(vacanteDetalles.getFechaVencimiento());
                vacanteExistente.setEstado(vacanteDetalles.getEstado());

                // Guardar la vacante actualizada
                Vacante vacanteActualizada = vacanteService.actualizarVacante(vacanteExistente);
                return ResponseEntity.ok(vacanteActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Crear nueva vacante
     * 
     * @param nuevaVacante
     * @return vacante creada
     */
    @PostMapping("/vacantes")
    public ResponseEntity<Vacante> crearVacante(@RequestBody Vacante nuevaVacante) {
        try {
            Vacante vacanteCreada = vacanteService.crearVacante(nuevaVacante);
            return ResponseEntity.ok(vacanteCreada);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
