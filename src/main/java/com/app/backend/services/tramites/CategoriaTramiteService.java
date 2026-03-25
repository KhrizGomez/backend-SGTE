package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.CategoriaTramiteResponseDTO;
import lombok.NonNull;
import java.util.List;

public interface CategoriaTramiteService {
    List<CategoriaTramiteResponseDTO> listarTodas();
    List<CategoriaTramiteResponseDTO> listarActivas();
    CategoriaTramiteResponseDTO obtenerPorId(@NonNull Integer id);
    CategoriaTramiteResponseDTO crear(CategoriaTramiteResponseDTO dto);
    CategoriaTramiteResponseDTO actualizar(@NonNull Integer id, CategoriaTramiteResponseDTO dto);
    void eliminar(@NonNull Integer id);
}

