package com.app.backend.services.sistema.impl;

import com.app.backend.dtos.sistema.AutenticacionRequestDTO;
import com.app.backend.dtos.sistema.AutenticacionRespuestaDTO;
import com.app.backend.entities.academico.Coordinador;
import com.app.backend.entities.academico.Decano;
import com.app.backend.entities.academico.Estudiante;
import com.app.backend.entities.sistema.Credencial;
import com.app.backend.entities.sistema.Rol;
import com.app.backend.entities.sistema.Usuario;
import com.app.backend.repositories.academico.CoordinadorRepository;
import com.app.backend.repositories.academico.DecanoRepository;
import com.app.backend.repositories.academico.EstudianteRepository;
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
    private final EstudianteRepository estudianteRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final DecanoRepository decanoRepository;

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
        Rol primerRol = usuario.getRoles() != null
                ? usuario.getRoles().stream().findFirst().orElse(null)
                : null;
        Integer idRol = primerRol != null ? primerRol.getIdRol() : null;
        String rol = primerRol != null ? primerRol.getNombreRol() : "";

        // 5. Obtener carrera si el usuario es estudiante o coordinador
        Estudiante estudiante = estudianteRepository
                .findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElse(null);
        Coordinador coordinador = coordinadorRepository
                .findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElse(null);
        Decano decano = decanoRepository
                .findByUsuarioIdUsuario(usuario.getIdUsuario())
                .orElse(null);

        Integer idCarrera = null;
        String carrera = null;
        if (estudiante != null && estudiante.getCarrera() != null) {
            idCarrera = estudiante.getCarrera().getIdCarrera();
            carrera = estudiante.getCarrera().getNombreCarrera();
        } else if (coordinador != null && coordinador.getCarrera() != null) {
            idCarrera = coordinador.getCarrera().getIdCarrera();
            carrera = coordinador.getCarrera().getNombreCarrera();
        }

        Integer idFacultad = decano != null && decano.getFacultad() != null
                ? decano.getFacultad().getIdFacultad() : null;
        String facultad = decano != null && decano.getFacultad() != null
                ? decano.getFacultad().getNombreFacultad() : null;

        log.info("Inicio de sesión exitoso para: {} con rol: {}", peticion.getNombreUsuario(), rol);

        return AutenticacionRespuestaDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correoInstitucional(usuario.getCorreoInstitucional())
                .correoPersonal(usuario.getCorreoPersonal())
                .telefono(usuario.getTelefono())
                .estado(usuario.getEstado())
                .idCarrera(idCarrera)
                .carrera(carrera)
                .idFacultad(idFacultad)
                .facultad(facultad)
                .idRol(idRol)
                .rol(rol)
                .mensaje("Inicio de sesión exitoso")
                .build();
    }
}
