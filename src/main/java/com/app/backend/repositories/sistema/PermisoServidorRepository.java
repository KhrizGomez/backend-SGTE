package com.app.backend.repositories.sistema;

import com.app.backend.entities.sistema.PermisoServidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoServidorRepository extends JpaRepository<PermisoServidor, Integer> {
    List<PermisoServidor> findByRolServidorIdRolSrv(Integer idRolSrv);
}
