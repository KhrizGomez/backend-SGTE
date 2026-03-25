package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.DefinicionFlujoResponseDTO;
import lombok.NonNull;
import java.util.List;

public interface DefinicionFlujoService {
    List<DefinicionFlujoResponseDTO> listarTodos();
    List<DefinicionFlujoResponseDTO> listarActivos();
    DefinicionFlujoResponseDTO obtenerPorId(@NonNull Integer id);
    DefinicionFlujoResponseDTO crear(DefinicionFlujoResponseDTO dto);
    DefinicionFlujoResponseDTO actualizar(@NonNull Integer id, DefinicionFlujoResponseDTO dto);
    void eliminar(@NonNull Integer id);
}

