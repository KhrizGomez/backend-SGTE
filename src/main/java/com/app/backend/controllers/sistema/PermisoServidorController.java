// package com.app.backend.controllers.sistema;

// import com.app.backend.dtos.sistema.PermisoServidorDTO;
// import com.app.backend.services.sistema.PermisoServidorService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/sistema/permisos-servidor")
// @RequiredArgsConstructor
// public class PermisoServidorController {

//     private final PermisoServidorService permisoServidorService;

//     @GetMapping("/rol/{idRolSrv}")
//     public ResponseEntity<List<PermisoServidorDTO>> listarPorRol(@PathVariable Integer idRolSrv) { return ResponseEntity.ok(permisoServidorService.listarPorRol(idRolSrv)); }

//     @PostMapping
//     public ResponseEntity<PermisoServidorDTO> crear(@RequestBody PermisoServidorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(permisoServidorService.crear(dto)); }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> eliminar(@PathVariable Integer id) { permisoServidorService.eliminar(id); return ResponseEntity.noContent().build(); }
// }
