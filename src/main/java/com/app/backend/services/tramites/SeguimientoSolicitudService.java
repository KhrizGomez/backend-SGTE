package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.SeguimientoSolicitudResponseDTO;
import lombok.NonNull;
import java.util.List;

public interface SeguimientoSolicitudService {
    List<SeguimientoSolicitudResponseDTO> listarPorSolicitud(@NonNull Integer idSolicitud);
    SeguimientoSolicitudResponseDTO crear(SeguimientoSolicitudResponseDTO dto);
}

