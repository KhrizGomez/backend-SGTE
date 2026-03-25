package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.DetallesTramiteResponseDTO;
import lombok.NonNull;

import java.util.List;

public interface DetallesTramiteService {
    List<DetallesTramiteResponseDTO> listarTodos();
    List<DetallesTramiteResponseDTO> listarPorCarrera(@NonNull Integer idCarrera);
    DetallesTramiteResponseDTO obtenerPorTipoTramite(@NonNull Integer idTipoTramite);
}
