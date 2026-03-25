package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.request.CrearSolicitudRequestDTO;
import com.app.backend.dtos.tramites.response.SolicitudResponseDTO;
import com.app.backend.dtos.tramites.response.SolicitudesTramitesVigentesRespuestaDTO;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SolicitudService {
    List<SolicitudResponseDTO> listarTodas();

    List<SolicitudResponseDTO> listarPorUsuario(@NonNull Integer idUsuario);

    List<SolicitudResponseDTO> listarPorEstado(String estado);

    SolicitudResponseDTO obtenerPorId(@NonNull Integer id);

    SolicitudResponseDTO obtenerPorCodigo(String codigo);

    SolicitudResponseDTO crear(SolicitudResponseDTO dto);

    SolicitudResponseDTO actualizar(@NonNull Integer id, SolicitudResponseDTO dto);

    void eliminar(@NonNull Integer id);

    List<SolicitudesTramitesVigentesRespuestaDTO> listarTramitesVigente();

    void crearSolicitudConDocumentos(CrearSolicitudRequestDTO dto, List<MultipartFile> archivos);
}


