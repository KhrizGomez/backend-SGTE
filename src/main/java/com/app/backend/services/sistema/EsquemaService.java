package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.EsquemaDTO;
import lombok.NonNull;
import java.util.List;

public interface EsquemaService {
    List<EsquemaDTO> listarTodos();
    EsquemaDTO obtenerPorId(@NonNull Integer id);
    EsquemaDTO crear(EsquemaDTO dto);
    EsquemaDTO actualizar(@NonNull Integer id, EsquemaDTO dto);
    void eliminar(@NonNull Integer id);
}
