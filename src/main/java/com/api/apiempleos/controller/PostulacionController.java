package com.api.apiempleos.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.apiempleos.entity.Postulacion;
import com.api.apiempleos.service.PostulacionService;

@RestController
@RequestMapping("/api")
public class PostulacionController {
    @Autowired
    private PostulacionService postulacionService;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("El endpoint está funcionando correctamente.");
    }

    @PostMapping("/postulaciones")
    public ResponseEntity<String> recibirPostulacion(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("telefono") String telefono,
            @RequestParam("comentario") String comentario,
            @RequestParam("idVacante") Integer idVacante,
            @RequestPart("archivo") MultipartFile archivo) {
        try {
            Postulacion postulacion = new Postulacion();
            postulacion.setNombre(nombre);
            postulacion.setEmail(email);
            postulacion.setTelefonoOpc(telefono);
            postulacion.setComentarioOpc(comentario);
            postulacion.setVacanteId(idVacante);

            String pathArchivo = postulacionService.guardarArchivo(archivo);

            postulacion.setCvPath(pathArchivo);
            postulacion.setProcesadoRpa(false);
            postulacion.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            postulacionService.guardarPostulacion(postulacion);

            postulacionService.registrarEnCSV(postulacion);
            postulacionService.enviarNotificacion(postulacion);

            return ResponseEntity.ok("Postulación recibida correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la postulación: " + e.getMessage());
        }
    }
}
