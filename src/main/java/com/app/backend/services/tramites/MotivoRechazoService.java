package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.MotivoRechazoDTO;
import lombok.NonNull;
import java.util.List;

public interface MotivoRechazoService {
    List<MotivoRechazoDTO> listarTodos();
    List<MotivoRechazoDTO> listarActivos();
    MotivoRechazoDTO obtenerPorId(@NonNull Integer id);
    MotivoRechazoDTO crear(MotivoRechazoDTO dto);
    MotivoRechazoDTO actualizar(@NonNull Integer id, MotivoRechazoDTO dto);
    void eliminar(@NonNull Integer id);
}
