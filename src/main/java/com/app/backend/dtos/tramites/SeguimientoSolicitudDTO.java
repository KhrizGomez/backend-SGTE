package com.app.backend.dtos.tramites;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoSolicitudDTO {
    private Integer idSeguimiento;
    private Integer idSolicitud;
    private Integer idPaso;
    private Integer idEtapa;
    private String estado;
    private Integer procesadoPorId;
    private String comentarios;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaCompletado;
    private Boolean slaExcedido;
}
