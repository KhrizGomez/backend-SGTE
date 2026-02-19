package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.RequisitoTramiteDTO;
import com.app.backend.entities.tramites.RequisitoTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.RequisitoTramiteRepository;
import com.app.backend.repositories.tramites.TipoTramiteRepository;
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

    private final RequisitoTramiteRepository requisitoTramiteRepository;
    private final TipoTramiteRepository tipoTramiteRepository;

    @Override @Transactional(readOnly = true)
    public List<RequisitoTramiteDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite) { return requisitoTramiteRepository.findByTipoTramiteIdTipoTramiteOrderByNumeroOrdenAsc(idTipoTramite).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public RequisitoTramiteDTO obtenerPorId(@NonNull Integer id) { return toDTO(requisitoTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Requisito no encontrado con id: " + id))); }

    @Override
    public RequisitoTramiteDTO crear(RequisitoTramiteDTO dto) {
        RequisitoTramite r = RequisitoTramite.builder()
                .tipoTramite(tipoTramiteRepository.findById(dto.getIdTipoTramite()).orElseThrow(() -> new RecursoNoEncontradoException("Tipo trámite no encontrado: " + dto.getIdTipoTramite())))
                .nombreRequisito(dto.getNombreRequisito()).descripcionRequisito(dto.getDescripcionRequisito())
                .esObligatorio(dto.getEsObligatorio() != null ? dto.getEsObligatorio() : true)
                .tipoDocumento(dto.getTipoDocumento()).tamanoMaxMb(dto.getTamanoMaxMb() != null ? dto.getTamanoMaxMb() : 10)
                .extensionesPermitidas(dto.getExtensionesPermitidas()).numeroOrden(dto.getNumeroOrden() != null ? dto.getNumeroOrden() : 0).build();
        return toDTO(requisitoTramiteRepository.save(r));
    }

    @Override
    public RequisitoTramiteDTO actualizar(@NonNull Integer id, RequisitoTramiteDTO dto) {
        RequisitoTramite r = requisitoTramiteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Requisito no encontrado con id: " + id));
        r.setNombreRequisito(dto.getNombreRequisito()); r.setDescripcionRequisito(dto.getDescripcionRequisito()); r.setEsObligatorio(dto.getEsObligatorio());
        r.setTipoDocumento(dto.getTipoDocumento()); r.setTamanoMaxMb(dto.getTamanoMaxMb()); r.setExtensionesPermitidas(dto.getExtensionesPermitidas()); r.setNumeroOrden(dto.getNumeroOrden());
        return toDTO(requisitoTramiteRepository.save(r));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!requisitoTramiteRepository.existsById(id)) throw new RecursoNoEncontradoException("Requisito no encontrado con id: " + id); requisitoTramiteRepository.deleteById(id); }

    private RequisitoTramiteDTO toDTO(RequisitoTramite r) { return RequisitoTramiteDTO.builder().idRequisito(r.getIdRequisito()).idTipoTramite(r.getTipoTramite().getIdTipoTramite()).nombreRequisito(r.getNombreRequisito()).descripcionRequisito(r.getDescripcionRequisito()).esObligatorio(r.getEsObligatorio()).tipoDocumento(r.getTipoDocumento()).tamanoMaxMb(r.getTamanoMaxMb()).extensionesPermitidas(r.getExtensionesPermitidas()).numeroOrden(r.getNumeroOrden()).build(); }
}
