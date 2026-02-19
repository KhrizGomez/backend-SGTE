package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.PermisoServidorDTO;
import com.app.backend.entities.sistema.PermisoServidor;
import com.app.backend.entities.sistema.RolServidor;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.sistema.PermisoServidorRepository;
import com.app.backend.repositories.sistema.RolServidorRepository;
import com.app.backend.services.sistema.PermisoServidorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class PermisoServidorServiceImpl implements PermisoServidorService {

    private final PermisoServidorRepository permisoRepository;
    private final RolServidorRepository rolServidorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermisoServidorDTO> listarPorRol(@NonNull Integer idRolSrv) {
        return permisoRepository.findByRolServidorIdRolSrv(idRolSrv).stream().map(this::toDTO).toList();
    }

    @Override
    public PermisoServidorDTO crear(PermisoServidorDTO dto) {
        RolServidor rol = rolServidorRepository.findById(dto.getIdRolSrv())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol servidor no encontrado con id: " + dto.getIdRolSrv()));

        PermisoServidor permiso = PermisoServidor.builder()
                .rolServidor(rol)
                .tipoObjeto(dto.getTipoObjeto())
                .nombreObjeto(dto.getNombreObjeto())
                .privilegio(dto.getPrivilegio())
                .build();
        return toDTO(permisoRepository.save(permiso));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!permisoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Permiso no encontrado con id: " + id);
        }
        permisoRepository.deleteById(id);
    }

    private PermisoServidorDTO toDTO(PermisoServidor p) {
        return PermisoServidorDTO.builder()
                .idPermisoSrv(p.getIdPermisoSrv())
                .idRolSrv(p.getRolServidor().getIdRolSrv())
                .tipoObjeto(p.getTipoObjeto())
                .nombreObjeto(p.getNombreObjeto())
                .privilegio(p.getPrivilegio())
                .build();
    }
}
