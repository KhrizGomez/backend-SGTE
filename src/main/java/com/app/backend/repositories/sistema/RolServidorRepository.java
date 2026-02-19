package com.app.backend.repositories.sistema;

import com.app.backend.entities.sistema.RolServidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolServidorRepository extends JpaRepository<RolServidor, Integer> {
    Optional<RolServidor> findByNombreRolDb(String nombreRolDb);
}
