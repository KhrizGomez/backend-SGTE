package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.CategoriaTramiteDTO;
import com.app.backend.services.tramites.CategoriaTramiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites/categorias")
@RequiredArgsConstructor
public class CategoriaTramiteController {

    private final CategoriaTramiteService categoriaTramiteService;

    @GetMapping
    public ResponseEntity<List<CategoriaTramiteDTO>> listarTodas() { return ResponseEntity.ok(categoriaTramiteService.listarTodas()); }

    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaTramiteDTO>> listarActivas() { return ResponseEntity.ok(categoriaTramiteService.listarActivas()); }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaTramiteDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(categoriaTramiteService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<CategoriaTramiteDTO> crear(@RequestBody CategoriaTramiteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(categoriaTramiteService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaTramiteDTO> actualizar(@PathVariable Integer id, @RequestBody CategoriaTramiteDTO dto) { return ResponseEntity.ok(categoriaTramiteService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { categoriaTramiteService.eliminar(id); return ResponseEntity.noContent().build(); }
}
