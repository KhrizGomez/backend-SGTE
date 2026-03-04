// package com.app.backend.controllers.academico;

// import com.app.backend.dtos.academico.DecanoDTO;
// import com.app.backend.services.academico.DecanoService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/academico/decanos")
// @RequiredArgsConstructor
// public class DecanoController {

//     private final DecanoService decanoService;

//     @GetMapping
//     public ResponseEntity<List<DecanoDTO>> listarTodos() { return ResponseEntity.ok(decanoService.listarTodos()); }

//     @GetMapping("/activos")
//     public ResponseEntity<List<DecanoDTO>> listarActivos() { return ResponseEntity.ok(decanoService.listarActivos()); }

//     @GetMapping("/{id}")
//     public ResponseEntity<DecanoDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(decanoService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<DecanoDTO> crear(@RequestBody DecanoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(decanoService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<DecanoDTO> actualizar(@PathVariable Integer id, @RequestBody DecanoDTO dto) { return ResponseEntity.ok(decanoService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { decanoService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
