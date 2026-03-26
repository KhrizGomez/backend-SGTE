package com.app.backend.services.documentos;

import com.app.backend.dtos.documentos.response.DocumentoAdjuntoDTO;
import lombok.NonNull;
import java.util.List;

public interface DocumentoAdjuntoService {
    List<DocumentoAdjuntoDTO> listarPorSolicitud(@NonNull Integer idSolicitud);
    DocumentoAdjuntoDTO obtenerPorId(@NonNull Integer id);
    DocumentoAdjuntoDTO crear(DocumentoAdjuntoDTO dto);
    void eliminar(@NonNull Integer id);
}
