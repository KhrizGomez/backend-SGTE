package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredencialServidorDTO {
    private Integer idCredencialSrv;
    private Integer idUsuario;
    private String nombreUsuarioDb;
    private String metodoAuth;
    private Boolean estaActivo;
}
