package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.PlazoTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlazoTramiteRepository extends JpaRepository<PlazoTramite, Integer> {
    List<PlazoTramite> findByTipoTramiteIdTipoTramite(Integer idTipoTramite);
}
