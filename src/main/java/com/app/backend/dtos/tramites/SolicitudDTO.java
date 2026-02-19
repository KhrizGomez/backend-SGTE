package com.app.backend.dtos.tramites;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudDTO {
    private Integer idSolicitud;
    private String codigoSolicitud;
    private Integer idTipoTramite;
    private Integer idUsuario;
    private Integer idCarrera;
    private Integer creadoPorId;
    private String detallesSolicitud;
    private String prioridad;
    private Integer pasoActualId;
    private String estadoActual;
    private LocalDate fechaEstimadaFin;
    private LocalDateTime fechaRealFin;
    private String resolucion;
}
