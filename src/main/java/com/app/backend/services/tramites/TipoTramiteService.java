package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.TipoTramiteDTO;
import com.app.backend.dtos.tramites.response.PlantillaTramiteResponseDTO;

import lombok.NonNull;
import java.util.List;

public interface TipoTramiteService {
    List<TipoTramiteDTO> listarTodos();
    List<TipoTramiteDTO> listarActivos();
    List<TipoTramiteDTO> listarPorCategoria(@NonNull Integer idCategoria);
    TipoTramiteDTO obtenerPorId(@NonNull Integer id);
    TipoTramiteDTO crear(TipoTramiteDTO dto);
    TipoTramiteDTO actualizar(@NonNull Integer id, TipoTramiteDTO dto);
    void eliminar(@NonNull Integer id);
    List<PlantillaTramiteResponseDTO> listarPlantillasTramites(String categoria, Boolean activo, String busqueda);
}
