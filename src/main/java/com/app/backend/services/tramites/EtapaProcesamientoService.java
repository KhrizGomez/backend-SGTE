package com.app.backend.services.tramites;

import com.app.backend.dtos.tramites.EtapaProcesamientoDTO;
import lombok.NonNull;
import java.util.List;

public interface EtapaProcesamientoService {
    List<EtapaProcesamientoDTO> listarTodas();
    EtapaProcesamientoDTO obtenerPorId(@NonNull Integer id);
    EtapaProcesamientoDTO crear(EtapaProcesamientoDTO dto);
    EtapaProcesamientoDTO actualizar(@NonNull Integer id, EtapaProcesamientoDTO dto);
    void eliminar(@NonNull Integer id);
}
