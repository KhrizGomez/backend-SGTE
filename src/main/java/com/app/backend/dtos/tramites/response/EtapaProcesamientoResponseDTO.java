package com.app.backend.dtos.tramites.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtapaProcesamientoResponseDTO {
    private Integer idEtapa;
    private String nombreEtapa;
    private String descripcionEtapa;
    private String codigoEtapa;
}

