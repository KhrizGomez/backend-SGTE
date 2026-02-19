package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoServidorDTO {
    private Integer idPermisoSrv;
    private Integer idRolSrv;
    private String tipoObjeto;
    private String nombreObjeto;
    private String privilegio;
}
