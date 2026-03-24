package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.TipoTramiteDTO;
import com.app.backend.entities.tramites.PlantillaTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.CategoriaRepository;
import com.app.backend.repositories.tramites.FlujoTrabajoRepository;
import com.app.backend.repositories.tramites.PlantillaTramiteRepository;
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

    private final PlantillaTramiteRepository plantillaTramiteRepository;
    private final CategoriaRepository categoriaRepository;
    private final FlujoTrabajoRepository flujoTrabajoRepository;

    @Override @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarTodos() { return plantillaTramiteRepository.findAll().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarActivos() { return plantillaTramiteRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarPorCategoria(@NonNull Integer idCategoria) { return plantillaTramiteRepository.findByCategoriaIdCategoria(idCategoria).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public TipoTramiteDTO obtenerPorId(@NonNull Integer id) { return toDTO(plantillaTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada con id: " + id))); }

    @Override
    public TipoTramiteDTO crear(TipoTramiteDTO dto) {
        PlantillaTramite t = PlantillaTramite.builder()
                .nombrePlantilla(dto.getNombreTramite())
                .descripcionPlantilla(dto.getDescripcionTramite())
                .diasResolucionEstimados(dto.getDiasEstimados() != null ? dto.getDiasEstimados() : 5)
                .estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true)
                .disponibleExternos(dto.getDisponibleExternos() != null ? dto.getDisponibleExternos() : false)
                .build();
        if (dto.getIdCategoria() != null) t.setCategoria(categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        if (dto.getIdFlujo() != null) t.setFlujoTrabajo(flujoTrabajoRepository.findById(dto.getIdFlujo()).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado: " + dto.getIdFlujo())));
        return toDTO(plantillaTramiteRepository.save(t));
    }

    @Override
    public TipoTramiteDTO actualizar(@NonNull Integer id, TipoTramiteDTO dto) {
        PlantillaTramite t = plantillaTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada con id: " + id));
        t.setNombrePlantilla(dto.getNombreTramite()); t.setDescripcionPlantilla(dto.getDescripcionTramite()); t.setDiasResolucionEstimados(dto.getDiasEstimados()); t.setEstaActivo(dto.getEstaActivo()); t.setDisponibleExternos(dto.getDisponibleExternos());
        if (dto.getIdCategoria() != null) t.setCategoria(categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        if (dto.getIdFlujo() != null) t.setFlujoTrabajo(flujoTrabajoRepository.findById(dto.getIdFlujo()).orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado: " + dto.getIdFlujo())));
        return toDTO(plantillaTramiteRepository.save(t));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!plantillaTramiteRepository.existsById(id)) throw new RecursoNoEncontradoException("Plantilla no encontrada con id: " + id); plantillaTramiteRepository.deleteById(id); }

    private TipoTramiteDTO toDTO(PlantillaTramite t) {
        return TipoTramiteDTO.builder()
                .idTipoTramite(t.getIdPlantilla())
                .nombreTramite(t.getNombrePlantilla())
                .descripcionTramite(t.getDescripcionPlantilla())
                .idCategoria(t.getCategoria() != null ? t.getCategoria().getIdCategoria() : null)
                .idFlujo(t.getFlujoTrabajo() != null ? t.getFlujoTrabajo().getIdFlujo() : null)
                .diasEstimados(t.getDiasResolucionEstimados())
                .estaActivo(t.getEstaActivo())
                .disponibleExternos(t.getDisponibleExternos())
                .build();
    }
}
