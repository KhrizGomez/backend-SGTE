package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.EtapaProcesamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtapaProcesamientoRepository extends JpaRepository<EtapaProcesamiento, Integer> {
    Optional<EtapaProcesamiento> findByCodigoEtapa(String codigoEtapa);
}
