package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.DefinicionFlujoDTO;
import lombok.NonNull;
import java.util.List;

public interface DefinicionFlujoService {
    List<DefinicionFlujoDTO> listarTodos();
    List<DefinicionFlujoDTO> listarActivos();
    DefinicionFlujoDTO obtenerPorId(@NonNull Integer id);
    DefinicionFlujoDTO crear(DefinicionFlujoDTO dto);
    DefinicionFlujoDTO actualizar(@NonNull Integer id, DefinicionFlujoDTO dto);
    void eliminar(@NonNull Integer id);
}
