package com.app.backend.entities.sistema;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permisos_servidor", schema = "sistema",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_permiso",
        columnNames = {"id_rol_srv", "tipo_objeto", "nombre_objeto", "privilegio"}
    ))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PermisoServidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso_srv")
    private Integer idPermisoSrv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol_srv", nullable = false)
    private RolServidor rolServidor;

    @Column(name = "tipo_objeto", nullable = false, length = 50)
    private String tipoObjeto;

    @Column(name = "nombre_objeto", length = 255)
    private String nombreObjeto;

    @Column(name = "privilegio", nullable = false, length = 50)
    private String privilegio;
}
