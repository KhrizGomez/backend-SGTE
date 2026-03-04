// package com.app.backend.controllers.tramites;

// import com.app.backend.dtos.tramites.TipoTramiteDTO;
// import com.app.backend.services.tramites.TipoTramiteService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/tramites/tipos")
// @RequiredArgsConstructor
// public class TipoTramiteController {

//     private final TipoTramiteService tipoTramiteService;

//     @GetMapping
//     public ResponseEntity<List<TipoTramiteDTO>> listarTodos() { return ResponseEntity.ok(tipoTramiteService.listarTodos()); }

//     @GetMapping("/activos")
//     public ResponseEntity<List<TipoTramiteDTO>> listarActivos() { return ResponseEntity.ok(tipoTramiteService.listarActivos()); }

//     @GetMapping("/categoria/{idCategoria}")
//     public ResponseEntity<List<TipoTramiteDTO>> listarPorCategoria(@PathVariable Integer idCategoria) { return ResponseEntity.ok(tipoTramiteService.listarPorCategoria(idCategoria)); }

//     @GetMapping("/{id}")
//     public ResponseEntity<TipoTramiteDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(tipoTramiteService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<TipoTramiteDTO> crear(@RequestBody TipoTramiteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(tipoTramiteService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<TipoTramiteDTO> actualizar(@PathVariable Integer id, @RequestBody TipoTramiteDTO dto) { return ResponseEntity.ok(tipoTramiteService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { tipoTramiteService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
