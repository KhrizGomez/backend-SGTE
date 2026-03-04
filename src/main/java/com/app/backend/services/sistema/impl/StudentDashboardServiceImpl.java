package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.NotificacionDTO;
import com.app.backend.dtos.sistema.StudentDashboardDTO;
import com.app.backend.dtos.tramites.SolicitudDTO;
import com.app.backend.entities.sistema.Notificacion;
import com.app.backend.entities.sistema.Usuario;
import com.app.backend.entities.tramites.Solicitud;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.sistema.NotificacionRepository;
import com.app.backend.repositories.sistema.UsuarioRepository;
import com.app.backend.repositories.tramites.SolicitudRepository;
import com.app.backend.services.sistema.IStudentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentDashboardServiceImpl implements IStudentDashboardService {

    private final UsuarioRepository usuarioRepository;
    private final SolicitudRepository solicitudRepository;
    private final NotificacionRepository notificacionRepository;

    @Override
    public StudentDashboardDTO obtenerDashboard(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + idUsuario));

        List<Solicitud> todasLasSolicitudes = solicitudRepository.findByUsuarioIdUsuario(idUsuario);

        long solicitudesActivas = todasLasSolicitudes.stream()
                .filter(s -> !s.getEstadoActual().equalsIgnoreCase("finalizado") &&
                        !s.getEstadoActual().equalsIgnoreCase("aprobado") &&
                        !s.getEstadoActual().equalsIgnoreCase("rechazado"))
                .count();

        List<SolicitudDTO> solicitudesRecientes = todasLasSolicitudes.stream()
                .sorted((s1, s2) -> s2.getIdSolicitud().compareTo(s1.getIdSolicitud())) // Asumiendo que ID mayor es más
                                                                                        // reciente
                .limit(5)
                .map(this::mapSolicitudToDTO)
                .collect(Collectors.toList());

        List<NotificacionDTO> notificacionesRecientes = notificacionRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .sorted((n1, n2) -> n2.getIdNotificacion().compareTo(n1.getIdNotificacion()))
                .limit(5)
                .map(this::mapNotificacionToDTO)
                .collect(Collectors.toList());

        return StudentDashboardDTO.builder()
                .nombreEstudiante(usuario.getNombres() + " " + usuario.getApellidos())
                .solicitudesActivas((int) solicitudesActivas)
                .horasAcumuladas(15) // Placeholder como se discutió
                .solicitudesRecientes(solicitudesRecientes)
                .notificaciones(notificacionesRecientes)
                .build();
    }

    private SolicitudDTO mapSolicitudToDTO(Solicitud s) {
        return SolicitudDTO.builder()
                .idSolicitud(s.getIdSolicitud())
                .codigoSolicitud(s.getCodigoSolicitud())
                .idTipoTramite(s.getTipoTramite().getIdTipoTramite())
                .idUsuario(s.getUsuario().getIdUsuario())
                .idCarrera(s.getCarrera() != null ? s.getCarrera().getIdCarrera() : null)
                .prioridad(s.getPrioridad())
                .estadoActual(s.getEstadoActual())
                .fechaEstimadaFin(s.getFechaEstimadaFin())
                .build();
    }

    private NotificacionDTO mapNotificacionToDTO(Notificacion n) {
        return NotificacionDTO.builder()
                .idNotificacion(n.getIdNotificacion())
                .idTipo(n.getTipoNotificacion().getIdTipo())
                .idUsuario(n.getUsuario().getIdUsuario())
                .idSolicitud(n.getSolicitud() != null ? n.getSolicitud().getIdSolicitud() : null)
                .titulo(n.getTitulo())
                .mensaje(n.getMensaje())
                .estaLeida(n.getEstaLeida())
                .build();
    }
}
