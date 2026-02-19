package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.RechazoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechazoSolicitudRepository extends JpaRepository<RechazoSolicitud, Integer> {
    List<RechazoSolicitud> findBySolicitudIdSolicitud(Integer idSolicitud);
}
