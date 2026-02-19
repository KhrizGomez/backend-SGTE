package com.app.backend.entities.sistema;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "roles_servidor", schema = "sistema")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RolServidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_srv")
    private Integer idRolSrv;

    @Column(name = "nombre_rol_db", nullable = false, unique = true, length = 63)
    private String nombreRolDb;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "esta_activo")
    @Builder.Default
    private Boolean estaActivo = true;

    @OneToMany(mappedBy = "rolServidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PermisoServidor> permisos;

    @ManyToMany(mappedBy = "rolesServidor")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "rolServidor")
    private List<EsquemaRol> esquemasRoles;
}
