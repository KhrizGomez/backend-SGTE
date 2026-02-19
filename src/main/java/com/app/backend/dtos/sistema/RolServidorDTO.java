package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolServidorDTO {
    private Integer idRolSrv;
    private String nombreRolDb;
    private String descripcion;
    private Boolean estaActivo;
}
