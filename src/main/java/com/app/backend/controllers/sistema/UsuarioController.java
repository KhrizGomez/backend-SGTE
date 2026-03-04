// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.UsuarioDTO;
// import com.app.backend.services.sistema.UsuarioService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/sistema/usuarios")
// @RequiredArgsConstructor
// public class UsuarioController {

//     private final UsuarioService usuarioService;

//     @GetMapping
//     public ResponseEntity<List<UsuarioDTO>> listarTodos() {
//         return ResponseEntity.ok(usuarioService.listarTodos());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Integer id) {
//         return ResponseEntity.ok(usuarioService.obtenerPorId(id));
//     }

//     @GetMapping("/cedula/{cedula}")
//     public ResponseEntity<UsuarioDTO> obtenerPorCedula(@PathVariable String cedula) {
//         return ResponseEntity.ok(usuarioService.obtenerPorCedula(cedula));
//     }

//     @PostMapping
//     public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO dto) {
//         return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(dto));
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
//         return ResponseEntity.ok(usuarioService.actualizar(id, dto));
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
//         usuarioService.eliminar(id);
//         return ResponseEntity.noContent().build();
//     }
// }
