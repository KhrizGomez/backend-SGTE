package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.EsquemaRolDTO;
import lombok.NonNull;
import java.util.List;

public interface EsquemaRolService {
    List<EsquemaRolDTO> listarPorEsquema(@NonNull Integer idEsquema);
    EsquemaRolDTO crear(EsquemaRolDTO dto);
    void eliminar(@NonNull Integer id);
}
