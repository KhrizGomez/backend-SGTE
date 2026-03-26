package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.DetallesPlantillaResponseDTO;
import lombok.NonNull;

import java.util.List;

public interface DetallesTramiteService {
    List<DetallesPlantillaResponseDTO> listarTodos();
    List<DetallesPlantillaResponseDTO> listarPorCarrera(@NonNull Integer idCarrera);
    List<DetallesPlantillaResponseDTO> listarPorFacultad(@NonNull Integer idFacultad);
    DetallesPlantillaResponseDTO obtenerPorPlantilla(@NonNull Integer idPlantilla);
}
