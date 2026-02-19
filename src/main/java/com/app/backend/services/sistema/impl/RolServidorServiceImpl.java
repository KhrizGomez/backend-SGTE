package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.RolServidorDTO;
import com.app.backend.entities.sistema.RolServidor;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.sistema.RolServidorRepository;
import com.app.backend.services.sistema.RolServidorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class RolServidorServiceImpl implements RolServidorService {

    private final RolServidorRepository rolServidorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RolServidorDTO> listarTodos() {
        return rolServidorRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RolServidorDTO obtenerPorId(@NonNull Integer id) {
        return toDTO(rolServidorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol servidor no encontrado con id: " + id)));
    }

    @Override
    public RolServidorDTO crear(RolServidorDTO dto) {
        RolServidor rol = RolServidor.builder()
                .nombreRolDb(dto.getNombreRolDb())
                .descripcion(dto.getDescripcion())
                .estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true)
                .build();
        return toDTO(rolServidorRepository.save(rol));
    }

    @Override
    public RolServidorDTO actualizar(@NonNull Integer id, RolServidorDTO dto) {
        RolServidor rol = rolServidorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol servidor no encontrado con id: " + id));
        rol.setNombreRolDb(dto.getNombreRolDb());
        rol.setDescripcion(dto.getDescripcion());
        rol.setEstaActivo(dto.getEstaActivo());
        return toDTO(rolServidorRepository.save(rol));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!rolServidorRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Rol servidor no encontrado con id: " + id);
        }
        rolServidorRepository.deleteById(id);
    }

    private RolServidorDTO toDTO(RolServidor r) {
        return RolServidorDTO.builder()
                .idRolSrv(r.getIdRolSrv())
                .nombreRolDb(r.getNombreRolDb())
                .descripcion(r.getDescripcion())
                .estaActivo(r.getEstaActivo())
                .build();
    }
}
