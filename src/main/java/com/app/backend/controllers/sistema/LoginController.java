package com.app.backend.controllers.sistema;

import com.app.backend.dtos.sistema.LoginRequestDTO;
import com.app.backend.dtos.sistema.LoginRespuestaDTO;
import com.app.backend.services.sistema.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistema/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginRespuestaDTO> login(@RequestBody LoginRequestDTO request) {
        LoginRespuestaDTO respuesta = loginService.login(request);
        return ResponseEntity.ok(respuesta);
    }
}
