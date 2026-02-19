package com.app.backend.controllers.academico;

import com.app.backend.dtos.academico.UniversidadDTO;
import com.app.backend.services.academico.UniversidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academico/universidades")
@RequiredArgsConstructor
public class UniversidadController {

    private final UniversidadService universidadService;

    @GetMapping
    public ResponseEntity<List<UniversidadDTO>> listarTodas() { return ResponseEntity.ok(universidadService.listarTodas()); }

    @GetMapping("/{id}")
    public ResponseEntity<UniversidadDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(universidadService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<UniversidadDTO> crear(@RequestBody UniversidadDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(universidadService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<UniversidadDTO> actualizar(@PathVariable Integer id, @RequestBody UniversidadDTO dto) { return ResponseEntity.ok(universidadService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { universidadService.eliminar(id); return ResponseEntity.noContent().build(); }
}
