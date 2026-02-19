package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.PasoFlujoDTO;
import lombok.NonNull;
import java.util.List;

public interface PasoFlujoService {
    List<PasoFlujoDTO> listarPorFlujo(@NonNull Integer idFlujo);
    PasoFlujoDTO obtenerPorId(@NonNull Integer id);
    PasoFlujoDTO crear(PasoFlujoDTO dto);
    PasoFlujoDTO actualizar(@NonNull Integer id, PasoFlujoDTO dto);
    void eliminar(@NonNull Integer id);
}
