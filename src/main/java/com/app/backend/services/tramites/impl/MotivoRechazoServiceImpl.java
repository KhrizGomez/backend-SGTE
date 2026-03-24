package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.MotivoRechazoDTO;
import com.app.backend.entities.tramites.MotivoRechazo;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.CategoriaRepository;
import com.app.backend.repositories.tramites.MotivoRechazoRepository;
import com.app.backend.services.tramites.MotivoRechazoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class MotivoRechazoServiceImpl implements MotivoRechazoService {

    private final MotivoRechazoRepository motivoRechazoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override @Transactional(readOnly = true)
    public List<MotivoRechazoDTO> listarTodos() { return motivoRechazoRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<MotivoRechazoDTO> listarActivos() { return motivoRechazoRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public MotivoRechazoDTO obtenerPorId(@NonNull Integer id) { return toDTO(motivoRechazoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Motivo rechazo no encontrado con id: " + id))); }

    @Override
    public MotivoRechazoDTO crear(MotivoRechazoDTO dto) {
        MotivoRechazo m = MotivoRechazo.builder().codigoMotivo(dto.getCodigoMotivo()).nombreMotivo(dto.getNombreMotivo()).descripcionMotivo(dto.getDescripcionMotivo()).estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true).build();
        if (dto.getIdCategoria() != null) m.setCategoria(categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        return toDTO(motivoRechazoRepository.save(m));
    }

    @Override
    public MotivoRechazoDTO actualizar(@NonNull Integer id, MotivoRechazoDTO dto) {
        MotivoRechazo m = motivoRechazoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Motivo rechazo no encontrado con id: " + id));
        m.setCodigoMotivo(dto.getCodigoMotivo()); m.setNombreMotivo(dto.getNombreMotivo()); m.setDescripcionMotivo(dto.getDescripcionMotivo()); m.setEstaActivo(dto.getEstaActivo());
        if (dto.getIdCategoria() != null) m.setCategoria(categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        return toDTO(motivoRechazoRepository.save(m));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!motivoRechazoRepository.existsById(id)) throw new RecursoNoEncontradoException("Motivo rechazo no encontrado con id: " + id); motivoRechazoRepository.deleteById(id); }

    private MotivoRechazoDTO toDTO(MotivoRechazo m) { return MotivoRechazoDTO.builder().idMotivo(m.getIdMotivo()).codigoMotivo(m.getCodigoMotivo()).nombreMotivo(m.getNombreMotivo()).descripcionMotivo(m.getDescripcionMotivo()).idCategoria(m.getCategoria() != null ? m.getCategoria().getIdCategoria() : null).estaActivo(m.getEstaActivo()).build(); }
}
