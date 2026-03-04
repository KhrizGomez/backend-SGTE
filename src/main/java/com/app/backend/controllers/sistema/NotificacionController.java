// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.NotificacionDTO;
// import com.app.backend.services.sistema.NotificacionService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/sistema/notificaciones")
// @RequiredArgsConstructor
// public class NotificacionController {

//     private final NotificacionService notificacionService;

//     @GetMapping("/usuario/{idUsuario}")
//     public ResponseEntity<List<NotificacionDTO>> listarPorUsuario(@PathVariable Integer idUsuario) { return ResponseEntity.ok(notificacionService.listarPorUsuario(idUsuario)); }

//     @GetMapping("/usuario/{idUsuario}/no-leidas")
//     public ResponseEntity<List<NotificacionDTO>> listarNoLeidasPorUsuario(@PathVariable Integer idUsuario) { return ResponseEntity.ok(notificacionService.listarNoLeidasPorUsuario(idUsuario)); }

//     @GetMapping("/{id}")
//     public ResponseEntity<NotificacionDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(notificacionService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<NotificacionDTO> crear(@RequestBody NotificacionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(dto)); }

//     @PatchMapping("/{id}/leida")
//     public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Integer id) { return ResponseEntity.ok(notificacionService.marcarComoLeida(id)); }
// }
