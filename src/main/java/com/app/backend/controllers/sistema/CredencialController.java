package com.app.backend.controllers.sistema;

import com.app.backend.dtos.sistema.CredencialDTO;
import com.app.backend.services.sistema.CredencialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistema/credenciales")
@RequiredArgsConstructor
public class CredencialController {

    private final CredencialService credencialService;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CredencialDTO> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(credencialService.obtenerPorUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<CredencialDTO> guardar(@RequestBody CredencialDTO dto) {
        return ResponseEntity.ok(credencialService.guardar(dto));
    }
}
