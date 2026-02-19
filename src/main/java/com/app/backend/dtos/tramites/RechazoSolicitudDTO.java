package com.app.backend.dtos.tramites;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RechazoSolicitudDTO {
    private Integer idRechazo;
    private Integer idSolicitud;
    private Integer idMotivo;
    private Integer rechazadoPorId;
    private String comentarios;
    private LocalDateTime fechaRechazo;
    private Boolean notificacionEnviada;
    private LocalDateTime fechaNotificacion;
}
