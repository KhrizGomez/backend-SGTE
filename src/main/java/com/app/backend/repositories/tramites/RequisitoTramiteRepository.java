package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.RequisitoTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisitoTramiteRepository extends JpaRepository<RequisitoTramite, Integer> {
    List<RequisitoTramite> findByTipoTramiteIdTipoTramiteOrderByNumeroOrdenAsc(Integer idTipoTramite);
}
