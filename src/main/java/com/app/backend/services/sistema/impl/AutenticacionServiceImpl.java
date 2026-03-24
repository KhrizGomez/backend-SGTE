package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.AutenticacionRequestDTO;
import com.app.backend.dtos.sistema.AutenticacionRespuestaDTO;
import com.app.backend.entities.sistema.Credencial;
import com.app.backend.entities.sistema.Rol;
import com.app.backend.entities.sistema.Usuario;
import com.app.backend.repositories.sistema.CredencialRepository;
import com.app.backend.services.sistema.AutenticacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutenticacionServiceImpl implements AutenticacionService {

    private final CredencialRepository credencialRepository;

    @Override
    public AutenticacionRespuestaDTO iniciarSesion(AutenticacionRequestDTO peticion) {
        log.info("Intento de inicio de sesión para usuario: {}", peticion.getNombreUsuario());

        // 1. Buscar la credencial por nombre de usuario
        Credencial credencial = credencialRepository
                .findByNombreUsuario(peticion.getNombreUsuario())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado: " + peticion.getNombreUsuario()));

        // 2. Validar contraseña (comparación simple — sin hash por ahora)
        if (!credencial.getHashContrasena().equals(peticion.getContrasena())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        // 3. Validar que el usuario esté activo
        Usuario usuario = credencial.getUsuario();
        if (usuario == null || Boolean.FALSE.equals(usuario.getEstado())) {
            throw new IllegalArgumentException("El usuario no está activo");
        }

        // 4. Obtener rol del usuario
        String rol = usuario.getRoles() != null
                ? usuario.getRoles().stream()
                        .map(Rol::getNombreRol)
                        .findFirst()
                        .orElse("")
                :"";

        log.info("Inicio de sesión exitoso para: {} con roles: {}", peticion.getNombreUsuario(), rol);

        return AutenticacionRespuestaDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(credencial.getNombreUsuario())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correoInstitucional(usuario.getCorreoInstitucional())
                .correoPersonal(usuario.getCorreoPersonal())
                .telefono(usuario.getTelefono())
                .estado(usuario.getEstado())
                .rol(rol)
                .mensaje("Inicio de sesión exitoso")
                .build();
    }
}
