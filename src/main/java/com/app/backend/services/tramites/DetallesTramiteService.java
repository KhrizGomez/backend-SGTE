package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.DetallesTramiteDTO;
import lombok.NonNull;

import java.util.List;

public interface DetallesTramiteService {
    List<DetallesTramiteDTO> listarTodos();
    List<DetallesTramiteDTO> listarPorCarrera(@NonNull Integer idCarrera);
    DetallesTramiteDTO obtenerPorTipoTramite(@NonNull Integer idTipoTramite);
}