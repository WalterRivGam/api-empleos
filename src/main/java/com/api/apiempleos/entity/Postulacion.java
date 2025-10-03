package com.api.apiempleos.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "postulacion")
public class Postulacion {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vacante_id")
    private Integer vacanteId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono_opc")
    private String telefonoOpc;

    @Column(name = "cv_path")
    private String cvPath;

    @Column(name = "comentario_opc")
    private String comentarioOpc;

    @Column(name = "procesado_rpa")
    private Boolean procesadoRpa = false;

    @Column(name = "created_at")
    private Timestamp createdAt;

}
