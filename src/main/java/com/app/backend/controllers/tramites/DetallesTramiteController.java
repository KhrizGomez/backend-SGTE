package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.DetallesTramiteDTO;
import com.app.backend.dtos.tramites.response.PlantillaTramiteResponseDTO;
import com.app.backend.services.tramites.DetallesTramiteService;
import com.app.backend.services.tramites.TipoTramiteService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tramites")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DetallesTramiteController {

    private final DetallesTramiteService detallesTramiteService;
    private final TipoTramiteService tipoTramiteService;

    @GetMapping("/detalles/carrera/{idCarrera}")
    public ResponseEntity<List<DetallesTramiteDTO>> listarPorCarrera(@PathVariable Integer idCarrera) {
        return ResponseEntity.ok(detallesTramiteService.listarPorCarrera(idCarrera));
    }

    @GetMapping("/plantillas")
    public ResponseEntity<List<PlantillaTramiteResponseDTO>> listarPlantillasTramites(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String busqueda) {
        return ResponseEntity.ok(tipoTramiteService.listarPlantillasTramites(categoria, activo, busqueda));
    }

    @GetMapping("/detalles/plantilla")
    public ResponseEntity<DetallesTramiteDTO> obtenerPorTipoTramite(@RequestParam Integer idPlantilla) {
        return ResponseEntity.ok(detallesTramiteService.obtenerPorTipoTramite(idPlantilla));
    }
}