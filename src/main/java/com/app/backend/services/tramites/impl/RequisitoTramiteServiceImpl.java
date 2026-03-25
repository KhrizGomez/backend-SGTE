package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.response.RequisitoTramiteResponseDTO;
import com.app.backend.entities.tramites.RequisitoPlantilla;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.RequisitoPlantillaRepository;
import com.app.backend.repositories.tramites.PlantillaTramiteRepository;
import com.app.backend.services.tramites.RequisitoTramiteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class RequisitoTramiteServiceImpl implements RequisitoTramiteService {

    private final RequisitoPlantillaRepository requisitoPlantillaRepository;
    private final PlantillaTramiteRepository plantillaTramiteRepository;

    @Override @Transactional(readOnly = true)
    public List<RequisitoTramiteResponseDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite) { return requisitoPlantillaRepository.findByPlantillaIdPlantillaOrderByNumeroOrdenAsc(idTipoTramite).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public RequisitoTramiteResponseDTO obtenerPorId(@NonNull Integer id) { return toDTO(requisitoPlantillaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Requisito no encontrado con id: " + id))); }

    @Override
    public RequisitoTramiteResponseDTO crear(RequisitoTramiteResponseDTO dto) {
        RequisitoPlantilla r = RequisitoPlantilla.builder()
                .plantilla(plantillaTramiteRepository.findById(dto.getIdTipoTramite()).orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada: " + dto.getIdTipoTramite())))
                .nombreRequisito(dto.getNombreRequisito()).descripcionRequisito(dto.getDescripcionRequisito())
                .esObligatorio(dto.getEsObligatorio() != null ? dto.getEsObligatorio() : true)
                .tipoDocumento(dto.getTipoDocumento()).tamanoMaxMb(dto.getTamanoMaxMb() != null ? dto.getTamanoMaxMb() : 10)
                .extensionesPermitidas(dto.getExtensionesPermitidas()).numeroOrden(dto.getNumeroOrden() != null ? dto.getNumeroOrden() : 0).build();
        return toDTO(requisitoPlantillaRepository.save(r));
    }

    @Override
    public RequisitoTramiteResponseDTO actualizar(@NonNull Integer id, RequisitoTramiteResponseDTO dto) {
        RequisitoPlantilla r = requisitoPlantillaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Requisito no encontrado con id: " + id));
        r.setNombreRequisito(dto.getNombreRequisito()); r.setDescripcionRequisito(dto.getDescripcionRequisito()); r.setEsObligatorio(dto.getEsObligatorio());
        r.setTipoDocumento(dto.getTipoDocumento()); r.setTamanoMaxMb(dto.getTamanoMaxMb()); r.setExtensionesPermitidas(dto.getExtensionesPermitidas()); r.setNumeroOrden(dto.getNumeroOrden());
        return toDTO(requisitoPlantillaRepository.save(r));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!requisitoPlantillaRepository.existsById(id)) throw new RecursoNoEncontradoException("Requisito no encontrado con id: " + id); requisitoPlantillaRepository.deleteById(id); }

    private RequisitoTramiteResponseDTO toDTO(RequisitoPlantilla r) { return RequisitoTramiteResponseDTO.builder().idRequisito(r.getIdRequisito()).idTipoTramite(r.getPlantilla().getIdPlantilla()).nombreRequisito(r.getNombreRequisito()).descripcionRequisito(r.getDescripcionRequisito()).esObligatorio(r.getEsObligatorio()).tipoDocumento(r.getTipoDocumento()).tamanoMaxMb(r.getTamanoMaxMb()).extensionesPermitidas(r.getExtensionesPermitidas()).numeroOrden(r.getNumeroOrden()).build(); }
}

