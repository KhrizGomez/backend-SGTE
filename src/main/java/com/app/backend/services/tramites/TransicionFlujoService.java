package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.response.TransicionFlujoResponseDTO;
import lombok.NonNull;
import java.util.List;

public interface TransicionFlujoService {
    List<TransicionFlujoResponseDTO> listarPorFlujo(@NonNull Integer idFlujo);
    TransicionFlujoResponseDTO crear(TransicionFlujoResponseDTO dto);
    void eliminar(@NonNull Integer id);
}

