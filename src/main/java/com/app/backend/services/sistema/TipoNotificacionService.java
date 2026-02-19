package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.TipoNotificacionDTO;
import lombok.NonNull;
import java.util.List;

public interface TipoNotificacionService {
    List<TipoNotificacionDTO> listarTodos();
    TipoNotificacionDTO obtenerPorId(@NonNull Integer id);
    TipoNotificacionDTO crear(TipoNotificacionDTO dto);
    TipoNotificacionDTO actualizar(@NonNull Integer id, TipoNotificacionDTO dto);
    void eliminar(@NonNull Integer id);
}
