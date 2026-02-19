package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.CredencialDTO;
import lombok.NonNull;

public interface CredencialService {
    CredencialDTO obtenerPorUsuario(@NonNull Integer idUsuario);
    CredencialDTO guardar(CredencialDTO dto);
}
