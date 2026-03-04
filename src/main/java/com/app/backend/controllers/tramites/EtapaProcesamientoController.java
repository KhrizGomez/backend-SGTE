// package com.app.backend.controllers.tramites;

// import com.app.backend.dtos.tramites.EtapaProcesamientoDTO;
// import com.app.backend.services.tramites.EtapaProcesamientoService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/tramites/etapas")
// @RequiredArgsConstructor
// public class EtapaProcesamientoController {

//     private final EtapaProcesamientoService etapaProcesamientoService;

//     @GetMapping
//     public ResponseEntity<List<EtapaProcesamientoDTO>> listarTodas() { return ResponseEntity.ok(etapaProcesamientoService.listarTodas()); }

//     @GetMapping("/{id}")
//     public ResponseEntity<EtapaProcesamientoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(etapaProcesamientoService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<EtapaProcesamientoDTO> crear(@RequestBody EtapaProcesamientoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(etapaProcesamientoService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<EtapaProcesamientoDTO> actualizar(@PathVariable Integer id, @RequestBody EtapaProcesamientoDTO dto) { return ResponseEntity.ok(etapaProcesamientoService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { etapaProcesamientoService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
