package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.MotivoRechazoDTO;
import com.app.backend.services.tramites.MotivoRechazoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites/motivos-rechazo")
@RequiredArgsConstructor
public class MotivoRechazoController {

    private final MotivoRechazoService motivoRechazoService;

    @GetMapping
    public ResponseEntity<List<MotivoRechazoDTO>> listarTodos() { return ResponseEntity.ok(motivoRechazoService.listarTodos()); }

    @GetMapping("/activos")
    public ResponseEntity<List<MotivoRechazoDTO>> listarActivos() { return ResponseEntity.ok(motivoRechazoService.listarActivos()); }

    @GetMapping("/{id}")
    public ResponseEntity<MotivoRechazoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(motivoRechazoService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<MotivoRechazoDTO> crear(@RequestBody MotivoRechazoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(motivoRechazoService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<MotivoRechazoDTO> actualizar(@PathVariable Integer id, @RequestBody MotivoRechazoDTO dto) { return ResponseEntity.ok(motivoRechazoService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { motivoRechazoService.eliminar(id); return ResponseEntity.noContent().build(); }
}
