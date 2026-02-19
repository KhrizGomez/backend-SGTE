package com.app.backend.controllers.sistema;

import com.app.backend.dtos.sistema.RolServidorDTO;
import com.app.backend.services.sistema.RolServidorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sistema/roles-servidor")
@RequiredArgsConstructor
public class RolServidorController {

    private final RolServidorService rolServidorService;

    @GetMapping
    public ResponseEntity<List<RolServidorDTO>> listarTodos() { return ResponseEntity.ok(rolServidorService.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<RolServidorDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(rolServidorService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<RolServidorDTO> crear(@RequestBody RolServidorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(rolServidorService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<RolServidorDTO> actualizar(@PathVariable Integer id, @RequestBody RolServidorDTO dto) { return ResponseEntity.ok(rolServidorService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { rolServidorService.eliminar(id); return ResponseEntity.noContent().build(); }
}
