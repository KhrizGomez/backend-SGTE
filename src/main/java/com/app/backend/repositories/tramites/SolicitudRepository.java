package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    Optional<Solicitud> findByCodigoSolicitud(String codigoSolicitud);
    List<Solicitud> findByUsuarioIdUsuario(Integer idUsuario);
    List<Solicitud> findByEstadoActual(String estadoActual);
    List<Solicitud> findByTipoTramiteIdTipoTramite(Integer idTipoTramite);
}
