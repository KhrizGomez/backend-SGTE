package com.app.backend.dtos.sistema;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsquemaDTO {
    private Integer idEsquema;
    private String nombreEsquema;
    private String descripcion;
    private Boolean estaActivo;
    private LocalDateTime fechaCreacion;
}
