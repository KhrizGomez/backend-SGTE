// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.EsquemaRolDTO;
// import com.app.backend.services.sistema.EsquemaRolService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/sistema/esquema-roles")
// @RequiredArgsConstructor
// public class EsquemaRolController {

//     private final EsquemaRolService esquemaRolService;

//     @GetMapping("/esquema/{idEsquema}")
//     public ResponseEntity<List<EsquemaRolDTO>> listarPorEsquema(@PathVariable Integer idEsquema) { return ResponseEntity.ok(esquemaRolService.listarPorEsquema(idEsquema)); }

//     @PostMapping
//     public ResponseEntity<EsquemaRolDTO> crear(@RequestBody EsquemaRolDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(esquemaRolService.crear(dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { esquemaRolService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
