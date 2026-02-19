package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.DefinicionFlujoDTO;
import com.app.backend.entities.tramites.DefinicionFlujo;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.DefinicionFlujoRepository;
import com.app.backend.repositories.sistema.UsuarioRepository;
import com.app.backend.services.tramites.DefinicionFlujoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class DefinicionFlujoServiceImpl implements DefinicionFlujoService {

    private final DefinicionFlujoRepository definicionFlujoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override @Transactional(readOnly = true)
    public List<DefinicionFlujoDTO> listarTodos() { return definicionFlujoRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<DefinicionFlujoDTO> listarActivos() { return definicionFlujoRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public DefinicionFlujoDTO obtenerPorId(@NonNull Integer id) { return toDTO(definicionFlujoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado con id: " + id))); }

    @Override
    public DefinicionFlujoDTO crear(DefinicionFlujoDTO dto) {
        DefinicionFlujo f = DefinicionFlujo.builder().nombreFlujo(dto.getNombreFlujo()).descripcionFlujo(dto.getDescripcionFlujo()).estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true).version(dto.getVersion() != null ? dto.getVersion() : 1).build();
        if (dto.getCreadoPorId() != null) { f.setCreadoPor(usuarioRepository.findById(dto.getCreadoPorId()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getCreadoPorId()))); }
        return toDTO(definicionFlujoRepository.save(f));
    }

    @Override
    public DefinicionFlujoDTO actualizar(@NonNull Integer id, DefinicionFlujoDTO dto) {
        DefinicionFlujo f = definicionFlujoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado con id: " + id));
        f.setNombreFlujo(dto.getNombreFlujo()); f.setDescripcionFlujo(dto.getDescripcionFlujo()); f.setEstaActivo(dto.getEstaActivo()); f.setVersion(dto.getVersion());
        return toDTO(definicionFlujoRepository.save(f));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!definicionFlujoRepository.existsById(id)) throw new RecursoNoEncontradoException("Flujo no encontrado con id: " + id); definicionFlujoRepository.deleteById(id); }

    private DefinicionFlujoDTO toDTO(DefinicionFlujo f) { return DefinicionFlujoDTO.builder().idFlujo(f.getIdFlujo()).nombreFlujo(f.getNombreFlujo()).descripcionFlujo(f.getDescripcionFlujo()).estaActivo(f.getEstaActivo()).version(f.getVersion()).creadoPorId(f.getCreadoPor() != null ? f.getCreadoPor().getIdUsuario() : null).build(); }
}
