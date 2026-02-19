package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.DefinicionFlujoDTO;
import com.app.backend.services.tramites.DefinicionFlujoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites/flujos")
@RequiredArgsConstructor
public class DefinicionFlujoController {

    private final DefinicionFlujoService definicionFlujoService;

    @GetMapping
    public ResponseEntity<List<DefinicionFlujoDTO>> listarTodos() { return ResponseEntity.ok(definicionFlujoService.listarTodos()); }

    @GetMapping("/activos")
    public ResponseEntity<List<DefinicionFlujoDTO>> listarActivos() { return ResponseEntity.ok(definicionFlujoService.listarActivos()); }

    @GetMapping("/{id}")
    public ResponseEntity<DefinicionFlujoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(definicionFlujoService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<DefinicionFlujoDTO> crear(@RequestBody DefinicionFlujoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(definicionFlujoService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<DefinicionFlujoDTO> actualizar(@PathVariable Integer id, @RequestBody DefinicionFlujoDTO dto) { return ResponseEntity.ok(definicionFlujoService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { definicionFlujoService.eliminar(id); return ResponseEntity.noContent().build(); }
}
