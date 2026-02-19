package com.app.backend.entities.sistema;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "esquemas_roles", schema = "sistema",
    uniqueConstraints = @UniqueConstraint(
        name = "esquemas_roles_uq",
        columnNames = {"id_esquema", "id_rol_srv"}
    ))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EsquemaRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_esquema_rol")
    private Integer idEsquemaRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_esquema", nullable = false)
    private Esquema esquema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol_srv", nullable = false)
    private RolServidor rolServidor;

    @Column(name = "nivel_acceso", nullable = false, length = 20)
    @Builder.Default
    private String nivelAcceso = "lectura";

    @Column(name = "puede_crear_objetos")
    @Builder.Default
    private Boolean puedeCrearObjetos = false;

    @Column(name = "puede_eliminar")
    @Builder.Default
    private Boolean puedeEliminar = false;

    @Column(name = "esta_activo")
    @Builder.Default
    private Boolean estaActivo = true;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;
}
