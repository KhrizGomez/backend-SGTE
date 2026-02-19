package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.TransicionFlujoDTO;
import com.app.backend.services.tramites.TransicionFlujoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites/transiciones")
@RequiredArgsConstructor
public class TransicionFlujoController {

    private final TransicionFlujoService transicionFlujoService;

    @GetMapping("/flujo/{idFlujo}")
    public ResponseEntity<List<TransicionFlujoDTO>> listarPorFlujo(@PathVariable Integer idFlujo) { return ResponseEntity.ok(transicionFlujoService.listarPorFlujo(idFlujo)); }

    @PostMapping
    public ResponseEntity<TransicionFlujoDTO> crear(@RequestBody TransicionFlujoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(transicionFlujoService.crear(dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { transicionFlujoService.eliminar(id); return ResponseEntity.noContent().build(); }
}
