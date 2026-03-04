// package com.app.backend.controllers.tramites;

// import com.app.backend.dtos.tramites.RechazoSolicitudDTO;
// import com.app.backend.services.tramites.RechazoSolicitudService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/tramites/rechazos")
// @RequiredArgsConstructor
// public class RechazoSolicitudController {

//     private final RechazoSolicitudService rechazoSolicitudService;

//     @GetMapping("/solicitud/{idSolicitud}")
//     public ResponseEntity<List<RechazoSolicitudDTO>> listarPorSolicitud(@PathVariable Integer idSolicitud) { return ResponseEntity.ok(rechazoSolicitudService.listarPorSolicitud(idSolicitud)); }

//     @PostMapping
//     public ResponseEntity<RechazoSolicitudDTO> crear(@RequestBody RechazoSolicitudDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(rechazoSolicitudService.crear(dto)); }
// }
