package com.app.backend.dtos.tramites.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtapaRequestDTO {
    private String nombreEtapa;
    private String descripcionEtapa;
    private String codigoEtapa;
}