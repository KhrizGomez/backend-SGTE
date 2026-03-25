package com.app.backend.dtos.tramites;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearSolicitudTramiteDTO {

    @NotNull(message = "El id de la plantilla es obligatorio")
    private Integer idPlantilla;

    private String detallesSolicitud;

    @Builder.Default
    private String prioridad = "Normal";

    @Builder.Default
    private Integer pasoActual = 1;

    /**
     * Lista de IDs de requisitos, uno por cada archivo adjunto, en el mismo orden.
     * Si no hay archivos, puede ser null o vacía.
     */
    private List<Integer> idsRequisitos;
}
