// package com.app.backend.controllers.tramites;

// import com.app.backend.dtos.tramites.PlazoTramiteDTO;
// import com.app.backend.services.tramites.PlazoTramiteService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/tramites/plazos")
// @RequiredArgsConstructor
// public class PlazoTramiteController {

//     private final PlazoTramiteService plazoTramiteService;

//     @GetMapping("/tipo-tramite/{idTipoTramite}")
//     public ResponseEntity<List<PlazoTramiteDTO>> listarPorTipoTramite(@PathVariable Integer idTipoTramite) { return ResponseEntity.ok(plazoTramiteService.listarPorTipoTramite(idTipoTramite)); }

//     @GetMapping("/{id}")
//     public ResponseEntity<PlazoTramiteDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(plazoTramiteService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<PlazoTramiteDTO> crear(@RequestBody PlazoTramiteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(plazoTramiteService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<PlazoTramiteDTO> actualizar(@PathVariable Integer id, @RequestBody PlazoTramiteDTO dto) { return ResponseEntity.ok(plazoTramiteService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { plazoTramiteService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
