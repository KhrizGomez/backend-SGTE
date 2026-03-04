// package com.app.backend.controllers.tramites;

// import com.app.backend.dtos.tramites.SolicitudDTO;
// import com.app.backend.services.tramites.SolicitudService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/tramites/solicitudes")
// @RequiredArgsConstructor
// public class SolicitudController {

//     private final SolicitudService solicitudService;

//     @GetMapping
//     public ResponseEntity<List<SolicitudDTO>> listarTodas() { return ResponseEntity.ok(solicitudService.listarTodas()); }

//     @GetMapping("/usuario/{idUsuario}")
//     public ResponseEntity<List<SolicitudDTO>> listarPorUsuario(@PathVariable Integer idUsuario) { return ResponseEntity.ok(solicitudService.listarPorUsuario(idUsuario)); }

//     @GetMapping("/estado/{estado}")
//     public ResponseEntity<List<SolicitudDTO>> listarPorEstado(@PathVariable String estado) { return ResponseEntity.ok(solicitudService.listarPorEstado(estado)); }

//     @GetMapping("/{id}")
//     public ResponseEntity<SolicitudDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(solicitudService.obtenerPorId(id)); }

//     @GetMapping("/codigo/{codigo}")
//     public ResponseEntity<SolicitudDTO> obtenerPorCodigo(@PathVariable String codigo) { return ResponseEntity.ok(solicitudService.obtenerPorCodigo(codigo)); }

//     @PostMapping
//     public ResponseEntity<SolicitudDTO> crear(@RequestBody SolicitudDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<SolicitudDTO> actualizar(@PathVariable Integer id, @RequestBody SolicitudDTO dto) { return ResponseEntity.ok(solicitudService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { solicitudService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
