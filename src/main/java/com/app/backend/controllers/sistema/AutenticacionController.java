package com.app.backend.controllers.sistema;

import com.app.backend.dtos.sistema.AutenticacionRequestDTO;
import com.app.backend.dtos.sistema.AutenticacionRespuestaDTO;
import com.app.backend.services.sistema.AutenticacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistema/autenticacion")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AutenticacionController {

    private final AutenticacionService autenticacionService;
    
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<AutenticacionRespuestaDTO> iniciarSesion(@RequestBody AutenticacionRequestDTO peticion) {
        AutenticacionRespuestaDTO respuesta = autenticacionService.iniciarSesion(peticion);
        return ResponseEntity.ok(respuesta);
    }
}
