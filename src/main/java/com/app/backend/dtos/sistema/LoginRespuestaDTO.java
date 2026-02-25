package com.app.backend.dtos.sistema;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespuestaDTO {
    private Integer idUsuario;
    private String nombreUsuario;
    private String nombres;
    private String apellidos;
    private String correoInstitucional;
    private String correoPersonal;
    private String telefono;
    private Boolean estado;
    private List<String> roles;
    private String mensaje;
}
