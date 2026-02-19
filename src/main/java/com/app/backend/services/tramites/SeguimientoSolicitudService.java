package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.SeguimientoSolicitudDTO;
import lombok.NonNull;
import java.util.List;

public interface SeguimientoSolicitudService {
    List<SeguimientoSolicitudDTO> listarPorSolicitud(@NonNull Integer idSolicitud);
    SeguimientoSolicitudDTO crear(SeguimientoSolicitudDTO dto);
}
