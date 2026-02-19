package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.SeguimientoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoSolicitudRepository extends JpaRepository<SeguimientoSolicitud, Integer> {
    List<SeguimientoSolicitud> findBySolicitudIdSolicitudOrderByFechaEntradaAsc(Integer idSolicitud);
}
