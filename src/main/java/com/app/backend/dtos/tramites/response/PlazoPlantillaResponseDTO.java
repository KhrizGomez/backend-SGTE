package com.app.backend.dtos.tramites.response;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlazoPlantillaResponseDTO {
    private Integer idPlazo;
    private Integer idPlantilla;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;
    private Boolean permiteExtension;
    private Integer diasMaxExtension;
}