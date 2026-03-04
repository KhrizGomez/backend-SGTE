package com.app.backend.controllers.sistema;

import com.app.backend.dtos.sistema.StudentDashboardDTO;
import com.app.backend.services.sistema.IStudentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistema/dashboard-estudiante")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Ajustar según necesidad de CORS
public class StudentDashboardController {

    private final IStudentDashboardService dashboardService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<StudentDashboardDTO> obtenerDashboard(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(dashboardService.obtenerDashboard(idUsuario));
    }
}
