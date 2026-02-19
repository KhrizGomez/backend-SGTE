package com.app.backend.dtos.tramites;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoTramiteDTO {
    private Integer idTipoTramite;
    private String nombreTramite;
    private String descripcionTramite;
    private Integer idCategoria;
    private Integer idFlujo;
    private Integer diasEstimados;
    private Boolean estaActivo;
    private Boolean disponibleExternos;
}
