package com.app.backend.dtos.tramites.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccionPasoRequestDTO {

    @NotNull(message = "El id de la solicitud es obligatorio")
    private Integer idSolicitud;

    private String comentarios;

    // Solo para rechazo
    private Integer idMotivo;
}
