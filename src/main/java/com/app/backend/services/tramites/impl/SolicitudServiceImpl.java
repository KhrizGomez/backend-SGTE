package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.SolicitudDTO;
import com.app.backend.dtos.tramites.response.SolicitudesTramitesVigentesRespuestaDTO;
import com.app.backend.entities.tramites.Solicitud;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.academico.CarreraRepository;
import com.app.backend.repositories.sistema.UsuarioRepository;
import com.app.backend.repositories.tramites.PasoFlujoRepository;
import com.app.backend.repositories.tramites.PlantillaTramiteRepository;
import com.app.backend.repositories.tramites.SolicitudRepository;
import com.app.backend.services.externos.IJwtService;
import com.app.backend.services.tramites.SolicitudService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final PlantillaTramiteRepository plantillaTramiteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final PasoFlujoRepository pasoFlujoRepository;
    private final HttpServletRequest request;
    private final IJwtService jwtService;

    @Override @Transactional(readOnly = true)
    public List<SolicitudDTO> listarTodas() { return solicitudRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorUsuario(@NonNull Integer idUsuario) { return solicitudRepository.findByUsuarioIdUsuario(idUsuario).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<SolicitudDTO> listarPorEstado(String estado) { return solicitudRepository.findByEstadoActual(estado).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public SolicitudDTO obtenerPorId(@NonNull Integer id) { return toDTO(solicitudRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con id: " + id))); }

    @Override @Transactional(readOnly = true)
    public SolicitudDTO obtenerPorCodigo(String codigo) { return toDTO(solicitudRepository.findByCodigoSolicitud(codigo).orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con código: " + codigo))); }

    @Override
    public SolicitudDTO crear(SolicitudDTO dto) {
        Solicitud s = Solicitud.builder()
                .codigoSolicitud(dto.getCodigoSolicitud())
                .plantilla(plantillaTramiteRepository.findById(dto.getIdPlantilla()).orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada: " + dto.getIdPlantilla())))
                .usuario(usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getIdUsuario())))
                .detallesSolicitud(dto.getDetallesSolicitud())
                .prioridad(dto.getPrioridad() != null ? dto.getPrioridad() : "normal")
                .estadoActual(dto.getEstadoActual() != null ? dto.getEstadoActual() : "pendiente")
                .fechaEstimadaFin(dto.getFechaEstimadaFin())
                .build();
        if (dto.getIdCarrera() != null) s.setCarrera(carreraRepository.findById(dto.getIdCarrera()).orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada: " + dto.getIdCarrera())));
        if (dto.getCreadoPorId() != null) s.setCreadoPor(usuarioRepository.findById(dto.getCreadoPorId()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getCreadoPorId())));
        if (dto.getPasoActualId() != null) s.setPasoActual(pasoFlujoRepository.findById(dto.getPasoActualId()).orElseThrow(() -> new RecursoNoEncontradoException("Paso no encontrado: " + dto.getPasoActualId())));
        return toDTO(solicitudRepository.save(s));
    }

    @Override
    public SolicitudDTO actualizar(@NonNull Integer id, SolicitudDTO dto) {
        Solicitud s = solicitudRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con id: " + id));
        s.setDetallesSolicitud(dto.getDetallesSolicitud()); s.setPrioridad(dto.getPrioridad()); s.setEstadoActual(dto.getEstadoActual());
        s.setFechaEstimadaFin(dto.getFechaEstimadaFin()); s.setFechaRealFin(dto.getFechaRealFin()); s.setResolucion(dto.getResolucion());
        if (dto.getPasoActualId() != null) s.setPasoActual(pasoFlujoRepository.findById(dto.getPasoActualId()).orElseThrow(() -> new RecursoNoEncontradoException("Paso no encontrado: " + dto.getPasoActualId())));
        return toDTO(solicitudRepository.save(s));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!solicitudRepository.existsById(id)) throw new RecursoNoEncontradoException("Solicitud no encontrada con id: " + id); solicitudRepository.deleteById(id); }

    private SolicitudDTO toDTO(Solicitud s) {
        return SolicitudDTO.builder().idSolicitud(s.getIdSolicitud()).codigoSolicitud(s.getCodigoSolicitud()).idPlantilla(s.getPlantilla().getIdPlantilla()).idUsuario(s.getUsuario().getIdUsuario()).idCarrera(s.getCarrera() != null ? s.getCarrera().getIdCarrera() : null).creadoPorId(s.getCreadoPor() != null ? s.getCreadoPor().getIdUsuario() : null).detallesSolicitud(s.getDetallesSolicitud()).prioridad(s.getPrioridad()).pasoActualId(s.getPasoActual() != null ? s.getPasoActual().getIdPaso() : null).estadoActual(s.getEstadoActual()).fechaCreacion(s.getFechaCreacion()).fechaEstimadaFin(s.getFechaEstimadaFin()).fechaRealFin(s.getFechaRealFin()).resolucion(s.getResolucion()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudesTramitesVigentesRespuestaDTO> listarTramitesVigente(){
        try {
            Integer idUsuario = null;
            String encaAuth = request.getHeader("Authorization");
            if (encaAuth != null && encaAuth.startsWith("Bearer ")){
                String jwt = encaAuth.substring(7);
                idUsuario = jwtService.extraerIdUsuario(jwt);
            }
            return solicitudRepository.listarTramitesVigentes(idUsuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las solicitudes de tramites: " + e.getMessage());
        } 
    }
}
