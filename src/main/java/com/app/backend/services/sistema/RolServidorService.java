package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.RolServidorDTO;
import lombok.NonNull;
import java.util.List;

public interface RolServidorService {
    List<RolServidorDTO> listarTodos();
    RolServidorDTO obtenerPorId(@NonNull Integer id);
    RolServidorDTO crear(RolServidorDTO dto);
    RolServidorDTO actualizar(@NonNull Integer id, RolServidorDTO dto);
    void eliminar(@NonNull Integer id);
}
