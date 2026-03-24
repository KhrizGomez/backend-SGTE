package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.DefinicionFlujoDTO;
import com.app.backend.entities.tramites.FlujoTrabajo;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.FlujoTrabajoRepository;
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

    private final FlujoTrabajoRepository flujoTrabajoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override @Transactional(readOnly = true)
    public List<DefinicionFlujoDTO> listarTodos() { return flujoTrabajoRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<DefinicionFlujoDTO> listarActivos() { return flujoTrabajoRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public DefinicionFlujoDTO obtenerPorId(@NonNull Integer id) { return toDTO(flujoTrabajoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado con id: " + id))); }

    @Override
    public DefinicionFlujoDTO crear(DefinicionFlujoDTO dto) {
        FlujoTrabajo f = FlujoTrabajo.builder().nombreFlujo(dto.getNombreFlujo()).descripcion(dto.getDescripcionFlujo()).estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true).version(dto.getVersion() != null ? dto.getVersion() : 1).build();
        if (dto.getCreadoPorId() != null) { f.setCreadoPor(usuarioRepository.findById(dto.getCreadoPorId()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getCreadoPorId()))); }
        return toDTO(flujoTrabajoRepository.save(f));
    }

    @Override
    public DefinicionFlujoDTO actualizar(@NonNull Integer id, DefinicionFlujoDTO dto) {
        FlujoTrabajo f = flujoTrabajoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado con id: " + id));
        f.setNombreFlujo(dto.getNombreFlujo()); f.setDescripcion(dto.getDescripcionFlujo()); f.setEstaActivo(dto.getEstaActivo()); f.setVersion(dto.getVersion());
        return toDTO(flujoTrabajoRepository.save(f));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!flujoTrabajoRepository.existsById(id)) throw new RecursoNoEncontradoException("Flujo no encontrado con id: " + id); flujoTrabajoRepository.deleteById(id); }

    private DefinicionFlujoDTO toDTO(FlujoTrabajo f) { return DefinicionFlujoDTO.builder().idFlujo(f.getIdFlujo()).nombreFlujo(f.getNombreFlujo()).descripcionFlujo(f.getDescripcion()).estaActivo(f.getEstaActivo()).version(f.getVersion()).creadoPorId(f.getCreadoPor() != null ? f.getCreadoPor().getIdUsuario() : null).build(); }
}
