package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.TransicionFlujoDTO;
import lombok.NonNull;
import java.util.List;

public interface TransicionFlujoService {
    List<TransicionFlujoDTO> listarPorFlujo(@NonNull Integer idFlujo);
    TransicionFlujoDTO crear(TransicionFlujoDTO dto);
    void eliminar(@NonNull Integer id);
}
