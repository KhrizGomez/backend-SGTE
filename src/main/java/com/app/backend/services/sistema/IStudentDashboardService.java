package com.app.backend.services.sistema;

import com.app.backend.dtos.sistema.StudentDashboardDTO;

public interface IStudentDashboardService {
    StudentDashboardDTO obtenerDashboard(Integer idUsuario);
}
