package com.app.backend.dtos.tramites.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotivoRechazoResponseDTO {
    private Integer idMotivo;
    private String codigoMotivo;
    private String nombreMotivo;
    private String descripcionMotivo;
    private Integer idCategoria;
    private Boolean estaActivo;
}

