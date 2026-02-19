package com.app.backend.dtos.tramites;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasoFlujoDTO {
    private Integer idPaso;
    private Integer idFlujo;
    private Integer idEtapa;
    private Integer ordenPaso;
    private Integer rolRequeridoId;
    private Integer idUsuarioEncargado;
    private Integer horasSla;
}
