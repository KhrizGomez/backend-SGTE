package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.UsuarioDTO;
import lombok.NonNull;
import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarTodos();
    UsuarioDTO obtenerPorId(@NonNull Integer id);
    UsuarioDTO obtenerPorCedula(String cedula);
    UsuarioDTO crear(UsuarioDTO dto);
    UsuarioDTO actualizar(@NonNull Integer id, UsuarioDTO dto);
    void eliminar(@NonNull Integer id);
}
