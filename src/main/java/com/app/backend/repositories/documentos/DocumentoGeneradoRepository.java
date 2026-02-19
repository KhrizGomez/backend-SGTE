package com.app.backend.repositories.documentos;

import com.app.backend.entities.documentos.DocumentoGenerado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoGeneradoRepository extends JpaRepository<DocumentoGenerado, Integer> {
    List<DocumentoGenerado> findBySolicitudIdSolicitud(Integer idSolicitud);
    Optional<DocumentoGenerado> findByCodigoDocumento(String codigoDocumento);
}
