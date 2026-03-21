package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.LoginRequestDTO;
import com.app.backend.dtos.sistema.LoginRespuestaDTO;
import com.app.backend.entities.sistema.Credencial;
import com.app.backend.entities.sistema.Rol;
import com.app.backend.entities.sistema.Usuario;
import com.app.backend.repositories.sistema.CredencialRepository;
import com.app.backend.services.sistema.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {

    private final CredencialRepository credencialRepository;

    @Override
    public LoginRespuestaDTO login(LoginRequestDTO request) {
        log.info("Intento de login para usuario: {}", request.getNombreUsuario());

        // 1. Buscar la credencial por nombre de usuario
        Credencial credencial = credencialRepository
                .findByNombreUsuario(request.getNombreUsuario())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado: " + request.getNombreUsuario()));

        // 2. Validar contraseña (comparación simple — sin hash por ahora)
        if (!credencial.getHashContrasena().equals(request.getContrasena())) {
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

        log.info("Login exitoso para: {} con roles: {}", request.getNombreUsuario(), rol);

        return LoginRespuestaDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(credencial.getNombreUsuario())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correoInstitucional(usuario.getCorreoInstitucional())
                .correoPersonal(usuario.getCorreoPersonal())
                .telefono(usuario.getTelefono())
                .estado(usuario.getEstado())
                .rol(rol)
                .mensaje("Login exitoso")
                .build();
    }
}
