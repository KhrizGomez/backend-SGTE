// package com.app.backend.controllers.documentos;

// import com.app.backend.dtos.documentos.DocumentoAdjuntoDTO;
// import com.app.backend.services.documentos.DocumentoAdjuntoService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/documentos/adjuntos")
// @RequiredArgsConstructor
// public class DocumentoAdjuntoController {

//     private final DocumentoAdjuntoService documentoAdjuntoService;

//     @GetMapping("/solicitud/{idSolicitud}")
//     public ResponseEntity<List<DocumentoAdjuntoDTO>> listarPorSolicitud(@PathVariable Integer idSolicitud) { return ResponseEntity.ok(documentoAdjuntoService.listarPorSolicitud(idSolicitud)); }

//     @GetMapping("/{id}")
//     public ResponseEntity<DocumentoAdjuntoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(documentoAdjuntoService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<DocumentoAdjuntoDTO> crear(@RequestBody DocumentoAdjuntoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(documentoAdjuntoService.crear(dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { documentoAdjuntoService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
