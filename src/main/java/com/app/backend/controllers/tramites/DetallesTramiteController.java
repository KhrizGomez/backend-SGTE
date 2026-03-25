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
@CrossOrigin(origins = "http://localhost:4200")
public class DetallesTramiteController {

    private final DetallesTramiteService detallesTramiteService;

    @GetMapping("/detalles/carrera/{idCarrera}")
    public ResponseEntity<List<DetallesTramiteDTO>> listarPorCarrera(@PathVariable Integer idCarrera) {
        return ResponseEntity.ok(detallesTramiteService.listarPorCarrera(idCarrera));
    }
}