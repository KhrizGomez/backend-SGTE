// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.TipoNotificacionDTO;
// import com.app.backend.services.sistema.TipoNotificacionService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/sistema/tipos-notificacion")
// @RequiredArgsConstructor
// public class TipoNotificacionController {

//     private final TipoNotificacionService tipoNotificacionService;

//     @GetMapping
//     public ResponseEntity<List<TipoNotificacionDTO>> listarTodos() { return ResponseEntity.ok(tipoNotificacionService.listarTodos()); }

//     @GetMapping("/{id}")
//     public ResponseEntity<TipoNotificacionDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(tipoNotificacionService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<TipoNotificacionDTO> crear(@RequestBody TipoNotificacionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(tipoNotificacionService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<TipoNotificacionDTO> actualizar(@PathVariable Integer id, @RequestBody TipoNotificacionDTO dto) { return ResponseEntity.ok(tipoNotificacionService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { tipoNotificacionService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
