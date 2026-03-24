package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.HistorialSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialSolicitudRepository extends JpaRepository<HistorialSolicitud, Integer> {
    List<HistorialSolicitud> findBySolicitudIdSolicitudOrderByFechaEntradaAsc(Integer idSolicitud);
}
