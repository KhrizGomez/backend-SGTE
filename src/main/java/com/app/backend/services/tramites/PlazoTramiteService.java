package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.PlazoTramiteResponseDTO;
import lombok.NonNull;
import java.util.List;

public interface PlazoTramiteService {
    List<PlazoTramiteResponseDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite);
    PlazoTramiteResponseDTO obtenerPorId(@NonNull Integer id);
    PlazoTramiteResponseDTO crear(PlazoTramiteResponseDTO dto);
    PlazoTramiteResponseDTO actualizar(@NonNull Integer id, PlazoTramiteResponseDTO dto);
    void eliminar(@NonNull Integer id);
}

