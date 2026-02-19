package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.PlazoTramiteDTO;
import com.app.backend.entities.tramites.PlazoTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.PlazoTramiteRepository;
import com.app.backend.repositories.tramites.TipoTramiteRepository;
import com.app.backend.services.tramites.PlazoTramiteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class PlazoTramiteServiceImpl implements PlazoTramiteService {

    private final PlazoTramiteRepository plazoTramiteRepository;
    private final TipoTramiteRepository tipoTramiteRepository;

    @Override @Transactional(readOnly = true)
    public List<PlazoTramiteDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite) { return plazoTramiteRepository.findByTipoTramiteIdTipoTramite(idTipoTramite).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public PlazoTramiteDTO obtenerPorId(@NonNull Integer id) { return toDTO(plazoTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Plazo no encontrado con id: " + id))); }

    @Override
    public PlazoTramiteDTO crear(PlazoTramiteDTO dto) {
        PlazoTramite p = PlazoTramite.builder()
                .tipoTramite(tipoTramiteRepository.findById(dto.getIdTipoTramite()).orElseThrow(() -> new RecursoNoEncontradoException("Tipo trámite no encontrado: " + dto.getIdTipoTramite())))
                .fechaApertura(dto.getFechaApertura()).fechaCierre(dto.getFechaCierre())
                .permiteExtension(dto.getPermiteExtension() != null ? dto.getPermiteExtension() : false)
                .diasMaxExtension(dto.getDiasMaxExtension() != null ? dto.getDiasMaxExtension() : 0).build();
        return toDTO(plazoTramiteRepository.save(p));
    }

    @Override
    public PlazoTramiteDTO actualizar(@NonNull Integer id, PlazoTramiteDTO dto) {
        PlazoTramite p = plazoTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Plazo no encontrado con id: " + id));
        p.setFechaApertura(dto.getFechaApertura()); p.setFechaCierre(dto.getFechaCierre()); p.setPermiteExtension(dto.getPermiteExtension()); p.setDiasMaxExtension(dto.getDiasMaxExtension());
        return toDTO(plazoTramiteRepository.save(p));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!plazoTramiteRepository.existsById(id)) throw new RecursoNoEncontradoException("Plazo no encontrado con id: " + id); plazoTramiteRepository.deleteById(id); }

    private PlazoTramiteDTO toDTO(PlazoTramite p) { return PlazoTramiteDTO.builder().idPlazo(p.getIdPlazo()).idTipoTramite(p.getTipoTramite().getIdTipoTramite()).fechaApertura(p.getFechaApertura()).fechaCierre(p.getFechaCierre()).permiteExtension(p.getPermiteExtension()).diasMaxExtension(p.getDiasMaxExtension()).build(); }
}
