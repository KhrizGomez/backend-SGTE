package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.PermisoServidorDTO;
import lombok.NonNull;
import java.util.List;

public interface PermisoServidorService {
    List<PermisoServidorDTO> listarPorRol(@NonNull Integer idRolSrv);
    PermisoServidorDTO crear(PermisoServidorDTO dto);
    void eliminar(@NonNull Integer id);
}
