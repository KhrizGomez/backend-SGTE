package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.NotificacionDTO;
import lombok.NonNull;
import java.util.List;

public interface NotificacionService {
    List<NotificacionDTO> listarPorUsuario(@NonNull Integer idUsuario);
    List<NotificacionDTO> listarNoLeidasPorUsuario(@NonNull Integer idUsuario);
    NotificacionDTO obtenerPorId(@NonNull Integer id);
    NotificacionDTO crear(NotificacionDTO dto);
    NotificacionDTO marcarComoLeida(@NonNull Integer id);
}
