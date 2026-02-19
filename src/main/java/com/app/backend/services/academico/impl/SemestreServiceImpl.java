package com.app.backend.services.academico.impl;

import com.app.backend.dtos.academico.SemestreDTO;
import com.app.backend.entities.academico.Semestre;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.academico.SemestreRepository;
import com.app.backend.services.academico.SemestreService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class SemestreServiceImpl implements SemestreService {

    private final SemestreRepository semestreRepository;

    @Override @Transactional(readOnly = true)
    public List<SemestreDTO> listarTodos() { return semestreRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public SemestreDTO obtenerPorId(@NonNull Integer id) { return toDTO(semestreRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Semestre no encontrado con id: " + id))); }

    @Override @Transactional(readOnly = true)
    public SemestreDTO obtenerActivo() { return toDTO(semestreRepository.findByEstaActivoTrue().orElseThrow(() -> new RecursoNoEncontradoException("No hay semestre activo"))); }

    @Override
    public SemestreDTO crear(SemestreDTO dto) {
        Semestre s = Semestre.builder().codigoPeriodo(dto.getCodigoPeriodo()).nombrePeriodo(dto.getNombrePeriodo()).fechaInicio(dto.getFechaInicio()).fechaFin(dto.getFechaFin()).estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true).build();
        return toDTO(semestreRepository.save(s));
    }

    @Override
    public SemestreDTO actualizar(@NonNull Integer id, SemestreDTO dto) {
        Semestre s = semestreRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Semestre no encontrado con id: " + id));
        s.setCodigoPeriodo(dto.getCodigoPeriodo()); s.setNombrePeriodo(dto.getNombrePeriodo()); s.setFechaInicio(dto.getFechaInicio()); s.setFechaFin(dto.getFechaFin()); s.setEstaActivo(dto.getEstaActivo());
        return toDTO(semestreRepository.save(s));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!semestreRepository.existsById(id)) throw new RecursoNoEncontradoException("Semestre no encontrado con id: " + id); semestreRepository.deleteById(id); }

    private SemestreDTO toDTO(Semestre s) { return SemestreDTO.builder().idSemestre(s.getIdSemestre()).codigoPeriodo(s.getCodigoPeriodo()).nombrePeriodo(s.getNombrePeriodo()).fechaInicio(s.getFechaInicio()).fechaFin(s.getFechaFin()).estaActivo(s.getEstaActivo()).build(); }
}
