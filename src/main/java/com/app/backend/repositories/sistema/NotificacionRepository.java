package com.app.backend.repositories.sistema;

import com.app.backend.entities.sistema.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUsuarioIdUsuario(Integer idUsuario);
    List<Notificacion> findByUsuarioIdUsuarioAndEstaLeidaFalse(Integer idUsuario);
    List<Notificacion> findBySolicitudIdSolicitud(Integer idSolicitud);
}
