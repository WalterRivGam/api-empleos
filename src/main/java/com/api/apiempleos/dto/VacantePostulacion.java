package com.api.apiempleos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VacantePostulacion {
    private String tituloVacante;
    private Integer totalPostulaciones;

    public VacantePostulacion(String nombreVacante, Integer totalPostulaciones) {
        this.tituloVacante = nombreVacante;
        this.totalPostulaciones = totalPostulaciones;
    }
}
