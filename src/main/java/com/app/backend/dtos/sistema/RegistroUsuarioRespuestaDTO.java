package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroUsuarioRespuestaDTO {
    private Integer idUsuario;
    private Integer idEstudiante;
    private Integer idCoordinador;
    private Integer idDecano;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correoInstitucional;
    private String rol;
    private String mensaje;
}
