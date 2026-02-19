package com.app.backend.controllers.academico;

import com.app.backend.dtos.academico.CoordinadorDTO;
import com.app.backend.services.academico.CoordinadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academico/coordinadores")
@RequiredArgsConstructor
public class CoordinadorController {

    private final CoordinadorService coordinadorService;

    @GetMapping
    public ResponseEntity<List<CoordinadorDTO>> listarTodos() { return ResponseEntity.ok(coordinadorService.listarTodos()); }

    @GetMapping("/carrera/{idCarrera}")
    public ResponseEntity<List<CoordinadorDTO>> listarPorCarrera(@PathVariable Integer idCarrera) { return ResponseEntity.ok(coordinadorService.listarPorCarrera(idCarrera)); }

    @GetMapping("/activos")
    public ResponseEntity<List<CoordinadorDTO>> listarActivos() { return ResponseEntity.ok(coordinadorService.listarActivos()); }

    @GetMapping("/{id}")
    public ResponseEntity<CoordinadorDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(coordinadorService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<CoordinadorDTO> crear(@RequestBody CoordinadorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(coordinadorService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<CoordinadorDTO> actualizar(@PathVariable Integer id, @RequestBody CoordinadorDTO dto) { return ResponseEntity.ok(coordinadorService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { coordinadorService.eliminar(id); return ResponseEntity.noContent().build(); }
}
