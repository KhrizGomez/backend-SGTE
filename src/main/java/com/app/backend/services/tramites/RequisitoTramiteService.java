package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.RequisitoTramiteResponseDTO;
import lombok.NonNull;
import java.util.List;

public interface RequisitoTramiteService {
    List<RequisitoTramiteResponseDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite);
    RequisitoTramiteResponseDTO obtenerPorId(@NonNull Integer id);
    RequisitoTramiteResponseDTO crear(RequisitoTramiteResponseDTO dto);
    RequisitoTramiteResponseDTO actualizar(@NonNull Integer id, RequisitoTramiteResponseDTO dto);
    void eliminar(@NonNull Integer id);
}

