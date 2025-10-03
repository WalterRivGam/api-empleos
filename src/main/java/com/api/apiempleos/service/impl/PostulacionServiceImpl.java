package com.api.apiempleos.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.apiempleos.entity.Postulacion;
import com.api.apiempleos.repository.PostulacionRepository;
import com.api.apiempleos.service.PostulacionService;

import jakarta.transaction.Transactional;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${correo.destinatario.notificacion}")
    private String destinatarioNotificacion;

    @Override
    @Transactional
    public void guardarPostulacion(Postulacion postulacion) {
        postulacionRepository.save(postulacion);
    }

    @Override
    public String guardarArchivo(MultipartFile archivo) {
        try {
            // Directorio fijo donde guardar los archivos
            String uploadDir = "C:\\postulaciones\\archivos";

            // Crear el directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar un nombre único para evitar colisiones
            String fileName = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();

            // Ruta completa del archivo
            Path filePath = uploadPath.resolve(fileName);

            // Guardar físicamente
            archivo.transferTo(filePath.toFile());

            // Devolver la ruta absoluta
            return filePath.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public void registrarEnCSV(Postulacion postulacion) {
        String filePath = "C:\\postulaciones\\postulaciones.csv";
        File file = new File(filePath);

        boolean archivoNuevo = !file.exists(); // si no existe, es nuevo

        try (FileWriter fw = new FileWriter(filePath, true);
                PrintWriter pw = new PrintWriter(fw)) {

            // Si es nuevo, escribe encabezados
            if (archivoNuevo) {
                pw.println("Nombre,Email,TelefonoOpc,VacanteId,CvPath,CreatedAt");
            }

            // Escribe los datos de la postulación
            pw.printf("%s,%s,%s,%s,%s,%s%n",
                    postulacion.getNombre(),
                    postulacion.getEmail(),
                    postulacion.getTelefonoOpc(),
                    postulacion.getVacanteId(),
                    postulacion.getCvPath(),
                    postulacion.getCreatedAt());

        } catch (IOException e) {
            throw new RuntimeException("Error al escribir CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public void enviarNotificacion(Postulacion postulacion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatarioNotificacion);
        message.setSubject("Nueva postulación recibida");
        message.setText("Se recibió la postulación de: " + postulacion.getNombre()
                + "\nEmail: " + postulacion.getEmail()
                + "\nTeléfono: " + postulacion.getTelefonoOpc()
                + "\nVacante ID: " + postulacion.getVacanteId()
                + "\nArchivo: " + postulacion.getCvPath());
        mailSender.send(message);
    }

}
