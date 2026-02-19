package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.EsquemaDTO;
import com.app.backend.entities.sistema.Esquema;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.sistema.EsquemaRepository;
import com.app.backend.services.sistema.EsquemaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class EsquemaServiceImpl implements EsquemaService {

    private final EsquemaRepository esquemaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EsquemaDTO> listarTodos() {
        return esquemaRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EsquemaDTO obtenerPorId(@NonNull Integer id) {
        return toDTO(esquemaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Esquema no encontrado con id: " + id)));
    }

    @Override
    public EsquemaDTO crear(EsquemaDTO dto) {
        Esquema esquema = Esquema.builder()
                .nombreEsquema(dto.getNombreEsquema())
                .descripcion(dto.getDescripcion())
                .estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true)
                .build();
        return toDTO(esquemaRepository.save(esquema));
    }

    @Override
    public EsquemaDTO actualizar(@NonNull Integer id, EsquemaDTO dto) {
        Esquema esquema = esquemaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Esquema no encontrado con id: " + id));
        esquema.setNombreEsquema(dto.getNombreEsquema());
        esquema.setDescripcion(dto.getDescripcion());
        esquema.setEstaActivo(dto.getEstaActivo());
        return toDTO(esquemaRepository.save(esquema));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!esquemaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Esquema no encontrado con id: " + id);
        }
        esquemaRepository.deleteById(id);
    }

    private EsquemaDTO toDTO(Esquema e) {
        return EsquemaDTO.builder()
                .idEsquema(e.getIdEsquema())
                .nombreEsquema(e.getNombreEsquema())
                .descripcion(e.getDescripcion())
                .estaActivo(e.getEstaActivo())
                .fechaCreacion(e.getFechaCreacion())
                .build();
    }
}
