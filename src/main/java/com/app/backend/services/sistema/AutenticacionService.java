package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.AutenticacionRequestDTO;
import com.app.backend.dtos.sistema.AutenticacionRespuestaDTO;

public interface AutenticacionService {
    AutenticacionRespuestaDTO iniciarSesion(AutenticacionRequestDTO peticion);
}
