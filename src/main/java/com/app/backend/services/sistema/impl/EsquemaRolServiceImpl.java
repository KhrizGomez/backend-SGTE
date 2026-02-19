package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.EsquemaRolDTO;
import com.app.backend.entities.sistema.Esquema;
import com.app.backend.entities.sistema.EsquemaRol;
import com.app.backend.entities.sistema.RolServidor;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.sistema.EsquemaRepository;
import com.app.backend.repositories.sistema.EsquemaRolRepository;
import com.app.backend.repositories.sistema.RolServidorRepository;
import com.app.backend.services.sistema.EsquemaRolService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class EsquemaRolServiceImpl implements EsquemaRolService {

    private final EsquemaRolRepository esquemaRolRepository;
    private final EsquemaRepository esquemaRepository;
    private final RolServidorRepository rolServidorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EsquemaRolDTO> listarPorEsquema(@NonNull Integer idEsquema) {
        return esquemaRolRepository.findByEsquemaIdEsquema(idEsquema).stream().map(this::toDTO).toList();
    }

    @Override
    public EsquemaRolDTO crear(EsquemaRolDTO dto) {
        Esquema esquema = esquemaRepository.findById(dto.getIdEsquema())
                .orElseThrow(() -> new RecursoNoEncontradoException("Esquema no encontrado con id: " + dto.getIdEsquema()));
        RolServidor rol = rolServidorRepository.findById(dto.getIdRolSrv())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol servidor no encontrado con id: " + dto.getIdRolSrv()));

        EsquemaRol er = EsquemaRol.builder()
                .esquema(esquema)
                .rolServidor(rol)
                .nivelAcceso(dto.getNivelAcceso())
                .puedeCrearObjetos(dto.getPuedeCrearObjetos())
                .puedeEliminar(dto.getPuedeEliminar())
                .estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true)
                .build();
        return toDTO(esquemaRolRepository.save(er));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!esquemaRolRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("EsquemaRol no encontrado con id: " + id);
        }
        esquemaRolRepository.deleteById(id);
    }

    private EsquemaRolDTO toDTO(EsquemaRol er) {
        return EsquemaRolDTO.builder()
                .idEsquemaRol(er.getIdEsquemaRol())
                .idEsquema(er.getEsquema().getIdEsquema())
                .idRolSrv(er.getRolServidor().getIdRolSrv())
                .nivelAcceso(er.getNivelAcceso())
                .puedeCrearObjetos(er.getPuedeCrearObjetos())
                .puedeEliminar(er.getPuedeEliminar())
                .estaActivo(er.getEstaActivo())
                .fechaAsignacion(er.getFechaAsignacion())
                .build();
    }
}
