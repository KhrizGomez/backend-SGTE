package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.TipoTramiteDTO;
import com.app.backend.entities.tramites.TipoTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.CategoriaTramiteRepository;
import com.app.backend.repositories.tramites.DefinicionFlujoRepository;
import com.app.backend.repositories.tramites.TipoTramiteRepository;
import com.app.backend.services.tramites.TipoTramiteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class TipoTramiteServiceImpl implements TipoTramiteService {

    private final TipoTramiteRepository tipoTramiteRepository;
    private final CategoriaTramiteRepository categoriaTramiteRepository;
    private final DefinicionFlujoRepository definicionFlujoRepository;

    @Override @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarTodos() { return tipoTramiteRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarActivos() { return tipoTramiteRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarPorCategoria(@NonNull Integer idCategoria) { return tipoTramiteRepository.findByCategoriaIdCategoria(idCategoria).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public TipoTramiteDTO obtenerPorId(@NonNull Integer id) { return toDTO(tipoTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Tipo trámite no encontrado con id: " + id))); }

    @Override
    public TipoTramiteDTO crear(TipoTramiteDTO dto) {
        TipoTramite t = TipoTramite.builder().nombreTramite(dto.getNombreTramite()).descripcionTramite(dto.getDescripcionTramite()).diasEstimados(dto.getDiasEstimados() != null ? dto.getDiasEstimados() : 5).estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true).disponibleExternos(dto.getDisponibleExternos() != null ? dto.getDisponibleExternos() : false).build();
        if (dto.getIdCategoria() != null) t.setCategoria(categoriaTramiteRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        if (dto.getIdFlujo() != null) t.setDefinicionFlujo(definicionFlujoRepository.findById(dto.getIdFlujo()).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado: " + dto.getIdFlujo())));
        return toDTO(tipoTramiteRepository.save(t));
    }

    @Override
    public TipoTramiteDTO actualizar(@NonNull Integer id, TipoTramiteDTO dto) {
        TipoTramite t = tipoTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Tipo trámite no encontrado con id: " + id));
        t.setNombreTramite(dto.getNombreTramite()); t.setDescripcionTramite(dto.getDescripcionTramite()); t.setDiasEstimados(dto.getDiasEstimados()); t.setEstaActivo(dto.getEstaActivo()); t.setDisponibleExternos(dto.getDisponibleExternos());
        if (dto.getIdCategoria() != null) t.setCategoria(categoriaTramiteRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        if (dto.getIdFlujo() != null) t.setDefinicionFlujo(definicionFlujoRepository.findById(dto.getIdFlujo()).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado: " + dto.getIdFlujo())));
        return toDTO(tipoTramiteRepository.save(t));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!tipoTramiteRepository.existsById(id)) throw new RecursoNoEncontradoException("Tipo trámite no encontrado con id: " + id); tipoTramiteRepository.deleteById(id); }

    private TipoTramiteDTO toDTO(TipoTramite t) { return TipoTramiteDTO.builder().idTipoTramite(t.getIdTipoTramite()).nombreTramite(t.getNombreTramite()).descripcionTramite(t.getDescripcionTramite()).idCategoria(t.getCategoria() != null ? t.getCategoria().getIdCategoria() : null).idFlujo(t.getDefinicionFlujo() != null ? t.getDefinicionFlujo().getIdFlujo() : null).diasEstimados(t.getDiasEstimados()).estaActivo(t.getEstaActivo()).disponibleExternos(t.getDisponibleExternos()).build(); }
}
