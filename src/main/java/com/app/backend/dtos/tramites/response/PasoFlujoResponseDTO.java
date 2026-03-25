package com.app.backend.dtos.tramites.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasoFlujoResponseDTO {
    private Integer idPaso;
    private Integer idFlujo;
    private Integer idEtapa;
    private Integer ordenPaso;
    private Integer rolRequeridoId;
    private Integer idUsuarioEncargado;
    private Integer horasSla;
}

