package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoNotificacionDTO {
    private Integer idTipo;
    private String codigoTipo;
    private String nombreTipo;
    private String plantillaDefecto;
    private Boolean estaActivo;
}
