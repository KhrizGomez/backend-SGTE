package com.app.backend.dtos.sistema;

import com.app.backend.dtos.tramites.SolicitudDTO;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PanelEstudianteDTO {
    private String nombreEstudiante;
    private Integer solicitudesActivas;
    private Integer horasAcumuladas;
    private List<SolicitudDTO> solicitudesRecientes;
    private List<NotificacionDTO> notificaciones;
}
