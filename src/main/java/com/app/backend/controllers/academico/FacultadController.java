// package com.app.backend.controllers.academico;

// import com.app.backend.dtos.academico.FacultadDTO;
// import com.app.backend.services.academico.FacultadService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/academico/facultades")
// @RequiredArgsConstructor
// public class FacultadController {

//     private final FacultadService facultadService;

//     @GetMapping
//     public ResponseEntity<List<FacultadDTO>> listarTodas() { return ResponseEntity.ok(facultadService.listarTodas()); }

//     @GetMapping("/universidad/{idUniversidad}")
//     public ResponseEntity<List<FacultadDTO>> listarPorUniversidad(@PathVariable Integer idUniversidad) { return ResponseEntity.ok(facultadService.listarPorUniversidad(idUniversidad)); }

//     @GetMapping("/{id}")
//     public ResponseEntity<FacultadDTO> obtenerPorId(@PathVariable Integer id) { return ResponseEntity.ok(facultadService.obtenerPorId(id)); }

//     @PostMapping
//     public ResponseEntity<FacultadDTO> crear(@RequestBody FacultadDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(facultadService.crear(dto)); }

//     @PutMapping("/{id}")
//     public ResponseEntity<FacultadDTO> actualizar(@PathVariable Integer id, @RequestBody FacultadDTO dto) { return ResponseEntity.ok(facultadService.actualizar(id, dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { facultadService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
