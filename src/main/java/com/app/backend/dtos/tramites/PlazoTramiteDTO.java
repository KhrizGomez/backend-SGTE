package com.app.backend.dtos.tramites;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlazoTramiteDTO {
    private Integer idPlazo;
    private Integer idTipoTramite;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;
    private Boolean permiteExtension;
    private Integer diasMaxExtension;
}
