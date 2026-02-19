package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.EtapaProcesamientoDTO;
import com.app.backend.entities.tramites.EtapaProcesamiento;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.EtapaProcesamientoRepository;
import com.app.backend.services.tramites.EtapaProcesamientoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class EtapaProcesamientoServiceImpl implements EtapaProcesamientoService {

    private final EtapaProcesamientoRepository etapaProcesamientoRepository;

    @Override @Transactional(readOnly = true)
    public List<EtapaProcesamientoDTO> listarTodas() { return etapaProcesamientoRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public EtapaProcesamientoDTO obtenerPorId(@NonNull Integer id) { return toDTO(etapaProcesamientoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Etapa no encontrada con id: " + id))); }

    @Override
    public EtapaProcesamientoDTO crear(EtapaProcesamientoDTO dto) {
        EtapaProcesamiento e = EtapaProcesamiento.builder().nombreEtapa(dto.getNombreEtapa()).descripcionEtapa(dto.getDescripcionEtapa()).codigoEtapa(dto.getCodigoEtapa()).build();
        return toDTO(etapaProcesamientoRepository.save(e));
    }

    @Override
    public EtapaProcesamientoDTO actualizar(@NonNull Integer id, EtapaProcesamientoDTO dto) {
        EtapaProcesamiento e = etapaProcesamientoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Etapa no encontrada con id: " + id));
        e.setNombreEtapa(dto.getNombreEtapa()); e.setDescripcionEtapa(dto.getDescripcionEtapa()); e.setCodigoEtapa(dto.getCodigoEtapa());
        return toDTO(etapaProcesamientoRepository.save(e));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!etapaProcesamientoRepository.existsById(id)) throw new RecursoNoEncontradoException("Etapa no encontrada con id: " + id); etapaProcesamientoRepository.deleteById(id); }

    private EtapaProcesamientoDTO toDTO(EtapaProcesamiento e) { return EtapaProcesamientoDTO.builder().idEtapa(e.getIdEtapa()).nombreEtapa(e.getNombreEtapa()).descripcionEtapa(e.getDescripcionEtapa()).codigoEtapa(e.getCodigoEtapa()).build(); }
}
