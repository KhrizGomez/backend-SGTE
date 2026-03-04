// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.ConfiguracionUsuarioDTO;
// import com.app.backend.services.sistema.ConfiguracionUsuarioService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/sistema/configuraciones-usuario")
// @RequiredArgsConstructor
// public class ConfiguracionUsuarioController {

//     private final ConfiguracionUsuarioService configuracionUsuarioService;

//     @GetMapping("/usuario/{idUsuario}")
//     public ResponseEntity<ConfiguracionUsuarioDTO> obtenerPorUsuario(@PathVariable Integer idUsuario) {
//         return ResponseEntity.ok(configuracionUsuarioService.obtenerPorUsuario(idUsuario));
//     }

//     @PostMapping
//     public ResponseEntity<ConfiguracionUsuarioDTO> guardar(@RequestBody ConfiguracionUsuarioDTO dto) {
//         return ResponseEntity.ok(configuracionUsuarioService.guardar(dto));
//     }
// }
