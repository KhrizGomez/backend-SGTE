package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.RequisitoTramiteDTO;
import lombok.NonNull;
import java.util.List;

public interface RequisitoTramiteService {
    List<RequisitoTramiteDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite);
    RequisitoTramiteDTO obtenerPorId(@NonNull Integer id);
    RequisitoTramiteDTO crear(RequisitoTramiteDTO dto);
    RequisitoTramiteDTO actualizar(@NonNull Integer id, RequisitoTramiteDTO dto);
    void eliminar(@NonNull Integer id);
}
