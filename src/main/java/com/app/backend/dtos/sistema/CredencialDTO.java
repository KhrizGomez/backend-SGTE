package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredencialDTO {
    private Integer idCredencial;
    private Integer idUsuario;
    private String hashContrasena;
    private Boolean estado;
}
