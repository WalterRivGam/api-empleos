package com.api.apiempleos.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.api.apiempleos.dto.VacantePostulacion;
import com.api.apiempleos.entity.Postulacion;

public interface PostulacionService {
    Postulacion guardarPostulacion(Postulacion postulacion);

    String guardarArchivo(MultipartFile archivo);

    void registrarEnCSV(Postulacion postulacion);

    void enviarNotificacion(Postulacion postulacion);

    List<VacantePostulacion> obtenerTop5PostulacionesPorVacante();
}
