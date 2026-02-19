package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.RequisitoTramiteDTO;
import com.app.backend.services.tramites.RequisitoTramiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites/requisitos")
@RequiredArgsConstructor
public class RequisitoTramiteController {

    private final RequisitoTramiteService requisitoTramiteService;

    @GetMapping("/tipo-tramite/{idTipoTramite}")
    public ResponseEntity<List<RequisitoTramiteDTO>> listarPorTipoTramite(@PathVariable Integer idTipoTramite) { return ResponseEntity.ok(requisitoTramiteService.listarPorTipoTramite(idTipoTramite)); }

    @GetMapping("/{id}")
    public ResponseEntity<RequisitoTramiteDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(requisitoTramiteService.obtenerPorId(id)); }

    @PostMapping
    public ResponseEntity<RequisitoTramiteDTO> crear(@RequestBody RequisitoTramiteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(requisitoTramiteService.crear(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<RequisitoTramiteDTO> actualizar(@PathVariable Integer id, @RequestBody RequisitoTramiteDTO dto) { return ResponseEntity.ok(requisitoTramiteService.actualizar(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) { requisitoTramiteService.eliminar(id); return ResponseEntity.noContent().build(); }
}
