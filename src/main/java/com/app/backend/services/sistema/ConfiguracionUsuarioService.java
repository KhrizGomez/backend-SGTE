package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.ConfiguracionUsuarioDTO;
import lombok.NonNull;

public interface ConfiguracionUsuarioService {
    ConfiguracionUsuarioDTO obtenerPorUsuario(@NonNull Integer idUsuario);
    ConfiguracionUsuarioDTO guardar(ConfiguracionUsuarioDTO dto);
}
