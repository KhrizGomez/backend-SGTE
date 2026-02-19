package com.app.backend.dtos.academico;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteDTO {
    private Integer idEstudiante;
    private Integer idUsuario;
    private Integer idCarrera;
    private Integer idSemestre;
    private String paralelo;
    private String estadoAcademico;
    private LocalDate fechaMatricula;
    private Boolean esExterno;
    private Integer idEstudianteSga;
    private LocalDateTime ultimaSincronizacion;
}
