package com.app.backend.controllers.academico;

import com.app.backend.dtos.academico.EstudianteDTO;
import com.app.backend.services.academico.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academico/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listarTodos() { return ResponseEntity.ok(estudianteService.listarTodos()); }

    @GetMapping("/carrera/{idCarrera}")
    public ResponseEntity<List<EstudianteDTO>> listarPorCarrera(@PathVariable Integer idCarrera) { return ResponseEntity.ok(estudianteService.listarPorCarrera(idCarrera)); }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(estudianteService.obtenerPorId(id)); }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<EstudianteDTO> obtenerPorUsuario(@PathVariable Integer idUsuario) { return ResponseEntity.ok(estudianteService.obtenerPorUsuario(idUsuario)); }

    @PostMapping
    public ResponseEntity<EstudianteDTO> crear(@RequestBody EstudianteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizar(@PathVariable Integer id, @RequestBody EstudianteDTO dto) { return ResponseEntity.ok(estudianteService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { estudianteService.eliminar(id); return ResponseEntity.noContent().build(); }
}
