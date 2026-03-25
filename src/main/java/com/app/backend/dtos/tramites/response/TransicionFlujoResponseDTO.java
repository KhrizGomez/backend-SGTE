package com.app.backend.dtos.tramites.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransicionFlujoResponseDTO {
    private Integer idTransicion;
    private Integer idFlujo;
    private Integer idPasoOrigen;
    private Integer idPasoDestino;
    private Boolean observacion;
    private Boolean documentoGenerado;
}

