package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.CategoriaTramiteDTO;
import lombok.NonNull;
import java.util.List;

public interface CategoriaTramiteService {
    List<CategoriaTramiteDTO> listarTodas();
    List<CategoriaTramiteDTO> listarActivas();
    CategoriaTramiteDTO obtenerPorId(@NonNull Integer id);
    CategoriaTramiteDTO crear(CategoriaTramiteDTO dto);
    CategoriaTramiteDTO actualizar(@NonNull Integer id, CategoriaTramiteDTO dto);
    void eliminar(@NonNull Integer id);
}
