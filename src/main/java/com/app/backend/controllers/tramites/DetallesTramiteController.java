package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.DetallesTramiteDTO;
import com.app.backend.services.tramites.DetallesTramiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tramites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DetallesTramiteController {

    private final DetallesTramiteService detallesTramiteService;

    @GetMapping("/detalles")
    public ResponseEntity<List<DetallesTramiteDTO>> listarTodos() {
        return ResponseEntity.ok(detallesTramiteService.listarTodos());
    }

    @GetMapping("/detalles/{idCategoria}")
    public ResponseEntity<DetallesTramiteDTO> obtenerPorTipoTramite(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(detallesTramiteService.obtenerPorTipoTramite(idCategoria));
    }
}