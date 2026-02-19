package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.UsuarioDTO;
import com.app.backend.entities.sistema.Rol;
import com.app.backend.entities.sistema.Usuario;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.sistema.RolRepository;
import com.app.backend.repositories.sistema.UsuarioRepository;
import com.app.backend.services.sistema.UsuarioService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(@NonNull Integer id) {
        return toDTO(usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorCedula(String cedula) {
        return toDTO(usuarioRepository.findByCedula(cedula)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con cédula: " + cedula)));
    }

    @Override
    public UsuarioDTO crear(UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .cedula(dto.getCedula())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .correoPersonal(dto.getCorreoPersonal())
                .correoInstitucional(dto.getCorreoInstitucional())
                .telefono(dto.getTelefono())
                .fechaNacimiento(dto.getFechaNacimiento())
                .genero(dto.getGenero())
                .direccion(dto.getDireccion())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();

        if (dto.getRolesIds() != null && !dto.getRolesIds().isEmpty()) {
            List<Rol> roles = rolRepository.findAllById(dto.getRolesIds());
            usuario.setRoles(roles);
        }

        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO actualizar(@NonNull Integer id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreoPersonal(dto.getCorreoPersonal());
        usuario.setCorreoInstitucional(dto.getCorreoInstitucional());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setGenero(dto.getGenero());
        usuario.setDireccion(dto.getDireccion());
        usuario.setEstado(dto.getEstado());

        if (dto.getRolesIds() != null) {
            List<Rol> roles = rolRepository.findAllById(dto.getRolesIds());
            usuario.setRoles(roles);
        }

        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario u) {
        return UsuarioDTO.builder()
                .idUsuario(u.getIdUsuario())
                .cedula(u.getCedula())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .correoPersonal(u.getCorreoPersonal())
                .correoInstitucional(u.getCorreoInstitucional())
                .telefono(u.getTelefono())
                .fechaNacimiento(u.getFechaNacimiento())
                .genero(u.getGenero())
                .direccion(u.getDireccion())
                .estado(u.getEstado())
                .rolesIds(u.getRoles() != null ? u.getRoles().stream().map(Rol::getIdRol).toList() : null)
                .build();
    }
}
