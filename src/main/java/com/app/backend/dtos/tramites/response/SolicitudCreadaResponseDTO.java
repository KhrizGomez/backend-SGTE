package com.app.backend.dtos.tramites.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudCreadaResponseDTO {
    private String mensaje;
    private Integer idPlantilla;
    private Integer cantidadArchivos;
}