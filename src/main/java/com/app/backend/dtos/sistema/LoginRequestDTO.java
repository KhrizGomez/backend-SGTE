package com.app.backend.dtos.sistema;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
    private String nombreUsuario;
    private String contrasena;
}
