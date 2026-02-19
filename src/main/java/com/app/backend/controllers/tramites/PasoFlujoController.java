package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.PasoFlujoDTO;
import com.app.backend.services.tramites.PasoFlujoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites/pasos-flujo")
@RequiredArgsConstructor
public class PasoFlujoController {

    private final PasoFlujoService pasoFlujoService;

    @GetMapping("/flujo/{idFlujo}")
    public ResponseEntity<List<PasoFlujoDTO>> listarPorFlujo(@PathVariable Integer idFlujo) { return ResponseEntity.ok(pasoFlujoService.listarPorFlujo(idFlujo)); }

    @GetMapping("/{id}")
    public ResponseEntity<PasoFlujoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(pasoFlujoService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<PasoFlujoDTO> crear(@RequestBody PasoFlujoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(pasoFlujoService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<PasoFlujoDTO> actualizar(@PathVariable Integer id, @RequestBody PasoFlujoDTO dto) { return ResponseEntity.ok(pasoFlujoService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { pasoFlujoService.eliminar(id); return ResponseEntity.noContent().build(); }
}
