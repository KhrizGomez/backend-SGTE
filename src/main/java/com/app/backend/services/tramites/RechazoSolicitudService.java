package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.request.RechazoSolicitudRequestDTO;
import lombok.NonNull;
import java.util.List;

public interface RechazoSolicitudService {
    List<RechazoSolicitudRequestDTO> listarPorSolicitud(@NonNull Integer idSolicitud);
    RechazoSolicitudRequestDTO crear(RechazoSolicitudRequestDTO dto);
}

