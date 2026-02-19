package com.app.backend.dtos.sistema;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsquemaRolDTO {
    private Integer idEsquemaRol;
    private Integer idEsquema;
    private Integer idRolSrv;
    private String nivelAcceso;
    private Boolean puedeCrearObjetos;
    private Boolean puedeEliminar;
    private Boolean estaActivo;
    private LocalDateTime fechaAsignacion;
}
