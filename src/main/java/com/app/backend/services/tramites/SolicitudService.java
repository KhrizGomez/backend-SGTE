package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.CrearSolicitudTramiteDTO;
import com.app.backend.dtos.tramites.SolicitudDTO;
import com.app.backend.dtos.tramites.response.SolicitudesTramitesVigentesRespuestaDTO;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SolicitudService {
    List<SolicitudDTO> listarTodas();

    List<SolicitudDTO> listarPorUsuario(@NonNull Integer idUsuario);

    List<SolicitudDTO> listarPorEstado(String estado);

    SolicitudDTO obtenerPorId(@NonNull Integer id);

    SolicitudDTO obtenerPorCodigo(String codigo);

    SolicitudDTO crear(SolicitudDTO dto);

    SolicitudDTO actualizar(@NonNull Integer id, SolicitudDTO dto);

    void eliminar(@NonNull Integer id);

    List<SolicitudesTramitesVigentesRespuestaDTO> listarTramitesVigente();

    void crearSolicitudConDocumentos(CrearSolicitudTramiteDTO dto, List<MultipartFile> archivos);
}
