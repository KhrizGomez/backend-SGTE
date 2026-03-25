package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.TipoTramiteResponseDTO;
import com.app.backend.dtos.tramites.response.PlantillaTramiteResponseDTO;

import lombok.NonNull;
import java.util.List;

public interface TipoTramiteService {
    List<TipoTramiteResponseDTO> listarTodos();
    List<TipoTramiteResponseDTO> listarActivos();
    List<TipoTramiteResponseDTO> listarPorCategoria(@NonNull Integer idCategoria);
    TipoTramiteResponseDTO obtenerPorId(@NonNull Integer id);
    TipoTramiteResponseDTO crear(TipoTramiteResponseDTO dto);
    TipoTramiteResponseDTO actualizar(@NonNull Integer id, TipoTramiteResponseDTO dto);
    void eliminar(@NonNull Integer id);
    List<PlantillaTramiteResponseDTO> listarPlantillasTramites(String categoria, Boolean activo, String busqueda);
}

