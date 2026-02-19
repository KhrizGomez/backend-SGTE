package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.RegistroUsuarioDTO;
import com.app.backend.dtos.sistema.RegistroUsuarioRespuestaDTO;

public interface RegistroUsuarioService {
    RegistroUsuarioRespuestaDTO registrarUsuario(RegistroUsuarioDTO dto);
}
