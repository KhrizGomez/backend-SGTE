// package com.app.backend.controllers.tramites;

// import com.app.backend.dtos.tramites.SeguimientoSolicitudDTO;
// import com.app.backend.services.tramites.SeguimientoSolicitudService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/tramites/seguimientos")
// @RequiredArgsConstructor
// public class SeguimientoSolicitudController {

//     private final SeguimientoSolicitudService seguimientoSolicitudService;

//     @GetMapping("/solicitud/{idSolicitud}")
//     public ResponseEntity<List<SeguimientoSolicitudDTO>> listarPorSolicitud(@PathVariable Integer idSolicitud) { return ResponseEntity.ok(seguimientoSolicitudService.listarPorSolicitud(idSolicitud)); }

//     @PostMapping
//     public ResponseEntity<SeguimientoSolicitudDTO> crear(@RequestBody SeguimientoSolicitudDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoSolicitudService.crear(dto)); }
// }
