package com.app.backend.repositories.academico;

import com.app.backend.entities.academico.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
    Optional<Semestre> findByCodigoPeriodo(String codigoPeriodo);
    Optional<Semestre> findByEstaActivoTrue();
}
