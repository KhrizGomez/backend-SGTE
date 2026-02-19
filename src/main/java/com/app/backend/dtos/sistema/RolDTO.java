package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDTO {
    private Integer idRol;
    private String nombreRol;
    private String descripcionRol;
    private Boolean esRolSistema;
    private Integer nivelJerarquico;
}
