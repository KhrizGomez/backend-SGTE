package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.RechazoSolicitudDTO;
import lombok.NonNull;
import java.util.List;

public interface RechazoSolicitudService {
    List<RechazoSolicitudDTO> listarPorSolicitud(@NonNull Integer idSolicitud);
    RechazoSolicitudDTO crear(RechazoSolicitudDTO dto);
}
