package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.TipoTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoTramiteRepository extends JpaRepository<TipoTramite, Integer> {
    List<TipoTramite> findByEstaActivoTrue();
    List<TipoTramite> findByCategoriaIdCategoria(Integer idCategoria);
}
