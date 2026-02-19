package com.app.backend.controllers.academico;

import com.app.backend.dtos.academico.CarreraDTO;
import com.app.backend.services.academico.CarreraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academico/carreras")
@RequiredArgsConstructor
public class CarreraController {

    private final CarreraService carreraService;

    @GetMapping
    public ResponseEntity<List<CarreraDTO>> listarTodas() { return ResponseEntity.ok(carreraService.listarTodas()); }

    @GetMapping("/facultad/{idFacultad}")
    public ResponseEntity<List<CarreraDTO>> listarPorFacultad(@PathVariable Integer idFacultad) { return ResponseEntity.ok(carreraService.listarPorFacultad(idFacultad)); }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(carreraService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<CarreraDTO> crear(@RequestBody CarreraDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraDTO> actualizar(@PathVariable Integer id, @RequestBody CarreraDTO dto) { return ResponseEntity.ok(carreraService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { carreraService.eliminar(id); return ResponseEntity.noContent().build(); }
}
