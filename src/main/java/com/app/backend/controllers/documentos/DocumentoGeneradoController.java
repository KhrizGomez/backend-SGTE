package com.app.backend.controllers.documentos;

import com.app.backend.dtos.documentos.DocumentoGeneradoDTO;
import com.app.backend.services.documentos.DocumentoGeneradoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos/generados")
@RequiredArgsConstructor
public class DocumentoGeneradoController {

    private final DocumentoGeneradoService documentoGeneradoService;

    @GetMapping("/solicitud/{idSolicitud}")
    public ResponseEntity<List<DocumentoGeneradoDTO>> listarPorSolicitud(@PathVariable Integer idSolicitud) { return ResponseEntity.ok(documentoGeneradoService.listarPorSolicitud(idSolicitud)); }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoGeneradoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(documentoGeneradoService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<DocumentoGeneradoDTO> crear(@RequestBody DocumentoGeneradoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(documentoGeneradoService.crear(dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { documentoGeneradoService.eliminar(id); return ResponseEntity.noContent().build(); }
}
