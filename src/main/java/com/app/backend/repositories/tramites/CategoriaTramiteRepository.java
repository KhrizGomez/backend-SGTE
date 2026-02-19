package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.CategoriaTramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaTramiteRepository extends JpaRepository<CategoriaTramite, Integer> {
    List<CategoriaTramite> findByEstaActivoTrue();
}
