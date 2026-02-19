package com.app.backend.repositories.sistema;

import com.app.backend.entities.sistema.EsquemaRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsquemaRolRepository extends JpaRepository<EsquemaRol, Integer> {
    List<EsquemaRol> findByEsquemaIdEsquema(Integer idEsquema);
    List<EsquemaRol> findByRolServidorIdRolSrv(Integer idRolSrv);
}
