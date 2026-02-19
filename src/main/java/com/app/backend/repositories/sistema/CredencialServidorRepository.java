package com.app.backend.repositories.sistema;

import com.app.backend.entities.sistema.CredencialServidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialServidorRepository extends JpaRepository<CredencialServidor, Integer> {
    Optional<CredencialServidor> findByNombreUsuarioDb(String nombreUsuarioDb);
    Optional<CredencialServidor> findByUsuarioIdUsuario(Integer idUsuario);
}
