package com.app.backend.dtos.tramites;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtapaProcesamientoDTO {
    private Integer idEtapa;
    private String nombreEtapa;
    private String descripcionEtapa;
    private String codigoEtapa;
}
