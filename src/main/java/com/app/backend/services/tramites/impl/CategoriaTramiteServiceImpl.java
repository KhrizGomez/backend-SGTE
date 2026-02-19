package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.CategoriaTramiteDTO;
import com.app.backend.entities.tramites.CategoriaTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.CategoriaTramiteRepository;
import com.app.backend.services.tramites.CategoriaTramiteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class CategoriaTramiteServiceImpl implements CategoriaTramiteService {

    private final CategoriaTramiteRepository categoriaTramiteRepository;

    @Override @Transactional(readOnly = true)
    public List<CategoriaTramiteDTO> listarTodas() { return categoriaTramiteRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<CategoriaTramiteDTO> listarActivas() { return categoriaTramiteRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public CategoriaTramiteDTO obtenerPorId(@NonNull Integer id) { return toDTO(categoriaTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id: " + id))); }

    @Override
    public CategoriaTramiteDTO crear(CategoriaTramiteDTO dto) {
        CategoriaTramite c = CategoriaTramite.builder().nombreCategoria(dto.getNombreCategoria()).descripcionCategoria(dto.getDescripcionCategoria()).estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true).build();
        return toDTO(categoriaTramiteRepository.save(c));
    }

    @Override
    public CategoriaTramiteDTO actualizar(@NonNull Integer id, CategoriaTramiteDTO dto) {
        CategoriaTramite c = categoriaTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id: " + id));
        c.setNombreCategoria(dto.getNombreCategoria()); c.setDescripcionCategoria(dto.getDescripcionCategoria()); c.setEstaActivo(dto.getEstaActivo());
        return toDTO(categoriaTramiteRepository.save(c));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!categoriaTramiteRepository.existsById(id)) throw new RecursoNoEncontradoException("Categoría no encontrada con id: " + id); categoriaTramiteRepository.deleteById(id); }

    private CategoriaTramiteDTO toDTO(CategoriaTramite c) { return CategoriaTramiteDTO.builder().idCategoria(c.getIdCategoria()).nombreCategoria(c.getNombreCategoria()).descripcionCategoria(c.getDescripcionCategoria()).estaActivo(c.getEstaActivo()).build(); }
}
