// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.EsquemaDTO;
// import com.app.backend.services.sistema.EsquemaService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/sistema/esquemas")
// @RequiredArgsConstructor
// public class EsquemaController {

//     private final EsquemaService esquemaService;

//     @GetMapping
//     public ResponseEntity<List<EsquemaDTO>> listarTodos() { return ResponseEntity.ok(esquemaService.listarTodos()); }

//     @GetMapping("/{id}")
//     public ResponseEntity<EsquemaDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(esquemaService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<EsquemaDTO> crear(@RequestBody EsquemaDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(esquemaService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<EsquemaDTO> actualizar(@PathVariable Integer id, @RequestBody EsquemaDTO dto) { return ResponseEntity.ok(esquemaService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { esquemaService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
