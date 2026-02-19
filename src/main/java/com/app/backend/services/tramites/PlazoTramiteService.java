package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.PlazoTramiteDTO;
import lombok.NonNull;
import java.util.List;

public interface PlazoTramiteService {
    List<PlazoTramiteDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite);
    PlazoTramiteDTO obtenerPorId(@NonNull Integer id);
    PlazoTramiteDTO crear(PlazoTramiteDTO dto);
    PlazoTramiteDTO actualizar(@NonNull Integer id, PlazoTramiteDTO dto);
    void eliminar(@NonNull Integer id);
}
