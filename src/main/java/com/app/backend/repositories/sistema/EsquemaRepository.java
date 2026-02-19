package com.app.backend.repositories.sistema;

import com.app.backend.entities.sistema.Esquema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsquemaRepository extends JpaRepository<Esquema, Integer> {
    Optional<Esquema> findByNombreEsquema(String nombreEsquema);
}
