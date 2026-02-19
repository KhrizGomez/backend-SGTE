package com.app.backend.entities.academico;

import com.app.backend.entities.sistema.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estudiantes", schema = "academico")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_semestre")
    private Semestre semestre;

    @Column(name = "paralelo", length = 1)
    private String paralelo;

    @Column(name = "estado_academico", length = 50)
    @Builder.Default
    private String estadoAcademico = "Regular";

    @Column(name = "fecha_matricula")
    private LocalDate fechaMatricula;

    @Column(name = "es_externo")
    @Builder.Default
    private Boolean esExterno = false;

    @Column(name = "id_estudiante_sga")
    private Integer idEstudianteSga;

    @Column(name = "ultima_sincronizacion")
    private LocalDateTime ultimaSincronizacion;
}
