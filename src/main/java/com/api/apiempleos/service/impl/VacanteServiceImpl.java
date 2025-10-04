package com.api.apiempleos.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.api.apiempleos.entity.Vacante;
import com.api.apiempleos.repository.VacanteRepository;
import com.api.apiempleos.service.VacanteService;

import jakarta.transaction.Transactional;

@Service
public class VacanteServiceImpl implements VacanteService {

    @Autowired
    private VacanteRepository vacanteRepository;

    @Override
    public List<Vacante> obtenerVacantesActivas() {
        return vacanteRepository.findAll().stream()
                .filter(v -> "ACTIVA".equalsIgnoreCase(v.getEstado()))
                .toList();
    }

    @Override
    public Optional<Vacante> obtenerVacantePorId(Integer id) {
        return vacanteRepository.findById(id);
    }

    @Override
    @Transactional
    public Vacante actualizarVacante(Vacante vacante) {
        return vacanteRepository.save(vacante);
    }

    @Override
    @Transactional
    public Vacante crearVacante(Vacante vacante) {
        if (vacante.getId() == null || vacante.getId() == 0) {
            vacante.setId(null);
        }
        vacante.setFechaPublicacion(LocalDate.now());
        vacante.setEstado("activa");
        vacante.setCreatedAt(LocalDateTime.now());
        return vacanteRepository.save(vacante);
    }

    /**
     * Expiración de vacantes
     * Se ejecuta todos los días a la 1 a.m.
     * Actualiza el estado de todas las vacantes activas cuya fecha de vencimiento
     * es anterior al día actual a 'cerrada' en la base de datos.
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void expirarVacante() {
        System.out.println("Expirar vacantes");
        obtenerVacantesActivas().forEach(vacante -> {
            if (vacante.getEstado().equalsIgnoreCase("activa")
                    && vacante.getFechaVencimiento().isBefore(LocalDate.now())) {
                vacante.setEstado("cerrada");
                vacanteRepository.save(vacante);
            }
        });
    }

}
