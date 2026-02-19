package com.app.backend.controllers.academico;

import com.app.backend.dtos.academico.SemestreDTO;
import com.app.backend.services.academico.SemestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academico/semestres")
@RequiredArgsConstructor
public class SemestreController {

    private final SemestreService semestreService;

    @GetMapping
    public ResponseEntity<List<SemestreDTO>> listarTodos() { return ResponseEntity.ok(semestreService.listarTodos()); }

    @GetMapping("/activo")
    public ResponseEntity<SemestreDTO> obtenerActivo() { return ResponseEntity.ok(semestreService.obtenerActivo()); }

    @GetMapping("/{id}")
    public ResponseEntity<SemestreDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(semestreService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<SemestreDTO> crear(@RequestBody SemestreDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(semestreService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<SemestreDTO> actualizar(@PathVariable Integer id, @RequestBody SemestreDTO dto) { return ResponseEntity.ok(semestreService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { semestreService.eliminar(id); return ResponseEntity.noContent().build(); }
}
