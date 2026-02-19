package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.DefinicionFlujo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefinicionFlujoRepository extends JpaRepository<DefinicionFlujo, Integer> {
    List<DefinicionFlujo> findByEstaActivoTrue();
}
