package com.app.backend.dtos.tramites;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefinicionFlujoDTO {
    private Integer idFlujo;
    private String nombreFlujo;
    private String descripcionFlujo;
    private Boolean estaActivo;
    private Integer version;
    private Integer creadoPorId;
}
