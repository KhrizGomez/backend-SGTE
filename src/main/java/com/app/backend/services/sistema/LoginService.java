package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.LoginRequestDTO;
import com.app.backend.dtos.sistema.LoginRespuestaDTO;

public interface LoginService {
    LoginRespuestaDTO login(LoginRequestDTO request);
}
